package com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Randi Dwi Nandra on 01/04/2017.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleListViewHolder> implements SimpleAdapterModel, SimpleAdapterView {
    private List<SimpleSingleList> mSimpleSingleLists;

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public SimpleSingleList getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public SimpleSingleList remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<SimpleSingleList> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(SimpleSingleList simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        SimpleSingleList simpleSingleList = mSimpleSingleLists.get(position);
        holder.title.setText("Order "+simpleSingleList.getTittle());
        holder.information.setText(simpleSingleList.getInformation());
        holder.status.setText(simpleSingleList.getStatus());
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }

    class SimpleListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_list_his_title)
        TextView title;

        @BindView(R.id.single_list_his_information)
        TextView information;

        @BindView(R.id.single_list_his_status)
        TextView status;

        public SimpleListViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.single_list_his_title);
            information = (TextView) view.findViewById(R.id.single_list_his_information);
            status = (TextView) view.findViewById(R.id.single_list_his_status);
        }
    }

}
