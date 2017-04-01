package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiFragment;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class PlanOrderFragment extends TiFragment<PlanOrderPresenter, PlanOrderView> implements PlanOrderView {



    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public PlanOrderPresenter providePresenter() {
        return new PlanOrderPresenter();
    }
}
