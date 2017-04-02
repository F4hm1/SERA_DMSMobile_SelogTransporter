package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class ActivityDetailSendModel extends Model{

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    public ActivityDetailSendModel(String orderCode, String personalId) {
        OrderCode = orderCode;
        PersonalId = personalId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getPersonalId() {
        return PersonalId;
    }
}
