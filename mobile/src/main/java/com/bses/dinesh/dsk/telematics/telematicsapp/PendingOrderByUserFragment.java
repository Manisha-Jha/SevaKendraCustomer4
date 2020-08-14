package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.CustomScrollView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PendingOrderByUserFragment extends Fragment {

    String mOrderTypeStr, mOffsetStr, mLimitStr;

    ArrayList<Order> mQATaskList = new ArrayList<Order>();
    ArrayList<Order> orderList;
    ProgressBar pbPendingTask;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView empty_text;
    RecyclerView recyclerView;
    PendingOrderAdpater mAdapter;
    CustomScrollView scrollview;
    View rootView;
    String sortBy;
    String searchText = "";
    View tab;
    TabLayout tabLayout;
    TextView tabOne;
    boolean pullToRefresh = false;
    String selecetdUserId;
    CustomScrollView.ScrollViewListener orderDetailsScrollViewListener = new CustomScrollView.ScrollViewListener() {

        @Override
        public void onScrollChanged(CustomScrollView scrollView, int currLen, int currTop, int prevLen, int prevTop) {

            View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
            // if diff is zero, then the bottom has been reached
            if (diff == 0) {
                if (mOrderTypeStr != null)
                    new UpdateListing(false).execute(mOffsetStr);
                else
                    scrollview.setEnabled(false);
            }

        }
    };
    SearchView.OnCloseListener searchCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            mQATaskList.clear();
            searchText = "";
            mOffsetStr = "0";
            new UpdateListing(true).execute(mOffsetStr);
            return false;
        }
    };
    SearchView.OnQueryTextListener taskSearchLstener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mQATaskList.clear();
            query = query.toLowerCase();
            searchText = query;
            mOffsetStr = "0";
            new UpdateListing(true).execute(mOffsetStr);

            InputMethodManager inputMethodManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

    };

    /*
        PendingOrderByUserFragment() {
            // doesn't do anything special
        }

        PendingOrderByUserFragment(String selecetdUserId){
            this.selecetdUserId = selecetdUserId;
        }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        selecetdUserId = getArguments().getString("user_id");

        rootView = inflater.inflate(R.layout.pending_order_list_layout, container, false);
        setHasOptionsMenu(true);
        // sqliteDatabaseHandler = new SqliteDatabaseHandler(getActivity());
        pbPendingTask = (ProgressBar) rootView.findViewById(R.id.today_task_progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        empty_text = (TextView) rootView.findViewById(R.id.empty_text);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        scrollview = (CustomScrollView) rootView.findViewById(R.id.lead_details_scrollview);
        scrollview.setScrollViewListener(orderDetailsScrollViewListener);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tab = tabLayout.getTabAt(0).getCustomView(); // fo
        tabOne = (TextView) tab.findViewById(R.id.tab);
        orderList = new ArrayList<>();
        mOrderTypeStr = "orderExecutionQAChecklist";// orders by user ID
        mOffsetStr = "0";
        mLimitStr = "0";
        sortBy = "DATE";
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                swipeRefreshLayout.setRefreshing(false);
                mQATaskList.clear();
                new UpdateListing(false).execute("0");
                pullToRefresh = true;
            }
        });
        new UpdateListing().execute("0");

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*inflater.inflate(R.menu.search_sort_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_menuItem);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(taskSearchLstener);
        searchView.setOnCloseListener(searchCloseListener);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mQATaskList.clear();
                        searchText = "";
                        mOffsetStr = "0";
                        new UpdateListing(true).execute(mOffsetStr);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });*/
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        /*SpannableString s;
        menu.findItem(R.id.Title).setEnabled(false);
        if (selectedMenuItem.equals("CustomerName")) {
            MenuItem miCustName = menu.findItem(R.id.SortByCustomerName);
            String miCustNameStr = miCustName.getTitle().toString();
            s = new SpannableString(miCustNameStr);
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),
                    0, s.length(), 0);
            miCustName.setTitle(s);
        } else {
            MenuItem miQADate = menu.findItem(R.id.SortByQADate);
            String miQADateStr = miQADate.getTitle().toString();
            s = new SpannableString(miQADateStr);
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),
                    0, s.length(), 0);
            miQADate.setTitle(s);
        }

        return;*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
        } else if (item.getItemId() == R.id.SortByCustomerName) {
            if (ischecked == true) {
                selectedMenuItem = "CustomerName";
                mOffsetStr = "0";
                new UpdateListing(true).execute(mOffsetStr);
                scrollview.scrollTo(0, scrollview.getTop());
                sortBy = "NAME";
                ischecked = false;
            }
        } else if (item.getItemId() == R.id.SortByQADate) {
            if (ischecked == false) {
                selectedMenuItem = "QADate";
                mOffsetStr = "0";
                new UpdateListing(true).execute(mOffsetStr);
                scrollview.scrollTo(0, scrollview.getTop());
                sortBy = "DATE";
                ischecked = true;
            }
        }

        ActivityCompat.invalidateOptionsMenu(getActivity());
        return super.onOptionsItemSelected(item);*/
        return false;
    }

    private class UpdateListing extends AsyncTask<String, Integer, Boolean> {
        List<Order> orderList;
        private ProgressDialog pd;
        private boolean showLoader;

        UpdateListing() {
            orderList = new ArrayList<>();
        }

        public UpdateListing(boolean showLoader) {
            this.showLoader = showLoader;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            if (pullToRefresh == true) {
                pd.dismiss();
                pullToRefresh = false;
            } else {
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                if (showLoader) {
                    pd.show();
                }
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            Boolean success = false;
            try {

                AppClient client = AppClient.getAppClient(getActivity());
                orderList = client.fetchUserTaskList(selecetdUserId, "Revert");

                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            mAdapter = new PendingOrderAdpater(orderList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            pd.dismiss();
        }
    }

    public class PendingOrderAdpater extends RecyclerView.Adapter<PendingOrderAdpater.MyViewHolder> {

        private List<Order> orderList;

        public PendingOrderAdpater(List<Order> orderList) {
            this.orderList = orderList;
        }

        @Override
        public PendingOrderAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pending_order_detail_listitem, parent, false);

            return new PendingOrderAdpater.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final PendingOrderAdpater.MyViewHolder holder, final int position) {

            final Order order = orderList.get(position);

            holder.tvCustomerName.setText(order.getFirstName() + " " + order.getLastName());
            holder.tvMobileNumVal.setText(order.getOrderNum());
            holder.tvApartmentAddress.setText(order.getStreet() + " " + order.getArea());
            holder.tvCustomerAddress.setText(order.getHouse_no() + " " + order.getBuilding_name() + " " + order.getStreet() +
                    " " + order.getArea() + " " + order.getPin());
            holder.imMilestoneImage.setImageResource(R.mipmap.ic_residential);

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("order_num", order.getOrderNum());
                    getActivity().startActivity(intent);
                }
            });
        }

        public void removeAt(int position) {
            mQATaskList.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position);
            notifyItemRangeChanged(position, mQATaskList.size());
        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }

        public void clear() {
            // TODO Auto-generated method stub
            orderList.clear();
            mAdapter.notifyDataSetChanged();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvCustomerName, tvMobileNumVal, tvCustomerAddress, tvApartmentAddress;
            ImageView imMilestoneImage;
            //Button btnSubmit;
            LinearLayout llItem;

            public MyViewHolder(View rootView) {
                super(rootView);

                llItem = (LinearLayout) rootView.findViewById(R.id.llItem);
                tvCustomerName = (TextView) rootView.findViewById(R.id.tvCustomerName);
                tvApartmentAddress = (TextView) rootView.findViewById(R.id.tvApartmentAddress);
                tvMobileNumVal = (TextView) rootView.findViewById(R.id.tvMobileNumVal);
                tvCustomerAddress = (TextView) rootView.findViewById(R.id.tvCustomerAddress);
                imMilestoneImage = (ImageView) rootView.findViewById(R.id.imMilestoneImage);
                //btnSubmit = rootView.findViewById(R.id.btnSubmit);

            }
        }


    }

