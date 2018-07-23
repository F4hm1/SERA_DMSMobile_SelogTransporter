package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 31/05/2018.
 * for SERA
 */

public class StatusKlaimSendModel extends Model {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    @SerializedName("ActivityCode")
    @Expose
    private String ActivityCode;


    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;


    @SerializedName("DocumentNo")
    @Expose
    private String DocumentNo;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Quantity")
    @Expose
    private String Quantity;

    @SerializedName("Amount")
    @Expose
    private String Amount;

    @SerializedName("Customer")
    @Expose
    private String Customer;

    @SerializedName("Location")
    @Expose
    private String Location;

    @SerializedName("Photo1")
    @Expose
    private String Photo1;


    @SerializedName("Photo2")
    @Expose
    private String Photo2;


    @SerializedName("Photo3")
    @Expose
    private String Photo3;


    public StatusKlaimSendModel(String orderCode, String assignmentId, String activityCode, String personalId, String personalCode, String documentNo, String description, String quantity, String amount, String customer, String location, String photo1, String photo2, String photo3) {
        OrderCode = orderCode;
        AssignmentId = assignmentId;
        ActivityCode = activityCode;
        PersonalId = personalId;
        PersonalCode = personalCode;
        DocumentNo = documentNo;
        Description = description;
        Quantity = quantity;
        Amount = amount;
        Customer = customer;
        Location = location;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
    }

    public String getActivityCode() {
        return ActivityCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getPersonalId() {
        return PersonalId;
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

    public String getAssignmentId() {
        return AssignmentId;
    }

    public String getPersonalCode() {
        return PersonalCode;
    }


    public String getDocumentNo() {
        return DocumentNo;
    }

    public String getDescription() {
        return Description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public String getCustomer() {
        return Customer;
    }

    public String getLocation() {
        return Location;
    }
}
