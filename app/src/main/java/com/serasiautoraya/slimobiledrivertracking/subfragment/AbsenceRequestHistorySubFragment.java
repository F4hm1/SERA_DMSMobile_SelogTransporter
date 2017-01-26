package com.serasiautoraya.slimobiledrivertracking.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryRequestListAdapter;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryRequestSingleList;
import com.serasiautoraya.slimobiledrivertracking.fragment.RequestHistoryFragment;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.CancelableRequest;
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleData;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 24/01/2017.
 */
public class AbsenceRequestHistorySubFragment extends Fragment implements CancelableRequest{

    private static HistoryRequestListAdapter historyRequestListAdapter;
    private RecyclerView recyclerView;
    private static List<HistoryRequestSingleList> historyRequestSingleLists = new ArrayList<>();
    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_absence_request_history, container, false);
        mqueue = VolleyUtil.getRequestQueue();
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_absence_request_history);
        historyRequestListAdapter = new HistoryRequestListAdapter(historyRequestSingleLists, getContext(), this);

        assignRecycler();
        assignRecyclerData();
    }

    public static void refreshRecycleView() {
        historyRequestSingleLists.clear();
        for (int i = 0; i < HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY.length; i++) {
            String id = HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY[i].getId();
            String date = HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY[i].getTimeRequest();
            String information = HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY[i].getTypeRequest();


            HistoryRequestSingleList historyAttendanceSingleList =
                    new HistoryRequestSingleList(id, date, information, "Pending");
            historyRequestSingleLists.add(historyAttendanceSingleList);
        }


        historyRequestListAdapter.notifyDataSetChanged();
    }

    private void assignRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyRequestListAdapter);
    }

    private void assignRecyclerData(){
        historyRequestSingleLists.clear();
        historyRequestListAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelRequest(String requestId){
        String url = HelperUrl.DELETE_REQUEST_ABSENCE;
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> header = new HashMap<>();
        params.put("Id", requestId);
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        header.put("X-API-KEY", HelperKey.API_KEY);
        GsonRequest<ModelSingleData> request = new GsonRequest<ModelSingleData>(
                Request.Method.DELETE,
                url,
                ModelSingleData.class,
                header,
                params,
                new Response.Listener<ModelSingleData>() {
                    @Override
                    public void onResponse(ModelSingleData response) {
                        if (response.getData().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cico_req_delete), getContext());
                            RequestHistoryFragment parentFragment = (RequestHistoryFragment) getParentFragment();
                            parentFragment.refreshRecyclerView();
                        }else{
                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_cico_request), getContext());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }
}
