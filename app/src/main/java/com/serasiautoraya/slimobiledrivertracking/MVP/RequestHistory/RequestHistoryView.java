package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory;

import android.text.Editable;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface RequestHistoryView extends BaseViewInterface{

    void onTextEndDateChangeAfter(Editable editable);

    void onTextStartDateChangeAfter(Editable editable);

    void setTextEndDate(String textEndDate);

    void setTextStartDate(String textStartDate);

}
