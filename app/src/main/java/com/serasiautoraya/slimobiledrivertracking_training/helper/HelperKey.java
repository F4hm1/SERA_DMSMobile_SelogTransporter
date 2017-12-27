package com.serasiautoraya.slimobiledrivertracking_training.helper;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperKey {
    //    Shared Activity (Bundle Intent code)
    public enum TransitionType {
        Explode, Slide, Fade
    }

//    Constant Value
    public static final int SAVED_IMAGE_DESIRED_WITDH = 768;

    //Permission Code
    public static final int STORAGE_PERMISSION_GRANTED_CODE = 1001;
    public static final int LOCATION_PERMISSION_GRANTED_CODE = 1002;
    public static final int SYSWINDOWS_PERMISSION_GRANTED_CODE = 1003;
    public static final int VIBRATE_PERMISSION_GRANTED_CODE = 1004;

    //SharedPreferences Code
    public static final String HAS_LOGIN = "LOGGED_IN";
    public static String KEY_USERNAME = "KEY_USERNAME";
    public static String KEY_PASSWORD = "KEY_PASSWORD";
    public static String KEY_NOTIF_ID = "KEY_NOTIF_ID";
    public static String KEY_LOC_LAT = "KEY_LOC_LAT";
    public static String KEY_LOC_LONG = "KEY_LOC_LONG";
    public static String KEY_LOC_ADDRESS = "KEY_LOC_ADDRESS";

    //Date format
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
    public static final String USER_DATE_FORMAT = "dd MMMM yyyy";

    //API - Key
    public static String API_KEY = "8d7ca5b010c22997b1f6910b111c8273417ec35a";

    //Generic Status
    public static final String STATUS_SUKSES = "1";
    public static final int AUTHORIZED_ACCESS = 1;

}
