package com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public interface OrderHistoryDetailView extends BaseViewInterface {

    void addActivityData(
            String activityTitle,
            String activityCode,
            String activityType,
            String timeTarget,
            String timeBaseline,
            String timeActual,
            String locationTargetText
    );

}
