/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.model.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.FullScreenHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Demonstrates how to switch between fullscreen mode and window mode.
 */
public class FullScreenSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setFullScreenHandler(new MyFullScreenHandler(frame, view));

        browser.loadURL("http://www.quirksmode.org/html5/tests/video.html");
    }

    private static class MyFullScreenHandler implements FullScreenHandler {
        private final JFrame parentFrame;
        private final BrowserView view;
        private final JFrame frame;

        private MyFullScreenHandler(JFrame parentFrame, BrowserView view) {
            this.parentFrame = parentFrame;
            this.view = view;
            this.frame = createFrame();
        }

        private static JFrame createFrame() {
            JFrame frame = new JFrame();
            frame.setUndecorated(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            return frame;
        }

        @Override
        public void onFullScreenEnter() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    parentFrame.getContentPane().remove(view);
                    frame.getContentPane().add(view);
                    frame.setVisible(true);
                    parentFrame.setVisible(false);
                }
            });
        }

        @Override
        public void onFullScreenExit() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    frame.getContentPane().remove(view);
                    parentFrame.getContentPane().add(view);
                    parentFrame.setVisible(true);
                    frame.setVisible(false);
                }
            });
        }
    }
}
