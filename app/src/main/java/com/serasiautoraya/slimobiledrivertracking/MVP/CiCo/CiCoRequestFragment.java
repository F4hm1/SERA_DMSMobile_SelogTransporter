package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRequestFragment extends TiFragment<CiCoRequestPresenter, CiCoRequestView> implements CiCoRequestView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_request_cico, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, getContext(), Title);
    }

    @Override
    public boolean getValidationForm() {
        return false;
    }

    @NonNull
    @Override
    public CiCoRequestPresenter providePresenter() {
        return new CiCoRequestPresenter(new RestConnection(getContext()));
    }
}
