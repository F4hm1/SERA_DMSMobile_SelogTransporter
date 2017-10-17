package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareImageView;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodSubmitActivity extends TiActivity<PodSubmitPresenter, PodSubmitView> implements PodSubmitView {
    @BindView(R.id.pod_card_submit)
    CardView mCardSubmit;

    @BindView(R.id.pod_card_container)
    CardView mCardContainer;

    @BindView(R.id.pod_gv_container)
    GridView mGvContainer;

    @BindView(R.id.pod_btn_submit)
    Button mBtnSubmit;

    @BindView(R.id.pod_spinner_reason)
    Spinner mSpinnerPodReason;

    @BindView(R.id.pod_tv_guide)
    TextView mTvPodGuide;

    private ProgressDialog mProgressDialog;
    private PodListAdapter mPodListAdapter;
    private boolean isFromCameraActivity = false;
    private RelativeLayout.LayoutParams mParamBtnSubmitNormal;
    private RelativeLayout.LayoutParams mParamBtnSubmitBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_capture);
        ButterKnife.bind(this);
        initializePodAdapter();
        initializeDynamicLayoutParams();
        initializeSpinnerReason();

    }

    private void initializeSpinnerReason() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PodSubmitActivity.this, R.array.documents_podreason_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPodReason.setAdapter(adapter);
        mSpinnerPodReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    mCardContainer.setVisibility(View.VISIBLE);
                    mCardSubmit.setLayoutParams(mParamBtnSubmitBottom);
                } else {
                    mCardContainer.setVisibility(View.GONE);
                    mCardSubmit.setLayoutParams(mParamBtnSubmitNormal);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeDynamicLayoutParams() {
        mParamBtnSubmitNormal = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamBtnSubmitNormal.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        mParamBtnSubmitNormal.addRule(RelativeLayout.BELOW, R.id.pod_card_reason);
        mParamBtnSubmitBottom = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamBtnSubmitBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    }

    private void initializePodAdapter() {
        mPodListAdapter = new PodListAdapter(this, new PodItemOnClickListener() {
            @Override
            public void onCapturePhoto(int position, SquareImageView squareImageView) {
                getPresenter().capturePhoto(position);
            }

            @Override
            public void onCloseThumbnail(int position, ImageButton imageButton) {
                imageButton.setVisibility(View.GONE);
                mPodListAdapter.remove(position);
                if (mPodListAdapter.getCount() < 1) {
                    mPodListAdapter.addItem(new PodItemModel(null));
                }
                mPodListAdapter.notifyDataSetChanged();
            }
        });
        mPodListAdapter.addItem(new PodItemModel(null));
        mGvContainer.setAdapter(mPodListAdapter);
        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(PodSubmitActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(PodSubmitActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, PodSubmitActivity.this, Title);
    }

    @NonNull
    @Override
    public PodSubmitPresenter providePresenter() {
        return new PodSubmitPresenter(new RestConnection(PodSubmitActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HelperKey.ACTIVITY_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    isFromCameraActivity = true;
                }
                break;
            }
        }
    }

    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmapScaled, int mCurrentSelectedPosition, boolean isPod) {
        if (mPodListAdapter.getItem(mCurrentSelectedPosition).getBitmap() == null) {
            if(mPodListAdapter.getCount() < 10){
                mPodListAdapter.addItem(new PodItemModel(null));
            }
        }
        mPodListAdapter.getItem(mCurrentSelectedPosition).setBitmap(bitmapScaled);
        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    @OnClick(R.id.pod_btn_submit)
    public void onClickSubmit(View view) {
        if(mSpinnerPodReason.getSelectedItemPosition() == 0){
            getPresenter().onRequestSubmitted(mPodListAdapter.getPodItemModels());
        }else{
            getPresenter().onConfirmActivity();
        }
    }

    @Override
    public void toggleProgressBar(int position, boolean show) {
        mPodListAdapter.getItem(position).getProgressBar().setIndeterminate(show);
        mPodListAdapter.getItem(position).getProgressBar().setVisibility(show ? View.VISIBLE : View.GONE);
        Log.d("PONDEX", "PONDEX: " + position + ": " + show);
//        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        }
    }

    @Override
    public void showConfirmationDialog(String activityName) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin semua dokumen telah terpenuhi dan akan melakukan proses " +
                "<b>" + activityName + "</b>" + "?");

        HelperUtil.showConfirmationAlertDialog(textMsg, PodSubmitActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity(mSpinnerPodReason.getSelectedItemPosition() == 0?"-":mSpinnerPodReason.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void setSubmitText(String activityName) {
        mBtnSubmit.setText(activityName);
    }

    @Override
    public void showPhotosRequiredAlert() {
        Snackbar.make(mGvContainer, "Harap mengambil minimal 1 foto POD", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setGuideText(String text) {
        mTvPodGuide.setText(text);
    }

}
