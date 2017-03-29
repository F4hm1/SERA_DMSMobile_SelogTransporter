package com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface TimeRestCallBackInterface {

    void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address);

    void callBackOnFail(String message);
}
