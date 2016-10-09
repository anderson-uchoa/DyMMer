/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.model.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The sample demonstrates how to display Select SSL Certificate dialog where
 * user must select required SSL certificate to continue loading web page.
 */
public class SelectSSLCertificateSample {
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
            public CloseStatus onSelectCertificate(CertificatesDialogParams params) {
                String message = "Select a certificate to authenticate yourself to "
                        + params.getHostPortPair().getHostPort();
                List<Certificate> certificates = params.getCertificates();
                if (!certificates.isEmpty()) {
                    Object[] selectionValues = certificates.toArray();
                    Object selectedValue = JOptionPane.showInputDialog(view, message,
                            "Select a certificate", JOptionPane.PLAIN_MESSAGE,
                            null, selectionValues, selectionValues[0]);
                    if (selectedValue != null) {
                        params.setSelectedCertificate((Certificate) selectedValue);
                        return CloseStatus.OK;
                    }
                }
                return CloseStatus.CANCEL;
            }
        });
        browser.loadURL("<URL that causes Select SSL Certificate dialog>");
    }
}
