package com.example.hovanly.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by Binc on 05/05/2016.
 */
public final class PermissionHelper {
    private static PermissionHelper sPermissionHelper;

    /**
     * constructor
     */
    private PermissionHelper() {
        // No-op
    }

    public static PermissionHelper getInstance() {
        if (sPermissionHelper == null) {
            sPermissionHelper = new PermissionHelper();
        }
        return sPermissionHelper;
    }

    public boolean isPermissionGranted(Context context, String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermission(@NonNull final Activity activity, final int requestCode, @NonNull final String permissionName) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)) {
            Snackbar.make(activity.findViewById(android.R.id.content),
                    "Please Grant Permissions",
                    Snackbar.LENGTH_INDEFINITE).setAction("SETTING", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode);
        }
    }
}