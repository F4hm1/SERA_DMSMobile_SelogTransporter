package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.customdialog.DatePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 23/11/2016.
 */
public class AbsenceRequestFragment extends Fragment{

    private Spinner mSpinnerAbsenceType;
    private EditText mEditTextTanggalMulai, mEditTextTanggalAkhir, mEditTextAlasan;
    private Button mBtnSubmit;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogMulai;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogBerakhir;

    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_absence_request, container, false);
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        mEditTextTanggalMulai = (EditText) view.findViewById(R.id.edittext_absence_datemulai);
        mEditTextTanggalAkhir = (EditText) view.findViewById(R.id.edittext_absence_dateberakhir);
        mEditTextAlasan = (EditText) view.findViewById(R.id.edittext_absence_reason);
        mSpinnerAbsenceType = (Spinner) view.findViewById(R.id.spinner_absence_tipeabsen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.absence_tipe_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAbsenceType.setAdapter(adapter);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_absence_submit);

        mDatePickerToEditTextDialogMulai = new DatePickerToEditTextDialog(mEditTextTanggalMulai, view.getContext());
        mDatePickerToEditTextDialogBerakhir= new DatePickerToEditTextDialog(mEditTextTanggalAkhir, view.getContext());

        mqueue = VolleyUtil.getRequestQueue();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String absentTypeSelected = HelperUtil.getValueStringArrayXML(
                        getResources().getStringArray(R.array.absence_tipe_array),
                        getResources().getStringArray(R.array.absence_tipe_array_val),
                        mSpinnerAbsenceType.getSelectedItem().toString());

                if(validateForm(absentTypeSelected)){
                    CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan request absence pada "+
                            "<b>"+mEditTextTanggalMulai.getText()+" - "+mEditTextTanggalAkhir.getText()+"</b>"+"?");

                    HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /**
                             * Web API address belum ada
                             * sementara request di pass */
//                            requestAbsence(absentTypeSelected);
                            Toast.makeText(getContext(), "Request absence sukses", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private int mStatusCode = 0;
    private void requestAbsence(String absentTypeSelected) {
        String url = HelperUrl.ABSENCE;

        HashMap<String, String> params = new HashMap<>();
        params.put("PersonalId", HelperBridge.MODEL_PERSONAL_DATA.getPersonalId());
        params.put("PersonalCode", HelperBridge.MODEL_PERSONAL_DATA.getPersonalCode());
        params.put("CompanyCode", HelperBridge.MODEL_PERSONAL_DATA.getCompanyCode());
        params.put("BranchCode", HelperBridge.MODEL_PERSONAL_DATA.getBranchCode());
        params.put("PlacementCode", HelperBridge.MODEL_PERSONAL_DATA.getPlacementCode());
        params.put("PoolCode", HelperBridge.MODEL_PERSONAL_DATA.getPoolCode());
        params.put("SubmitType", HelperKey.SUBMIT_TYPE_DEFAULT);
        params.put("AbsenceType", absentTypeSelected);
        params.put("WFStatus", HelperKey.WFSTATUS_PENDING);
        params.put("Reason", mEditTextAlasan.getText().toString());
        params.put("UserApproval", HelperBridge.MODEL_PERSONAL_DATA.getUserIdLevel2Name());
        /**
        * @Todo change long, lat, loc here
        * */
        params.put("Latitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLatitude());
        params.put("Longitude", ""+ LocationServiceUtil.getLocationManager(getContext()).getLastLocation().getLongitude());

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
        Toast.makeText(getContext(), "Submit clockout", Toast.LENGTH_LONG).show();
    }

    private boolean validateForm(String absentTypeSelected){
        boolean result = true;
        String errText = "";
        View focusView = null;

        if(TextUtils.isEmpty(mEditTextAlasan.getText().toString())){
            focusView = mEditTextAlasan;
            mEditTextAlasan.setError(getResources().getString(R.string.err_msg_empty_absent_keterangan));
            result = false;
        }

        if(absentTypeSelected.equalsIgnoreCase("-1")){
            focusView = mSpinnerAbsenceType;
            errText = getResources().getString(R.string.err_msg_empty_absent_absenttype);
            HelperUtil.showSimpleToast(errText, getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextTanggalAkhir.getText().toString())){
            focusView = mEditTextTanggalAkhir;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_absent_dateberakhir), getContext());
            result = false;
        }else if(mDatePickerToEditTextDialogBerakhir.isBeforeToday()){
            focusView = mEditTextTanggalAkhir;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_dateberakhir), getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextTanggalMulai.getText().toString())){
            focusView = mEditTextTanggalMulai;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_absent_datemulai), getContext());
            result = false;
        }else if(mDatePickerToEditTextDialogMulai.isBeforeToday()){
            focusView = mEditTextTanggalMulai;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_datemulai), getContext());
            result = false;
        }

        if(result == true){
            if(HelperUtil.isDateBefore(mEditTextTanggalMulai.getText().toString(), mEditTextTanggalAkhir.getText().toString())){
                focusView = mEditTextTanggalMulai;
                HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_date), getContext());
                result = false;
            }
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

}
