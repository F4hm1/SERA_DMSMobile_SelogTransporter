package com.serasiautoraya.slimobiledrivertracking.helper;

import android.os.Environment;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperUrl {
    public static final String SAVE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/SLI-Driver-Tracing/";


    /**
    * Host
    */
    private static String HOST = "http://192.168.8.100:811/api_att/";


    /**
     * Data Retrieval
     */
    public static String LOGIN = HOST + "restapifront_login/login/";
    public static String GET_ATTENDANCE_HISTORY = HOST + "restapifront_cico/history/";

    /**
    * Data Post
    */
    public static String CICO = HOST + "restapifront_cico/cico/";
    public static String CICO_REQUEST = HOST + "restapifront_cico/cico/";
    public static String ABSENCE = HOST + "restapifront_absence/absence/";



}
