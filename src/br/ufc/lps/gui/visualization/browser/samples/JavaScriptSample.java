package br.ufc.lps.gui.visualization.browser.samples;
/*

 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates two techniques to execute JavaScript code
 * on the loaded web page: without return value and with return value.
 */
public class JavaScriptSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Executes the passed JavaScript code asynchronously.
        browser.executeJavaScript("document.write('<html><title>" +
                "My Title</title><body>Hello from JxBrowser!</body></html>');");
    }
}
