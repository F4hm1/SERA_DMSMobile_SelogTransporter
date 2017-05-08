package com.serasiautoraya.slimobiledrivertracking.MVP.Fatigue;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 12/04/2017.
 */

public interface FatigueView extends BaseViewInterface, FormViewInterface{

    void onClickSubmit();

    void showConfirmationDialog();

    void showSuccessDialog(String message, String title);

    void finishActivity();

}
