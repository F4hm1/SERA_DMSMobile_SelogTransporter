package com.serasiautoraya.slimobiledrivertracking.MVP.NotificatonList;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public interface NotificationListView extends BaseViewInterface {

    void refreshRecyclerView();

    void showDeleteConfirmationDialog(String notificationDate);

}
