package com.bses.dinesh.dsk.fragMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.adapter.ListViewAdapter;
import com.bses.dinesh.dsk.adapter.SpinnerAdapterFilter;
import com.bses.dinesh.dsk.bean.BroadcastMsgBean;
import com.bses.dinesh.dsk.bean.McrOrederBean;
import com.bses.dinesh.dsk.proxies.BroadcastMsgProxie;
import com.bses.dinesh.dsk.proxies.EmpMessagesProxie;
import com.bses.dinesh.dsk.sevakendra.LoginScreen;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.GPSTracker;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Krishna on 4/29/2016.
 */
public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<McrOrederBean> empList = new ArrayList<McrOrederBean>();
    private List<McrOrederBean> empListFiltered = new ArrayList<McrOrederBean>();
    public List<McrOrederBean> empListMain = new ArrayList<McrOrederBean>();

    private RecyclerView recyclerView, recyclerEmpView;
    public ListViewAdapter mAdapter;
    private FloatingActionButton fab, fab2, fab3;
    private android.app.AlertDialog filterDialog;
    private EditText startDateEditText;
    private View filterDialogView;
    private String startOrEndDate = "";
    private TextView messageTitle;
    private TextView messageDescription;
    private EditText inputSdate, inputEdate;
    private TextInputLayout inputLayoutSdate, inputLayoutEdate;
    private UserPreferences userPreferences;
    private String startDate = "SELECT", endDate = "SELECT";
    private CheckBox cbAll, cbRead, cbUnread;
    private String readFilter = "ALL";
    public ProgressBar webservicePG;
    // private DBHandler db;
    private ArrayList<McrOrederBean> msgs;
    private ProgressDialog progressDialog;
    //broadcast data
    BroadcastMsgBean broadcastMsgBean;
    BroadcastMsgProxie broadcastMsgProxie;

    private McrOrederBean selectedTask;
    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    private FrameLayout fm1;
    private String stringLatitude = "";
    private String stringLongitude = "";
    // GPSTracker class
    GPSTracker gpsTracker;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner dropOrderType, dropPmActivity;
    private String dropOrderTypeVal = "", dropPmActivityVal = "";
    private String[] pmActiArr, pmActiArrSub, orderTypeArr, orderTypeArrSub;
    private String orderTypeTextVal, orderTypeIDVal, pmActiTextVal, pmActiIDVal;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressDialog = new ProgressDialog(getActivity());

        recyclerEmpView = (RecyclerView) rootView.findViewById(R.id.recycler_emp_listview);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fabfilter);

        userPreferences = UserPreferences.getInstance(getActivity());
        webservicePG = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SEVA KENDRA");


        gpsTracker = new GPSTracker(getActivity());

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());

            userPreferences.setLat(stringLatitude);
            userPreferences.setLong(stringLongitude);
        } else {

            gpsTracker.showSettingsAlert();
        }

        if (userPreferences.getUserid() != "") {
            prepareMessageData();
        } else {
            Intent intent = new Intent(getActivity(), LoginScreen.class);
            startActivity(intent);
        }

        init();


        return rootView;
    }


    private void init() {

        //recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_listview);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                filterDialogView = inflater.inflate(R.layout.alertdialog_filter, null);
                builder.setView(filterDialogView);

                TextView msg_title = (TextView) filterDialogView.findViewById(R.id.alert_title);
                msg_title.setText("Filter");
                TextView messageDescription = (TextView) filterDialogView.findViewById(R.id.message_description);
                // messageDescription.setText("");

                try {
                    // String[] orderTypeArr = getResources().getStringArray(R.array.order_type);;
                    orderTypeArr = getResources().getStringArray(R.array.order_type);
                    orderTypeArrSub = getResources().getStringArray(R.array.order_type_sub);

                    pmActiArr = getResources().getStringArray(R.array.pm_zdin);
                    pmActiArrSub = getResources().getStringArray(R.array.pm_zdin_sub);

                } catch (Exception er) {
                    er.printStackTrace();
                }


                dropOrderType = (Spinner) filterDialogView.findViewById(R.id.order_type);
                dropPmActivity = (Spinner) filterDialogView.findViewById(R.id.pm_activity_type);


                dropOrderType.setAdapter(new SpinnerAdapterFilter(getActivity(), R.layout.custom_spinner, orderTypeArr, orderTypeArrSub));
                dropOrderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if (position != 0) {

                            TextView pmTypeText = (TextView) view.findViewById(R.id.text_main_seen);
                            TextView pmTypeID = (TextView) view.findViewById(R.id.sub_text_seen);

                            pmActiTextVal = pmTypeText.getText().toString();
                            pmActiIDVal = pmTypeID.getText().toString();

                            //String compID = String.valueOf(reqId.getText().toString());
                            //taskCompGroup.execute(compID);
                        } else {
                            pmActiTextVal = "ALL";
                            pmActiIDVal = "ALL";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                dropPmActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {

                            TextView pmTypeText = (TextView) view.findViewById(R.id.text_main_seen);
                            TextView pmTypeID = (TextView) view.findViewById(R.id.sub_text_seen);

                            pmActiTextVal = pmTypeText.getText().toString();
                            pmActiIDVal = pmTypeID.getText().toString();
                        } else {
                            pmActiTextVal = "ALL";
                            pmActiIDVal = "ALL";
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                final AlertDialog filterAlert = builder.create();

                ImageButton closeDialog = (ImageButton) filterDialogView.findViewById(R.id.closeDialog);
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterAlert.dismiss();
                    }
                });

                final Button search = (Button) filterDialogView.findViewById(R.id.allocate_submit);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        filterAlert.dismiss();
                        empListFiltered.clear();

                        for (int i = 0; i < empListMain.size(); i++) {
                            if (empListMain.get(i).getILART().equalsIgnoreCase(pmActiIDVal)) {
                                empListFiltered.add(empListMain.get(i));

                            } else if (pmActiIDVal.equalsIgnoreCase("ALL")) {
                                empListFiltered.add(empListMain.get(i));

                            }
                        }

                        empList.clear();

                        empList.addAll(empListFiltered);
                        mAdapter = new ListViewAdapter(empList, getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerEmpView.setLayoutManager(mLayoutManager);
                        recyclerEmpView.setItemAnimator(new DefaultItemAnimator());
                        //recyclerEmpView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                        recyclerEmpView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                    }
                });


                filterAlert.show();


            }
        });


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return true;
            }
        });

        // return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        // showing refresh animation before making http call
        // init();
        swipeRefreshLayout.setRefreshing(true);
        prepareMessageData();
    }


    private void prepareMessageData() {
        //Create instance for AsyncCallWS
        GetMessagesCallWS task = new GetMessagesCallWS();
        //Call execute
        task.execute();
    }


    public void setSelectedDate(int iYear, int iMonth, int iDay) {
        // if(startOrEndDate.equals("start")){

        if (startOrEndDate.equals("start")) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] date_break = date.split("-");
            int current_year = Integer.parseInt(date_break[0]);
            int current_month = Integer.parseInt(date_break[1]);
            int current_day = Integer.parseInt(date_break[2]);

            if (iDay <= current_day && iMonth <= current_month && iYear <= current_year) {
                inputSdate.setText(iDay + "-" + iMonth + "-" + iYear);
                startDate = inputSdate.getText().toString().trim();
            } else {
                startDate = "SELECT";
                Snackbar.make(this.getView(), "Selected date should be greater than current date", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        } else {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] date_break = date.split("-");
            int current_year = Integer.parseInt(date_break[0]);
            int current_month = Integer.parseInt(date_break[1]);
            int current_day = Integer.parseInt(date_break[2]);

            if (iDay <= current_day && iMonth <= current_month && iYear <= current_year) {
                inputEdate.setText(iDay + "-" + iMonth + "-" + iYear);
                endDate = inputEdate.getText().toString().trim();
            } else {
                endDate = "SELECT";
                Snackbar.make(this.getView(), "Selected date should be greater than current date", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @SuppressLint("ValidFragment")
    class displayDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int iYear = calendar.get(Calendar.YEAR);
            int iMonth = calendar.get(Calendar.MONTH);
            int iDay = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, iYear, iMonth, iDay);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            setSelectedDate(yy, mm + 1, dd);
        }
    }


    private class GetMessagesCallWS extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            //  swipeRefreshLayout.setRefreshing(true);


            EmpMessagesProxie empMessageProxie = new EmpMessagesProxie();

            // empMessageProxie.setStrUserType(userPreferences.getUser_role());
            empMessageProxie.setStrDivision(userPreferences.getDivision());
            empMessageProxie.setStrUser_id(userPreferences.getUserid());

            msgs = new ArrayList<McrOrederBean>();
            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {
                if (ApplicationUtil.getInstance().checkInternetConnection(getActivity())) {


                    msgs = ApplicationUtil.getInstance().getWebservice().getEmpMessages(empMessageProxie, getActivity());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //webservicePG.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Please Wait...");
            progressDialog.incrementProgressBy(20);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);

            try {

                if (msgs != null) {

                    empListMain.clear();
                    for (McrOrederBean msg : msgs) {
                        empListMain.add(msg);

                    }


                    empList.addAll(empListMain);
                    progressDialog.dismiss();

                    mAdapter = new ListViewAdapter(empListMain, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerEmpView.setLayoutManager(mLayoutManager);
                    recyclerEmpView.setItemAnimator(new DefaultItemAnimator());
                    //recyclerEmpView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    recyclerEmpView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } else {
                    Snackbar.make(getView(), "Server not responding!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            } catch (Exception er) {
                er.printStackTrace();
                Snackbar.make(getView(), "Server not responding!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }


}
