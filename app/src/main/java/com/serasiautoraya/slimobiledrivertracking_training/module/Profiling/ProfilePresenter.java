package com.serasiautoraya.slimobiledrivertracking_training.module.Profiling;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ProfilePresenter extends TiPresenter<ProfileView> {

    @Override
    protected void onAttachView(@NonNull final ProfileView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public void loadProfileData(){
        getView().setProfileContent(
                HelperBridge.sModelLoginResponse.getFullname(),
                "Driver",
                HelperBridge.sModelLoginResponse.getCompany(),
                HelperBridge.sModelLoginResponse.getPoolName(),
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getFullname(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalName(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorName(),
                HelperBridge.sModelLoginResponse.getKtpEndDate().equalsIgnoreCase("")?"-": HelperUtil.getUserFormDate(HelperBridge.sModelLoginResponse.getKtpEndDate()),
                HelperBridge.sModelLoginResponse.getSIMEndDate().equalsIgnoreCase("")?"-": HelperUtil.getUserFormDate(HelperBridge.sModelLoginResponse.getSIMEndDate()),
                "Tanggal Berakhir "+ HelperBridge.sModelLoginResponse.getSimType()
        );
        getView().setProfilePhoto(HelperBridge.sModelLoginResponse.getPhotoFront());
    }

}
