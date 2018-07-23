package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

/**
 * Created by Fahmi Hakim on 31/05/2018.
 * for SERA
 */

public interface KlaimView extends BaseViewInterface, FormViewInterface {

    void onClickPhotoCapture1(View view);

    void onClickPhotoCapture2(View view);

    void onClickPhotoCapture3(View view);

    void showConfirmationDialog(String activityName);

    void onClickSubmit(View view);

    void startActivityCapture(Intent intent);

    void initializeFormContent();

    void setSubmitText(String text);

    void showPhotoPickerSourceDialog();

    void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD);

    void startActivityOpenGallery(Intent intent);

    void showConfirmationSuccess(String message, String title);

    Context returnContext();

    void setEdittext(String total);

}
