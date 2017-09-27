package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareImageView;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodSubmitActivity extends TiActivity<PodSubmitPresenter, PodSubmitView> implements PodSubmitView {

    @BindView(R.id.pod_gv_container)
    GridView mGvContainer;

    private SquareImageView mCurrentIvThumbnail;

    private ProgressDialog mProgressDialog;
    private PodListAdapter mPodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_capture);
        ButterKnife.bind(this);
    }

    @Override
    public void initialize() {
        mPodListAdapter = new PodListAdapter(this, new PodItemOnClickListener() {
            @Override
            public void onCapturePhoto(int position, SquareImageView squareImageView) {
                Snackbar.make(mGvContainer, "Captured: "+position, Snackbar.LENGTH_SHORT).show();
                squareImageView.setImageResource(R.drawable.ic_empty_attendance);
                mCurrentIvThumbnail = squareImageView;
            }

            @Override
            public void onCloseThumbnail(int position, ImageButton imageButton) {
                Snackbar.make(mGvContainer, "Closed: "+position, Snackbar.LENGTH_SHORT).show();
                imageButton.setVisibility(View.GONE);
            }
        });

        mPodListAdapter.addItem(new PodItemModel(null));
        mPodListAdapter.addItem(new PodItemModel(null));
        mPodListAdapter.addItem(new PodItemModel(null));
        mPodListAdapter.addItem(new PodItemModel(null));
        mGvContainer.setAdapter(mPodListAdapter);

        mPodListAdapter.notifyDataSetChanged();
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
//                    isFromCameraActivity = true;
                }
                break;
            }
        }
    }

}
