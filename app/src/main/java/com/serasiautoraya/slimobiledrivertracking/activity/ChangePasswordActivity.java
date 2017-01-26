package com.serasiautoraya.slimobiledrivertracking.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.SharedPrefsUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class ChangePasswordActivity extends AppCompatActivity {
    private EditText mEditTextOld, mEditTextPass, mEditTextPassConf;
    private Button mBtnSubmit;

    private RequestQueue mqueue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(t);
        assignActionBar();
        assignView();
    }

    private void assignView() {
        mEditTextOld = (EditText) findViewById(R.id.edittext_cp_old);
        mEditTextPass = (EditText) findViewById(R.id.edittext_cp_pass);
        mEditTextPassConf = (EditText) findViewById(R.id.edittext_cp_passconf);
        mBtnSubmit = (Button) findViewById(R.id.btn_cp_submit);

        mqueue = VolleyUtil.getRequestQueue();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan "+
                            "<b>"+" mengganti kata sandi"+"</b>"+"?");

                    HelperUtil.showConfirmationAlertDialog(textMsg, ChangePasswordActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestChangePassword();

                        }
                    });
                }
            }
        });
    }

    private boolean isFormValid(){
        //cek masukan password lama, baru, dan konfirmasi cocok disini
        boolean result = true;
        View focusView = null;

        if(TextUtils.isEmpty(mEditTextPassConf.getText().toString())){
            focusView = mEditTextPassConf;
            mEditTextPassConf.setError(getResources().getString(R.string.err_msg_empty_cp_confpass));
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextPass.getText().toString())){
            focusView = mEditTextPass;
            mEditTextPass.setError(getResources().getString(R.string.err_msg_empty_cp_pass));
            result = false;
        }

        if(TextUtils.isEmpty(mEditTextOld.getText().toString())){
            focusView = mEditTextOld;
            mEditTextOld.setError(getResources().getString(R.string.err_msg_empty_cp_oldpass));
            result = false;
        }

        if(result == true){
            if(!mEditTextOld.getText().toString().equals(SharedPrefsUtil.getString(this, HelperKey.KEY_PASSWORD, ""))){
                focusView = mEditTextOld;
                mEditTextOld.setError(getResources().getString(R.string.err_msg_wrong_cp_passlamasalah));
                result = false;
            }

            if(!mEditTextPass.getText().toString().equals(mEditTextPassConf.getText().toString())){
                focusView = mEditTextPassConf;
                mEditTextPassConf.setError(getResources().getString(R.string.err_msg_wrong_cp_passbarunotmatch));
                result = false;
            }
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }


    private int mStatusCode = 0;
    private void requestChangePassword() {
        String url = HelperUrl.CHANGE_PASSWORD;
        final String newPass = mEditTextPass.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("oldpassword", mEditTextOld.getText().toString());
        params.put("NewPassword", newPass);
        params.put("ConNewPassword", mEditTextPassConf.getText().toString());
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());

        HashMap<String, String> header = new HashMap<>();
        header.put("X-API-KEY", HelperKey.API_KEY);

        final ProgressDialog loading = ProgressDialog.show(this,getResources().getString(R.string.prog_msg_cp),getResources().getString(R.string.prog_msg_wait),true,false);
        GsonRequest<ModelSingleData> request = new GsonRequest<ModelSingleData>(
                Request.Method.PUT,
                url,
                ModelSingleData.class,
                header,
                params,
                new Response.Listener<ModelSingleData>() {
                    @Override
                    public void onResponse(ModelSingleData response) {
                        loading.dismiss();
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            clearForm();
                            SharedPrefsUtil.apply(ChangePasswordActivity.this, HelperKey.KEY_PASSWORD, newPass);
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cp), ChangePasswordActivity.this);
                        }else {
                            HelperUtil.showSimpleAlertDialog(response.getData(), ChangePasswordActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), ChangePasswordActivity.this);
                    }
                }
        );

        request.setShouldCache(false);
        mqueue.add(request);
    }


    private void assignActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        String title = getIntent().getStringExtra(HelperKey.EXTRA_KEY_TITLE);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    private void clearForm(){
        mEditTextOld.setText("");
        mEditTextPassConf.setText("");
        mEditTextPass.setText("");
    }
}

