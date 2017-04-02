package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailActivity extends TiActivity<ActivityDetailPresenter, ActivityDetailView> implements ActivityDetailView {

    @BindView(R.id.order_title_code_head) TextView mOrderCodeHead;
    @BindView(R.id.order_title_code) TextView mOrderCode;
    @BindView(R.id.order_title_hub) TextView mOrderHub;
    @BindView(R.id.order_title_nextdriver) TextView mOrderNextDriver;
    @BindView(R.id.order_title_origin) TextView mOrderOrigin;
    @BindView(R.id.order_title_dest) TextView mOrderDest;
    @BindView(R.id.order_title_etd) TextView mOrderEtd;
    @BindView(R.id.order_title_eta) TextView mOrderEta;
    @BindView(R.id.order_title_customer) TextView mOrderCustomer;
    @BindView(R.id.order_title_unit) TextView mOrderUnit;
    @BindView(R.id.order_title_nextdest) TextView mOrderNextDest;
    @BindView(R.id.order_title_nexteta) TextView mOrderNextEta;
    @BindView(R.id.order_title_laststatus) TextView mOrderLastStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_actionactivity);
        ButterKnife.bind(this);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {

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
        return new ActivityDetailPresenter();
    }

    @Override
    @OnClick(R.id.order_button_action)
    public void onActionClicked(View view) {
        getPresenter().onActionClicked();
    }
}
