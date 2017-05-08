package com.serasiautoraya.slimobiledrivertracking.MVP.Helper;


import android.graphics.Bitmap;

import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned.AssignedOrderResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Login.LoginResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class HelperBridge {

    public static LoginResponseModel sModelLoginResponse;

    public static List<AssignedOrderResponseModel> sActiveOrdersList;
    public static List<AssignedOrderResponseModel> sPlanOutstandingOrdersList;

    public static List<RequestHistoryResponseModel> sCiCoRequestHistoryList;
    public static List<RequestHistoryResponseModel> sOLCRequestHistoryList;
    public static List<RequestHistoryResponseModel> sOvertimeRequestHistoryList;
    public static List<RequestHistoryResponseModel> sAbsenceRequestHistoryList;

    public static Bitmap sBitmapSignature = null;


}
