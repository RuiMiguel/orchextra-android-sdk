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

package com.gigigo.orchextra.ui.scanner;

import android.os.Bundle;

import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.control.presenters.scanner.OxCodeScannerPresenter;
import com.gigigo.orchextra.control.presenters.scanner.OxCodeScannerView;
import com.gigigo.orchextra.control.presenters.scanner.entities.ScannerResultPresenter;
import com.gigigo.orchextra.device.permissions.PermissionCameraImp;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.ui.OxToolbarActivity;

import orchextra.javax.inject.Inject;

import me.dm7.barcodescanner.zbar.Result;
//TODO LIB_CRUNCH orchextrasdk-control
public class OxScannerActivity extends OxToolbarActivity implements OxCodeScannerView, OxZBarScannerView.ResultHandler {

    //TODO LIB_CRUNCH gggLib
    @Inject
    PermissionChecker permissionChecker;

    @Inject
    PermissionCameraImp cameraPermissionImp;
    //TODO LIB_CRUNCH orchextrasdk-control
    @Inject
    OxCodeScannerPresenter presenter;

    @Inject
    ActionDispatcher actionDispatcher;

    private OxZBarScannerView scannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.ox_activity_scanner_layout);

        initDI();
        //TODO LIB_CRUNCH orchextrasdk-control
        presenter.attachView(this);
    }

    private void initDI() {
        InjectorImpl injector = OrchextraManager.getInjector();
        if (injector != null) {
            injector.injectCodeScannerActivity(this);
        } else {
            finish();
        }
    }

    @Override
    public void initUi() {
        initViews();
        initScannerCamera();
    }

    public void initViews() {
        super.initViews();
        scannerView = (OxZBarScannerView) findViewById(R.id.cameraPreview);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.ox_scanner_toolbar_title;
    }

    private void initScannerCamera() {
        checkCameraPermission();
    }

    private void checkCameraPermission() {
        boolean isGranted = permissionChecker.isGranted(cameraPermissionImp);
        if (!isGranted) {
            permissionChecker.askForPermission(cameraPermissionImp, cameraPermissionResponseListener, this);
        } else {
            openScanner();
        }
    }
    //TODO LIB_CRUNCH gggLib
    private UserPermissionRequestResponseListener cameraPermissionResponseListener =
            new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed) {
            if (permissionAllowed) {
                openScanner();
            }
        }
    };

    private void openScanner() {
        scannerView.setupScanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    private void startCamera() {
        if (scannerView != null) {
            scannerView.startCamera();
            scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        }
    }

    private void stopCamera() {
        try {
            if (scannerView != null) {
                scannerView.stopCamera();
            }
        } catch (Exception e) {
            GGGLogImpl.log("Error al parar la camara del scanner");
        }
    }

    boolean isClosingActivity = false;

    private void closeActivity() {
        isClosingActivity=true;

        stopCamera();

        finish();
    }
    //TODO LIB_CRUNCH barcodescanner
    @Override
    public void handleResult(Result rawResult) {
        //TODO LIB_CRUNCH gggLogger
        GGGLogImpl.log(rawResult.toString());
//TODO LIB_CRUNCH orchextrasdk-control
        final ScannerResultPresenter scanResult = new ScannerResultPresenter();
        scanResult.setContent(rawResult.getContents());
        scanResult.setType(rawResult.getBarcodeFormat().getName());
//TODO LIB_CRUNCH gggLogger
        GGGLogImpl.log("Scanner Code: " + rawResult.getContents());
        GGGLogImpl.log("Scanner Type: " + rawResult.getBarcodeFormat().getName());
//TODO LIB_CRUNCH orchextrasdk-control
        presenter.sendScannerResult(scanResult);

        closeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//TODO LIB_CRUNCH orchextrasdk-control
        presenter.detachView();
    }
}
