package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class ActivityDetailSendModel extends Model{

    @SerializedName("AssignmentId")
    @Expose
    private Integer AssignmentId;

    public ActivityDetailSendModel(Integer assignmentId) {
        AssignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }
}
