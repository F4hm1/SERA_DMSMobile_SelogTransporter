package com.serasiautoraya.slimobiledrivertracking.MVP.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableResponseModel {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("ExpenseTypeCode")
    @Expose
    private String ExpenseTypeCode;

    @SerializedName("ExpenseTypeName")
    @Expose
    private String ExpenseTypeName;

    public String getPersonalId() {
        return PersonalId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getExpenseTypeCode() {
        return ExpenseTypeCode;
    }

    public String getExpenseTypeName() {
        return ExpenseTypeName;
    }
}
