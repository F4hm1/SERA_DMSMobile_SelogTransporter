package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiFragment;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public class OvertimeRequestFragment extends TiFragment<OvertimeRequestPresenter, OvertimeRequestView> implements OvertimeRequestView {
    @Override
    public boolean getValidationForm() {
        return false;
    }

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
    public OvertimeRequestPresenter providePresenter() {
        return null;
    }
}
