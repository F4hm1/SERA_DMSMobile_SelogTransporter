package com.serasiautoraya.slimobiledrivertracking_training.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.cache.plus.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class VolleyUtil {
    private static RequestQueue mRequestQueue;


    private VolleyUtil() {
        // no instances
    }

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
}
