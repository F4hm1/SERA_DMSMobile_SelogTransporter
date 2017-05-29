package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.OLCTrip;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface OLCTripRequestHistoryView extends BaseViewInterface{

    void refreshRecyclerView();

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);

}
