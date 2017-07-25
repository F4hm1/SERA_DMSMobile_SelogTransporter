package com.serasiautoraya.slimobiledrivertracking.MVP.WsInOutHistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.R;

import java.util.List;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutHistoryListAdapter extends RecyclerView.Adapter<WsInOutSingleViewHolder>
        implements SimpleAdapterModel<WsInOutResponseModel>, SimpleAdapterView {

    private List<WsInOutResponseModel> mSimpleSingleLists;

    @Override
    public WsInOutSingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_wsinout, parent, false);
        return new WsInOutSingleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WsInOutSingleViewHolder holder, int position) {
        WsInOutResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getDate().setText("Tanggal " + simpleSingleList.getDate());
        String keterangan = "";
        if (!simpleSingleList.getClockIn().equalsIgnoreCase("")) {
            keterangan += " Clock In |";
        }

        if (!simpleSingleList.getClockOut().equalsIgnoreCase("")) {
            keterangan += " Clock Out |";
        }

        if (!simpleSingleList.getAbsence().equalsIgnoreCase("")) {
            keterangan += " Tidak Hadir";
        }

        holder.getKeterangan().setText(keterangan);
        holder.getWsin().setText("Jadwal Masuk: " + simpleSingleList.getScheduleIn());
        holder.getWsout().setText("Jadwal Keluar: " + simpleSingleList.getScheduleOut());
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public Model getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public Model remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<WsInOutResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(WsInOutResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }
}
