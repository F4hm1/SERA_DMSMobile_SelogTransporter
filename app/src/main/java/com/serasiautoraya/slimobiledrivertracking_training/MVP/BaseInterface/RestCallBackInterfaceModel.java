package com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseModel.BaseResponseModel;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public interface RestCallBackInterfaceModel {

    void callBackOnSuccess(BaseResponseModel response);

    void callBackOnFail(String response);

    void callBackOnError(VolleyError error);
}
