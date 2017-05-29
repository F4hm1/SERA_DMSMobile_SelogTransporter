package com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class OrderHistoryDetailSendModel extends Model {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    public OrderHistoryDetailSendModel(String orderCode) {
        OrderCode = orderCode;
    }

}
