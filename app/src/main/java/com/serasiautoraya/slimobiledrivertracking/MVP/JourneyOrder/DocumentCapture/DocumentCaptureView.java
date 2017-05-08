package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.DocumentCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public interface DocumentCaptureView extends BaseViewInterface, FormViewInterface {
    void onTextFuelChangeAfter(Editable editable);

    void onClickPhotoCapture1(View view);

    void onClickPhotoCapture2(View view);

    void onClickPhotoCapture3(View view);

    boolean onTouchSignatureCapture(ImageView view, MotionEvent motionEvent);

    void onClickSignatureCapture(View view);

    void startActivityCapture(Intent intent);

    void startActivitySignature();

    void setImageThumbnail(Bitmap bitmap, int targetIvID);

    void setImageSign(Bitmap bitmap);

    void onClickSubmit(View view);

    void showConfirmationDialog(String activityName);


}
