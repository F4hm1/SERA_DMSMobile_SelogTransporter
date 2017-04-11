package com.serasiautoraya.slimobiledrivertracking.MVP.Profiling;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

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
            String sIMExp
    );

    void setProfilePhoto(String url);
}
