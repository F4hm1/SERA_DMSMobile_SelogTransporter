package com.serasiautoraya.slimobiledrivertracking.MVP.Helper;

/**
 * Created by Randi Dwi Nandra on 22/03/2017.
 */

public class HelperUrl {

    /**
     * Host
     */
    private static final String HOST = "http://seradmapimanagementdev.azure-api.net//";

    public static final String GET_SERVER_LOCALTIME = "http://api.geonames.org/timezoneJSON";

    public static final String POST_LOGIN = HOST + "auth/login/";

    public static final String GET_ATTENDANCE_HISTORY = HOST + "attendance/history/";

    public static final String GET_REQUEST_HISTORY = HOST + "attendance/requesthistory/";

    public static final String POST_CICO_REALTIME = HOST + "attendance/cicorealtime/";

    public static final String POST_CICO_REQUEST = HOST + "attendance/cico/";

    public static final String DELETE_CANCEL_CICO = HOST + "attendance/cico/";

    public static final String POST_ABSENCE_TEMPORARY = HOST + "attendance/absencetemp/";

    public static final String POST_ABSENCE = HOST + "attendance/absence/";

    public static final String DELETE_ABSENCE = HOST + "attendance/absence/";

    public static final String PUT_CHANGE_PASSWORD = HOST + "auth/password/";

    public static final String GET_ASSIGNED_ORDER = HOST + "order/assigned/";

    public static final String PUT_ACKNOWLEDGE_ORDER = HOST + "order/acknowledge/";

    public static final String PUT_LOCATION_UPDATE = HOST + "auth/location/";

    public static final String GET_ORDER_ACTIVITIES = HOST + "order/activity/";

    public static final String PUT_STATUS_UPDATE = HOST + "order/statusupdate/";

    public static final String POST_FATIGUE_INTERVIEW = HOST + "fatigue/interview/";

    public static final String POST_OVERTIME = HOST + "attendance/overtime/";

    public static final String GET_OVERTIME_AVAILABLE= HOST + "attendance/overtimechecking/";

    public static final String DELETE_OVERTIME = HOST + "attendance/overtime/";

    public static final String POST_OLCTRIP = HOST + "attendance/olctrip/";

    public static final String DELETE_OLCTRIP = HOST + "attendance/overtime/";
}
