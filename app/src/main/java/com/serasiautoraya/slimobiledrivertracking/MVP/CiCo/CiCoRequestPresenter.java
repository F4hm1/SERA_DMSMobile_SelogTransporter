package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRequestPresenter extends TiPresenter<CiCoRequestView> {

    private RestConnection mRestConnection;

    public CiCoRequestPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

}
