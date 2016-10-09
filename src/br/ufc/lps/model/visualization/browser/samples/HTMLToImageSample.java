/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
package br.ufc.lps.model.visualization.browser.samples;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.internal.LightWeightWidget;
import com.teamdev.jxbrowser.chromium.swing.internal.events.LightWeightWidgetListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
public class HTMLToImageSample {
    public static void main(String[] args) throws Exception {
        // #1 Create LIGHTWEIGHT Browser instance.
        Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        BrowserView view = new BrowserView(browser);

        // #2 Register LightWeightWidgetListener.onRepaint() to get notifications
        // about paint events. We expect that web page will be completely rendered twice:
        // 1. When its size is updated to 1280x1024.
        // 2. When HTML content is loaded and displayed.
        final CountDownLatch latch = new CountDownLatch(2);
        LightWeightWidget widget = (LightWeightWidget) view.getComponent(0);
        widget.addLightWeightWidgetListener(new LightWeightWidgetListener() {
            @Override
            public void onRepaint(Rectangle updatedRect, Dimension viewSize) {
                // Make sure that all view content has been repainted.
                if (viewSize.equals(updatedRect.getSize())) {
                    latch.countDown();
                }
            }
        });

        // #3 Set the required document size.
        browser.setSize(1280, 1024);

        // #4 Load web page and wait until web page is loaded completely.
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://google.com");
            }
        });

        // #5 Wait until Chromium renders web page content.
        latch.await(45, TimeUnit.SECONDS);

        // #6 Save java.awt.Image of the loaded web page into a PNG file.
        ImageIO.write((RenderedImage) widget.getImage(), "PNG", new File("google.com.png"));

        // #7 Dispose Browser instance.
        browser.dispose();
    }
}
