/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The sample demonstrates how to register MouseListener to Browser component.
 */
public class MouseListenerSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("e = " + e);
            }
        });

        browser.loadURL("http://www.teamdev.com/jxbrowser");
    }
}
