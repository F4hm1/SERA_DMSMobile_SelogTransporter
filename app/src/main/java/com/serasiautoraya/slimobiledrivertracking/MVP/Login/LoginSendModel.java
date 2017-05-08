package com.serasiautoraya.slimobiledrivertracking.MVP.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class LoginSendModel extends Model{

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("TokenFCM")
    @Expose
    private String tokenFCM;

    @SerializedName("IMEIPhone")
    @Expose
    private String iMEIPhone;

    public LoginSendModel(String username, String password, String tokenFCM, String iMEIPhone) {
        this.username = username;
        this.password = password;
        this.tokenFCM = tokenFCM;
        this.iMEIPhone = iMEIPhone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTokenFCM() {
        return tokenFCM;
    }

    public String getiMEIPhone() {
        return iMEIPhone;
    }
}
