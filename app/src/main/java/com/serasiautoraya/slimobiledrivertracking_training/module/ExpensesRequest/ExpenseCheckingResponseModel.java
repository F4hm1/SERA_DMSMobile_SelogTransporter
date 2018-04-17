package com.serasiautoraya.slimobiledrivertracking_training.module.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 05/04/2018.
 * for SERA
 */

public class ExpenseCheckingResponseModel extends Model {

    @SerializedName("ExpenseAvailability")
    @Expose
    private String ExpenseAvailability;

    public ExpenseCheckingResponseModel(String ExpenseAvailability) {
        ExpenseAvailability = ExpenseAvailability;
    }

    public String getCheckingStatus() {
        return ExpenseAvailability;
    }

}
