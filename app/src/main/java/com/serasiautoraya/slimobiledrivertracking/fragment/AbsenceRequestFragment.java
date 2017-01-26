package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
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

        int daysMinRequest = 0;
        daysMinRequest = Math.round(Float.valueOf(HelperBridge.MODEL_LOGIN_DATA.getMinHariRequestAbsence()));
        mDatePickerToEditTextDialogMulai = new DatePickerToEditTextDialog(mEditTextTanggalMulai, view.getContext(), false, false, daysMinRequest);
        mDatePickerToEditTextDialogBerakhir= new DatePickerToEditTextDialog(mEditTextTanggalAkhir, view.getContext(), false, false, daysMinRequest);

//        Log.i("TAG_REQMIN", "day: "+daysMinRequest);

        mqueue = VolleyUtil.getRequestQueue();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String absentTypeSelected = HelperUtil.getValueStringArrayXML(
                        getResources().getStringArray(R.array.absence_tipe_array),
                        getResources().getStringArray(R.array.absence_tipe_array_val),
                        mSpinnerAbsenceType.getSelectedItem().toString());

                if(validateForm(absentTypeSelected)){
                    String[] tanggalSplitMulai =  mEditTextTanggalMulai.getText().toString().split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                    final String tanggalMessageMulai = tanggalSplitMulai[2] + " " + HelperUtil.getMonthName(tanggalSplitMulai[1], getContext()) + " " + tanggalSplitMulai[0];

                    String[] tanggalSplitAkhir =  mEditTextTanggalAkhir.getText().toString().split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                    final String tanggalMessageAkhir = tanggalSplitAkhir[2] + " " + HelperUtil.getMonthName(tanggalSplitAkhir[1], getContext()) + " " + tanggalSplitAkhir[0];

                    CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan ketidakhadiran pada "+
                            "<b>"+tanggalMessageMulai+" - "+tanggalMessageAkhir+"</b>"+"?");


                    HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestAbsence(absentTypeSelected);
                        }
                    });
                }
            }
        });
    }

    private void requestAbsence(String absentTypeSelected) {
        String url = HelperUrl.ABSENCE;

        HashMap<String, String> params = new HashMap<>();
        params.put("idPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("wfStatus", HelperKey.WFSTATUS_REQUEST_ABSENCE);
        params.put("tanggal_mulai", mEditTextTanggalMulai.getText().toString());
        params.put("tanggal_akhir", mEditTextTanggalAkhir.getText().toString());
        params.put("transaksi_abs", absentTypeSelected);
        params.put("submittype", HelperKey.SUBMIT_TYPE_ABSENCE);
        params.put("reason", mEditTextAlasan.getText().toString());
        params.put("addby", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("destUser1", HelperBridge.MODEL_LOGIN_DATA.getIdUpLevel_1());
        params.put("EmaildestUser1", HelperBridge.MODEL_LOGIN_DATA.getEmailLvl_1());
        params.put("destUser2", "");

        HashMap<String, String> header = new HashMap<>();
        header.put("X-API-KEY", HelperKey.API_KEY);

        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_absence),getResources().getString(R.string.prog_msg_wait),true,false);
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
                            if(response.getData().toLowerCase().contains("success")){
                                clearForm();
                                HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_absent), getContext());
                            }else{
                                clearForm();
                                HelperUtil.showSimpleAlertDialog(response.getData(), getContext());
                            }
                        }else {
                            HelperUtil.showSimpleAlertDialog(response.getData(), getContext());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general) +" \nerror: "+error.toString(), getContext());
                    }
                }
        );

        request.setShouldCache(false);
        mqueue.add(request);
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
        }else if(!mDatePickerToEditTextDialogBerakhir.isInMaxRequest()){
            focusView = mEditTextTanggalAkhir;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_dateberakhir), getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextTanggalMulai.getText().toString())){
            focusView = mEditTextTanggalMulai;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_absent_datemulai), getContext());
            result = false;
        }else if(!mDatePickerToEditTextDialogMulai.isInMaxRequest()){
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

    private void clearForm(){
        mSpinnerAbsenceType.setSelection(0);
        mEditTextTanggalMulai.setText("");
        mEditTextTanggalAkhir.setText("");
        mEditTextAlasan.setText("");
    }
}
