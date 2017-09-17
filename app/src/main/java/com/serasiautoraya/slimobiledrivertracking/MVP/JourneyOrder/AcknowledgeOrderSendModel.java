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
    private Integer AssignmentId;

    public AcknowledgeOrderSendModel(String personalId, Integer assignmentId) {
        PersonalId = personalId;
        AssignmentId = assignmentId;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }
}
