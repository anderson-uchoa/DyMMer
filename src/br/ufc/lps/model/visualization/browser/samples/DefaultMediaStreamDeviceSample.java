package br.ufc.lps.model.visualization.browser.samples;
/*

 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The sample demonstrates how to get list of available audio and video capture devices,
 * register own MediaStreamDeviceProvider to provide Chromium with default audio/video capture
 * device to be used on a web page for working with webcam and accessing microphone.
 */
public class DefaultMediaStreamDeviceSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final MediaStreamDeviceManager deviceManager = browser.getMediaStreamDeviceManager();
        // Get list of all available audio capture devices (microphones).
        List<MediaStreamDevice> audioCaptureDevices =
                deviceManager.getMediaStreamDevices(MediaStreamType.AUDIO_CAPTURE);
        // Get list of all available video capture devices (webcams).
        List<MediaStreamDevice> videoCaptureDevices =
                deviceManager.getMediaStreamDevices(MediaStreamType.VIDEO_CAPTURE);
        // Register own provider to provide Chromium with default device.
        deviceManager.setMediaStreamDeviceProvider(new MediaStreamDeviceProvider() {
            @Override
            public void onRequestDefaultDevice(MediaStreamDeviceRequest request) {
                // Set first available device as default.
                List<MediaStreamDevice> availableDevices = request.getMediaStreamDevices();
                if (!availableDevices.isEmpty()) {
                    MediaStreamDevice defaultDevice = availableDevices.get(0);
                    request.setDefaultMediaStreamDevice(defaultDevice);
                }
            }
        });

        browser.loadURL("https://alexandre.alapetite.fr/doc-alex/html5-webcam/index.en.html");
    }
}
