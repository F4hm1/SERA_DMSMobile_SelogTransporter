package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Activity;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public interface ActivityDetailView extends BaseViewInterface {

    void onActionClicked(View view);

    void setDetailData(
            String codeHead,
            String code,
            String activityName,
            String activityType,
            String origin,
            String[] destination,
            String etd,
            String eta,
            String customer,
            String locationTarget,
            String timeTarget,
            String timeBaseline,
            String timeActual,
            String assignmentId,
            String cargoType,
            String unitModel,
            String unitnumber,
            String docNeed,
            String nextActivity,
            String nextActivityAddress,
            String addressTarget,
            String notes,
            String cargoDescription
    );

    void setButtonText(String text);

    void setButtonColor(String hexaCode);

    void changeActivity(Class cls);

    void showConfirmationDialog(String title, String activity);

    void showConfirmationAlertDialog(String title, String message, String activity);

    void finishActivity();

    void showConfirmationSuccess(String message, String title);

    void toggleButtonAction(boolean show);

    void generateDestination(String[] arrDestination);

    void setTempFragmentTarget(int id);

}
