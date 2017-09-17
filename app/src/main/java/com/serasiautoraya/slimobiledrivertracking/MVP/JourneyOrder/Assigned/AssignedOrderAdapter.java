package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleListViewHolder;
import com.serasiautoraya.slimobiledrivertracking.R;

import java.util.List;


/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AssignedOrderAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<AssignedOrderResponseModel>, SimpleAdapterView {
    private List<AssignedOrderResponseModel> mSimpleSingleLists;

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public AssignedOrderResponseModel getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public AssignedOrderResponseModel remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<AssignedOrderResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(AssignedOrderResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        AssignedOrderResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getTitle().setText("Order "+simpleSingleList.getOrderID());
        holder.getInformation().setText(simpleSingleList.getOrigin() +" - "+ simpleSingleList.getDestination());
        holder.getStatus().setText(simpleSingleList.getCurrentActivity());
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }
}