package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.activity.OrderDetailActivity;
import com.serasiautoraya.slimobiledrivertracking.adapter.OrderListAdapter;
import com.serasiautoraya.slimobiledrivertracking.adapter.OrderSingleList;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking.listener.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class ActiveOrderFragment extends Fragment {

    private OrderListAdapter orderListAdapter;
    private RecyclerView recyclerView;
    private List<OrderSingleList> orderSingleListList = new ArrayList<>();
    private TextView textViewKosong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * TODO This class is not needed, delete wae
        *
        * */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_order, container, false);
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_order_history);
        orderListAdapter = new OrderListAdapter(orderSingleListList);
        textViewKosong = (TextView)  view.findViewById(R.id.text_view_kosong);
        assignRecycler();
        assignRecyclerData();
        assignRecyclerListener();
    }

    private void assignRecyclerListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                HelperBridge.ORDER_CLICKED = orderListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra(HelperKey.EXTRA_KEY_TITLE, "Detail Order "+HelperBridge.ORDER_CLICKED.getmSingleListCode());
                startActivity(intent);
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
        recyclerView.setAdapter(orderListAdapter);
    }

    private void assignRecyclerData(){
        orderSingleListList.clear();

            OrderSingleList orderSingleList =
                    new OrderSingleList("173652",
                            "Yes",
                            "Badai",
                            "Jakarta",
                            "15/11/2016 08:00:00",
                            "16/11/2016 17:00:00",
                            "HMS",
                            "B 1916 TOW",
                            "Surabaya");

        OrderSingleList orderSingleList2 =
                new OrderSingleList("173678",
                        "Yes",
                        "Rendhy",
                        "Jakarta",
                        "15/11/2016 08:00:00",
                        "15/11/2016 19:00:00",
                        "HMS-2",
                        "B 1916 TOW",
                        "Semarang");

            orderSingleListList.add(orderSingleList);
        orderSingleListList.add(orderSingleList2);


        if(orderSingleListList.size() > 0){
            textViewKosong.setEnabled(false);
            textViewKosong.setText("");
        }else {
            textViewKosong.setEnabled(true);
            textViewKosong.setText(getText(R.string.content_active_order_empty));
        }
        orderListAdapter.notifyDataSetChanged();
    }
}
