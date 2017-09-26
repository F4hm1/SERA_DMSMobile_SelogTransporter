package com.serasiautoraya.slimobiledrivertracking.MVP.Dashboard;

import android.support.annotation.NonNull;
import android.util.Log;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Fatigue.FatigueActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Login.LoginActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.Profiling.ProfileActivity;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class DashboardPresenter extends TiPresenter<DashboardView> {

    private SharedPrefsModel mSharedPrefsModel;

    public DashboardPresenter(SharedPrefsModel sharedPrefsModel) {
        this.mSharedPrefsModel = sharedPrefsModel;
    }

    @Override
    protected void onAttachView(@NonNull final DashboardView view) {
        super.onAttachView(view);
        getView().initialize();
        getView().toggleMenu(
                HelperBridge.sModelLoginResponse.getRequestCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportServiceHour().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
        );
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT);
//        String dateToday = simpleDateFormat.format(calendar.getTime());
//        if(mSharedPrefsModel.get(HelperKey.KEY_LAST_CLOCKIN, "").equalsIgnoreCase(dateToday)
//                && !mSharedPrefsModel.get(HelperKey.KEY_LAST_FATIGUE_INTERVIEW, "").equalsIgnoreCase(dateToday)){
        getView().changeFragment(getView().getActiveFragment(HelperBridge.sTempFragmentTarget));
        HelperBridge.sTempFragmentTarget = 0;
        getView().setDrawerProfile(HelperBridge.sModelLoginResponse.getFullname(), HelperBridge.sModelLoginResponse.getCompany(), HelperBridge.sModelLoginResponse.getPhotoFront());
        if (!HelperBridge.sModelLoginResponse.getIsNeedFatigueInterview().equalsIgnoreCase("0")) {
            getView().changeActivity(FatigueActivity.class);
        }
//        }
    }

    public void onNavigationItemSelectedForFragment(int id) {
        getView().changeFragment(getView().getActiveFragment(id));
        HelperBridge.sTempFragmentTarget = 0;
    }

    public void onNavigationItemSelectedForActivity(int id) {
        getView().changeActivity(getView().getTargetActivityClass(id));
    }

    public void logout() {
        mSharedPrefsModel.clearAll();
        getView().changeActivity(LoginActivity.class);
        getView().showToast("Berhasil keluar");
    }

    public void loadDetailProfile() {
        getView().changeActivity(ProfileActivity.class);
    }

}
