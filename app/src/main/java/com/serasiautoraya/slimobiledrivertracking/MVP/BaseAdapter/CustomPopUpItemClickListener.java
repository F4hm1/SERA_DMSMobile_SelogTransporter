package com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter;

import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;

/**
 * Created by Randi Dwi Nandra on 04/04/2017.
 */

public interface CustomPopUpItemClickListener {

    boolean startAction(RequestHistoryResponseModel requestHistoryResponseModel, int menuId);

}
