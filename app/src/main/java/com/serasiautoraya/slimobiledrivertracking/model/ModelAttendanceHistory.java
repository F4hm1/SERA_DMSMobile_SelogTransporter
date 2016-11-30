package com.serasiautoraya.slimobiledrivertracking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 30/11/2016.
 */
public class ModelAttendanceHistory {

    @SerializedName("Tanggal")
    @Expose
    private String tanggal;

    @SerializedName("WsIn")
    @Expose
    private String wsIn;

    @SerializedName("WsOut")
    @Expose
    private String wsOut;

    @SerializedName("Ci")
    @Expose
    private String ci;

    @SerializedName("Co")
    @Expose
    private String co;

    @SerializedName("Absence")
    @Expose
    private String absence;


    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWsIn() {
        return wsIn;
    }

    public void setWsIn(String wsIn) {
        this.wsIn = wsIn;
    }

    public String getWsOut() {
        return wsOut;
    }

    public void setWsOut(String wsOut) {
        this.wsOut = wsOut;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }
}
