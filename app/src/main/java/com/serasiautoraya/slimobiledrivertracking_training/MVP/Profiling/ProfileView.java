package com.serasiautoraya.slimobiledrivertracking_training.MVP.Profiling;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public interface ProfileView extends BaseViewInterface{

    void setProfileContent(
            String nameFp,
            String posisiFp,
            String companyFp,
            String poolNameFp,
            String nrp,
            String fullname,
//          String training,
            String userCostumer,
            String doo,
            String kTPExp,
            String sIMExp,
            String sIMType
    );

    void setProfilePhoto(String url);
}
