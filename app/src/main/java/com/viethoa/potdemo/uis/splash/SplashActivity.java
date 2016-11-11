package com.viethoa.potdemo.uis.splash;

import android.Manifest;
import android.os.Bundle;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.viethoa.potdemo.base.BaseActivity;
import com.viethoa.potdemo.uis.main.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Ask permission
        RxPermissions.getInstance(this)
                .request(Manifest.permission.INTERNET)
                .subscribe(this::askGPSPermissionCallback);
    }

    private void askGPSPermissionCallback(boolean permissionGranted) {
        if (permissionGranted) {
            startActivity(MainActivity.newInstance(this));
        } else {
            finish();
        }
    }
}
