package com.serasiautoraya.slimobiledrivertracking.helper;

import android.graphics.Bitmap;

import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralSingleList;
import com.serasiautoraya.slimobiledrivertracking.adapter.OrderSingleList;
import com.serasiautoraya.slimobiledrivertracking.model.ModelPersonalData;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class HelperBridge {
    //List
    public static OrderSingleList ORDER_CLICKED;
    public static GeneralSingleList HISTORY_ORDER_CLICKED;

    //Bitmap
    public static Bitmap sFirstBitmap = null;
    public static Bitmap sSecondBitmap = null;
    public static Bitmap sTtdBitmap = null;

    //Temporary model data
    public static ModelPersonalData MODEL_PERSONAL_DATA;

    //Permission Status
    public static boolean sLocationGranted = false;
}
