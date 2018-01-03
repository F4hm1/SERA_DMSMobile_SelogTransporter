package com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class SharedPrefsModel {
    private SharedPreferences sharedPrefs;

    public SharedPrefsModel(Context context) {
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Save String data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param value  : value string stored in SharedPreferences
     */
    public void apply(String key, String value) {
        putString(key, value).apply();
    }

    /**
     * Save Integer data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param value  : value integer stored in SharedPreferences
     */
    public void apply(String key, int value) {
        putInt(key, value).apply();
    }

    /**
     * Save Boolean data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param value  : value boolean stored in SharedPreferences
     */
    public void apply(String key, boolean value) {
        putBoolean(key, value).apply();
    }

    /**
     * Load String data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param defaultValue  : defaultValue if there is no data in SharedPreferences with parsed key
     */
    public String get(String key, String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    /**
     * Load Integer data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param defaultValue  : defaultValue if there is no data in SharedPreferences with parsed key
     */
    public int get(String key, int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    /**
     * Load Boolean data to SharedPreferences
     * @param key : key as key in SharedPreferences
     * @param defaultValue  : defaultValue if there is no data in SharedPreferences with parsed key
     */
    public boolean get(String key, boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }

    /**
     * Clear all saved data in SharedPreferences and called when logout
     */
    public void clearAll() {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.clear();
        e.apply();
    }

    /**
     * Base method that use editor function directly, all of this method is used by this class caller method
     * @param key : key as key in SharedPreferences
     * @param value : Value based on every data type String, Int, Boolean
     * @return
     */
    @SuppressLint("CommitPrefEdits")
    private SharedPreferences.Editor putString(String key, String value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putString(key, value);
        return e;
    }

    private SharedPreferences.Editor putInt(String key, int value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putInt(key, value);
        return e;
    }

    private SharedPreferences.Editor putBoolean(String key, boolean value) {
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putBoolean(key, value);
        return e;
    }
}
