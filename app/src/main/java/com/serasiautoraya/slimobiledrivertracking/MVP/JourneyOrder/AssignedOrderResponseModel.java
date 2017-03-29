package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderResponseModel {

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
}
