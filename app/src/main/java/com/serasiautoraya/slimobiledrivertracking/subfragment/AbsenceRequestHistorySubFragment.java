package com.serasiautoraya.slimobiledrivertracking.subfragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
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
import com.serasiautoraya.slimobiledrivertracking.util.OverrideHurlStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    new HistoryRequestSingleList(id, date, information, "Menunggu");
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
    public void cancelRequest(final String requestId){
        Log.d("TAG_REQ", "req: "+requestId);
        HttpStack httpStack;
        httpStack = new OverrideHurlStack();
        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext(), httpStack);
        String URL = HelperUrl.DELETE_REQUEST_ABSENCE;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("data").equalsIgnoreCase(HelperKey.STATUS_SUKSES) ||
                            jsonResponse.getString("data").equalsIgnoreCase(HelperKey.STATUS_SUKSES_STRING)) {

                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_absence_req_delete), getContext());
                        RequestHistoryFragment parentFragment = (RequestHistoryFragment) getParentFragment();
                        parentFragment.refreshRecyclerView();
                    }else{
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_absence_request), getContext());
                    }

                } catch (JSONException e) {
                    HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_absence_request), getContext());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-API-KEY", HelperKey.API_KEY);
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("Id", requestId);
                params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
                return params;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                String json;
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    try {
                        json = new String(volleyError.networkResponse.data,
                                HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
                    } catch (UnsupportedEncodingException e) {
                        return new VolleyError(e.getMessage());
                    }
                    return new VolleyError(json);
                }
                return volleyError;
            }
        };
        requestQueue.add(deleteRequest);
    }
}
