package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.Absence;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestHistoryView extends BaseViewInterface {

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    void refreshAllData();

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);
}
