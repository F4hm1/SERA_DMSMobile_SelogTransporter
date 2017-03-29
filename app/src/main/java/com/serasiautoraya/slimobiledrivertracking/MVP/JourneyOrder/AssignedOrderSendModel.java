package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    public AssignedOrderSendModel(String personalId) {
        PersonalId = personalId;
    }

    public String getPersonalId() {
        return PersonalId;
    }
}
