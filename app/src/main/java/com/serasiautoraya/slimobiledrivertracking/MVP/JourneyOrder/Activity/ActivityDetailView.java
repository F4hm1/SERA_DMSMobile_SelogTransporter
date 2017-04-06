package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.view.View;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public interface ActivityDetailView extends BaseViewInterface{

    void onActionClicked(View view);

    void setDetailData(
            String codeHead,
            String code,
            String activityName,
            String activityType,
            String origin,
            String destination,
            String etd,
            String eta,
            String customer,
            String locationTarget,
            String timeTarget,
            String timeBaseline,
            String timeActual
            );

    void setButtonText(String text);

    void changeActivity(Class cls);

}
