package com.serasiautoraya.slimobiledrivertracking.helper;

import android.graphics.Bitmap;

import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralSingleList;
import com.serasiautoraya.slimobiledrivertracking.adapter.OrderSingleList;
import com.serasiautoraya.slimobiledrivertracking.model.ModelLoginResponse;
import com.serasiautoraya.slimobiledrivertracking.model.ModelPersonalData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelReportResponse;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class HelperBridge {
    //Model
    public static int maxRequest = 2; //temporary, nanti di parsing dari model Login

    //List
    public static OrderSingleList ORDER_CLICKED;
    public static GeneralSingleList HISTORY_ORDER_CLICKED;

    //Bitmap
    public static Bitmap sFirstBitmap = null;
    public static Bitmap sSecondBitmap = null;
    public static Bitmap sTtdBitmap = null;

    //Temporary model data
    public static ModelLoginResponse MODEL_LOGIN_DATA;
    public static ModelReportResponse[] MODEL_REPORT_ARRAY;

    //Permission Status
    public static boolean sLocationGranted = false;
}
