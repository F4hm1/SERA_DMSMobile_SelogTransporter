package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.FormViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import java.util.ArrayList;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public interface OvertimeRequestView extends BaseViewInterface, FormViewInterface{

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingInitialLoad(boolean isLoading);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void initializeOvertimeDates(ArrayList<OvertimeAvailableResponseModel> arrayList);
}
