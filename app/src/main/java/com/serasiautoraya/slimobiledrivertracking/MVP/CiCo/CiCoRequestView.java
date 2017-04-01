package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface CiCoRequestView extends BaseViewInterface, FormViewInterface {

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
