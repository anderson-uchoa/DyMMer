/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.web.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to enable Geolocation API in Chromium engine.
 */
public class GeolocationSample {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumVariable("GOOGLE_API_KEY", "My API Key");
        BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_ID", "My Client ID");
        BrowserPreferences.setChromiumVariable("GOOGLE_DEFAULT_CLIENT_SECRET", "My Client Secret");

        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://html5demos.com/geo");
    }
}
