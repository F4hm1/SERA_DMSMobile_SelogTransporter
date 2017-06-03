package com.serasiautoraya.slimobiledrivertracking.MVP.OLCTrip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.customdialog.DatePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public class OLCTripFragment extends TiFragment<OLCTripPresenter, OLCTripView> implements OLCTripView {

    @BindView(R.id.olctrip_spinner_olc)
    Spinner mSpinnerOLC;
    @BindView(R.id.olctrip_edittext_date)
    EditText mEtDate;
    @BindView(R.id.olctrip_edittext_tripamount)
    EditText mEtTripAmount;
    @BindView(R.id.olctrip_edittext_reason)
    EditText mEtReason;
    @BindView(R.id.olctrip_btn_submit)
    Button mButtonSubmit;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialog;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_olctrip_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_olctrip),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, getContext(), Title);
    }

    @NonNull
    @Override
    public OLCTripPresenter providePresenter() {
        return null;
    }

    @Override
    public boolean getValidationForm() {
        return false;
    }
}
