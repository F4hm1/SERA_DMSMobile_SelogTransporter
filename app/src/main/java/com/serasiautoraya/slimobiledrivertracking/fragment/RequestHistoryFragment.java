package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryRequestListAdapter;
import com.serasiautoraya.slimobiledrivertracking.adapter.HistoryRequestSingleList;
import com.serasiautoraya.slimobiledrivertracking.customdialog.DatePickerToEditTextDialog;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.ClickListener;
import com.serasiautoraya.slimobiledrivertracking.listener.RecyclerTouchListener;
import com.serasiautoraya.slimobiledrivertracking.model.ModelArrayData;
import com.serasiautoraya.slimobiledrivertracking.model.ModelReportResponse;
import com.serasiautoraya.slimobiledrivertracking.model.ModelRequestReportResponse;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.subfragment.AbsenceRequestHistorySubFragment;
import com.serasiautoraya.slimobiledrivertracking.subfragment.CicoRealtimeSubFragment;
import com.serasiautoraya.slimobiledrivertracking.subfragment.CicoRequestHistorySubFragment;
import com.serasiautoraya.slimobiledrivertracking.subfragment.CicoRequestSubFragment;
import com.serasiautoraya.slimobiledrivertracking.util.DividerRecycleViewDecoration;
import com.serasiautoraya.slimobiledrivertracking.util.LocationManagerUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 17/01/2017.
 */
public class RequestHistoryFragment extends Fragment {
    private EditText editTextDateMulai, editTextDateSelesai;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogMulai, mDatePickerToEditTextDialogBerakhir;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_history, container, false);
        assignView(view);
        Log.d("CALL_CREATE", "called");
        initRecyclerViewData();
        return view;
    }

    public void initRecyclerViewData() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());

        Calendar calendarMulai = Calendar.getInstance(TimeZone.getDefault());
        calendarMulai.set(Calendar.DAY_OF_MONTH, 1);
        String startdate = dateFormatter.format(calendarMulai.getTime());
        editTextDateMulai.setText(startdate);

        Calendar calendarAkhir = Calendar.getInstance(TimeZone.getDefault());
        calendarAkhir.set(Calendar.DAY_OF_MONTH, 1);
        calendarAkhir.set(Calendar.MONTH, calendarAkhir.get(Calendar.MONTH) + 1);
        String enddate = dateFormatter.format(calendarAkhir.getTime());
        editTextDateSelesai.setText(enddate);
    }

    private void assignView(View view) {
        editTextDateMulai = (EditText) view.findViewById(R.id.edittext_attendance_history_datemulai);
        editTextDateSelesai = (EditText) view.findViewById(R.id.edittext_attendance_history_dateakhir);

        mDatePickerToEditTextDialogMulai = new DatePickerToEditTextDialog(editTextDateMulai, view.getContext(), false, true);
        mDatePickerToEditTextDialogBerakhir = new DatePickerToEditTextDialog(editTextDateSelesai, view.getContext(), false, true);

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
                try {
                    mDatePickerToEditTextDialogBerakhir = new DatePickerToEditTextDialog(editTextDateSelesai, getContext(), false, true);
                    mDatePickerToEditTextDialogBerakhir.setMaxDateHistory(editTextDateMulai.getText().toString());
                    mDatePickerToEditTextDialogBerakhir.setMinDateHistory(editTextDateMulai.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                refreshRecyclerView();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_request_report);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_request_report);
        tabLayout.setupWithViewPager(viewPager);

        HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY = new ModelRequestReportResponse[0];
        HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY = new ModelRequestReportResponse[0];
    }

    public void refreshRecyclerView() {
        if (!editTextDateSelesai.getText().toString().equalsIgnoreCase("")) {
            readDataCicoRequest();
        }
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CicoRequestHistorySubFragment(), "Pengajuan CiCo");
        adapter.addFragment(new AbsenceRequestHistorySubFragment(), "Pengajuan Ketidakhadiran");
        viewPager.setAdapter(adapter);
    }

    void readDataAbsenceRequest() {
        String url = HelperUrl.GET_REPORT_REQUEST_HISTORY;
        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("startdate", HelperUtil.getServerFormDate(editTextDateMulai.getText().toString()));
        params.put("enddate", HelperUtil.getServerFormDate(editTextDateSelesai.getText().toString()));
        params.put("Type", "ABSENCE");
        header.put("X-API-KEY", HelperKey.API_KEY);

        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_report), getResources().getString(R.string.prog_msg_wait), true, false);
        GsonRequest<ModelArrayData> request = new GsonRequest<ModelArrayData>(
                Request.Method.GET,
                url,
                ModelArrayData.class,
                header,
                params,
                new Response.Listener<ModelArrayData>() {
                    @Override
                    public void onResponse(ModelArrayData response) {
                        loading.dismiss();
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY = new ModelRequestReportResponse[response.getData().length];
                            for (int i = 0; i < response.getData().length; i++) {
                                HelperBridge.MODEL_REQUEST_ABSENCE_REPORT_ARRAY[i] = HelperUtil.getMyObject(response.getData()[i], ModelRequestReportResponse.class);
                            }
                            AbsenceRequestHistorySubFragment.refreshRecycleView();
                            CicoRequestHistorySubFragment.refreshRecycleView();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("TAG_REQ_REP", "err absenc: " + editTextDateSelesai.getText().toString());
                        Log.d("TAG_REQ_REP", "err abs: " + error.toString());
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    void readDataCicoRequest() {
        String url = HelperUrl.GET_REPORT_REQUEST_HISTORY;
        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("IdPersonalData", HelperBridge.MODEL_LOGIN_DATA.getIdPersonalData());
        params.put("startdate", HelperUtil.getServerFormDate(editTextDateMulai.getText().toString()));
        params.put("enddate", HelperUtil.getServerFormDate(editTextDateSelesai.getText().toString()));
        params.put("Type", "CICO");
        header.put("X-API-KEY", HelperKey.API_KEY);

        final ProgressDialog loading = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_report), getResources().getString(R.string.prog_msg_wait), true, false);
        GsonRequest<ModelArrayData> request = new GsonRequest<ModelArrayData>(
                Request.Method.GET,
                url,
                ModelArrayData.class,
                header,
                params,
                new Response.Listener<ModelArrayData>() {
                    @Override
                    public void onResponse(ModelArrayData response) {
                        loading.dismiss();
                        if (response.getStatus().equalsIgnoreCase(HelperKey.STATUS_SUKSES)) {
                            HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY = new ModelRequestReportResponse[response.getData().length];
                            for (int i = 0; i < response.getData().length; i++) {
                                HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY[i] = HelperUtil.getMyObject(response.getData()[i], ModelRequestReportResponse.class);
                                Log.d("TAG_REQ_REP", HelperBridge.MODEL_REQUEST_CICO_REPORT_ARRAY[i].getTimeRequest());
                            }
                            readDataAbsenceRequest();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("TAG_REQ_REP", "err: " + error.toString());
                        HelperUtil.showSimpleAlertDialog(getResources().getString(R.string.err_msg_general), getContext());
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
