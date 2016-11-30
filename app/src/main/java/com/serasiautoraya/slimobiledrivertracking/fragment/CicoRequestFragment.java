package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class CicoRequestFragment extends Fragment {
    RelativeLayout mBtnClockin, mBtnClockout;
    RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cico_request, container, false);
        assignView(view);

        return view;
    }

    private void assignView(View view) {
        mBtnClockin = (RelativeLayout) view.findViewById(R.id.btn_rel_clockin_req);
        mBtnClockout = (RelativeLayout) view.findViewById(R.id.btn_rel_clockout_req);

        mBtnClockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockinConfirmationDialog(CicoRequestFragment.REQUEST_TYPE_CLOCKIN);
            }
        });

        mBtnClockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockinConfirmationDialog(CicoRequestFragment.REQUEST_TYPE_CLOCKOUT);
            }
        });
        mqueue = VolleyUtil.getRequestQueue();
    }

    private static final int REQUEST_TYPE_CLOCKOUT = 1;
    private static final int REQUEST_TYPE_CLOCKIN= 2;

    private void showClockinConfirmationDialog(final int requestType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, getResources().getConfiguration().locale);
        SimpleDateFormat timeFormat = new SimpleDateFormat(HelperKey.USER_TIME_FORMAT, getResources().getConfiguration().locale);
        Date newDate = new Date();
        String type = "Clock In";
        if(requestType == CicoRequestFragment.REQUEST_TYPE_CLOCKOUT){
            type = "Clock Out";
        }

        final String dateTimeCiCo = dateFormat.format(newDate) + " "+timeFormat.format(newDate);

        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan "+
                        "<b>"+type+"</b>"+" pada "+
                        "<b>"+dateFormat.format(newDate)+"</b>"+" pukul "+
                        "<b>"+timeFormat.format(newDate)+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * Web API address belum ada
                 * sementara request di pass */
//                if(requestType == CicoRequestFragment.REQUEST_TYPE_CLOCKOUT){
//                    requestCiCo(dateTimeCiCo, HelperKey.CLOCK_OUT_CODE);
//                }else {
//                    requestCiCo(dateTimeCiCo, HelperKey.CLOCK_IN_CODE);
//                }
                Toast.makeText(getContext()
                        , "Request cico sukses " +
                                "\nLatitude" + LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude()+
                                "\nLongitude"+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude() +
                                "\nLocation"+ LocationServiceUtil.getLocationManager(getContext()).getLastLocationName()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    private int mStatusCode = 0;
    private void requestCiCo(String dateTimeCiCo, String ciCoCode) {
        String url = HelperUrl.CICO;

        HashMap<String, String> params = new HashMap<>();
        params.put("PersonalId", HelperBridge.MODEL_PERSONAL_DATA.getPersonalId());
        params.put("PersonalCode", HelperBridge.MODEL_PERSONAL_DATA.getPersonalCode());
        params.put("CompanyCode", HelperBridge.MODEL_PERSONAL_DATA.getCompanyCode());
        params.put("BranchCode", HelperBridge.MODEL_PERSONAL_DATA.getBranchCode());
        params.put("PlacementCode", HelperBridge.MODEL_PERSONAL_DATA.getPlacementCode());
        params.put("PoolCode", HelperBridge.MODEL_PERSONAL_DATA.getPoolCode());
        params.put("SubmitType", HelperKey.SUBMIT_TYPE_DEFAULT);
        params.put("CicoType", ciCoCode);
        params.put("TimeCico", dateTimeCiCo);
        params.put("WFStatus", HelperKey.WFSTATUS_APPROVED);
//        params.put("Reason", HelperBridge.MODEL_PERSONAL_DATA.getPersonalId());
        params.put("UserApproval", HelperKey.APPROVED_AUTO);
        /*
        * @Todo change long, lat, loc here
        * */
        params.put("Latitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude());
        params.put("Longitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude());
        params.put("Location", LocationServiceUtil.getLocationManager(getContext()).getLastLocationName());

        HashMap<String, String> header = new HashMap<>();
        header.put("X-API-KEY", HelperKey.API_KEY);

        GsonRequest<String> request = new GsonRequest<String>(
                Request.Method.POST,
                url,
                String.class,
                header,
                params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (mStatusCode == HelperKey.HTTP_STAT_OK) {
                            Toast.makeText(getContext(), "Submit Clock In/Clock Out", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response != null) {
                    mStatusCode = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };

        request.setShouldCache(false);
        mqueue.add(request);
    }

}
