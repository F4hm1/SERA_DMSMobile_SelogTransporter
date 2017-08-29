package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.DocumentCapture.DocumentCaptureActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.activity.EvidenceCaptureActivity;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailPresenter extends TiPresenter<ActivityDetailView>  {

    private RestConnection mRestConnection;
    private ActivityDetailResponseModel mActivityDetailResponseModel;

    public ActivityDetailPresenter(RestConnection restConnection){
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ActivityDetailView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onActionClicked(){
        if(HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){

        }else {
            getView().changeActivity(DocumentCaptureActivity.class);
        }
    }

    public void loadDetailOrderData(String orderCode){

        /*
        * TODO change this lines code below
        * */

        setViewDetailData(orderCode);

//        getView().toggleLoading(true);
//        ActivityDetailSendModel activityDetailSendModel = new ActivityDetailSendModel(orderCode, HelperBridge.sModelLoginResponse.getPersonalId());
//        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
//            @Override
//            public void callBackOnSuccess(BaseResponseModel response) {
//                mActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
//                setViewDetailData();
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnFail(String response) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAILLLLSSS: " + response);
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnError(VolleyError error) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAIL: " + error.toString());
//                getView().toggleLoading(false);
//            }
//        });
    }

    private void setViewDetailData(String orderCode){
//        getView().setDetailData(
//                "Order "+mActivityDetailResponseModel.getOrderCode(),
//                mActivityDetailResponseModel.getOrderCode(),
//                mActivityDetailResponseModel.getActivityName(),
//                mActivityDetailResponseModel.getActivityType(),
//                mActivityDetailResponseModel.getOrigin(),
//                mActivityDetailResponseModel.getDestination(),
//                mActivityDetailResponseModel.getETD(),
//                mActivityDetailResponseModel.getETA(),
//                mActivityDetailResponseModel.getCustomer(),
//                mActivityDetailResponseModel.getLocationTargetText(),
//                mActivityDetailResponseModel.getTimeTarget(),
//                mActivityDetailResponseModel.getTimeBaseline(),
//                mActivityDetailResponseModel.getTimeActual()
//        );
//        getView().setButtonText(mActivityDetailResponseModel.getActivityName());

        getView().setDetailData(
                "Order "+ HelperBridge.sActivityDetailResponseModel.getOrderCode(),
                HelperBridge.sActivityDetailResponseModel.getOrderCode(),
                HelperBridge.sActivityDetailResponseModel.getActivityName(),
                HelperBridge.sActivityDetailResponseModel.getAcitivityType(),
                HelperBridge.sActivityDetailResponseModel.getOrigin(),
                HelperBridge.sActivityDetailResponseModel.getDestination(),
                HelperBridge.sActivityDetailResponseModel.getETD(),
                HelperBridge.sActivityDetailResponseModel.getETA(),
                HelperBridge.sActivityDetailResponseModel.getCustomer(),
                HelperBridge.sActivityDetailResponseModel.getLocationTargetText(),
                HelperBridge.sActivityDetailResponseModel.getTimeTarget(),
                HelperBridge.sActivityDetailResponseModel.getTimeBaseline(),
                HelperBridge.sActivityDetailResponseModel.getTimeActual()
        );

        Log.d("ACTIVITY DET:", HelperBridge.sActivityDetailResponseModel.getHashMapType().toString());

        /*
        * TODO uncomment this
        * */
//        getView().setButtonColor(HelperBridge.sActivityDetailResponseModel.getButtonActivityColor());
        getView().setButtonColor("#1976D2");
        getView().setButtonText(HelperBridge.sActivityDetailResponseModel.getActivityName());

    }

}
