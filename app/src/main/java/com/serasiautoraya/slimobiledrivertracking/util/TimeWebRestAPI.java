package com.serasiautoraya.slimobiledrivertracking.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.ServerCallBack;
import com.serasiautoraya.slimobiledrivertracking.model.ModelArrayData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelReportResponse;
import com.serasiautoraya.slimobiledrivertracking.model.ModelTimeRESTResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 18/01/2017.
 */
public class TimeWebRestAPI {
    private static TimeWebRestAPI timeWebRestAPI = null;
    private static Context sContext;
    private static RequestQueue mqueue;
    private static ModelTimeRESTResponse resultResponse = null;

    public static TimeWebRestAPI getInstance(Context context) {
        if (timeWebRestAPI == null) {
            timeWebRestAPI = new TimeWebRestAPI();
            mqueue = VolleyUtil.getRequestQueue();
        }
        sContext = context;
        return timeWebRestAPI;
    }


    public static ModelTimeRESTResponse getResultResponse() {
        return resultResponse;
    }

    public void getResponse(final ServerCallBack callback) {
        String url = HelperUrl.GET_SERVER_LOCALTIME;
        final ProgressDialog loading = ProgressDialog.show(sContext, sContext.getResources().getString(R.string.prog_msg_loadingjam),
                sContext.getResources().getString(R.string.prog_msg_wait), true, false);

        String lat = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLatitude();
        String lng = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLongitude();

        ModelTimeRESTResponse result = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("username", "randinandra");

        GsonRequest<ModelTimeRESTResponse> request = new GsonRequest<ModelTimeRESTResponse>(
                Request.Method.GET,
                url,
                ModelTimeRESTResponse.class,
                null,
                params,
                new Response.Listener<ModelTimeRESTResponse>() {
                    @Override
                    public void onResponse(ModelTimeRESTResponse response) {
                        loading.dismiss();
                        if (response != null) {
                            callback.onSuccessModelTimeRESTResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(sContext.getResources().getString(R.string.err_msg_general), sContext);
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    public static String getTimeZoneID(ModelTimeRESTResponse modelTimeRESTResponse){
        String result = "";
        switch (modelTimeRESTResponse.getGmtOffset()){
            case "7":
                result = "WIB";
                break;
            case "8":
                result = "WITA";
                break;
            case "9":
                result = "WIT";
                break;
        }
        return result;
    }
}
