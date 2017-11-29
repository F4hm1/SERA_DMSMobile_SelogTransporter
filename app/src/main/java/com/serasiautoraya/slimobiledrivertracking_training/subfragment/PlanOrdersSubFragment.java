package com.serasiautoraya.slimobiledrivertracking_training.subfragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.activity.ActionActivityActivity;
import com.serasiautoraya.slimobiledrivertracking_training.adapter.GeneralListAdapter;
import com.serasiautoraya.slimobiledrivertracking_training.adapter.GeneralOrderSingleList;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking_training.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking_training.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking_training.util.DividerRecycleViewDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class PlanOrdersSubFragment extends Fragment {

    @BindView(R.id.recycler_plan_orders) RecyclerView recyclerView;

    private GeneralListAdapter mPlanOrderListAdapter;
    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_plan_orders, container, false);
        ButterKnife.bind(this, view);
        assignView();
        return view;
    }

    private void assignView() {
        mPlanOrderListAdapter = new GeneralListAdapter(HelperBridge.sPlanOrdersList);
        mqueue = VolleyUtil.getRequestQueue();

        assignRecycler();
        assignRecyclerData();
        assignRecyclerListener();
    }

    private void assignRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mPlanOrderListAdapter);
    }

    private void assignRecyclerData(){
        /*
        * TODO access parent class to call API refresh active & plan order list
        * */

        mPlanOrderListAdapter.notifyDataSetChanged();
    }


    private void assignRecyclerListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
//                createDialogDetail();
//                dialog.show();

                /*
                * TODO change the way to access id/code order list
                * */


                GeneralOrderSingleList generalOrderSingleList = (GeneralOrderSingleList)HelperBridge.sPlanOrdersList.get(position);
                HelperBridge.MODEL_ACTIVITY_SELECTED = generalOrderSingleList.getModelActivityJourney();

                if(HelperBridge.MODEL_ACTIVITY_SELECTED.getStatus().equalsIgnoreCase(HelperKey.WAITING_ACK_CODE)){
                    Dialog dialog = HelperUtil.getConfirmOrderDialog(getActivity());
                    dialog.show();
                }else {
                    Intent intent = new Intent(getActivity(), ActionActivityActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), (View)view.findViewById(R.id.single_list_his_title), "trans_order_code");
                    startActivity(intent, options.toBundle());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
