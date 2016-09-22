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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The sample demonstrates how to print a specified frame with
 * the given ID on the currently loaded web page.
 */
public class PrintFrameSample {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final JButton print = new JButton("Print Frame #2");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Long> framesIds = browser.getFramesIds();
                if (framesIds.size() > 2) {
                    browser.print(framesIds.get(2));
                }
            }
        });
        frame.add(print, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("https://docs.oracle.com/javase/7/docs/api/");
    }
}
