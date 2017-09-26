package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareImageView;
import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareRelativeLayout;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;

import net.grandcentrix.thirtyinch.TiActivity;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by randi on 11/08/2017.
 */

public class PODCaptureActivity extends TiActivity<PODCapturePresenter, PODCaptureView> implements PODCaptureView {

    @BindView(R.id.pod_btn_submit)
    Button mBtnSubmit;

//    @BindView(R.id.pod_pb_progress_1)
//    ProgressBar mPbProgress1;
//
//    @BindView(R.id.pod_pb_progress_2)
//    ProgressBar mPbProgress2;
//
//    @BindView(R.id.pod_pb_progress_3)
//    ProgressBar mPbProgress3;
//
//    @BindView(R.id.pod_pb_progress_4)
//    ProgressBar mPbProgress4;
//
//    @BindView(R.id.pod_pb_progress_5)
//    ProgressBar mPbProgress5;


//    @BindView(R.id.pod_tv_progress_1)
//    TextView mTvProgress1;
//
//    @BindView(R.id.pod_tv_progress_2)
//    TextView mTvProgress2;
//
//    @BindView(R.id.pod_tv_progress_3)
//    TextView mTvProgress3;
//
//    @BindView(R.id.pod_tv_progress_4)
//    TextView mTvProgress4;
//
//    @BindView(R.id.pod_tv_progress_5)
//    TextView mTvProgress5;

    @BindView(R.id.pod_card_container_1)
    CardView mCardContainer1;

    @BindView(R.id.pod_card_container_2)
    CardView mCardContainer2;

    @BindView(R.id.pod_card_container_3)
    CardView mCardContainer3;

    @BindView(R.id.pod_card_container_4)
    CardView mCardContainer4;

    @BindView(R.id.pod_card_container_5)
    CardView mCardContainer5;


    @BindView(R.id.pod_grid_content)
    GridLayout mGl;
    int totalView = 10;
    int currentTotalViewShown = 0;
    SquareRelativeLayout[] squareRelativeLayoutArr;
    private boolean isFromCameraActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_podcapture);
        ButterKnife.bind(this);
        squareRelativeLayoutArr = new SquareRelativeLayout[totalView];
//        generateViewsThumbnail();
    }


    @OnClick(R.id.pod_btn_submit)
    public void onSubmit(View view) {

//        int total = 10;
//        int column = 3;
//        int row = total / column;
//        mGl.setColumnCount(column);
//        mGl.setRowCount(row + 1);
//
//        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
//
//            if (c == column) {
//                c = 0;
//                r++;
//            }
//            ImageView oImageView = new ImageView(this);
//            oImageView.setImageResource(R.drawable.ic_empty_attendance);
//
//            oImageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//
//            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            if (r == 0 && c == 0) {
//
//                colspan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//            }
//
//            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colspan);
//
//            mGl.addView(oImageView, gridParam);
//        }

        View photoThumbnail = LayoutInflater.from(this).inflate(R.layout.single_pod_thumbnail, null);
        mGl.addView(photoThumbnail);


//        addThumbnail();


//        View viewLay = LayoutInflater.from(PODCaptureActivity.this).inflate(R.layout.single_pod_thumbnail, null);
//        SquareRelativeLayout squareRelativeLayout = (SquareRelativeLayout)viewLay.findViewById(R.id.squareRelLayout);
////        squareRelativeLayout.refreshSize();
//
//        for (int i = 0; i <= totalView; i++) {
//            mGl.addView(squareRelativeLayout);
//        }
//        totalView++;
//        mGl.invalidate();
//        squareRelativeLayout.refreshSize();

//        ImageView oImageView = new ImageView(this);
//        oImageView.setImageResource(R.drawable.ic_empty_attendance);
//        oImageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//        mGl.addView(oImageView);

//        int progress1 = 0;
//        while (progress1 < 100) {
//            progress1 += 4;
//            if (progress1 >= 100) {
//                progress1 = 100;
//            }
//            mPbProgress1.setProgress(progress1);
//            mTvProgress1.setText(progress1 + "%");
//        }
//
//        int progress2 = 0;
//        while (progress2 < 100) {
//            progress2 += 3;
//            if (progress2 >= 100) {
//                progress2 = 100;
//            }
//            mPbProgress2.setProgress(progress2);
//            mTvProgress2.setText(progress2 + "%");
//        }
//
//        int progress3 = 0;
//        while (progress3 < 100) {
//            progress3 += 2;
//            if (progress3 >= 100) {
//                progress3 = 100;
//            }
//            mPbProgress3.setProgress(progress3);
//            mTvProgress3.setText(progress3 + "%");
//        }
//
//        int progress4 = 0;
//        while (progress4 < 100) {
//            progress4 += 1;
//            if (progress4 >= 100) {
//                progress4 = 100;
//            }
//            mPbProgress4.setProgress(progress4);
//            mTvProgress4.setText(progress4 + "%");
//        }
    }

    private void addThumbnail() {
        if (currentTotalViewShown < 10) {
            squareRelativeLayoutArr[currentTotalViewShown].setVisibility(View.VISIBLE);
            currentTotalViewShown++;
        }
    }

    private void generateViewsThumbnail() {
        for (int i = 0; i < totalView; i++) {
            View viewLay = LayoutInflater.from(PODCaptureActivity.this).inflate(R.layout.single_pod_thumbnail, null);
            SquareRelativeLayout squareRelativeLayout = (SquareRelativeLayout) viewLay.findViewById(R.id.squareRelLayout);
            squareRelativeLayout.setLayoutParams(new GridLayout.LayoutParams());
            squareRelativeLayout.invalidate();
            squareRelativeLayout.requestLayout();
            mGl.addView(squareRelativeLayout);
            mGl.invalidate();
            mGl.requestLayout();
            squareRelativeLayout.invalidate();
            squareRelativeLayout.requestLayout();
            squareRelativeLayoutArr[i] = squareRelativeLayout;
            Snackbar.make(mGl, "Created : " + i, Snackbar.LENGTH_SHORT);
//            if (i < 3) {
//                if (i == 0) {
//                    squareRelativeLayout.setVisibility(View.VISIBLE);
//                    currentTotalViewShown++;
//                } else {
//                    squareRelativeLayout.setVisibility(View.INVISIBLE);
//                }
//            } else {
//                squareRelativeLayout.setVisibility(View.GONE);
//            }
        }
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
    public PODCapturePresenter providePresenter() {
        return new PODCapturePresenter(new RestConnection(PODCaptureActivity.this));
    }

    @Override
    @OnClick({R.id.pod_card_container_1, R.id.pod_card_container_2, R.id.pod_card_container_3, R.id.pod_card_container_4, R.id.pod_card_container_5})
    public void onClickAddPhoto(View view) {
        getPresenter().capturePhoto(view.getId());
    }

    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD) {
        CardView cardView = (CardView) findViewById(targetIvID);
        int count = cardView.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = cardView.getChildAt(i);
            if (v instanceof SquareImageView) {
                SquareImageView squareImageView = (SquareImageView) v;
                squareImageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClickSubmit(View view) {

    }

    @Override
    public void showConfirmationDialog(String activityName) {

    }

    @Override
    public void setSubmitText(String text) {

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
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        }
    }
}
