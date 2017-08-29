package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.CiCo;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface CiCoRequestHistoryView extends BaseViewInterface{

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateCiCo,
            String requestDate,
            String requestStatus,
            String approvalBy
    );
}
