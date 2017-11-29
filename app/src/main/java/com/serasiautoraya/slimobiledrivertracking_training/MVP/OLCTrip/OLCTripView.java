package com.serasiautoraya.slimobiledrivertracking_training.MVP.OLCTrip;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public interface OLCTripView extends BaseViewInterface, FormViewInterface{

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
