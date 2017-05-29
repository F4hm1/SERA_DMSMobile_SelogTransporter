package com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory;

import android.text.Editable;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 22/05/2017.
 */

public interface OrderHistoryView  extends BaseViewInterface{
    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);

    void setTextStartDate(String startDate);

    void setTextEndDate(String endDate);

    void onTextEndDateChangeAfter(Editable editable);

    void onTextStartDateChangeAfter(Editable editable);

}
