/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.web.samples;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.PrintJob;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The sample demonstrates how to print currently loaded web page in
 * a PDF file with custom print settings.
 */
public class PrintToPDFSample {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        browser.setPrintHandler(new PrintHandler() {
            @Override
            public PrintStatus onPrint(PrintJob printJob) {
                PrintSettings settings = printJob.getPrintSettings();
                settings.setPrintToPDF(true);
                settings.setPDFFilePath("web_page.pdf");
                settings.setPrintBackgrounds(true);
                return PrintStatus.CONTINUE;
            }
        });

        final JButton print = new JButton("Print to PDF");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.print();
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(print, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
