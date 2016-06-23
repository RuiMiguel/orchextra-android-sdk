/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gigigo.orchextra;

import android.webkit.WebView;

import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.ui.webview.OxWebView;

public final class Orchextra {

    private Orchextra() {
    }

    public static void initialize(OrchextraBuilder orchextraBuilder) {
        OrchextraManager.checkInitMethodCall(orchextraBuilder.getApplication(), orchextraBuilder.getOrchextraCompletionCallback());

        OrchextraManager.setLogLevel(orchextraBuilder.getOrchextraLogLevel());
        OrchextraManager.sdkInit(orchextraBuilder.getApplication(), orchextraBuilder.getOrchextraCompletionCallback());
        OrchextraManager.saveApiKeyAndSecret(orchextraBuilder.getApiKey(), orchextraBuilder.getApiSecret());
        OrchextraManager.setImageRecognition(orchextraBuilder.getImageRecognitionModule());
    }

    public static void start() {
        OrchextraManager.sdkStart();
    }

    public static synchronized void reinit(String apiKey, String apiSecret) {
        OrchextraManager.sdkReinit(apiKey, apiSecret);
    }

    public static synchronized void startImageRecognition() {
        OrchextraManager.startImageRecognition();
    }

    public static synchronized void stop() {
        OrchextraManager.sdkStop();
    }

    public static synchronized void setCustomSchemeReceiver(final CustomSchemeReceiver customSchemeReceiver) {
        if (customSchemeReceiver != null) {
            OrchextraManager.setCustomSchemeReceiver(new CustomOrchextraSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    customSchemeReceiver.onReceive(scheme);
                }
            });
        }
    }

    public static synchronized void setUser(ORCUser orcUser) {
        OrchextraManager.setUser(orcUser);
    }

    public static void startScannerActivity() {
        OrchextraManager.openScannerView();
    }
}
