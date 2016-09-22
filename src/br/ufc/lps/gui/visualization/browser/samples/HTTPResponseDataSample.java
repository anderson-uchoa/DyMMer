/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * This sample demonstrates how to capture response body of HTTP request.
 */
public class HTTPResponseDataSample {
    public static void main(final String[] args) {
        LoggerProvider.setLevel(Level.OFF);

        BrowserContext browserContext = BrowserContext.defaultContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onDataReceived(DataReceivedParams params) {
                if (params.getMimeType().equals("text/html")) {
                    String data = new String(params.getData(),
                            Charset.forName("UTF-8"));
                    System.out.println("data = " + data);
                }
            }
        });

        Browser browser = new Browser(browserContext);
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://www.wikipedia.org/");
    }
}
