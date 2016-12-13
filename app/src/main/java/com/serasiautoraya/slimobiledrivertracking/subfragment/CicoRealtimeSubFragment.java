package com.serasiautoraya.slimobiledrivertracking.subfragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.serasiautoraya.slimobiledrivertracking.activity.FatigueInterviewActivity;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class CicoRealtimeSubFragment extends Fragment {
    RelativeLayout mBtnClockin, mBtnClockout;
    RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_realtime_cico, container, false);
        assignView(view);

        return view;
    }

    private void assignView(View view) {
        mBtnClockin = (RelativeLayout) view.findViewById(R.id.btn_rel_clockin_req);
        mBtnClockout = (RelativeLayout) view.findViewById(R.id.btn_rel_clockout_req);

        mBtnClockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockinConfirmationDialog(CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKIN);
            }
        });

        mBtnClockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockinConfirmationDialog(CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKOUT);
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
        if(requestType == CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKOUT){
            type = "Clock Out";
        }

        final String tanggal = dateFormat.format(newDate);
        final String waktu = timeFormat.format(newDate);

        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan "+
                "<b>"+type+"</b>"+" pada "+
                "<b>"+tanggal+"</b>"+" pukul "+
                "<b>"+waktu+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * Web API address belum ada
                 * sementara request di pass */
//                if(requestType == CicoRequestFragment.REQUEST_TYPE_CLOCKOUT){
//                    requestCiCo(tanggal, waktu, HelperKey.CLOCK_OUT_CODE);
//                }else {
//                    requestCiCo(tanggal, waktu, HelperKey.CLOCK_IN_CODE);
//                }

                Intent goToFatigue = new Intent(getContext(), FatigueInterviewActivity.class);
                goToFatigue.putExtra(HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_FATIGUE_INTERVIEW);
                startActivity(goToFatigue);


                Toast.makeText(getContext()
                        , "Request cico sukses " +
                                "\nLatitude" + LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude()+
                                "\nLongitude"+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude() +
                                "\nLocation"+ LocationServiceUtil.getLocationManager(getContext()).getLastLocationName()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestCiCo(String tanggal, String waktu, String ciCoCode) {
        String url = HelperUrl.CICO;

        HashMap<String, String> params = new HashMap<>();
        params.put("idPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("wfStatus", HelperKey.WFSTATUS_PENDING);
        params.put("type", ciCoCode);
        params.put("tanggal", tanggal);
        params.put("waktu", waktu);
        params.put("reason", "");
        params.put("addby", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("submittype", HelperKey.SUBMIT_TYPE_ABSENCE);
        params.put("destUser1", HelperBridge.MODEL_LOGIN_DATA.getIdUpLevel_1());
        params.put("EmaildestUser1", HelperBridge.MODEL_LOGIN_DATA.getEmailLvl_1());
        params.put("destUser2", "");
        params.put("Latitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude());
        params.put("Longitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude());
        params.put("Location", LocationServiceUtil.getLocationManager(getContext()).getLastLocationName());

        HashMap<String, String> header = new HashMap<>();
        header.put("X-API-KEY", HelperKey.API_KEY);

        GsonRequest<ModelSingleData> request = new GsonRequest<ModelSingleData>(
                Request.Method.POST,
                url,
                ModelSingleData.class,
                header,
                params,
                new Response.Listener<ModelSingleData>() {
                    @Override
                    public void onResponse(ModelSingleData response) {
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cico), getContext());
                        }else {
                            HelperUtil.showSimpleAlertDialog(response.getData(), getContext());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        );

        request.setShouldCache(false);
        mqueue.add(request);
    }
}
