/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.web.samples;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * This sample demonstrates how to read and modify PUT data of
 * HTTP request using NetworkDelegate API.
 */
public class PUTDataSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserContext browserContext = browser.getContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onBeforeURLRequest(BeforeURLRequestParams params) {
                if ("PUT".equals(params.getMethod())) {
                    UploadData uploadData = params.getUploadData();
                    UploadDataType dataType = uploadData.getType();
                    if (dataType == UploadDataType.PLAIN_TEXT) {
                        TextData data = (TextData) uploadData;
                        data.setText("My data");
                    } else if (dataType == UploadDataType.BYTES) {
                        BytesData data = (BytesData) uploadData;
                        data.setData("My data".getBytes());
                    }
                    // Apply modified upload data that will be sent to a web server.
                    params.setUploadData(uploadData);
                }
            }
        });
        browser.loadURL(new LoadURLParams("http://localhost/", "key=value"));
    }
}
