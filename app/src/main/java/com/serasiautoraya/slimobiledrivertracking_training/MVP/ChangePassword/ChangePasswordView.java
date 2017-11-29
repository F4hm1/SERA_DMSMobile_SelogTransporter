package com.serasiautoraya.slimobiledrivertracking_training.MVP.ChangePassword;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public interface ChangePasswordView extends BaseViewInterface{

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    boolean getValidationForm(String savedPassword);
}
