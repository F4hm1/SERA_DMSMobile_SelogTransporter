package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class StatusUpdateSendModel extends Model {

    @SerializedName("ActivityCode")
    @Expose
    private String ActivityCode;

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("LocationRealCoordinates")
    @Expose
    private String LocationRealCoordinates;

    @SerializedName("LocationRealText")
    @Expose
    private String LocationRealText;

    @SerializedName("Photo1")
    @Expose
    private String Photo1;

    @SerializedName("Photo2")
    @Expose
    private String Photo2;

    @SerializedName("Photo3")
    @Expose
    private String Photo3;

    @SerializedName("CodeVerification")
    @Expose
    private String CodeVerification;

    @SerializedName("Signature")
    @Expose
    private String Signature;

    @SerializedName("Expenses")
    @Expose
    private String Expenses;

    public StatusUpdateSendModel(String activityCode, String personalId, String locationRealCoordinates, String locationRealText, String photo1, String photo2, String photo3, String codeVerification, String signature, String expenses) {
        ActivityCode = activityCode;
        PersonalId = personalId;
        LocationRealCoordinates = locationRealCoordinates;
        LocationRealText = locationRealText;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        CodeVerification = codeVerification;
        Signature = signature;
        Expenses = expenses;
    }

    public String getActivityCode() {
        return ActivityCode;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getLocationRealCoordinates() {
        return LocationRealCoordinates;
    }

    public String getLocationRealText() {
        return LocationRealText;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public String getCodeVerification() {
        return CodeVerification;
    }

    public String getSignature() {
        return Signature;
    }

    public String getExpenses() {
        return Expenses;
    }
}
