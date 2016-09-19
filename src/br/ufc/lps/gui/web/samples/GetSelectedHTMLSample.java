/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.web.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The example demonstrates how to get selected HTML
 * on the loaded web page.
 */
public class GetSelectedHTMLSample {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);

        JButton button = new JButton("Get Selected HTML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String html = browser.getSelectedHTML();
                JOptionPane.showMessageDialog(view,
                        html, "Selected HTML", JOptionPane.PLAIN_MESSAGE);
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(button, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://teamdev.com");
    }
}