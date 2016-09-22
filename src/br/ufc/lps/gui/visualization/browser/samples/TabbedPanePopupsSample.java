/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to display popups in JTabbedPane.
 */
public class TabbedPanePopupsSample {
    public static void main(String[] args) {
        Browser browser = new Browser();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Browser", new BrowserView(browser));

        browser.setPopupHandler(new MyPopupHandler(tabbedPane));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.popuptest.com");
    }

    private static class MyPopupHandler implements PopupHandler {

        private final JTabbedPane tabbedPane;

        private MyPopupHandler(JTabbedPane tabbedPane) {
            this.tabbedPane = tabbedPane;
        }

        @Override
        public PopupContainer handlePopup(PopupParams params) {
            return new PopupContainer() {
                @Override
                public void insertBrowser(final Browser browser, Rectangle initialBounds) {
                    browser.setPopupHandler(new MyPopupHandler(tabbedPane));
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            tabbedPane.add("Popup", new BrowserView(browser));
                        }
                    });
                }
            };
        }
    }
}
