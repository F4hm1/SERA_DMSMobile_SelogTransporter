package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderResponseModel extends Model{

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Customer")
    @Expose
    private String Customer;

    @SerializedName("Origin")
    @Expose
    private String Origin;

    @SerializedName("Destination")
    @Expose
    private String Destination;

    @SerializedName("ETA")
    @Expose
    private String ETA;

    @SerializedName("ETD")
    @Expose
    private String ETD;

    @SerializedName("Active")
    @Expose
    private String Active;

    @SerializedName("Acknowledge")
    @Expose
    private String Acknowledge;

    public AssignedOrderResponseModel(String orderCode, String status, String customer, String origin, String destination, String ETA, String ETD, String active, String acknowledge) {
        OrderCode = orderCode;
        Status = status;
        Customer = customer;
        Origin = origin;
        Destination = destination;
        this.ETA = ETA;
        this.ETD = ETD;
        Active = active;
        Acknowledge = acknowledge;
    }

    public String getAcknowledge() {
        return Acknowledge;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getStatus() {
        return Status;
    }

    public String getCustomer() {
        return Customer;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getDestination() {
        return Destination;
    }

    public String getETA() {
        return ETA;
    }

    public String getETD() {
        return ETD;
    }

    public String getActive() {
        return Active;
    }
}
