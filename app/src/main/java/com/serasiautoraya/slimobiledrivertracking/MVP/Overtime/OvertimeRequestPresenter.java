package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public class OvertimeRequestPresenter extends TiPresenter<OvertimeRequestView> {

    private RestConnection mRestConnection;
    private OvertimeSendModel mOvertimeSendModel;
    private ArrayList<OvertimeAvailableResponseModel> overtimeAvailableResponseModelList;

    public OvertimeRequestPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final OvertimeRequestView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void initialRequestHistoryData() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        String endDate = dateFormatter.format(new Date());

        /*
        * TODO Change this with actual maximaum retrieve overtime
        * */
        int maxRetrieveDays = 7;
        String cutOffDate = dateFormatter.format(new Date(System.currentTimeMillis() - (maxRetrieveDays * 24 * 60 * 60 * 1000)));

        loadRequestHistoryData(cutOffDate, endDate);
//        setDummyData();
    }

    private void setDummyData() {
        overtimeAvailableResponseModelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            overtimeAvailableResponseModelList.add(new OvertimeAvailableResponseModel(
                    (i * 2 - 1 + 3) + " Januari 2017",
                    "ID_01",
                    "08." + i,
                    "16." + i,
                    "06." + i,
                    "08." + i,
                    "Freeday False",
                    "OV_01",
                    "Overtime Awal"
            ));

            if(i%2==0){
                overtimeAvailableResponseModelList.add(new OvertimeAvailableResponseModel(
                        (i * 2 - 1 + 3) + " Januari 2017",
                        "ID_01",
                        "08." + i,
                        "16." + i,
                        "16." + i,
                        "18." + i,
                        "Freeday False",
                        "OV_02",
                        "Overtime Akhir"
                ));
            }

            if(i%3==0){
                overtimeAvailableResponseModelList.add(new OvertimeAvailableResponseModel(
                        (i * 2 - 1 + 3) + " Januari 2017",
                        "ID_01",
                        "08." + i,
                        "16." + i,
                        "00." + i,
                        "01." + i,
                        "Freeday False",
                        "OV_02",
                        "Overtime Libur"
                ));
            }
        }

        if(overtimeAvailableResponseModelList.isEmpty()){
            getView().setNoOvertime();
        }else {
            ArrayList<OvertimeAvailableResponseModel> cleanedList = getNonDuplicatedDate(overtimeAvailableResponseModelList);
            getView().initializeOvertimeDates(cleanedList);
        }
    }

    private ArrayList<OvertimeAvailableResponseModel> getNonDuplicatedDate(ArrayList<OvertimeAvailableResponseModel> data){
        ArrayList<OvertimeAvailableResponseModel> cleanedList = new ArrayList<>();
        cleanedList.addAll(data);
        Map<String, OvertimeAvailableResponseModel> map = new LinkedHashMap<>();
        for (OvertimeAvailableResponseModel value : cleanedList) {
            map.put(value.getDate(), value);
        }
        cleanedList.clear();
        cleanedList.addAll(map.values());
        return cleanedList;
    }

    public void loadRequestHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        getView().toggleLoadingInitialLoad(true);
        OvertimeAvailableSendModel overtimeAvailableSendModel = new OvertimeAvailableSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                startDate,
                endDate
        );

        final OvertimeRequestView overtimeRequestView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_OVERTIME_AVAILABLE, overtimeAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                overtimeAvailableResponseModelList = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    overtimeAvailableResponseModelList.add(Model.getModelInstance(response.getData()[i], OvertimeAvailableResponseModel.class));
                }

                if(overtimeAvailableResponseModelList.isEmpty()){
                    overtimeRequestView.setNoOvertime();
                }else {
                    ArrayList<OvertimeAvailableResponseModel> cleanedList = getNonDuplicatedDate(overtimeAvailableResponseModelList);
                    overtimeRequestView.initializeOvertimeDates(cleanedList);
                }
                overtimeRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                overtimeRequestView.setNoOvertime();
                overtimeRequestView.showToast("FAILLLLSSS: " + response);
                overtimeRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                overtimeRequestView.setNoOvertime();
                overtimeRequestView.showToast("FAIL: " + error.toString());
                overtimeRequestView.toggleLoadingInitialLoad(false);
            }
        });
    }

    public void  onSubmitClicked(Object itemSelected, String reason){
        OvertimeAvailableTypeAdapter overtimeAvailableTypeAdapter = (OvertimeAvailableTypeAdapter) itemSelected;
        OvertimeAvailableResponseModel overtimeAvailableResponseModel = overtimeAvailableTypeAdapter.getOvertimeAvailableResponseModel();
        mOvertimeSendModel = new OvertimeSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getPersonalCode(),
                HelperTransactionCode.WFSTATUS_PENDING,
                overtimeAvailableResponseModel.getOvertimeTypeCode(),
                overtimeAvailableResponseModel.getDate(),
                overtimeAvailableResponseModel.getOvertimeStart(),
                overtimeAvailableResponseModel.getOvertimeEnd(),
                reason,
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE,
                HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                overtimeAvailableResponseModel.getIdCico()
        );
        getView().showConfirmationDialog();
    }

    public void onRequestSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_OVERTIME, mOvertimeSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal melakukan pengajuan lembur, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void onDateSelected(OvertimeAvailableResponseModel selectedItem, int i) {
        Log.d("OVERTIME", selectedItem.getDate() + " -- " + selectedItem.getOvertimeTypeName());
        ArrayList<OvertimeAvailableTypeAdapter> overtimeAvailableTypeAdapterArrayList = new ArrayList<>();
        for (OvertimeAvailableResponseModel value :
                overtimeAvailableResponseModelList) {
            if (value.getDate().equalsIgnoreCase(selectedItem.getDate())) {
                overtimeAvailableTypeAdapterArrayList.add(new OvertimeAvailableTypeAdapter(value));
            }
        }
        getView().initializeOvertimeTypes(overtimeAvailableTypeAdapterArrayList);
    }

    public void onTypeSelected(OvertimeAvailableTypeAdapter selectedItem, int i) {
        getView().initializeOvertimeTimes(selectedItem.getOvertimeAvailableResponseModel().getOvertimeStart(), selectedItem.getOvertimeAvailableResponseModel().getOvertimeEnd());
    }
}
