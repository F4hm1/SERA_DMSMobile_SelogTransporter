package com.serasiautoraya.slimobiledrivertracking_training.module.WsInOutHistory;

import android.text.Editable;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;

/**
 * Created by randi on 24/07/2017.
 */

public interface WsInOutView extends BaseViewInterface{

    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    void toggleEmptyInfo(boolean show);

    void setTextStartDate(String startDate);

    void setTextEndDate(String endDate);

    void onTextEndDateChangeAfter(Editable editable);

    void onTextStartDateChangeAfter(Editable editable);

    void showDetailDialog(
            String date,
            String wsIn,
            String wsOut,
            String clockIn,
            String clockOut,
            String absence
    );

}
