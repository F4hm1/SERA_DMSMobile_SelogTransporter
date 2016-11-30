package com.serasiautoraya.slimobiledrivertracking.util;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ThemedSpinnerAdapter;

import com.serasiautoraya.slimobiledrivertracking.activity.LoginActivity;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class AppInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        initTimber();
        initVolley();
        initConfig();
//        initUploadService();

    }

//    private void initUploadService() {
//        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
//    }


//    private void initTimber() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        }
//    }

    private void initVolley() {
        VolleyUtil.init(this);
    }

    private void initConfig() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
