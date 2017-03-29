package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OvertimeAvailableResponseModel {

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("IdCico")
    @Expose
    private String IdCico;

    @SerializedName("ScheduleIn")
    @Expose
    private String ScheduleIn;

    @SerializedName("ScheduleOut")
    @Expose
    private String ScheduleOut;

    @SerializedName("OvertimeStart")
    @Expose
    private String OvertimeStart;

    @SerializedName("OvertimeEnd")
    @Expose
    private String OvertimeEnd;

    @SerializedName("Freeday")
    @Expose
    private String Freeday;

    public String getDate() {
        return Date;
    }

    public String getIdCico() {
        return IdCico;
    }

    public String getScheduleIn() {
        return ScheduleIn;
    }

    public String getScheduleOut() {
        return ScheduleOut;
    }

    public String getOvertimeStart() {
        return OvertimeStart;
    }

    public String getOvertimeEnd() {
        return OvertimeEnd;
    }

    public String getFreeday() {
        return Freeday;
    }
}