/*
    public class ActionLogListing extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        String navgiation, uid, milestoneorderid, milestoneid;

        ActionLogListing(String navgiation, String uid, String milestoneid, String milestoneorderid) {
            this.navgiation = navgiation;
            this.uid = uid;
            this.milestoneid = milestoneid;
            this.milestoneorderid = milestoneorderid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pd == null) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }
        }

        @Override
        protected String doInBackground(String... args0) {
            try {
                String link = Webservice.ORDERDETAILS;
                String data = URLEncoder.encode(DBTableColumnName.USER_ID, "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8");
                data += "&" + URLEncoder.encode(DBTableColumnName.QA_MILESTONE_ORDER_ID, "UTF-8") + "=" + URLEncoder.encode(milestoneorderid, "UTF-8");
                data += "&" + URLEncoder.encode("details_type", "UTF-8") + "=" + URLEncoder.encode(mDetailsTypeStr, "UTF-8");
                data += "&" + URLEncoder.encode(DBTableColumnName.QA_MILESTONE_ID, "UTF-8") + "=" + URLEncoder.encode(milestoneid, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("result", result);

            try {
                JSONObject jobj = new JSONObject(result);
                JSONObject gg = jobj.getJSONObject("result");
                mMasgStr = gg.getString("msg");

                if (mMasgStr.equals("0")) {
                    Toast.makeText(getActivity(), (String) "msg : 1",
                            Toast.LENGTH_LONG).show();

                } else if (mMasgStr.equals("1")) {
                    orderList.clear();

                    mUniqueOidStr = gg.getString("unique_oid");
                    mOidStr = gg.getString("o_id");
                    mCustomerNameStr = gg.getString("customer_name");
                    // mPropertyAddressStr = gg.getString("customer_address");

                    JSONArray jarray = gg.getJSONArray("arrMilestonePlanDtls");

                    try {

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject c = jarray.getJSONObject(i);
                            mOrderIdStr = c.getString("order_id");
                            mQuoteIdStr = c.getString("quote_id");
                            mWorkOrderIdStr = c.getString("unique_workorder_id");
                            mMilestonePlanId = c.getString("milestone_plan_id");
                            mSectionOfHouseStr = c.getString("section_of_house_title");
                            mExecSellernameStr = c.getString("executioner_seller_name");
                            mQAactivityName = c.getString("activity");
                            mFlagInternalWork = c.getString("flag_internal_workpart");
                            mSectionOfHouseID = c.getString("section_of_house_id");

                            milestone = new Milestone(mOrderIdStr, mQuoteIdStr, mMilestonePlanId,
                                    mWorkOrderIdStr, mSectionOfHouseStr, mExecSellernameStr,
                                    mQAactivityName, mFlagInternalWork, mSectionOfHouseID);
                            orderList.add(milestone);
                        }

                        pref.saveQuoteIdpref(milestone.getQuote_id());
                        pref.saveMileStoneId(milestone.getMilestonePlanId());
                        pref.saveUserIdPref(mUserIdStr);
                        pref.saveSectionOfHousepref(milestone.getSection_house());
                        pref.saveSectionOfHouseIDpref(milestone.getmSectionOfHouseID());
                        pref.saveActivitypref(milestone.getActivity());
                        pref.saveFlagInternalWork(milestone.getmFlagInternalWork());
                        String milestonePlanIdStr = PendingOrderByUserFragment.milestone.getMilestonePlanId();
                        pref.saveMileStoneId(milestonePlanIdStr);
                        Intent intent;
                        if (navgiation.equals("Approve")) {
                            intent = new Intent(getActivity(),
                                    Task_Approve_Step_1_Activity.class);
                        } else {
                            intent = new Intent(getActivity(),
                                    Task_Reject_Step_1_Activity.class);
                        }

                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra(DBTableColumnName.QA_SECTION_OF_HOUSE, milestone.getSection_house());
                        intent.putExtra(DBTableColumnName.QA_ACTIVITY_NAME, milestone.getActivity());
                        intent.putExtra("activity_log_status", "1");
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // progressBar.setVisibility(View.GONE);
                    pd.dismiss();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
*/
}
