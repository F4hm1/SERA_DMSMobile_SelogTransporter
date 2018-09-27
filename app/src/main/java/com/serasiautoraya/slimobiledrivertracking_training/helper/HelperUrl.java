package com.serasiautoraya.slimobiledrivertracking_training.helper;

import android.os.Environment;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperUrl {
    public static final String DIRECTORY_NAME = "SLI-Transporter";
    public static final String SAVE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/"+DIRECTORY_NAME+"/";


    /**
    * Host
    */
    //private static String HOST_PROD = "https://damira.sera.astra.co.id/DMSAPI/";


    /*
    * HOST DEVELOPMENT
    *
    * */

    private static final String HOST = "http://drivermanagementapidev.azurewebsites.net/";

    /**
     * Data Retrieval
     */
    public static String LOGIN = HOST + "auth/login/";

    /**
     * Data Retrieval
     */
    public static String LOGIN_PROD = HOST + "RestAPIFront_Login/login/";

}
