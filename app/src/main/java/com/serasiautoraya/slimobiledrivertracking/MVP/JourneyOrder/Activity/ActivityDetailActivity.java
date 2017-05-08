package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailActivity extends TiActivity<ActivityDetailPresenter, ActivityDetailView> implements ActivityDetailView {

    @BindView(R.id.order_title_code_head) TextView mTvOrderCodeHead;
    @BindView(R.id.order_title_code) TextView mTvOrderCode;
    @BindView(R.id.order_title_activityname) TextView mTvOrderActivityName;
    @BindView(R.id.order_title_activitytype) TextView mTvOrderActivityType;
    @BindView(R.id.order_title_origin) TextView mTvOrderOrigin;
    @BindView(R.id.order_title_dest) TextView mTvOrderDest;
    @BindView(R.id.order_title_etd) TextView mTvOrderEtd;
    @BindView(R.id.order_title_eta) TextView mTvOrderEta;
    @BindView(R.id.order_title_customer) TextView mTvOrderCustomer;
    @BindView(R.id.order_title_locationtarget) TextView mTvOrderLocationTarget;
    @BindView(R.id.order_title_timetarget) TextView mTvOrderTimeTarget;
    @BindView(R.id.order_title_timebaseline) TextView mTvOrderTimeBaseline;
    @BindView(R.id.order_title_timeactual) TextView mTvOrderTimeActual;

    @BindView(R.id.order_button_action) Button mTvButtonAction;

    private String mOrderCode;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_actionactivity);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mOrderCode = bundle.getString(HelperKey.KEY_INTENT_ORDERCODE);
        }
    }

    @Override
    public void initialize() {
        getPresenter().loadDetailOrderData(mOrderCode);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(ActivityDetailActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public ActivityDetailPresenter providePresenter() {
        return new ActivityDetailPresenter(new RestConnection(ActivityDetailActivity.this));
    }

    @Override
    @OnClick(R.id.order_button_action)
    public void onActionClicked(View view) {
        getPresenter().onActionClicked();
    }

    @Override
    public void setDetailData(String codeHead, String code, String activityName, String activityType, String origin, String destination, String etd, String eta, String customer, String locationTarget, String timeTarget, String timeBaseline, String timeActual) {
        mTvOrderCodeHead.setText(codeHead);
        mTvOrderCode.setText(code);
        mTvOrderActivityName.setText(activityName);
        mTvOrderActivityType.setText(activityType);
        mTvOrderOrigin.setText(origin);
        mTvOrderDest.setText(destination);
        mTvOrderEtd.setText(etd);
        mTvOrderEta.setText(eta);
        mTvOrderCustomer.setText(customer);
        mTvOrderLocationTarget.setText(locationTarget);
        mTvOrderTimeTarget.setText(timeTarget);
        mTvOrderTimeBaseline.setText(timeBaseline);
        mTvOrderTimeActual.setText(timeActual);
    }

    @Override
    public void setButtonText(String text) {
        mTvButtonAction.setText(text);
    }

    @Override
    public void changeActivity(Class cls) {
        Intent i = new Intent(ActivityDetailActivity.this, cls);
        startActivity(i);
    }
}
