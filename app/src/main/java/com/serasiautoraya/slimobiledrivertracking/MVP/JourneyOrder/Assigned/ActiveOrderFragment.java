package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.EmptyInfoView;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class ActiveOrderFragment extends TiFragment<ActiveOrderPresenter, ActiveOrderView> implements ActiveOrderView {

    @BindView(R.id.recycler_active_orders)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_active_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_order);
        mEmptyInfoView.setText("Tidak terdapat order yang aktif");
        this.initializeRecylerView();
        this.initializeRecylerListener();
        getPresenter().loadOrdersdata();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
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
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void changeActivityAction(String key, String value, Class targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    @Override
    public void toggleEmptyInfo(boolean show) {
        if (show) {
            mEmptyInfoView.setVisibility(View.VISIBLE);
        } else {
            mEmptyInfoView.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ActiveOrderPresenter providePresenter() {
        return new ActiveOrderPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerView() {
        AssignedOrderAdapter simpleListAdapter = new AssignedOrderAdapter();
        mSimpleAdapterView = simpleListAdapter;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        getPresenter().setAdapter(simpleListAdapter);
    }

    private void initializeRecylerListener() {
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                getPresenter().onItemClicked(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
