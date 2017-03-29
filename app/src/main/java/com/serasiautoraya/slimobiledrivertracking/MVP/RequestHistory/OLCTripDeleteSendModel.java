package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OLCTripDeleteSendModel extends Model{
    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("RequestID")
    @Expose
    private String RequestID;

    public OLCTripDeleteSendModel(String personalId, String requestID) {
        PersonalId = personalId;
        RequestID = requestID;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getRequestID() {
        return RequestID;
    }
}
