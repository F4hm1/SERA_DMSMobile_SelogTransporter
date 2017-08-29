package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by randi on 15/08/2017.
 */

public class PODCapturePresenter extends TiPresenter<PODCaptureView> {

    private RestConnection mRestConnection;

    public PODCapturePresenter(RestConnection mRestConnection){
        this.mRestConnection = mRestConnection;
    }

}
