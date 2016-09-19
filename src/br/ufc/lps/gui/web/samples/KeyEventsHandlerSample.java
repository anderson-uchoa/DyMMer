/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.web.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.InputEventsHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This sample demonstrates how to register key events handler to handle/suppress
 * Ctrl+A key events (e.g. to prevent text selection).
 */
public class KeyEventsHandlerSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.setKeyEventsHandler(new InputEventsHandler<KeyEvent>() {
            public boolean handle(KeyEvent event) {
                return event.isControlDown() && event.getKeyCode() == KeyEvent.VK_A;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
