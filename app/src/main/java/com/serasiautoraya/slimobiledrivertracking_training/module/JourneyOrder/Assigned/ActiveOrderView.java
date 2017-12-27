package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Assigned;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface ActiveOrderView extends BaseViewInterface {

    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    void toggleEmptyInfo(boolean show);

    void setTextEmptyInfoStatus(boolean success);

}
