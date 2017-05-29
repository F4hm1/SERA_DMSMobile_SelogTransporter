package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory;

import android.text.Editable;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface RequestHistoryView extends BaseViewInterface{

    void onTextEndDateChangeAfter(Editable editable);

    void onTextStartDateChangeAfter(Editable editable);

    void setTextEndDate(String textEndDate);

    void setTextStartDate(String textStartDate);

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingInitialLoad(boolean isLoading);

    void initializeTabs();
}
