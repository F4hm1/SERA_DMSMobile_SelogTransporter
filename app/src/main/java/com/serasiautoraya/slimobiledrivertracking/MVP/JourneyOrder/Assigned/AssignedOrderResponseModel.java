package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderResponseModel extends Model{

    @SerializedName("OrderID")
    @Expose
    public String OrderID;
    @SerializedName("AssignmentID")
    @Expose
    public Integer AssignmentId;
    @SerializedName("Status")
    @Expose
    public Integer Status;
    @SerializedName("Customer")
    @Expose
    public String Customer;
    @SerializedName("Origin")
    @Expose
    public String Origin;
    @SerializedName("Destination")
    @Expose
    public String Destination;
    @SerializedName("ETA")
    @Expose
    public String ETA;
    @SerializedName("ETD")
    @Expose
    public String ETD;
    @SerializedName("CurrentActivity")
    @Expose
    public String CurrentActivity;
    @SerializedName("Active")
    @Expose
    public String Active;
    @SerializedName("Acknowledge")
    @Expose
    public String Acknowledge;


    public AssignedOrderResponseModel(String orderID, Integer assignmentID, Integer status, String customer, String origin, String destination, String ETA, String ETD) {
        OrderID = orderID;
        AssignmentId = assignmentID;
        Status = status;
        Customer = customer;
        Origin = origin;
        Destination = destination;
        this.ETA = ETA;
        this.ETD = ETD;
    }

    public String getOrderID() {
        return OrderID;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }

    public Integer getStatus() {
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

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setAssignmentId(Integer assignmentId) {
        AssignmentId = assignmentId;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
    }

    public void setETD(String ETD) {
        this.ETD = ETD;
    }

    public String getCurrentActivity() {
        return CurrentActivity;
    }

    public String getActive() {
        return Active;
    }

    public String getAcknowledge() {
        return Acknowledge;
    }
}
