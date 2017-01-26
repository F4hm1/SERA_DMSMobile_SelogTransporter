package com.serasiautoraya.slimobiledrivertracking.subfragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
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
import com.serasiautoraya.slimobiledrivertracking.listener.ServerCallBack;
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelTimeRESTResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.GPSTracker;
import com.serasiautoraya.slimobiledrivertracking.util.LocationManagerUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;
import com.serasiautoraya.slimobiledrivertracking.util.PermissionsUtil;
import com.serasiautoraya.slimobiledrivertracking.util.TimeWebRestAPI;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class CicoRealtimeSubFragment extends Fragment {
    RelativeLayout mBtnClockin, mBtnClockout;
    RequestQueue mqueue;
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_realtime_cico, container, false);
        assignView(view);
        //TODO CHANGE
//        LocationManagerUtil.getInstance(getActivity()).getLocation();
//        gps = new GPSTracker(getActivity());
//        // check if GPS location can get Location
//        if (gps.canGetLocation()) {
//
//            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Log.d("Your Location", "latitude:" + gps.getLatitude()
//                        + ", longitude: " + gps.getLongitude());
//
//                double lon = gps.getLongitude();
//                double lat = gps.getLatitude();
//            }
//        }
        return view;
    }

    private void assignView(View view) {
        mBtnClockin = (RelativeLayout) view.findViewById(R.id.btn_rel_clockin_req);
        mBtnClockout = (RelativeLayout) view.findViewById(R.id.btn_rel_clockout_req);

        mBtnClockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeWebRestAPI.getInstance(getContext()).getResponse(new ServerCallBack() {
                    @Override
                    public void onSuccessModelTimeRESTResponse(ModelTimeRESTResponse result) {
                        showClockinConfirmationDialog(CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKIN, result);
                    }
                });
            }
        });

        mBtnClockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeWebRestAPI.getInstance(getContext()).getResponse(new ServerCallBack() {
                    @Override
                    public void onSuccessModelTimeRESTResponse(ModelTimeRESTResponse result) {
                        showClockinConfirmationDialog(CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKOUT, result);
                    }
                });
            }
        });
        mqueue = VolleyUtil.getRequestQueue();
    }

    private static final int REQUEST_TYPE_CLOCKOUT = 1;
    private static final int REQUEST_TYPE_CLOCKIN= 2;

    private void showClockinConfirmationDialog(final int requestType, ModelTimeRESTResponse timeRESTResponse) {
        String type = "Clock In";
        if(requestType == CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKOUT){
            type = "Clock Out";
        }
        String timeZoneId = TimeWebRestAPI.getTimeZoneID(timeRESTResponse);

        String[] timeSplit = timeRESTResponse.getTime().split(" ");
        String[] tanggalSplit =  timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);

        final String tanggal = timeSplit[0];
        final String waktu = timeSplit[1];
        final String tanggalMessage = tanggalSplit[2] + " " + HelperUtil.getMonthName(tanggalSplit[1], getContext()) + " " + tanggalSplit[0];

        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan "+
                "<b>"+type+"</b>"+" pada "+
                "<b>"+tanggalMessage+"</b>"+" pukul "+
                "<b>"+waktu+" "+timeZoneId+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!PermissionsUtil.issLocationGranted()){
                    PermissionsUtil.requestLocationPermission(getActivity());
                }else {
                    if(requestType == CicoRealtimeSubFragment.REQUEST_TYPE_CLOCKOUT){
                        requestCiCo(tanggal, waktu, HelperKey.CLOCK_OUT_CODE, tanggalMessage);
                    }else {
                        requestCiCo(tanggal, waktu, HelperKey.CLOCK_IN_CODE, tanggalMessage);
                    }
                }
            }
        });
    }

    private void requestCiCo(final String tanggal, final String waktu, final String ciCoCode, final String tanggalMessage) {
        String url = HelperUrl.CICO;
        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cico),getResources().getString(R.string.prog_msg_wait),true,false);

        String lat = ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude();
        String longl = ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude();
        String address = ""+LocationServiceUtil.getLocationManager(getContext()).getLastLocationName();

        HashMap<String, String> params = new HashMap<>();
        params.put("idPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("code", HelperBridge.MODEL_LOGIN_DATA.getCode());
        params.put("wfStatus", HelperKey.WFSTATUS_APPROVED);
        params.put("type", ciCoCode);
        params.put("tanggal", tanggal);
        params.put("waktu", waktu);
        params.put("reason", "");
        params.put("addby", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("submittype", HelperKey.SUBMIT_TYPE_DEFAULT);
        params.put("destUser1", HelperBridge.MODEL_LOGIN_DATA.getIdUpLevel_1());
        params.put("EmaildestUser1", HelperBridge.MODEL_LOGIN_DATA.getEmailLvl_1());
        params.put("destUser2", "");
        params.put("Latitude", lat);
        params.put("Longitude", longl);
        params.put("Address", address);
        params.put("Pool", HelperBridge.MODEL_LOGIN_DATA.getPool());
        params.put("Mobile", HelperKey.MOBILE_CODE);
        params.put("terminalId", "");

        final String fWaktu = waktu;
        final String fTanggal = tanggal;
        final String typeTrans = ciCoCode == HelperKey.CLOCK_IN_CODE?"Clock In":"Clock Out";

        Log.d("TAG_LEK", params.toString());


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
                        loading.dismiss();
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            String textMsg = "Transaksi "+typeTrans+" berhasil pukul "+fWaktu+" tanggal "+tanggalMessage+".";
                            HelperUtil.showSimpleAlertDialogCustomTitle(textMsg, getContext(), "Informasi");
                        }else {
                            HelperUtil.showSimpleAlertDialog(response.getData(), getContext());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        String textMsg = typeTrans+" pada tanggal "+tanggalMessage+" pukul "+fWaktu+" gagal, harap periksa koneksi ada";
                        HelperUtil.showSimpleAlertDialog(textMsg, getContext());
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }
}