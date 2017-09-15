package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AcknowledgeOrderSendModel extends Model {
    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    public AcknowledgeOrderSendModel(String personalId, String assignmentId) {
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
