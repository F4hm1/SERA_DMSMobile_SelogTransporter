package com.serasiautoraya.slimobiledrivertracking.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.model.ModelArrayData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelLoginResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;
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
        VolleyUtil.init(LoginActivity.this);
        HttpsTrustManager.allowAllSSL();
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(LoginActivity.this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 1001);
//            }
//        }
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
            onLogin(username, password);
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
                        showProgress(false, "");
                        if(response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)){
                            ModelLoginResponse responseModel  = HelperUtil.getMyObject(response.getData()[0], ModelLoginResponse.class);
                                if(responseModel.getAccessM() == HelperKey.AUTHORIZED_ACCESS){
                                    HelperBridge.MODEL_LOGIN_DATA = responseModel;
                                    HelperBridge.maxRequest = HelperBridge.MODEL_LOGIN_DATA.getMaxHariRequestDriver();
                                    HelperUtil.showSimpleToast(getResources().getString(R.string.success_msg_login), LoginActivity.this);

                                    SharedPrefsUtil.apply(LoginActivity.this, HelperKey.HAS_LOGIN, true);
                                    SharedPrefsUtil.apply(LoginActivity.this, HelperKey.KEY_PASSWORD, password);
                                    SharedPrefsUtil.apply(LoginActivity.this, HelperKey.KEY_USERNAME, username);

                                    HelperUtil.goToActivity(LoginActivity.this, DashboardActivity.class);
                                    LoginActivity.this.finish();
                                }else{
                                    HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_not_authorizad), LoginActivity.this);
                                }
                        }else{
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_wrong_login), LoginActivity.this);
                            if(SharedPrefsUtil.getBoolean(LoginActivity.this, HelperKey.HAS_LOGIN, false)){
                                restartLogin();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, "");
                        if(SharedPrefsUtil.getBoolean(LoginActivity.this, HelperKey.HAS_LOGIN, false)){
                            HelperUtil.showSimpleAlertDialogCustomAction(getResources().getString(R.string.err_msg_general), LoginActivity.this, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            });
                        }else {
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), LoginActivity.this);
                        }
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    private void restartLogin() {
        SharedPrefsUtil.clearAll(this);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void passLogin(){
        HelperUtil.showSimpleToast(getResources().getString(R.string.warn_msg_memuatdata), LoginActivity.this);
        mqueue = VolleyUtil.getRequestQueue();
        String password = SharedPrefsUtil.getString(this, HelperKey.KEY_PASSWORD, "");
        String username = SharedPrefsUtil.getString(this, HelperKey.KEY_USERNAME, "");
        showProgress(true, getResources().getString(R.string.progress_msg_loaddata));
        onLogin(username, password);
    }

    protected void onPause() {
        super.onPause();
//        mqueue.cancelAll(TAG_LOGIN);
    }
}
