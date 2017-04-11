package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.Absence;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestHistoryView extends BaseViewInterface {

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

}
