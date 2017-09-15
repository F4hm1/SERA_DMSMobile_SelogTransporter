package com.serasiautoraya.slimobiledrivertracking.MVP.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableSendModel extends Model {

    @SerializedName("Ids")
    @Expose
    private String PersonalId;

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    public ExpenseAvailableSendModel(String personalId, String assignmentId) {
        PersonalId = personalId;
        AssignmentId = assignmentId;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getAssignmentId() {
        return AssignmentId;
    }
}
