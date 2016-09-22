/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.gui.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * This sample demonstrates how to read and modify POST data of
 * HTTP request using NetworkDelegate.
 */
public class POSTDataSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserContext browserContext = browser.getContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onBeforeURLRequest(BeforeURLRequestParams params) {
                if ("POST".equals(params.getMethod())) {
                    UploadData uploadData = params.getUploadData();
                    UploadDataType dataType = uploadData.getType();
                    if (dataType == UploadDataType.FORM_URL_ENCODED) {
                        FormData data = (FormData) uploadData;
                        data.setPair("key1", "value1", "value2");
                        data.setPair("key2", "value2");
                    } else if (dataType == UploadDataType.MULTIPART_FORM_DATA) {
                        MultipartFormData data = (MultipartFormData) uploadData;
                        data.setPair("key1", "value1", "value2");
                        data.setPair("key2", "value2");
                        data.setFilePair("file3", "C:\\Test.zip");
                    } else if (dataType == UploadDataType.PLAIN_TEXT) {
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
