package com.serasiautoraya.slimobiledrivertracking.helper;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperKey {
    //Permission Code
    public static final int STORAGE_PERMISSION_GRANTED_CODE = 1001;
    public static final int LOCATION_PERMISSION_GRANTED_CODE = 1002;

    //SharedPreferences Code
    public static final String HAS_LOGIN = "LOGGED_IN";
    public static String KEY_USERNAME = "KEY_USERNAME";
    public static String KEY_PASSWORD = "KEY_PASSWORD";

    //Activity Code
    public static final int ACTIVITY_PROVE = 1001;
    public static final int ACTIVITY_FIRST_IMAGE_CAPTURE = 1002;
    public static final int ACTIVITY_SECOND_IMAGE_CAPTURE = 1003;

    //Date format
    public static final String USER_DATE_FORMAT = "dd-MM-yyyy";
    public static final String USER_TIME_FORMAT = "kk:mm:ss";

    //Activity Title
    public static final String EXTRA_KEY_TITLE = "TITLE";
    public static final String TITLE_ACTIVITY_LOADING = "Bukti Loading";
    public static final String TITLE_ACTIVITY_UNLOADING = "Bukti Unloading";
    public static final String TITLE_ACTIVITY_CHECKPOINT= "Bukti Checkpoint";
    public static final String TITLE_ACTIVITY_END_ORDER= "Bukti End Order";
    public static final String TITLE_ACTIVITY_CHANGE_PASS= "Ganti Password";

    //API - Key
    public static String API_KEY = "8d7ca5b010c22997b1f6910b111c8273417ec35a";

    //HTTP Status
    public static final int HTTP_STAT_OK = 200;
    public static final int HTTP_STAT_BADREQ = 400;

    //Static API Code
    public static final String CLOCK_IN_CODE = "TRXCC_01";
    public static final String CLOCK_OUT_CODE = "TRXCC_02";
    public static final String WFSTATUS_APPROVED = "WFSTS_10";
    public static final String WFSTATUS_PENDING = "WFSTS_01";
    public static final String SUBMIT_TYPE_DEFAULT = "CCSMT_04";
    public static final String APPROVED_AUTO = "Automatic Approved";


}
