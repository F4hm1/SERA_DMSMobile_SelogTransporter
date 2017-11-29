package com.serasiautoraya.slimobiledrivertracking_training.MVP.Absence;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestView extends BaseViewInterface, FormViewInterface{

    void initializeDatePickerMinRequest(int dayMinRequest);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
