package com.serasiautoraya.slimobiledrivertracking_training.MVP.RequestHistory.OLCTrip;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface OLCTripRequestHistoryView extends BaseViewInterface{

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    void refreshAllData();

    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateStart,
            String tripCount,
            String oLCStatus ,
            String requestDate,
            String requestStatus,
            String approvalBy
    );

}
