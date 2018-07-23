package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 04/06/2018.
 * for SERA
 */

public class KlaimAvailableSendModel extends Model {

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    public KlaimAvailableSendModel(String assignmentId) {
        this.AssignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return AssignmentId;
    }
}
