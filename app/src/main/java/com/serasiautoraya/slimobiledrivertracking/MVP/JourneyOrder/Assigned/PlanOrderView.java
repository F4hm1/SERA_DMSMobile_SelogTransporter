package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface PlanOrderView extends BaseViewInterface{

    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    void showAcknowledgeDialog();

}
