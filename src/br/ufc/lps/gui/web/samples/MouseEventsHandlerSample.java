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
import java.awt.event.MouseEvent;

/**
 * This sample demonstrates how to register mouse events handler
 * to handle/suppress mouse wheel events.
 */
public class MouseEventsHandlerSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.setMouseEventsHandler(new InputEventsHandler<MouseEvent>() {
            @Override
            public boolean handle(MouseEvent event) {
                return event.getID() == MouseEvent.MOUSE_WHEEL;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
