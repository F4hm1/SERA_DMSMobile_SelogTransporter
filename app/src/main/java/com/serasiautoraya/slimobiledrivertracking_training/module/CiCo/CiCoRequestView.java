package com.serasiautoraya.slimobiledrivertracking_training.module.CiCo;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface CiCoRequestView extends BaseViewInterface, FormViewInterface {

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
