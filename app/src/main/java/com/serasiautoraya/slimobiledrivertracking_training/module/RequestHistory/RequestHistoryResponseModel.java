package com.serasiautoraya.slimobiledrivertracking_training.module.RequestHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class RequestHistoryResponseModel extends Model {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("TransType")
    @Expose
    private String TransType;

    @SerializedName("DateStart")
    @Expose
    private String DateStart;

    @SerializedName("DateEnd")
    @Expose
    private String DateEnd;

    @SerializedName("TimeStart")
    @Expose
    private String TimeStart;

    @SerializedName("TimeEnd")
    @Expose
    private String TimeEnd;

    @SerializedName("OvertimeType")
    @Expose
    private String OvertimeType;

    @SerializedName("AbsenceType")
    @Expose
    private String AbsenceType;

    @SerializedName("TripCount")
    @Expose
    private String TripCount;

    @SerializedName("OLCStatus")
    @Expose
    private String OLCStatus;

    @SerializedName("RequestDate")
    @Expose
    private String RequestDate;

    @SerializedName("RequestStatus")
    @Expose
    private String RequestStatus;

    @SerializedName("ApprovalBy")
    @Expose
    private String ApprovalBy;

    /*
    * TODO Delete this constructor and SET method (not needed)
    * */

    public RequestHistoryResponseModel(String id, String transType, String dateStart, String dateEnd, String timeStart, String timeEnd, String overtimeType, String absenceType, String tripCount, String OLCStatus, String requestDate, String requestStatus, String approvalBy) {
        Id = id;
        TransType = transType;
        DateStart = dateStart;
        DateEnd = dateEnd;
        TimeStart = timeStart;
        TimeEnd = timeEnd;
        OvertimeType = overtimeType;
        AbsenceType = absenceType;
        TripCount = tripCount;
        this.OLCStatus = OLCStatus;
        RequestDate = requestDate;
        RequestStatus = requestStatus;
        ApprovalBy = approvalBy;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getId() {
        return Id;
    }

    public String getTransType() {
        return TransType;
    }

    public String getDateStart() {
        return DateStart;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public String getTimeStart() {
        return TimeStart;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    public String getOvertimeType() {
        return OvertimeType;
    }

    public String getAbsenceType() {
        return AbsenceType;
    }

    public String getTripCount() {
        return TripCount;
    }

    public String getOLCStatus() {
        return OLCStatus;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public String getApprovalBy() {
        return ApprovalBy;
    }

}
