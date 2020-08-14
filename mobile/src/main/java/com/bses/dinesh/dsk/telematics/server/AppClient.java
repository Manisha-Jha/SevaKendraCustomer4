package com.bses.dinesh.dsk.telematics.server;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

/*
import com.jhamobi.brplapp.data.Customer;
import com.jhamobi.brplapp.data.Order;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.data.TasksDataModel;
import com.jhamobi.brplapp.data.UserLocationData;
import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.utils.CommonUtilities;
*/

import com.bses.dinesh.dsk.telematics.data.Customer;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.data.TasksDataModel;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.bses.dinesh.dsk.telematics.server.DBTableColumnNames.ALLOTED_ORDER_STATUS;
/*
import static com.jhamobi.brplapp.server.DBTableColumnNames.ALLOTED_ORDER_STATUS;*/

public class AppClient
{
    private static final String URL = "jdbc:oracle:thin:@" + AppConstants.HOST + ":" + AppConstants.PORT + ":" + AppConstants.DBNAME;
    static Connection connection = null;
    private static AppClient instance;

    private AppClient(Context context) throws Exception
    {
    }

    public static AppClient getAppClient(Context context) throws Exception {

        if (instance == null) {
            if (context != null)
                instance = new AppClient(context);
        }
        return instance;
    }

