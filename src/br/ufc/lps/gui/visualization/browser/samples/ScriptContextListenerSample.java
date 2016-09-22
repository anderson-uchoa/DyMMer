/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to register and use ScriptContextListener to execute
 * custom JavaScript code before any other JavaScript on the loaded web page is executed.
 * Use this approach for injecting your custom JavaScript code when it's necessary.
 */
public class ScriptContextListenerSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
                Browser browser = event.getBrowser();
                // Access and modify document.title property before any other
                // JavaScript on the loaded web page has been executed.
                JSValue document = browser.executeJavaScriptAndReturnValue("document");
                document.asObject().setProperty("title", "My title");
            }
        });

        browser.loadURL("http://google.com");
    }
}
