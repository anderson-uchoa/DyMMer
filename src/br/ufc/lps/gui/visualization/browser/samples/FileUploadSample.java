/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.FileChooserMode;
import com.teamdev.jxbrowser.chromium.FileChooserParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The sample demonstrates how to register your DialogHandler and
 * override the functionality that displays file chooser when
 * user uploads file using INPUT TYPE="file" HTML element on a web page.
 */
public class FileUploadSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        final BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setDialogHandler(new DefaultDialogHandler(view) {
            @Override
            public CloseStatus onFileChooser(FileChooserParams params) {
                if (params.getMode() == FileChooserMode.Open) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        params.setSelectedFiles(selectedFile.getAbsolutePath());
                        return CloseStatus.OK;
                    }
                }
                return CloseStatus.CANCEL;
            }
        });
        browser.loadURL("http://www.cs.tut.fi/~jkorpela/forms/file.html");
    }
}
