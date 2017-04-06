package com.serasiautoraya.slimobiledrivertracking.MVP.Absence;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestView extends BaseViewInterface, FormViewInterface{

    void initializeDatePickerMinRequest(int dayMinRequest);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
