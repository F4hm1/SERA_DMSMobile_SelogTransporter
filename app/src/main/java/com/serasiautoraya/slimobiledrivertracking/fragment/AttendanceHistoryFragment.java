package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryAttendanceListAdapter;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryAttendanceSingleList;
import com.serasiautoraya.slimobiledrivertracking.customdialog.DatePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking.model.ModelArrayData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelReportResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.util.DividerRecycleViewDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 30/11/2016.
 */
public class AttendanceHistoryFragment extends Fragment {

    private HistoryAttendanceListAdapter historyAttendanceListAdapter;
    private RecyclerView recyclerView;
    private List<HistoryAttendanceSingleList> historyAttendanceSingleLists = new ArrayList<>();
//    private TextView textViewKosong;
    private EditText editTextDateMulai, editTextDateSelesai;
    private LinearLayout tableTitle;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogMulai, mDatePickerToEditTextDialogBerakhir;

    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_history, container, false);
        assignView(view);
        return view;
    }

    private void assignView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_attendance_history);
        historyAttendanceListAdapter = new HistoryAttendanceListAdapter(historyAttendanceSingleLists);
//        textViewKosong = (TextView)  view.findViewById(R.id.text_view_kosong_attendance_history);
        tableTitle = (LinearLayout) view.findViewById(R.id.title_attendance_history_table);
        editTextDateMulai = (EditText) view.findViewById(R.id.edittext_attendance_history_datemulai);
        editTextDateSelesai = (EditText) view.findViewById(R.id.edittext_attendance_history_dateakhir);

        mDatePickerToEditTextDialogMulai = new DatePickerToEditTextDialog(editTextDateMulai, view.getContext());
        mDatePickerToEditTextDialogBerakhir= new DatePickerToEditTextDialog(editTextDateSelesai, view.getContext());

        mqueue = VolleyUtil.getRequestQueue();

        editTextDateMulai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * Web API address belum ada
                 * sementara request di pass */
//                readData();
//                refreshRecycleView();

                HelperUtil.showSimpleToast("Mulai Changed", getContext());
            }
        });

        editTextDateSelesai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * Web API address belum ada
                 * sementara request di pass */
//                readData();
//                refreshRecycleView();

                HelperUtil.showSimpleToast("Selesai changed", getContext());
            }
        });

        assignRecycler();
        assignRecyclerData();
        assignRecyclerListener();
    }

    private void refreshRecycleView() {
        historyAttendanceSingleLists.clear();
        for (int i = 0; i < HelperBridge.MODEL_REPORT_ARRAY.length; i++) {
            HistoryAttendanceSingleList historyAttendanceSingleList =
                    new HistoryAttendanceSingleList(
                            HelperBridge.MODEL_REPORT_ARRAY[i].getTanggal(),
                            HelperBridge.MODEL_REPORT_ARRAY[i].getAbsence(),
                            HelperBridge.MODEL_REPORT_ARRAY[i].getWsIn(),
                            HelperBridge.MODEL_REPORT_ARRAY[i].getWsOut(),
                            HelperBridge.MODEL_REPORT_ARRAY[i].getCi(),
                            HelperBridge.MODEL_REPORT_ARRAY[i].getCo());
            historyAttendanceSingleLists.add(historyAttendanceSingleList);
        }

        historyAttendanceListAdapter.notifyDataSetChanged();
    }

    private void assignRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(historyAttendanceListAdapter);
    }

    private void assignRecyclerData(){
        historyAttendanceSingleLists.clear();

        for (int i = 0; i < 17; i++) {
            HistoryAttendanceSingleList historyAttendanceSingleList =
                    new HistoryAttendanceSingleList("2016-11-01", "Cuti sakit","08:17:12", "18:02:45", "08:17:12", "18:02:45");
            historyAttendanceSingleLists.add(historyAttendanceSingleList);
            historyAttendanceSingleList =
                    new HistoryAttendanceSingleList("2016-11-01", "Hadir","08:17:12", "18:02:45", "08:17:12", "18:02:45");
            historyAttendanceSingleLists.add(historyAttendanceSingleList);
        }

        if(historyAttendanceSingleLists.size() > 0){
//            textViewKosong.setEnabled(false);
//            textViewKosong.setVisibility(View.GONE);
//            textViewKosong.setText("");
            tableTitle.setEnabled(true);
            tableTitle.setVisibility(View.VISIBLE);
        }else {
//            textViewKosong.setEnabled(true);
//            textViewKosong.setVisibility(View.VISIBLE);
//            textViewKosong.setText(getText(R.string.content_history_cico_empty));
            tableTitle.setEnabled(false);
            tableTitle.setVisibility(View.GONE);
        }
        historyAttendanceListAdapter.notifyDataSetChanged();
    }

    void readData(){
        String url = HelperUrl.GET_ATTENDANCE_HISTORY;
        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("startdate", editTextDateMulai.getText().toString());
        params.put("enddate", editTextDateSelesai.getText().toString());
        header.put("X-API-KEY", HelperKey.API_KEY);
        GsonRequest<ModelArrayData> request = new GsonRequest<ModelArrayData>(
                Request.Method.POST,
                url,
                ModelArrayData.class,
                header,
                params,
                new Response.Listener<ModelArrayData>() {
                    @Override
                    public void onResponse(ModelArrayData response) {
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            HelperBridge.MODEL_REPORT_ARRAY = new ModelReportResponse[response.getData().length];
                            for (int i = 0; i < response.getData().length ; i++) {
                                HelperBridge.MODEL_REPORT_ARRAY[i] = HelperUtil.getMyObject(response.getData()[i], ModelReportResponse.class);
                            }
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

    private void assignRecyclerListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
//                createDialogDetail();
//                dialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
