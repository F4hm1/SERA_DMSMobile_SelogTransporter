package com.serasiautoraya.slimobiledrivertracking.subfragment;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.net.http.Headers;
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
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
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
import com.serasiautoraya.slimobiledrivertracking.model.ModelSingleDataUnstatus;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.CustomHurlStack;
import com.serasiautoraya.slimobiledrivertracking.util.HttpDeleteWithBody;
import com.serasiautoraya.slimobiledrivertracking.util.OverrideHurlStack;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Randi Dwi Nandra on 24/01/2017.
 */
public class CicoRequestHistorySubFragment extends Fragment implements CancelableRequest{

    private static HistoryRequestListAdapter historyRequestListAdapter;
    private RecyclerView recyclerView;
    private static   List<HistoryRequestSingleList> historyRequestSingleLists = new ArrayList<>();
    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_cico_request_history, container, false);
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_cico_request_history);
        historyRequestListAdapter = new HistoryRequestListAdapter(historyRequestSingleLists, getContext(), this);
        mqueue = VolleyUtil.getRequestQueue();
        assignRecycler();
        assignRecyclerData();
    }

    public static void refreshRecycleView() {
        historyRequestSingleLists.clear();
        for (int i = 0; i < HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY.length; i++) {
            String id = HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY[i].getId();
            String date = HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY[i].getTimeRequest();
            String information = HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY[i].getTypeRequest();

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


    public void cancelRequestDeleteVolley(String requestId){
        String url = HelperUrl.DELETE_REQUEST_CICO;
        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);
        HashMap<String, String> params = new HashMap<>();
        params.put("Id", requestId);
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());


        Log.d("URL_DELETE", url+" == "+requestId+"&&"+HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        final String reqIdTemp = requestId;

        HashMap<String, String> header = new HashMap<>();
        header.put("X-API-KEY", HelperKey.API_KEY);
        GsonRequest<ModelSingleDataUnstatus> request = new GsonRequest<ModelSingleDataUnstatus>(
                Request.Method.DELETE,
                url,
                ModelSingleDataUnstatus.class,
                header,
                params,
                new Response.Listener<ModelSingleDataUnstatus>() {
                    @Override
                    public void onResponse(ModelSingleDataUnstatus response) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog("OKE: " +" \nTrxId: "+reqIdTemp+" \nresData: "+response.getData(), getContext());
//                        if (response.getData().equalsIgnoreCase(HelperKey.STATUS_SUKSES_STRING) || response.getData().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
//                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cico_req_delete) +" \nTrxId: "+reqIdTemp+" \nresData: "+response.getData(), getContext());
////                            RequestHistoryFragment parentFragment = (RequestHistoryFragment) getParentFragment();
////                            parentFragment.refreshRecyclerView();
//                        }else{
//                            HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_cico_request), getContext());
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    @Override
    public void cancelRequest(final String requestId){
        HttpStack httpStack;
        httpStack = new OverrideHurlStack();
        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext(), httpStack);
        String URL = HelperUrl.DELETE_REQUEST_CICO;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getString("data").equalsIgnoreCase(HelperKey.STATUS_SUKSES) ||
                        jsonResponse.getString("data").equalsIgnoreCase(HelperKey.STATUS_SUKSES_STRING)) {

                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.success_msg_cico_req_delete), getContext());
                        RequestHistoryFragment parentFragment = (RequestHistoryFragment) getParentFragment();
                        parentFragment.refreshRecyclerView();
                    }else{
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_cico_request), getContext());
                    }

                } catch (JSONException e) {
                    HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_fail_delete_cico_request), getContext());
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


    public void cancelRequestDeleteHttp(final String requestId){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Id", requestId);
                        jsonObject.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                        DefaultHttpClient client = new DefaultHttpClient();

                        SchemeRegistry registry = new SchemeRegistry();
                        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                        registry.register(new Scheme("https", socketFactory, 443));
                        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
                        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

                        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

                        Header header = new Header() {
                            @Override
                            public String getName() {
                                return "X-API-KEY";
                            }

                            @Override
                            public String getValue() {
                                return HelperKey.API_KEY;
                            }

                            @Override
                            public HeaderElement[] getElements() throws ParseException {
                                return new HeaderElement[0];
                            }
                        };

                        HttpEntity entity = new StringEntity(jsonObject.toString());
                        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(HelperUrl.DELETE_REQUEST_CICO);
                        httpDeleteWithBody.setEntity(entity);
                        httpDeleteWithBody.setHeader(header);

                        HttpResponse response = httpClient.execute(httpDeleteWithBody);
                        HelperUtil.showSimpleAlertDialog("OKE: " +response.getStatusLine().toString(), getContext());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }


    public void cancelRequestDelete(String requestId){

    }

}
