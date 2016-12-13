package com.serasiautoraya.slimobiledrivertracking.subfragment;

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
import com.serasiautoraya.slimobiledrivertracking.customdialog.TimePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class CicoRequestSubFragment extends Fragment {
    private Spinner mSpinnerTransactionType;
    private EditText mEditTextTanggal, mEditTextWaktu, mEditTextAlasan;
    private Button mBtnSubmit;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialog;
    private TimePickerToEditTextDialog mTimePickerToEditTextDialog;

    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_request_cico, container, false);
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        mEditTextTanggal = (EditText) view.findViewById(R.id.edittext_cico_date);
        mEditTextWaktu = (EditText) view.findViewById(R.id.edittext_cico_time);
        mEditTextAlasan = (EditText) view.findViewById(R.id.edittext_cico_reason);
        mSpinnerTransactionType = (Spinner) view.findViewById(R.id.spinner_cico_transaction_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cico_tipe_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTransactionType.setAdapter(adapter);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_cico_submit);

        mDatePickerToEditTextDialog = new DatePickerToEditTextDialog(mEditTextTanggal, view.getContext());
        mTimePickerToEditTextDialog = new TimePickerToEditTextDialog(mEditTextWaktu, view.getContext());

        mqueue = VolleyUtil.getRequestQueue();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String requestType = HelperUtil.getValueStringArrayXML(
                        getResources().getStringArray(R.array.cico_tipe_array),
                        getResources().getStringArray(R.array.cico_tipe_array_val),
                        mSpinnerTransactionType.getSelectedItem().toString());

                if(validateForm(requestType)){
                    CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan request "+
                            "<b>"+mSpinnerTransactionType.getSelectedItem().toString()+"</b>"+" pada "+
                            "<b>"+ mEditTextTanggal.getText()+", pukul "+ mEditTextWaktu.getText()+"</b>"+"?");

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
                            Toast.makeText(getContext(), "Request absence sukses", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
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
            focusView = mSpinnerTransactionType;
            errText = getResources().getString(R.string.err_msg_empty_cico_transactiontype);
            HelperUtil.showSimpleToast(errText, getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextTanggal.getText().toString())){
            focusView = mEditTextTanggal;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_cico_date), getContext());
            result = false;
        }else if(!mDatePickerToEditTextDialog.isInMaxRequest() || !mDatePickerToEditTextDialog.isBeforeToday()){
            focusView = mEditTextTanggal;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_cico_date), getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextWaktu.getText().toString())){
            focusView = mEditTextWaktu;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_cico_time), getContext());
            result = false;
        }else if(mDatePickerToEditTextDialog.isToday() && !mTimePickerToEditTextDialog.isBeforeCurrentTime()){
            focusView = mEditTextWaktu;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_cico_time), getContext());
            result = false;
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    private void requestCiCo(String dateTimeCiCo, String ciCoCode) {
        String url = HelperUrl.CICO_REQUEST;

        HashMap<String, String> params = new HashMap<>();
        params.put("idPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("wfStatus", HelperKey.WFSTATUS_PENDING);
        params.put("type", ciCoCode);
        params.put("tanggal", mEditTextTanggal.getText().toString());
        params.put("waktu", mEditTextWaktu.getText().toString());
        params.put("reason", mEditTextAlasan.getText().toString());
        params.put("addby", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("submittype", HelperKey.SUBMIT_TYPE_ABSENCE);
        params.put("destUser1", HelperBridge.MODEL_LOGIN_DATA.getIdUpLevel_1());
        params.put("EmaildestUser1", HelperBridge.MODEL_LOGIN_DATA.getEmailLvl_1());
        params.put("destUser2", "");

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
                            clearForm();
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cico_req), getContext());
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

    private void clearForm(){
        mSpinnerTransactionType.setSelection(0);
        mEditTextTanggal.setText("");
        mEditTextWaktu.setText("");
        mEditTextAlasan.setText("");
    }
}
