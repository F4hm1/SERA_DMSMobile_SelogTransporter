package com.serasiautoraya.slimobiledrivertracking_training.module.Fatigue;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 12/04/2017.
 */

public interface FatigueView extends BaseViewInterface, FormViewInterface{

    void onClickSubmit();

    void showConfirmationDialog();

    void showSuccessDialog(String message, String title);

    void finishActivity();

}
