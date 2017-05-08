package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class ActivityDetailResponseModel {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("ActivityCode")
    @Expose
    private String ActivityCode;

    @SerializedName("ActivityName")
    @Expose
    private String ActivityName;

    @SerializedName("ButtonActivityColor")
    @Expose
    private String ButtonActivityColor;

    @SerializedName("AcitivityType")
    @Expose
    private String AcitivityType;

    @SerializedName("IsPhoto")
    @Expose
    private String IsPhoto;

    @SerializedName("IsSignature")
    @Expose
    private String IsSignature;

    @SerializedName("IsExpenseForm")
    @Expose
    private String IsExpenseForm;

    @SerializedName("IsCodeVerification")
    @Expose
    private String IsCodeVerification;

    @SerializedName("CodeVerification")
    @Expose
    private String CodeVerification;

    @SerializedName("TimeTarget")
    @Expose
    private String TimeTarget;

    @SerializedName("TimeBaseline")
    @Expose
    private String TimeBaseline;

    @SerializedName("TimeActual")
    @Expose
    private String TimeActual;

    @SerializedName("LocationTargetCoordinates")
    @Expose
    private String LocationTargetCoordinates;

    @SerializedName("LocationTargetText")
    @Expose
    private String LocationTargetText;

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

    @SerializedName("Customer")
    @Expose
    private String Customer;

    @SerializedName("CargoType")
    @Expose
    private String CargoType;

    @SerializedName("UnitModel")
    @Expose
    private String UnitModel;

    @SerializedName("UnitNumber")
    @Expose
    private String UnitNumber;

    @SerializedName("NextActivity")
    @Expose
    private String NextActivity;

    @SerializedName("NextActivityLocation")
    @Expose
    private String NextActivityLocation;

    @SerializedName("NextActivityTimeTarget")
    @Expose
    private String NextActivityTimeTarget;

    public String getOrderCode() {
        return OrderCode;
    }

    public String getActivityCode() {
        return ActivityCode;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public String getButtonActivityColor() {
        return ButtonActivityColor;
    }

    public String getAcitivityType() {
        return AcitivityType;
    }

    public String getIsPhoto() {
        return IsPhoto;
    }

    public String getIsSignature() {
        return IsSignature;
    }

    public String getIsExpenseForm() {
        return IsExpenseForm;
    }

    public String getIsCodeVerification() {
        return IsCodeVerification;
    }

    public String getCodeVerification() {
        return CodeVerification;
    }

    public String getTimeTarget() {
        return TimeTarget;
    }

    public String getTimeBaseline() {
        return TimeBaseline;
    }

    public String getTimeActual() {
        return TimeActual;
    }

    public String getLocationTargetCoordinates() {
        return LocationTargetCoordinates;
    }

    public String getLocationTargetText() {
        return LocationTargetText;
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

    public String getCustomer() {
        return Customer;
    }

    public String getCargoType() {
        return CargoType;
    }

    public String getUnitModel() {
        return UnitModel;
    }

    public String getUnitNumber() {
        return UnitNumber;
    }

    public String getNextActivity() {
        return NextActivity;
    }

    public String getNextActivityLocation() {
        return NextActivityLocation;
    }

    public String getNextActivityTimeTarget() {
        return NextActivityTimeTarget;
    }
}
