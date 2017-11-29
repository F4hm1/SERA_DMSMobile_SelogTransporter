package com.serasiautoraya.slimobiledrivertracking_training.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class ActionActivityActivity extends AppCompatActivity {

    @BindView(R.id.order_title_code_head) TextView mOrderCodeHead;
    @BindView(R.id.order_title_code) TextView mOrderCode;
    @BindView(R.id.order_title_activityname) TextView mOrderHub;
    @BindView(R.id.order_title_activitytype) TextView mOrderNextDriver;
    @BindView(R.id.order_title_origin) TextView mOrderOrigin;
    @BindView(R.id.order_title_dest) TextView mOrderDest;
    @BindView(R.id.order_title_etd) TextView mOrderEtd;
    @BindView(R.id.order_title_eta) TextView mOrderEta;
    @BindView(R.id.order_title_customer) TextView mOrderCustomer;
    @BindView(R.id.order_title_locationtarget) TextView mOrderUnit;
    @BindView(R.id.order_title_timetarget) TextView mOrderNextDest;
    @BindView(R.id.order_title_timebaseline) TextView mOrderNextEta;
    @BindView(R.id.order_title_timeactual) TextView mOrderLastStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionactivity);
        ButterKnife.bind(this);

        // Re-enter transition is executed when returning back to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT); // Use START if using right - to - left locale
        slideTransition.setInterpolator(new AnticipateOvershootInterpolator());
        slideTransition.setDuration(500);

        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);

        getWindow().setAllowReturnTransitionOverlap(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrderCodeHead.setText(HelperBridge.MODEL_ACTIVITY_SELECTED.getCode());
        mOrderCode.setText(HelperBridge.MODEL_ACTIVITY_SELECTED.getCode());
        mOrderNextDest.setText(HelperBridge.MODEL_ACTIVITY_SELECTED.getInformation());
        mOrderLastStatus.setText(HelperBridge.MODEL_ACTIVITY_SELECTED.getStatus());
    }

    @OnClick (R.id.order_button_action)
    void actionOnClick(View view){
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i = new Intent(ActionActivityActivity.this, EvidenceCaptureActivity.class);
        i.putExtra(HelperKey.KEY_TRANSITION_TYPE, HelperKey.TransitionType.Slide);
        i.putExtra(HelperKey.KEY_TITLE_ANIM, "Capture Evidence");
        startActivity(i, options.toBundle());
    }
}
