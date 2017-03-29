package com.serasiautoraya.slimobiledrivertracking.subfragment;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.activity.ActionActivityActivity;
import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralListAdapter;
import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralOrderSingleList;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.DividerRecycleViewDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class ActiveOrdersSubFragment extends Fragment {

    @BindView(R.id.recycler_active_orders) RecyclerView recyclerView;

    private GeneralListAdapter mActiveOrderListAdapter;
    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_active_orders, container, false);
        ButterKnife.bind(this, view);
        assignView();
        return view;
    }

    private void assignView() {
        mActiveOrderListAdapter = new GeneralListAdapter(HelperBridge.sActiveOrdersList);
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
        recyclerView.setAdapter(mActiveOrderListAdapter);
    }

    private void assignRecyclerData(){
        /*
        * TODO access parent class to call API refresh active & plan order list
        * */

        mActiveOrderListAdapter.notifyDataSetChanged();
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

//                TextView tv = (TextView) view.findViewById(R.id.single_list_his_title);
//                HelperUtil.showSimpleToast("Clicked: "+tv.getText().toString(), getContext());
                GeneralOrderSingleList generalOrderSingleList = (GeneralOrderSingleList)HelperBridge.sActiveOrdersList.get(position);
                HelperBridge.MODEL_ACTIVITY_SELECTED = generalOrderSingleList.getModelActivityJourney();
                Intent intent = new Intent(getActivity(), ActionActivityActivity.class);
//                Pass data object in the bundle and populate details activity.
//                intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), (View)view.findViewById(R.id.single_list_his_title), "trans_order_code");
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


}