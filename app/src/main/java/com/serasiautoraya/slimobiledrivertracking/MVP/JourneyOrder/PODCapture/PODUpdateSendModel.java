package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by randi on 15/08/2017.
 */

public class PODUpdateSendModel extends Model{


    @SerializedName("PersonalId")
    @Expose
    public String personalId;
    @SerializedName("ActivityCode")
    @Expose
    public String activityCode;
    @SerializedName("Reason")
    @Expose
    public String reason;
    @SerializedName("TimeStamp")
    @Expose
    public String timeStamp;

    /**
     * No args constructor for use in serialization
     *
     */
    public PODUpdateSendModel() {
    }

    /**
     *
     * @param activityCode
     * @param timeStamp
     * @param reason
     * @param personalId
     */
    public PODUpdateSendModel(String personalId, String activityCode, String reason, String timeStamp) {
        super();
        this.personalId = personalId;
        this.activityCode = activityCode;
        this.reason = reason;
        this.timeStamp = timeStamp;
    }

}
