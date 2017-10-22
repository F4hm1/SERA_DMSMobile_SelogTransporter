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

    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateStart,
            String dateEnd,
            String absenceType,
            String requestDate,
            String requestStatus,
            String approvalBy
    );
}
