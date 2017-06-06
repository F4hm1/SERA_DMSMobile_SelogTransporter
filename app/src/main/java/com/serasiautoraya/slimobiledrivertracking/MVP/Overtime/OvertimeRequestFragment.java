package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.customdialog.DatePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public class OvertimeRequestFragment extends TiFragment<OvertimeRequestPresenter, OvertimeRequestView> implements OvertimeRequestView {

    @BindView(R.id.overtime_spinner_datechoice)
    Spinner mSpinnerDateChoice;
    @BindView(R.id.overtime_spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.overtime_edittext_startdate)
    EditText mEtDateStart;
    @BindView(R.id.overtime_edittext_enddate)
    EditText mEtDateEnd;
    @BindView(R.id.overtime_edittext_endtime)
    EditText mEtEndTime;
    @BindView(R.id.overtime_edittext_starttime)
    EditText mEtStartTime;
    @BindView(R.id.overtime_edittext_reason)
    EditText mEtReason;
    @BindView(R.id.overtime_btn_submit)
    Button mButtonSubmit;

    @BindView(R.id.overtime_lin_timerange)
    Button mLinearTimeRange;


    private DatePickerToEditTextDialog mDatePickerToEditTextDialogStart;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogEnd;
    private ProgressDialog mProgressDialog;

    private ArrayAdapter<String> mArrayAdapterDatesChoice;
    private ArrayAdapter<String> mArrayAdapterOvertimeTypes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overtime_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        this.initializePickerDialog();
        this.initializeSpinners();
        this.hideHideableView();
        getPresenter().initialRequestHistoryData();
    }

    private void hideHideableView() {
        mSpinnerType.setVisibility(View.GONE);
        mLinearTimeRange.setVisibility(View.GONE);
        mEtReason.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.GONE);
    }

    private void initializeSpinners() {
        String[] tanggalOvertime = {"Tidak terdapat overtime"};
        mArrayAdapterDatesChoice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tanggalOvertime);
        mArrayAdapterDatesChoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDateChoice.setAdapter(mArrayAdapterDatesChoice);
        mSpinnerDateChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().onDateSelected(adapterView.getSelectedItem().toString(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public OvertimeRequestPresenter providePresenter() {
        return new OvertimeRequestPresenter(new RestConnection((this.getContext())));
    }

    @Override
    public boolean getValidationForm() {
        return false;
    }

    @Override
    @OnTextChanged(value = R.id.overtime_edittext_enddate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextEndDateChangeAfter(Editable editable) {
        if (!mEtDateEnd.getText().toString().equalsIgnoreCase("")) {
            getPresenter().loadRequestHistoryData(mEtDateStart.getText().toString(), mEtDateEnd.getText().toString());
        }
    }

    @Override
    @OnTextChanged(value = R.id.overtime_edittext_startdate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextStartDateChangeAfter(Editable editable) {
        try {
            mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
            mDatePickerToEditTextDialogEnd.setMaxDateHistory(mEtDateStart.getText().toString());
            mDatePickerToEditTextDialogEnd.setMinDateHistory(mEtDateStart.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTextEndDate(String textEndDate) {
        mEtDateEnd.setText(textEndDate);
    }

    @Override
    public void setTextStartDate(String textStartDate) {
        mEtDateStart.setText(textStartDate);
    }

    @Override
    public void toggleLoadingInitialLoad(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan "+
                "<b>"+mSpinnerType.getSelectedItem().toString()+"</b>"+" pada "+
                "<b>"+ mSpinnerDateChoice.getSelectedItem().toString()+"</b>"+"?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.overtime_btn_submit)
    public void onSubmitClicked(View view) {
        if(getValidationForm()){
//            String requestCicoCode = HelperUtil.getValueStringArrayXML(
//                    getResources().getStringArray(R.array.cico_tipe_array),
//                    getResources().getStringArray(R.array.cico_tipe_array_val),
//                    mSpinnerOLC.getSelectedItem().toString());
//            getPresenter().onSubmitClicked(
//                    mDatePickerToEditTextDialog.getDateServerFormat(),
//                    requestCicoCode,
//                    mEtTripAmount.getText().toString(),
//                    mEtReason.getText().toString());
        }
    }

    @Override
    public void initializeOvertimeDates(ArrayAdapter<String> datesArray) {
        mArrayAdapterDatesChoice.clear();
        mArrayAdapterDatesChoice = datesArray;
        mArrayAdapterDatesChoice.setNotifyOnChange(true);
        mArrayAdapterDatesChoice.notifyDataSetChanged();
    }

    private void initializePickerDialog(){
        mDatePickerToEditTextDialogStart = new DatePickerToEditTextDialog(mEtDateStart, getContext(), false, true);
        mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
    }
}
