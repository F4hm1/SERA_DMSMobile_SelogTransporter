package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface ActiveOrderView extends BaseViewInterface {

    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);

}
