package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AbsenceDeleteSendModel {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    public AbsenceDeleteSendModel(String personalId, String requestId) {
        PersonalId = personalId;
        RequestId = requestId;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getRequestId() {
        return RequestId;
    }
}
