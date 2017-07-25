package com.serasiautoraya.slimobiledrivertracking.MVP.ExpensesRequest;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseRequestPresenter extends TiPresenter<ExpenseRequestView> {

    private RestConnection mRestConnection;
    private ExpenseRequestSendModel mExpenseRequestSendModel;
    ExpenseAvailableResponseModel expenseAvailableResponseModel;
    private String selectedOrderCode;

    public ExpenseRequestPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ExpenseRequestView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onSearchClicked(String orderCode) {
        selectedOrderCode = orderCode;
        getView().toggleLoadingSearchingOrder(true);
        final ExpenseAvailableSendModel expenseAvailableSendModel = new ExpenseAvailableSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                orderCode
        );

        final ExpenseRequestView expenseRequestView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_EXPENSE_AVAILABLE, expenseAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                expenseAvailableResponseModel = Model.getModelInstance(response.getData()[0], ExpenseAvailableResponseModel.class);
                generateExpenseInputValue(expenseAvailableResponseModel);
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                expenseRequestView.showToast(response);
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                expenseRequestView.showToast("FAIL: " + error.toString());
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }
        });
    }

    private void generateExpenseInputValue(ExpenseAvailableResponseModel expenseAvailableResponseModel) {
        String[] typeCode = expenseAvailableResponseModel.getExpenseTypeCode().split(";");
        String[] typeName = expenseAvailableResponseModel.getExpenseTypeName().split(";");

        HashMap<String, ExpenseInputModel> expenseInputList = new HashMap<>();

        for (int i = 0; i < typeCode.length; i++) {
            expenseInputList.put(typeCode[i], new ExpenseInputModel(typeCode[i], typeName[i], "0"));
        }

        getView().setExpenseInputForm(expenseInputList, typeCode);


    }

    public void onSubmitClicked(HashMap<String, String> expenseResult) {
        String exType = "";
        String exAmount = "";
        for (Map.Entry<String, String> entry : expenseResult.entrySet()) {
            String expenseCode = entry.getKey();
            String amount = entry.getValue();
            exType += expenseCode + ";";
            exAmount += amount + ";";
        }


        exType = exType.substring(0, exType.length() - 1);
        exAmount = exAmount.substring(0, exAmount.length() - 1);

        final String expenseTypes = exType;
        final String expenseAmounts = exAmount;

        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                mExpenseRequestSendModel = new ExpenseRequestSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        selectedOrderCode,
                        expenseTypes,
                        expenseAmounts,
                        timeRESTResponseModel.getTime()
                );
                getView().toggleLoading(false);
                getView().showConfirmationDialog();
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });
    }

    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_EXPENSE, mExpenseRequestSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
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
                getView().showStandardDialog("Gagal menyimpan data expense, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }
}
