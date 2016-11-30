package com.serasiautoraya.slimobiledrivertracking.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

/**
 * Created by Randi Dwi Nandra on 29/11/2016.
 */
public class PermissionsUtil implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static boolean sLocationGranted = false;
    private static boolean sExternalStorageGranted = false;
    private static boolean sInternetGranted = false;

    private static Activity sCurrentActivity;
    public static void requestLocationPermission(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    HelperKey.LOCATION_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sLocationGranted = true;
        }
    }

    public static void requestStoragePermission(Activity activity){
        sCurrentActivity = activity;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( activity, new String[] {  android.Manifest.permission.WRITE_EXTERNAL_STORAGE  },
                    HelperKey.STORAGE_PERMISSION_GRANTED_CODE);
        }else{
            PermissionsUtil.sExternalStorageGranted = true;
        }
    }

    public static boolean issLocationGranted() {
        return sLocationGranted;
    }

    public static boolean issExternalStorageGranted() {
        return sExternalStorageGranted;
    }

    public static boolean issInternetGranted() {
        return sInternetGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case HelperKey.LOCATION_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sLocationGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin lokasi untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestLocationPermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan ijin lokasi untuk aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
            case HelperKey.STORAGE_PERMISSION_GRANTED_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionsUtil.sExternalStorageGranted = true;
                } else {
//                    HelperUtil.showConfirmationAlertDialogNoCancel("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestStoragePermission(sCurrentActivity);
//                        }
//                    });
                    if(SharedPrefsUtil.getBoolean(sCurrentActivity, HelperKey.HAS_LOGIN, false)){
                        System.exit(0);
                    }else{
                        HelperUtil.showSimpleToast("Mohon aktifkan ijin akses storage untuk aplikasi ini", sCurrentActivity.getBaseContext());
                    }
                }
                break;
            }
        }
    }
}
