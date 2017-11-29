package com.serasiautoraya.slimobiledrivertracking_training.MVP.OrderHistory.OrderHistoryDetail;

import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.BaseViewInterface;

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
