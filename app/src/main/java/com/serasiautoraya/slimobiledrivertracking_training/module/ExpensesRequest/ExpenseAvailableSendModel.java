package com.serasiautoraya.slimobiledrivertracking_training.module.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableSendModel extends Model {

    @SerializedName("asignmentId")
    @Expose
    private Integer asignmentId;

    public ExpenseAvailableSendModel(Integer assignmentId) {
        this.asignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return this.asignmentId;
    }
}