    public static Connection createConnection(String driver, String url, String username, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection createNewConnection() {
        return createConnection(AppConstants.DRIVER, URL, AppConstants.DBADMIN, AppConstants.DBPASSWORD);
    }

    public Users login(String userID, String password) {
        Users user = null;
        ResultSet rs = null;
        Statement getStatement = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            getStatement = connection.createStatement();

            String statement = "SELECT * from " + DBTableColumnNames.LOGIN_MASTER_TABLE +
                    " WHERE " + DBTableColumnNames.USER_ID + "='" + userID + "'" +
                    " AND " + DBTableColumnNames.USER_PASSWORD + "='" + password + "' AND " +
                    "(ROLE_ID='Seva_MGR' OR ROLE_ID='Seva_MA')";
            rs = getStatement.executeQuery(statement);
            while (rs.next()) {
                user = new Users();
                user.setId(rs.getString(DBTableColumnNames.USER_ID));
                user.setName(rs.getString(DBTableColumnNames.USER_NAME));
                user.setPhoneNum(rs.getString(DBTableColumnNames.USER_PHONE_NUM));
                user.setUserEmailId(rs.getString(DBTableColumnNames.USER_EMAIL_ID));
                user.setLat(rs.getString(DBTableColumnNames.USER_LATITUDE));
                user.setLng(rs.getString(DBTableColumnNames.USER_LONGITUDE));
                user.setUserRoleID(rs.getString(DBTableColumnNames.USER_ROLE_ID));
                user.setUserDivName(rs.getString(DBTableColumnNames.USER_DIVISION_NAME));
                user.setUserDesignation(rs.getString(DBTableColumnNames.USER_DESIGNATION));
                user.setActiveStatus(rs.getString(DBTableColumnNames.USER_ACTIVE_STATUS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    if (rs != null)
                        rs.close();
                    if (getStatement != null)
                        getStatement.close();
                    // connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;

    }

    public String getLoggedInUser(String userID, String password) {
        String loggedInUserType = null;
        Users user = null;
        ResultSet rs = null;
        Statement getStatement = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            getStatement = connection.createStatement();

            String statement = "SELECT * from " + DBTableColumnNames.LOGIN_MASTER_TABLE +
                    " WHERE " + DBTableColumnNames.USER_ID + "='" + userID + "'" +
                    " AND " + DBTableColumnNames.USER_PASSWORD + "='" + password + "'";
            rs = getStatement.executeQuery(statement);
            while (rs.next()) {
                if (rs.getString(DBTableColumnNames.USER_ROLE_ID).toString().equals('A')
                        && rs.getString(DBTableColumnNames.USER_DESIGNATION).toUpperCase().equals("MANAGER")
                        && rs.getString(DBTableColumnNames.USER_ACTIVE_STATUS).equals('Y')) {
                    loggedInUserType = "MANAGER";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    if (rs != null)
                        rs.close();
                    if (getStatement != null)
                        getStatement.close();
                    // connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return loggedInUserType;

    }

    public String getDivisionList(String userID) throws Exception {
        ResultSet rs = null;
        String divisionList = null;


        String statement = "SELECT " + DBTableColumnNames.USER_DIVISION_NAME + " from " + DBTableColumnNames.LOGIN_MASTER_TABLE +
                " WHERE " + DBTableColumnNames.USER_ID + "='" + userID + "'";
        Statement getStatement = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            getStatement = connection.createStatement();
            //rs = getStatement.executeQuery(statement);
            boolean execute = getStatement.execute(statement);
            if (execute) {
                rs = getStatement.getResultSet();
                if (rs != null && rs.next()) {
                    divisionList = rs.getString(DBTableColumnNames.USER_DIVISION_NAME);
                }
            }
        } finally {
            if (rs != null)
                rs.close();
            if (getStatement != null)
                getStatement.close();
            // connection.close();
        }
        return divisionList;
    }

    public List<Users> getAllUserDetailByDivision(String divisionName) throws Exception {
        ResultSet rs = null;
        List<Users> usersList = new ArrayList<>();

        /*
        select * from (select user_id,timestamp,LATITUDE,LONGITUDE,RECORD_ENTRY_DATE,STATUS, row_number() over(partition by user_id order by timestamp desc)
        as rn from SK_LOCATION_ROUTE_TRACKING where user_id='fuser7' ) t where t.rn = 1
         */

        String statement = "SELECT * from " + DBTableColumnNames.LOGIN_MASTER_TABLE
                + " WHERE " + DBTableColumnNames.USER_DIVISION_NAME + " LIKE " + "'%" + divisionName + "%'"
                + " AND " + DBTableColumnNames.USER_ROLE_ID + "='" + AppConstants.FIELD_USER_ROLE + "'"
                + " AND " + DBTableColumnNames.USER_ROLE_ID + "!='" + AppConstants.MANAGER_USER_ROLE + "'";


        /*SELECT * from SK_LOGIN_MST WHERE DIVISION LIKE '%CE%'
                        AND ROLE_ID = 'Seva_MA'
                        AND DESIGNATION != 'MANAGER'*/
        Statement getStatement = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            getStatement = connection.createStatement();
            rs = getStatement.executeQuery(statement);
            boolean execute = getStatement.execute(statement);
            if (execute) {
                rs = getStatement.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
                        Users user = new Users();
                        user.setId(rs.getString(DBTableColumnNames.USER_ID));
                        user.setName(rs.getString(DBTableColumnNames.USER_NAME));
                        user.setLat(rs.getString(DBTableColumnNames.USER_LATITUDE));
                        user.setLng(rs.getString(DBTableColumnNames.USER_LONGITUDE));
                        user.setUserDivName(rs.getString(DBTableColumnNames.USER_DIVISION_NAME));
                        user.setUserEmailId(rs.getString(DBTableColumnNames.USER_EMAIL_ID));
                        user.setPhoneNum(rs.getString(DBTableColumnNames.USER_PHONE_NUM));
                        user.setPhoneNum(rs.getString(DBTableColumnNames.USER_PHONE_NUM));
                        user.setStatus(rs.getString(ALLOTED_ORDER_STATUS));
                        usersList.add(user);
                    }
                }
            }
        } finally {
            if (rs != null)
                rs.close();
            if (getStatement != null)
                getStatement.close();
        }
        return usersList;
    }

    public List<Users> getAllUserDetailByAllDivision(String divisionNameList) throws Exception {
        ResultSet rs = null;
        List<Users> usersList = new ArrayList<>();

        final String[] divisionArray = divisionNameList.split(",");

        String divisionCondStmt = DBTableColumnNames.USER_DIVISION_NAME + " LIKE ";

        String fieldUserCondStmt = " AND " + DBTableColumnNames.USER_ROLE_ID + "='" + AppConstants.FIELD_USER_ROLE + "'"
                + " AND " + DBTableColumnNames.USER_ROLE_ID + "!='" + AppConstants.MANAGER_USER_ROLE + "'";

        String div1CondStmt = divisionCondStmt + "'%" + divisionArray[0] + "%'" + fieldUserCondStmt;

        StringBuilder sb = new StringBuilder();
        for (int ii = 0; ii < divisionArray.length - 1; ii++) {
            String str = " OR " + divisionCondStmt + "'%" + divisionArray[ii + 1] + "%'" + fieldUserCondStmt;
            //str.concat(str);
            sb.append(str);
        }


        String statement = "SELECT * from " + DBTableColumnNames.LOGIN_MASTER_TABLE + " WHERE " + div1CondStmt + sb.toString();

        Statement getStatement = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            getStatement = connection.createStatement();
            rs = getStatement.executeQuery(statement);
            boolean execute = getStatement.execute(statement);
            if (execute) {
                rs = getStatement.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
                        Users user = new Users();
                        user.setId(rs.getString(DBTableColumnNames.USER_ID));
                        user.setName(rs.getString(DBTableColumnNames.USER_NAME));
                        user.setLat(rs.getString(DBTableColumnNames.USER_LATITUDE));
                        user.setLng(rs.getString(DBTableColumnNames.USER_LONGITUDE));
                        user.setUserEmailId(rs.getString(DBTableColumnNames.USER_EMAIL_ID));
                        user.setPhoneNum(rs.getString(DBTableColumnNames.USER_PHONE_NUM));
                        usersList.add(user);
                    }
                }
            }
        } finally {
            if (rs != null)
                rs.close();
            if (getStatement != null)
                getStatement.close();
        }
        return usersList;
    }

    public void insertUserLngLat(String userId, double lat, double lng, String imei_number, String status) throws SQLException {

        PreparedStatement stmt = null;
        String columns = " (" + DBTableColumnNames.USER_ID + ","
                + DBTableColumnNames.USER_LATITUDE + ","
                + DBTableColumnNames.USER_LONGITUDE + ","
                + DBTableColumnNames.RECORD_ENTRY_DATE + ","
                + DBTableColumnNames.TIMESTAMP + ","
                + ALLOTED_ORDER_STATUS + ","
                + DBTableColumnNames.IMEI_NO + ","
                + DBTableColumnNames.RECORD_ENTRY_TIME + ")";
        String values = " values(?,?,?,?,?,?,?,?)";
        String statement = "INSERT INTO " + DBTableColumnNames.LOCATION_ROUTE_TRACKING_TABLE + columns + values;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, userId);
            stmt.setDouble(2, lat);
            stmt.setDouble(3, lng);
            stmt.setString(4, CommonUtilities.todayDate());
            //stmt.setString(4, "28-11-2019");
            stmt.setString(5, String.valueOf(System.currentTimeMillis()));
            stmt.setString(6, status);
            stmt.setString(7, imei_number);
            stmt.setString(8, CommonUtilities.currentTime());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
                //connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        updateUserTableLngLat(userId, lat, lng,status);
    }

    public List<TasksDataModel> fetchTaskList() {
        List<TasksDataModel> tasksList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.TASKS_TABLE);
            while (rs.next()) {
                TasksDataModel task = new TasksDataModel();
                task.setId(rs.getString(DBTableColumnNames.TASK_ID));
                task.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                task.setName(rs.getString(DBTableColumnNames.TASK_NAME));
                task.setLng(rs.getDouble(DBTableColumnNames.TASK_LONGITUDE));
                task.setLat(rs.getDouble(DBTableColumnNames.TASK_LATTITUDE));
                task.setCreateOn(rs.getDate(DBTableColumnNames.TASK_CREATION_DATE));
                task.setAddress(rs.getString(DBTableColumnNames.TASK_ADRRESS));
                task.setDate(rs.getDate(DBTableColumnNames.TASK_DATE));
                task.setStatus(rs.getString(DBTableColumnNames.TASK_STATUS));
                task.setRequestTypeName(rs.getString(DBTableColumnNames.REQUEST_TYPE_NAME));
                task.setEmail(rs.getString(DBTableColumnNames.CUSTOMER_MAILID));
                task.setMobile(rs.getString(DBTableColumnNames.CUSTOMER_MOBILE));
                task.setAddress(rs.getString(DBTableColumnNames.CUSTOMER_ADDRESS));

                tasksList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasksList;
    }

    public List<Order> fetchUserTaskList(String userId, String status) {
        List<Order> orderList = new ArrayList<>();
        ResultSet rs;
        String statement = null;
        if (status != null) {
            statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " INNER JOIN " +
                    DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + " ON "
                    + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                    + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM +
                    " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.USER_ID + "='" + userId + "'" +
                    " AND " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + ALLOTED_ORDER_STATUS + "='" + status + "'";
        } else {
            statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " INNER JOIN " +
                    DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + " ON "
                    + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                    + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM +
                    " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.USER_ID + "='" + userId + "'";
        }

        Statement stmt = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            boolean execute = stmt.execute(statement);
            if (execute) {
                rs = stmt.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
                        Order order = new Order();
                        order.setOrderNum(rs.getString(DBTableColumnNames.ORDER_NUM));
                        order.setFirstName(rs.getString(DBTableColumnNames.CUSTOMER_FIRST_NAME));
                        order.setLastName(rs.getString(DBTableColumnNames.CUSTOMER_LAST_NAME));
                        order.setHouse_no(rs.getString(DBTableColumnNames.CUSTOMER_HOUSE_NO));
                        order.setBuilding_name(rs.getString(DBTableColumnNames.CUSTOMER_BUILDING_NAME));
                        order.setStreet(rs.getString(DBTableColumnNames.CUSTOMER_STREET));
                        order.setArea(rs.getString(DBTableColumnNames.CUSTOMER_AREA));
                        order.setPin(rs.getString(DBTableColumnNames.CUSTOMER_PIN));
                        orderList.add(order);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }

    public List<Order> fetchOrderDetailsTask(String order_num) {
        List<Order> orderList = new ArrayList<>();
        ResultSet rs;
        String statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " INNER JOIN " +
                DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + " ON "
                + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM +
                " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE +
                "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = '" + order_num + "'";
        Statement stmt = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            boolean execute = stmt.execute(statement);
            if (execute) {
                rs = stmt.getResultSet();
                if (rs != null) {
                    while (rs.next()) {
                        Order order = new Order();
                        Log.e("TAG", rs.getString(DBTableColumnNames.CUSTOMER_FATHER_NAME));
                        order.setOrderNum(rs.getString(DBTableColumnNames.ORDER_NUM));
                        order.setFirstName(rs.getString(DBTableColumnNames.CUSTOMER_FIRST_NAME));
                        order.setLastName(rs.getString(DBTableColumnNames.CUSTOMER_LAST_NAME));
                        order.setHouse_no(rs.getString(DBTableColumnNames.CUSTOMER_HOUSE_NO));
                        order.setBuilding_name(rs.getString(DBTableColumnNames.CUSTOMER_BUILDING_NAME));
                        order.setStreet(rs.getString(DBTableColumnNames.CUSTOMER_STREET));
                        order.setArea(rs.getString(DBTableColumnNames.CUSTOMER_AREA));
                        order.setPin(rs.getString(DBTableColumnNames.CUSTOMER_PIN));
                        order.setGender(rs.getString(DBTableColumnNames.CUSTOMER_GENDER));
                        order.setFather_name(rs.getString(DBTableColumnNames.CUSTOMER_FATHER_NAME));
                        order.setMother_name(rs.getString(DBTableColumnNames.CUSTOMER_MOTHER_NAME));
                        //order.setMobile_no(rs.getString(DBTableColumnNames.CUSTOMER_MOBILE_NUM));
                        //order.setEmail(rs.getString(DBTableColumnNames.CUSTOMER_EMAIL));
                        order.setEntry_date_1(rs.getString(DBTableColumnNames.CUSTOMER_ENTRY_DATE));
                        order.setStatus(rs.getString(DBTableColumnNames.CUSTOMER_STATUS));
                        orderList.add(order);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }
//,String create_date
    public void insertUserTrackingTime(String userId,String user_name,String order_address,String create_date,
                                       String order_num, String start_time, String end_time,
                                       String lat, String lng, String status) throws SQLException
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        PreparedStatement stmt = null;
        String columns = " (" + DBTableColumnNames.USER_ID + ","
                + DBTableColumnNames.ORDER_NUM + ","
                + DBTableColumnNames.CUSTOMER_NAME + ","
                + DBTableColumnNames.CUSTOMER_FORM_OPEN_TIME + ","
                + DBTableColumnNames.CUSTOMER_FORM_SUBMIT_TIME +","
                + DBTableColumnNames.LOCATION_GOOGLE +","
                + DBTableColumnNames.ADDRESS_GOOGLE +","
                + DBTableColumnNames.DATE_STAMP +","
                + DBTableColumnNames.CUSTOMER_FORM_OPEN_DATE_DSK +","
                + DBTableColumnNames.CUSTOMER_FORM_SUBMIT_DATE_DSK +","
                + DBTableColumnNames.LOCATION_GOOGLE_LAT +","
                + DBTableColumnNames.LOCATION_GOOGLE_LNG +","
                + DBTableColumnNames.TIME_STAMP_GOOGLE + ")";
        String values = " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String statement = "INSERT INTO " + DBTableColumnNames.CUSTOMER_WAIT_TIME_CUSTOMER_TRACKING + columns + values;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, userId);
            stmt.setString(2, order_num);
            stmt.setString(3, user_name);
            stmt.setString(4, start_time);
            stmt.setString(5, end_time);
            stmt.setString(6, "Noida");
            stmt.setString(7, "Sec 12");
            stmt.setString(8,dateFormat.format(date));
            stmt.setString(9,"123");
            stmt.setString(10,"123");
            stmt.setString(11,"123");
            stmt.setString(12,"123");
            stmt.setString(13,"123");
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG","Cannot submit");
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                //connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        insertUserTrackingLatLng(userId,lat,lng,user_name,create_date,order_address,"","",
                "", "",order_num,status);
    }

    public void insertUserTrackingLatLng(String userId,String lat,String lng,String user_name,String createon,
                                         String order_address,String division,String order_date,
                                         String order_time,String timestamp,String order_num, String status) throws SQLException
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date entry_date = new Date();
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        //DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        //Date date = sdf.parse(createon);

        PreparedStatement stmt = null;
        String columns = " (" + DBTableColumnNames.USER_ID + ","
                + DBTableColumnNames.USER_LATEST_LATITUDE + ","
                + DBTableColumnNames.USER_LATEST_LONGITUDE + ","
                + DBTableColumnNames.USER_NAME + ","
                + DBTableColumnNames.USER_CREATEON +","
                + DBTableColumnNames.ORDER_ADDRESS +","
                + DBTableColumnNames.USER_DIVISION_NAME +","
                + DBTableColumnNames.ORDER_DATE +","
                + DBTableColumnNames.ALLOTED_ORDER_ENTRY_DATE +","
                + DBTableColumnNames.ALLOTED_ORDER_TIME +","
                + DBTableColumnNames.ALLOTED_ORDER_TIMESTAMP +","
                + DBTableColumnNames.ORDER_NUM +","
                + ALLOTED_ORDER_STATUS + ")";
        String values = " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String statement = "INSERT INTO " + DBTableColumnNames.TRACKING_REQUEST_TABLE + columns + values;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, userId);
            stmt.setString(2, lat);
            stmt.setString(3, lng);
            stmt.setString(4, user_name);
            stmt.setString(5, createon);
            stmt.setString(6, order_address);
            stmt.setString(7, division);
            stmt.setString(8, order_date);
            stmt.setString(9, dateFormat.format(entry_date));
            stmt.setString(10,currentTime);
            stmt.setString(11,timestamp);
            stmt.setString(12,order_num);
            stmt.setString(13,status);

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG","Cannot submit");
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertUserTrackingHomeLatLng(String userId,String lat,String lng,String status) throws SQLException
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date entry_date = new Date();
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        PreparedStatement stmt = null;
        String columns = " (" + DBTableColumnNames.USER_ID + ","
                + DBTableColumnNames.USER_LATEST_LATITUDE + ","
                + DBTableColumnNames.USER_LATEST_LONGITUDE + ","
                + DBTableColumnNames.USER_NAME + ","
                + DBTableColumnNames.USER_CREATEON +","
                + DBTableColumnNames.ORDER_ADDRESS +","
                + DBTableColumnNames.USER_DIVISION_NAME +","
                + DBTableColumnNames.ORDER_DATE +","
                + DBTableColumnNames.ALLOTED_ORDER_ENTRY_DATE +","
                + DBTableColumnNames.ALLOTED_ORDER_TIME +","
                + DBTableColumnNames.ALLOTED_ORDER_TIMESTAMP +","
                + DBTableColumnNames.ORDER_NUM +","
                + ALLOTED_ORDER_STATUS + ")";
        String values = " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String statement_insert = "INSERT INTO " + DBTableColumnNames.TRACKING_REQUEST_TABLE + columns + values;

        try {
            stmt = connection.prepareStatement(statement_insert);
            stmt.setString(1, userId);
            stmt.setString(2, lat);
            stmt.setString(3, lng);
            stmt.setString(4, "");
            stmt.setString(5, "");
            stmt.setString(6, "");
            stmt.setString(7, "");
            stmt.setString(8, "");
            stmt.setString(9,dateFormat.format(entry_date));
            stmt.setString(10,currentTime);
            stmt.setString(11,"");
            stmt.setString(12,"");
            stmt.setString(13,status);

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG","Cannot submit");
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


/*
      ResultSet rs;
        String statement = "SELECT * from " + DBTableColumnNames.TRACKING_REQUEST_TABLE +
                " WHERE " + DBTableColumnNames.USER_ID + " = '" + userId + "' and "
                + DBTableColumnNames.ALLOTED_ORDER_ENTRY_DATE + " = '" + dateFormat.format(entry_date) + "' " +
                "and ORDER_NO IS NULL";
        Log.e("TAG_DATE",dateFormat.format(entry_date));
        */
/*
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Statement stmt1 = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt1 = connection.createStatement();
            boolean execute = stmt1.execute(statement);
            if (execute)
            {
                rs = stmt1.getResultSet();
                if (rs != null)
                {
                    if(rs.getFetchSize()>0)
                    {
                        Log.e("TAG","update");
                        PreparedStatement stmtUpdate = null;
                        String statementUpdate = "UPDATE " + DBTableColumnNames.TRACKING_REQUEST_TABLE +
                                " SET " + DBTableColumnNames.USER_LATEST_LATITUDE + " = '"+lat+"', "
                                + DBTableColumnNames.USER_LATEST_LONGITUDE + " = '"+lng+"', ORDER_TIME = '"
                                +currentTime+"' " + "WHERE " + DBTableColumnNames.USER_ID + "='" + userId + "'";
                        try {
                            stmtUpdate = connection.prepareStatement(statementUpdate);
                            stmtUpdate.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (stmtUpdate != null)
                                    stmtUpdate.close();
                                // connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Log.e("TAG","insert");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt1 != null)
                    stmt1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        */
    }

    public List<Customer> fetchFieldUserOrderList(String userId, String status) {
        List<Customer> custList = new ArrayList<>();

        ResultSet rs;
        Statement stmt = null;

        String statement = null;
        if(status!=null)
        {
            statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " INNER JOIN "+
                    DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + " ON "
                    + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                    + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM +
                    " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.USER_ID + "='" + userId + "'"+
                    " AND "+ DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + ALLOTED_ORDER_STATUS +"='" + status + "'";
        }
        else
        {
            statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " INNER JOIN "+
                    DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + " ON "
                    + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                    + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM +
                    " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.USER_ID + "='" + userId + "'";
        }

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
/*
            String statement = "SELECT * from " + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE
                    + " JOIN " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " ON "
                    + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM + " = "
                    + DBTableColumnNames.CUSTOMER_NEW_CONNECTION_REQUEST_TABLE + "." + DBTableColumnNames.ALLOTED_ORDER_NUM
                    + " WHERE " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + "." + DBTableColumnNames.USER_ID
                    + "='" + userId + "'";
*/
            rs = stmt.executeQuery(statement);

            while (rs.next()) {
                Customer cust = new Customer();
                cust.setOrderNum(rs.getString(DBTableColumnNames.ORDER_NUM));
                cust.setFirstName(rs.getString(DBTableColumnNames.CUSTOMER_FIRST_NAME));
                cust.setLastName(rs.getString(DBTableColumnNames.CUSTOMER_LAST_NAME));
                cust.setHouse_no(rs.getString(DBTableColumnNames.CUSTOMER_HOUSE_NO));
                cust.setBuilding_name(rs.getString(DBTableColumnNames.CUSTOMER_BUILDING_NAME));
                cust.setStreet(rs.getString(DBTableColumnNames.CUSTOMER_STREET));
                cust.setArea(rs.getString(DBTableColumnNames.CUSTOMER_AREA));
                cust.setPin(rs.getString(DBTableColumnNames.CUSTOMER_PIN));
                custList.add(cust);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return custList;
    }

    public void insertTask(Map<String, Object> taskInfo) {
        PreparedStatement stmt = null;
        String columns = " ("
                + DBTableColumnNames.TASK_ID
                + ","
                + DBTableColumnNames.USER_ID
                + ","
                + DBTableColumnNames.TASK_LATTITUDE
                + ","
                + DBTableColumnNames.TASK_LONGITUDE
                + ","
                + DBTableColumnNames.TASK_NAME
                + ","
                + DBTableColumnNames.TASK_CREATION_DATE
                + ","
                + DBTableColumnNames.TASK_ADRRESS
                + ","
                + DBTableColumnNames.TASK_DATE
                + ","
                + DBTableColumnNames.TASK_TIME
                + ","
                + DBTableColumnNames.TIMESTAMP
                + ","
                + DBTableColumnNames.TASK_STATUS
                + ","
                + DBTableColumnNames.CUSTOMER_REQUEST_NUM
                + ","
                + DBTableColumnNames.CUSTOMER_MOBILE
                + ","
                + DBTableColumnNames.CUSTOMER_MAILID
                + ","
                + DBTableColumnNames.REQUEST_TYPE_ID
                + ","
                + DBTableColumnNames.CUSTOMER_ADDRESS
                + ","
                + DBTableColumnNames.REQUEST_TYPE_NAME
                + ")";
        String values = " values(TASKS_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String statement = "INSERT INTO " + DBTableColumnNames.TASKS_TABLE + columns + values;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.prepareStatement(statement);


            SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            timeFormat.setTimeZone(timeZone);
            Calendar cal = Calendar.getInstance();
            String timeStamp = timeFormat.format(cal.getTime());

            stmt.setString(1, taskInfo.get(AppConstants.USER_ID).toString());
            stmt.setString(2, taskInfo.get(AppConstants.TASK_LATTITUDE).toString());
            stmt.setString(3, taskInfo.get(AppConstants.TASK_LONGITUDE).toString());
            stmt.setString(4, taskInfo.get(AppConstants.TASK_NAME).toString());
            stmt.setString(5, CommonUtilities.todayDate());
            stmt.setString(6, taskInfo.get(AppConstants.CUSTOMER_ADDRESS).toString());
            stmt.setString(7, CommonUtilities.todayDate());
            stmt.setString(8, timeStamp);
            stmt.setString(9, String.valueOf(System.currentTimeMillis()));
            stmt.setString(10, taskInfo.get(AppConstants.TASK_STATUS).toString());
            stmt.setString(11, taskInfo.get(AppConstants.CUSTOMER_REQUEST).toString());
            stmt.setString(12, taskInfo.get(AppConstants.CUSTOMER_MOBILE).toString());
            stmt.setString(13, taskInfo.get(AppConstants.CUSTOMER_EMAILID).toString());
            stmt.setString(14, taskInfo.get(AppConstants.CUSTOMER_REQUEST_TYPE_ID).toString());
            stmt.setString(15, taskInfo.get(AppConstants.CUSTOMER_ADDRESS).toString());
            stmt.setString(16, taskInfo.get(AppConstants.CUSTOMER_REQUEST_TYPE_NAME).toString());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertUser(Map<String, Object> userInfo) throws SQLException {
        PreparedStatement stmt = null;
        String columns = " ("
                + DBTableColumnNames.USER_ID
                + ","
                + DBTableColumnNames.USER_LATITUDE
                + ","
                + DBTableColumnNames.USER_LONGITUDE
                + ","
                + DBTableColumnNames.USER_PASSWORD
                + ","
                + DBTableColumnNames.USER_NAME
                + ","
                + DBTableColumnNames.USER_TYPE_ID
                + ","
                + DBTableColumnNames.USER_EMAIL_ID
                + ","
                + DBTableColumnNames.USER_TYPE_NAME
                + ","
                + DBTableColumnNames.USER_DIVISION_ID
                + ","
                + DBTableColumnNames.USER_DIVISION_NAME +
                ")";

        String values = " values(USERS_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
        String statement = "INSERT INTO " + DBTableColumnNames.USERS_TABLE + columns + values;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, userInfo.get(AppConstants.USER_LATTITUDE).toString());
            stmt.setString(2, userInfo.get(AppConstants.USER_LONGITUDE).toString());
            stmt.setString(3, userInfo.get(AppConstants.USER_PASSWORD).toString());
            stmt.setString(4, userInfo.get(AppConstants.USER_NAME).toString());
            stmt.setString(5, userInfo.get(AppConstants.USER_TYPE_ID).toString());
            stmt.setString(6, userInfo.get(AppConstants.USER_EMAILID).toString());
            stmt.setString(7, userInfo.get(AppConstants.USER_TYPE_NAME).toString());
            stmt.setString(8, userInfo.get(AppConstants.USER_DIVISION_ID).toString());
            stmt.setString(9, userInfo.get(AppConstants.USER_DIVISION_NAME).toString());

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<TasksData> fetchUserTaskFrmTaskTableList(String userId) {
        List<TasksData> tasksList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "'");
            while (rs.next()) {
                TasksData task = new TasksData();
                task.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                task.setDate(rs.getString(DBTableColumnNames.ALLOTED_ORDER_ENTRY_DATE));
                task.setRequestNo(rs.getString(DBTableColumnNames.ALLOTED_ORDER_NUM));
                task.setStatus(rs.getString(ALLOTED_ORDER_STATUS));
                tasksList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasksList;
    }

    public List<TasksData> fetchUserTrackingDetail(String userId) {
        List<TasksData> tasksList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.TRACKING_REQUEST_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "'");
            while (rs.next()) {
                TasksData task = new TasksData();
                task.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                task.setName(rs.getString(DBTableColumnNames.TASK_NAME));
                task.setLat(rs.getString(DBTableColumnNames.USER_LATEST_LATITUDE));
                task.setLng(rs.getString(DBTableColumnNames.USER_LATEST_LONGITUDE));
                task.setCreateOn(rs.getString(DBTableColumnNames.TASK_CREATION_DATE));
                task.setAddress(rs.getString(DBTableColumnNames.TASK_ADRRESS));
                task.setDate(rs.getString(DBTableColumnNames.TASK_DATE));
                task.setTime(rs.getString(DBTableColumnNames.TASK_TIME));
                task.setStatus(rs.getString(DBTableColumnNames.TASK_STATUS));
                tasksList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasksList;
    }

    public List<UserLocationData> fetchFieldLocationList(String userId)
    {
        List<UserLocationData> locList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.LOCATION_ROUTE_TRACKING_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "' AND "
                    + DBTableColumnNames.RECORD_ENTRY_DATE + "='"+CommonUtilities.todayDate()+"'");
            while (rs.next()) {
                UserLocationData locationData = new UserLocationData();

                locationData.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                locationData.setLat(rs.getString(DBTableColumnNames.FIELD_LOCATION_LAT));
                locationData.setLng(rs.getString(DBTableColumnNames.FIELD_LOCATION_LNG));
                locationData.setDate(rs.getString(DBTableColumnNames.RECORD_ENTRY_DATE));
                locationData.setStatus(rs.getString(DBTableColumnNames.CUSTOMER_STATUS));
                locationData.setTime("");
                locList.add(locationData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return locList;
    }

    public void updateUserTableLngLat(String userId, double lat, double lng, String status) throws SQLException {
        PreparedStatement stmtUpdate = null;
        String statementUpdate = "UPDATE " + DBTableColumnNames.LOGIN_MASTER_TABLE +
                " SET " + DBTableColumnNames.USER_LATITUDE + " = '" + lat + "', "
                + DBTableColumnNames.USER_LONGITUDE + " = '" + lng + "', " +
                ALLOTED_ORDER_STATUS + " = '" + status + "' "+
                "WHERE " + DBTableColumnNames.USER_ID + "='" + userId + "'";

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmtUpdate = connection.prepareStatement(statementUpdate);
            stmtUpdate.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtUpdate != null)
                    stmtUpdate.close();
                // connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void getAllTaskList(String userId, double lat, double lng) throws SQLException {
        PreparedStatement stmtUpdate = null;
        String statementUpdate = "UPDATE " + DBTableColumnNames.USERS_TABLE +
                " SET " + DBTableColumnNames.USER_LATITUDE + " = " + lat + ", " + DBTableColumnNames.USER_LONGITUDE + " = " + lng + " WHERE " + DBTableColumnNames.USER_ID + " = " + userId;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmtUpdate = connection.prepareStatement(statementUpdate);
            stmtUpdate.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtUpdate != null)
                    stmtUpdate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void assignTask(String taskId, String userId) throws SQLException {
        PreparedStatement stmtUpdate = null;
        String statementUpdate = "UPDATE " + DBTableColumnNames.TASKS_TABLE +
                " SET " + DBTableColumnNames.USER_ID + " = " + userId + " WHERE " + DBTableColumnNames.TASK_ID + " = " + taskId;

        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmtUpdate = connection.prepareStatement(statementUpdate);
            stmtUpdate.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtUpdate != null)
                    stmtUpdate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void new_updateUserTableLngLat1(String userId, double lat, double lng) throws SQLException {
        PreparedStatement stmtUpdate = null;
        String statementUpdate = "UPDATE " + DBTableColumnNames.LOGIN_MASTER_TABLE +
                " SET " + DBTableColumnNames.USER_LATITUDE + " = " + lat + ", "
                + DBTableColumnNames.USER_LONGITUDE + " = " + lng + " " +
                "WHERE " + DBTableColumnNames.USER_ID + " = " + userId;
        //Connection connection = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmtUpdate = connection.prepareStatement(statementUpdate);
            stmtUpdate.execute();
            Log.e("TAG", "Successfully  updated" + lat + " " + lng);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtUpdate != null)
                    stmtUpdate.close();
                // connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UserLocationData> fetchDatewiseLocDataByUser(String userId, String date) {
        List<UserLocationData> locList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;
        //String date = "14-11-2019";
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();
            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.LOCATION_ROUTE_TRACKING_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "' AND " + DBTableColumnNames.RECORD_ENTRY_DATE +
                    "='" + date + "'");
            //SELECT * from SK_LOCATION_ROUTE_TRACKING WHERE USER_ID='pras' AND ENTRY_DATE ='14-11-2019'
/*
            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.TRACKING_REQUEST_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "' AND " + DBTableColumnNames.ENTRY_DATE + "='" + date + "'");
            */
            while (rs.next())
            {
                UserLocationData locationData = new UserLocationData();
                locationData.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                locationData.setLat(rs.getString(DBTableColumnNames.FIELD_LOCATION_LAT));
                locationData.setLng(rs.getString(DBTableColumnNames.FIELD_LOCATION_LNG));
                locationData.setDate(rs.getString(DBTableColumnNames.RECORD_ENTRY_DATE));
                locationData.setStatus(rs.getString(DBTableColumnNames.CUSTOMER_STATUS));
                locationData.setTime(rs.getString(DBTableColumnNames.RECORD_ENTRY_TIME));
/*
                locationData.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                locationData.setLat(rs.getString(DBTableColumnNames.USER_LATEST_LATITUDE));
                locationData.setLng(rs.getString(DBTableColumnNames.USER_LATEST_LONGITUDE));
                locationData.setDate(rs.getString(DBTableColumnNames.ENTRY_DATE));
                locationData.setStatus(rs.getString(DBTableColumnNames.CUSTOMER_STATUS));
                locationData.setTime("");
*/
                //locationData.setTime(rs.getTime(DBTableColumnNames.TIMESTAMP));
                locList.add(locationData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return locList;
    }

    public List<TasksData> fetchDateWiseUserTaskFromTaskTableList(String userId,String date, String status)
    {
        List<TasksData> tasksList = new ArrayList<>();
        ResultSet rs;
        Statement stmt = null;
        try {
            if (connection != null) {
                if (connection.isClosed())
                    connection = createNewConnection();

            } else {
                connection = createNewConnection();
            }
            stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.CUSTOMER_ORDER_TABLE + " WHERE " +
            //        DBTableColumnNames.USER_ID + "='" + userId + "'");

            rs = stmt.executeQuery("SELECT * from " + DBTableColumnNames.TRACKING_REQUEST_TABLE + " WHERE " +
                    DBTableColumnNames.USER_ID + "='" + userId + "' AND " + DBTableColumnNames.ENTRY_DATE + "='"
                    + date + "' " +
                    "AND " + ALLOTED_ORDER_STATUS + "='" + status + "'");

            while (rs.next()) {
                TasksData task = new TasksData();
                task.setUserid(rs.getString(DBTableColumnNames.USER_ID));
                //task.setDate(rs.getString(DBTableColumnNames.ALLOTED_ORDER_ENTRY_DATE));
                //task.setRequestNo(rs.getString(DBTableColumnNames.ALLOTED_ORDER_NUM));
                //task.setStatus(rs.getString(ALLOTED_ORDER_STATUS));
                task.setLat(rs.getString(DBTableColumnNames.USER_LATEST_LATITUDE));
                task.setLng(rs.getString(DBTableColumnNames.USER_LATEST_LONGITUDE));
                task.setName(rs.getString(DBTableColumnNames.USER_NAME));
                task.setCreateOn(rs.getString(DBTableColumnNames.USER_CREATEON));
                task.setDate(rs.getString(DBTableColumnNames.ENTRY_DATE));
                task.setTime(rs.getString(DBTableColumnNames.ALLOTED_ORDER_TIME));
                task.setAddress(rs.getString(DBTableColumnNames.ORDER_ADDRESS));

                //locationData.setLat(rs.getString(DBTableColumnNames.USER_LATEST_LATITUDE));
                //locationData.setLng(rs.getString(DBTableColumnNames.USER_LATEST_LONGITUDE));
                //locationData.setDate(rs.getString(DBTableColumnNames.ENTRY_DATE));
                //locationData.setStatus(rs.getString(DBTableColumnNames.CUSTOMER_STATUS));
                //locationData.setTime("");


                tasksList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasksList;
    }
}
