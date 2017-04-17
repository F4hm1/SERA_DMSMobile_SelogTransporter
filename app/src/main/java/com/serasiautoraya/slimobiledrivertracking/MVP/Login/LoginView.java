package com.serasiautoraya.slimobiledrivertracking.MVP.Login;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public interface LoginView extends BaseViewInterface, FormViewInterface {

    void onSubmitButtonClicked(View view);

    void changeActivity(Class targetActivity);

    void startInitializeLocation();
}
