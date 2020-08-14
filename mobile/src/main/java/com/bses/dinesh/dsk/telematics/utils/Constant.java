package com.bses.dinesh.dsk.telematics.utils;

public class Constant {
    public interface ServerEndpoint {

        String LOGIN = "TrackiyaWebApp/trackiyawebapp/App/login/";
        String GET_DASHBOARD_DETAILS = "ws-login/ws-dashboard.php/";

    }

    public interface SharedPreferences {
        String LOGGED_IN_USER = "LOGGED_IN_USER";
        String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
        String USER_LOGGED_IN_LAT = "USER_LOGGED_IN_LAT";
        String USER_LOGGED_IN_LONG = "USER_LOGGED_IN_LONG";
        String IS_USER_LOCATION_STARTED = "IS_USER_LOCATION_STARTED";

    }

    public interface Common {
        String SHARED_PREFERENCES = "BRPL_SHARED_PREFERENCES";
    }

    public interface ConstantValues {
        String USER_TYPE_FIELD_TEAM = "team";
        String BLANK_VALUE = "";
    }

    public interface IntentExtras {
        String NAV_VIEW_ID = "NAV_VIEW_ID";
        String DATA = "DATA";
    }

    public interface InterfaceCommon {
        String EDIT = "EDIT";
    }

    public interface BackgroundService {
        // Milliseconds per second
        static final int MILLISECONDS_PER_SECOND = 1000;
        // Update frequency in seconds
        //static final int UPDATE_INTERVAL_IN_SECONDS = 5;
        static final int UPDATE_INTERVAL_IN_SECONDS = 10;
        // Update frequency in milliseconds
        public static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
        // The fastest update frequency, in seconds
        //static final int FASTEST_INTERVAL_IN_SECONDS = 5;
        static final int FASTEST_INTERVAL_IN_SECONDS = 10;
        // A fast frequency ceiling in milliseconds
        public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    }

    public interface DateFormat {
        String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
        String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd"; //In which you need put here
        String DATE_FORMAT_DD_MM_YY = "dd/MM/yy";
        String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";
        String DATE_FORMAT_DD_MM_YY_KK_MM_SS = "dd/MM/yy kk:mm:ss";
        String DATE_FORMAT_DD_MMM_YYYY_KK_MM_SS = "dd-MMM-yyyy kk:mm:ss";
    }

    public interface DatabaseTable {
        String TABLE_TASKS = "Tasks";
        String TABLE_TASKS_DATA = "TasksData";
        String TABLE_DIVISION = "Division";
        String TABLE_REQUEST_TYPE = "RequestType";
        String TABLE_USER_TYPE = "UserType";
        String TABLE_USERS = "Users";
        String TABLE_FIELD_LOCATION = "fieldLocation";

    }

}
