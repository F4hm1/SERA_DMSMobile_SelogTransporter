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

    @SerializedName("FCM")
    @Expose
    private String tokenFCM;

    public LoginSendModel(String username, String password, String tokenFCM) {
        this.username = username;
        this.password = password;
        this.tokenFCM = tokenFCM;
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
}
