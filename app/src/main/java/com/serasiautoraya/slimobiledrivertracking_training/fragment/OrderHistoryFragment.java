package com.serasiautoraya.slimobiledrivertracking_training.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.adapter.GeneralListAdapter;
import com.serasiautoraya.slimobiledrivertracking_training.adapter.GeneralSingleList;
import com.serasiautoraya.slimobiledrivertracking_training.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking_training.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking_training.util.DividerRecycleViewDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class OrderHistoryFragment extends Fragment {

    private GeneralListAdapter generalListAdapter;
    private RecyclerView recyclerView;
    private List<GeneralSingleList> generalSingleLists = new ArrayList<>();
    private TextView textViewKosong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        assignView(view);
        return view;
    }


    private void assignView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_order_history);
        generalListAdapter = new GeneralListAdapter(generalSingleLists);
        textViewKosong = (TextView)  view.findViewById(R.id.text_view_kosong);
        assignRecycler();
        assignRecyclerData();
        assignRecyclerListener();
    }

    private void assignRecyclerListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                createDialogDetail();
                dialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void assignRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(generalListAdapter);
    }

    private void assignRecyclerData(){
        generalSingleLists.clear();

        GeneralSingleList generalSingleList =
                new GeneralSingleList("Order Code 173652", "Rute Jakarta-Semarang", "Order selesai");

        GeneralSingleList generalSingleList1 =
                new GeneralSingleList("Order Code 173678", "Rute Jakarta-Surabaya", "Order terhenti");


        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList1);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList1);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList1);
        generalSingleLists.add(generalSingleList1);
        generalSingleLists.add(generalSingleList);
        generalSingleLists.add(generalSingleList1);

        if(generalSingleLists.size() > 0){
            textViewKosong.setEnabled(false);
            textViewKosong.setVisibility(View.GONE);
            textViewKosong.setText("");
        }else {
            textViewKosong.setEnabled(true);
            textViewKosong.setVisibility(View.VISIBLE);
            textViewKosong.setText(getText(R.string.content_active_order_empty));
        }
        generalListAdapter.notifyDataSetChanged();
    }

    private Dialog dialog;
    private void createDialogDetail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_detail_history, null))
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
    }


}
