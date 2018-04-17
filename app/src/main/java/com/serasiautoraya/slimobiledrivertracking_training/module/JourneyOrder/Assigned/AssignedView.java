package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Assigned;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface AssignedView extends BaseViewInterface{

    void initializeTabs(boolean isAnyOrderActive, boolean isUpdateLocationActive, String currentAct);

    void onRefreshClicked(View view);

    void refreshAssignedList();

}
