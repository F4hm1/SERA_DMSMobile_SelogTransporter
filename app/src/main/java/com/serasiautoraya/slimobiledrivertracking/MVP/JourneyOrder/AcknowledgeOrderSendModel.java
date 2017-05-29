package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AcknowledgeOrderSendModel extends Model {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("DateAck")
    @Expose
    private String DateAck;

    @SerializedName("TimeAck")
    @Expose
    private String TimeAck;

    public AcknowledgeOrderSendModel(String orderCode, String personalId, String dateAck, String timeAck) {
        OrderCode = orderCode;
        PersonalId = personalId;
        DateAck = dateAck;
        TimeAck = timeAck;
    }

    public String getDateAck() {
        return DateAck;
    }

    public String getTimeAck() {
        return TimeAck;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getPersonalId() {
        return PersonalId;
    }
}
