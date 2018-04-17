package com.serasiautoraya.slimobiledrivertracking_training.module.Login;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public interface LoginView extends BaseViewInterface, FormViewInterface {

    void onSubmitButtonClicked(View view);

    void changeActivity(Class targetActivity);

    void startInitializeLocation();

    void setCachedFormLogin(String username, String password);

    void goToPlayStore();
}
