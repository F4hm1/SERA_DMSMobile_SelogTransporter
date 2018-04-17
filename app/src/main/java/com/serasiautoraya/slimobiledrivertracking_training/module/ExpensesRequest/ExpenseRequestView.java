package com.serasiautoraya.slimobiledrivertracking_training.module.ExpensesRequest;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by randi on 03/07/2017.
 */

public interface ExpenseRequestView extends BaseViewInterface, FormViewInterface{

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingSearchingOrder(boolean isLoading);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void setExpenseInputForm(HashMap<String, ExpenseInputModel> expenseInputList, String[] typeCodeList);

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingInitialLoad(boolean isLoading);

    void setNoAvailableExpense();

    void initializeOvertimeDates(ExpenseAvailableResponseModel expenseAvailableResponseModel);

    void showConfirmationSuccess(String message, String title);

    void setTotalExpense(String total);

    void hideRequestGroupInput();

    void resetAmountView();

    void changeActivityAction(String[] key, String[] value, Class targetActivity);

}
