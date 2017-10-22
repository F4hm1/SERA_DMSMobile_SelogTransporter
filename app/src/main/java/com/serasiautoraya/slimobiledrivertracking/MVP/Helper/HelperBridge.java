package com.serasiautoraya.slimobiledrivertracking.MVP.Helper;


import android.graphics.Bitmap;

import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned.AssignedOrderResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit.PodStatusResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Login.LoginResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail.OrderHistoryDetailResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;

import net.grandcentrix.thirtyinch.TiActivity;

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

    public static List<OrderHistoryResponseModel> sOderHistoryList;

    public static Bitmap sBitmapSignature = null;

    public static ActivityDetailResponseModel sActivityDetailResponseModel;

    public static PodStatusResponseModel sPodStatusResponseModel;

    public static String sTempSelectedOrderCode = "";

    public static AssignedOrderResponseModel sAssignedOrderResponseModel;

    public static List<OrderHistoryDetailResponseModel> sOrderHistoryDetailActivityList;

    public static int sUpdateLocationInterval;

    public static int sTempFragmentTarget = 0;

    public static TiActivity sCurrentDetailActivity = null;

    public static boolean sListOrderRetrievalSuccess = true;

}
