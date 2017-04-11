package com.serasiautoraya.slimobiledrivertracking.MVP.Dashboard;

import android.support.annotation.NonNull;
import com.serasiautoraya.slimobiledrivertracking.MVP.Profiling.ProfileActivity;
import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class DashboardPresenter extends TiPresenter<DashboardView> {

    @Override
    protected void onAttachView(@NonNull final DashboardView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public void onNavigationItemSelectedForFragment(int id){
        getView().changeFragment(getView().getActiveFragment(id));
    }

    public void onNavigationItemSelectedForActivity(int id){
        getView().changeActivity(getView().getTargetActivityClass(id));
    }

    public void loadDetailProfile(){
        getView().changeActivity(ProfileActivity.class);
    }

}
