package com.serasiautoraya.slimobiledrivertracking.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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
import com.serasiautoraya.slimobiledrivertracking.model.ModelArrayData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelLoginResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.PermissionsUtil;
import com.serasiautoraya.slimobiledrivertracking.util.SharedPrefsUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class LoginActivity extends AppCompatActivity{
    // UI references.
    private EditText mUsername;
    private EditText mPasswordView;
    private Button mLogin;
    private RequestQueue mqueue;

    private String TAG_LOGIN = "tag_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(!SharedPrefsUtil.getBoolean(this, HelperKey.HAS_LOGIN, false)){
            setContentView(R.layout.activity_login);
            assignViews();
            actionViews();
        }else {
            passLogin();
        }
        initPermissions();
    }

    private void initPermissions() {
        if(!PermissionsUtil.issLocationGranted()){
            PermissionsUtil.requestLocationPermission(LoginActivity.this);
        }
        if(!PermissionsUtil.issExternalStorageGranted()){
            PermissionsUtil.requestStoragePermission(LoginActivity.this);
        }
    }

    private void assignViews() {
        mUsername = (EditText) findViewById(R.id.input_username);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mqueue = VolleyUtil.getRequestQueue();
    }

    private void actionViews() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PermissionsUtil.issLocationGranted() && PermissionsUtil.issExternalStorageGranted()){
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                    attemptLogin();
                }else{
                    initPermissions();
                }
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        mUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        String errText = "";
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            focusView = mPasswordView;
            mPasswordView.setError(getResources().getString(R.string.err_msg_empty_password));
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            focusView = mUsername;
            mUsername.setError(getResources().getString(R.string.err_msg_empty_username));
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true, getResources().getString(R.string.progress_msg_login));
            /**
             * Web API address belum ada
             * sementara login di pass */
//            onLogin(username, password);
            HelperUtil.goToActivity(LoginActivity.this, DashboardActivity.class);
            LoginActivity.this.finish();
        }
    }

    /**
     * Show the progress UI and hide the login form.
     */
    private void showProgress(final boolean show, String msg) {
        if(show){
            HelperUtil.showProgressDialog(this, msg);
        }else {
            HelperUtil.dismissProgressDialog();
        }
    }

    private void onLogin(final String username, final String password) {
        String url = HelperUrl.LOGIN;
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> header = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        header.put("X-API-KEY", HelperKey.API_KEY);
        GsonRequest<ModelArrayData> request = new GsonRequest<ModelArrayData>(
                Request.Method.POST,
                url,
                ModelArrayData.class,
                header,
                params,
                new Response.Listener<ModelArrayData>() {
                    @Override
                    public void onResponse(ModelArrayData response) {
                        if(response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)){
                            HelperBridge.MODEL_LOGIN_DATA = HelperUtil.getMyObject(response.getData()[0], ModelLoginResponse.class);
                            HelperUtil.showSimpleToast(getResources().getString(R.string.success_msg_login), LoginActivity.this);

                            SharedPrefsUtil.apply(LoginActivity.this, HelperKey.HAS_LOGIN, true);
                            SharedPrefsUtil.apply(LoginActivity.this, HelperKey.KEY_PASSWORD, password);
                            SharedPrefsUtil.apply(LoginActivity.this, HelperKey.KEY_USERNAME, username);

                            HelperUtil.goToActivity(LoginActivity.this, DashboardActivity.class);
                            LoginActivity.this.finish();
                        }else{
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_wrong_login), LoginActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, "");
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_wrong_login), LoginActivity.this);
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    private void passLogin(){
        HelperUtil.showSimpleToast(getResources().getString(R.string.warn_msg_memuatdata), LoginActivity.this);
        mqueue = VolleyUtil.getRequestQueue();
        String password = SharedPrefsUtil.getString(this, HelperKey.KEY_PASSWORD, "");
        String username = SharedPrefsUtil.getString(this, HelperKey.KEY_USERNAME, "");
        showProgress(true, getResources().getString(R.string.progress_msg_loaddata));
        /**
         * Web API address belum ada
         * sementara login di pass */
//            onLogin(username, password);
        HelperUtil.goToActivity(LoginActivity.this, DashboardActivity.class);
        LoginActivity.this.finish();
    }

    protected void onPause() {
        super.onPause();
//        mqueue.cancelAll(TAG_LOGIN);
    }
}
