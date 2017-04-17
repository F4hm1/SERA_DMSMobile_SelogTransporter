package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.CiCo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.CustomPopUpItemClickListener;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryAdapter;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class CiCoRequestHistoryFragment extends TiFragment<CiCoRequestHistoryPresenter, CiCoRequestHistoryView>
        implements CiCoRequestHistoryView{

    @BindView(R.id.recycler_cico_request_history) RecyclerView mRecyclerView;
    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_cico_request_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        this.initializeRecylerView();
        getPresenter().loadRequestHistoryData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);
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

    }

    @NonNull
    @Override
    public CiCoRequestHistoryPresenter providePresenter() {
        return new CiCoRequestHistoryPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerView() {
        RequestHistoryAdapter simpleListAdapter = new RequestHistoryAdapter(new CustomPopUpItemClickListener<RequestHistoryResponseModel>() {
            @Override
            public boolean startAction(RequestHistoryResponseModel requestHistoryResponseModel, int menuId) {
                switch (menuId) {
                    case R.id.menu_popup_detail_request:
                        /*
                        * TODO Change this code below
                        * */
                        showToast("Detailed bro - "+requestHistoryResponseModel.getRequestDate());
                        return true;
                    case R.id.menu_popup_cancel_request:
                        getPresenter().onCancelClicked(requestHistoryResponseModel);
                        return true;
                    default:
                }
                return false;
            }
        });

        mSimpleAdapterView = simpleListAdapter;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        getPresenter().setAdapter(simpleListAdapter);
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void showCancelConfirmationDialog(String requestDate) {
                /*
        * TODO Change format date to user format
        * */
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan <b>membatalkan pengajuan Clock In/Out</b>"+
                "pada <b>" + requestDate + "</b>?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onCancelationSubmitted();
            }
        });
    }
}
