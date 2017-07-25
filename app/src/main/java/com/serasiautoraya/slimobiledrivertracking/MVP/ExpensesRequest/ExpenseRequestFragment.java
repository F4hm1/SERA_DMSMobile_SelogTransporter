package com.serasiautoraya.slimobiledrivertracking.MVP.ExpensesRequest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseRequestFragment extends TiFragment<ExpenseRequestPresenter, ExpenseRequestView> implements ExpenseRequestView {


    @BindView(R.id.expense_lin_searchgroup)
    LinearLayout mLinSearchgroup;

    @BindView(R.id.expense_lin_requestgroup)
    LinearLayout mLinRequestGroup;

    @BindView(R.id.expense_lin_inputgroup)
    LinearLayout mLinInputGroup;

    @BindView(R.id.expense_et_searchfield)
    EditText mEtSearchOrder;

    @BindView(R.id.expense_btn_search)
    Button mBtnSearch;

    @BindView(R.id.expense_btn_submit)
    Button mButtonSubmit;

    private ProgressDialog mProgressDialog;
    private ArrayAdapter<ExpenseAvailableAdapter> mArrayAdapterDatesChoice;
    private HashMap<String, EditText> mHashEtAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mLinRequestGroup.setVisibility(View.GONE);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_expense_submit), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
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

    @Override
    public void toggleLoadingSearchingOrder(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_expense), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin jumlah yang anda masukan sudah benar?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.expense_btn_submit)
    public void onSubmitClicked(View view) {
        HashMap<String, String> expenseResult = new HashMap<>();
        for (Map.Entry<String, EditText> entry : mHashEtAmount.entrySet()) {
            String expenseCode = entry.getKey();
            String amount = entry.getValue().getText().toString();
            expenseResult.put(expenseCode, amount);
        }
        getPresenter().onSubmitClicked(expenseResult);
    }

    @Override
    @OnClick(R.id.expense_btn_search)
    public void onSearchClicked(View view) {
        getPresenter().onSearchClicked(mEtSearchOrder.getText().toString());
    }

    @Override
    public void setExpenseInputForm(HashMap<String, ExpenseInputModel> expenseInputList, String[] typeCodeList) {
        mHashEtAmount = new HashMap<>();
        if (typeCodeList.length > 0) {
            mLinRequestGroup.setVisibility(View.VISIBLE);
        } else {
            mLinRequestGroup.setVisibility(View.GONE);
        }

        if (mLinInputGroup.getChildCount() > 0) {
            mLinInputGroup.removeAllViews();
        }
        for (int i = 0; i < typeCodeList.length; i++) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.single_list_expenseinput, null);
            EditText etAmount = (EditText) v.findViewById(R.id.expense_input_et);
            etAmount.setText(expenseInputList.get(typeCodeList[i]).getAmount());
            TextView tvInputLabel = (TextView) v.findViewById(R.id.expense_input_label);
            tvInputLabel.setText(expenseInputList.get(typeCodeList[i]).getNameType());
            mHashEtAmount.put(typeCodeList[i], etAmount);
            mLinInputGroup.addView(v);
        }
    }

    @NonNull
    @Override
    public ExpenseRequestPresenter providePresenter() {
        return new ExpenseRequestPresenter(new RestConnection((this.getContext())));
    }

    @Override
    public boolean getValidationForm() {
        return true;
    }
}
