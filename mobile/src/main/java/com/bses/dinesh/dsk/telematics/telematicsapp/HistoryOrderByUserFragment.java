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
import android.widget.FrameLayout;
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

import com.google.android.material.tabs.TabLayout;


import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Order;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.utils.CustomScrollView;

import java.util.ArrayList;
import java.util.List;


public class HistoryOrderByUserFragment extends Fragment {

    String mMsgStr, mUserIdStr, mOidStr, mEmailIdStr, mWorkOidStr, mUniqueOidStr, mApartmentStr = "", mNameStr = "", mPlannedDateStr = "", mActualCompletionDateStr = "", mLobTitleStr = "",
            mOrderTypeStr, mTotalRecordsStr, mOffsetStr, mLimitStr, mMilestonePlanIdStr = "", mQaActivityName = "", mQASectionOfHouseStr = "", mExecSellerName = "";

    ArrayList<Order> mOrderList = new ArrayList<Order>();
    ProgressBar pbTodayTask;
    View rootView;
    //AppPreferences pref;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView empty_text;
    RecyclerView recyclerView;
    OrderHistoryAdpater mAdapter;
    CustomScrollView scrollview;
    List<Integer> plannedDateChangesPosList;
    String sortBy;
    String searchText = "";
    List<Integer> customerNameChangesPosList;
    View tab;
    TabLayout tabLayout;
    String selectedMenuItem = "QADate";
    TextView tabTwo;
    boolean pullToRefresh = false;

    String selecetdUserId;
    CustomScrollView.ScrollViewListener leadDetailsScrollViewListener = new CustomScrollView.ScrollViewListener() {

        @Override
        public void onScrollChanged(CustomScrollView scrollView, int currLen, int currTop, int prevLen, int prevTop) {
            plannedDateChangesPosList.clear();
            customerNameChangesPosList.clear();

            View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
            // if diff is zero, then the bottom has been reached
            if (diff == 0) {
                if (mOrderTypeStr != null)
                    new UpdateListing().execute(mOffsetStr);
                else
                    scrollview.setEnabled(false);

            }

        }
    };
    SearchView.OnCloseListener searchCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            mOrderList.clear();
            searchText = "";
            new UpdateListing().execute("0");
            return false;
        }
    };
    SearchView.OnQueryTextListener taskSearchLstener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mOrderList.clear();
            query = query.toLowerCase();
            searchText = query;
            new UpdateListing().execute("0");

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        selecetdUserId = getArguments().getString("user_id");

        rootView = inflater.inflate(R.layout.order_history_list, container, false);
        setHasOptionsMenu(true);

        pbTodayTask = (ProgressBar) rootView.findViewById(R.id.today_task_progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        empty_text = (TextView) rootView.findViewById(R.id.empty_text);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        scrollview = (CustomScrollView) rootView.findViewById(R.id.lead_details_scrollview);
        scrollview.setScrollViewListener(leadDetailsScrollViewListener);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tab = tabLayout.getTabAt(1).getCustomView(); // fo
        tabTwo = (TextView) tab.findViewById(R.id.tab);

        plannedDateChangesPosList = new ArrayList<>();
        customerNameChangesPosList = new ArrayList<>();

        //  pref = new AppPreferences(getActivity());
        //  mUserIdStr = pref.getUserIdPref();
        //  mEmailIdStr = pref.getUserEmail();
        //  mOrderTypeStr = "orderExecutionQAChecklist"; //for all orders
        mOrderTypeStr = "";
        mOffsetStr = "0";
        mLimitStr = "0";
        sortBy = "DATE";

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                swipeRefreshLayout.setRefreshing(false);
                mOrderList.clear();
                new UpdateListing().execute("0");
                pullToRefresh = true;
            }
        });
        new UpdateListing().execute("0");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       /* inflater.inflate(R.menu.search_sort_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_menuItem);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(taskSearchLstener);
        searchView.setOnCloseListener(searchCloseListener);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mOrderList.clear();
                        searchText = "";
                        new UpdateListing().execute("0");
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });*/
    }

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        SpannableString s;
        menu.findItem(R.id.Title).setEnabled(false);
        if (selectedMenuItem.equals("CustomerName")) {
            MenuItem miCustName = menu.findItem(R.id.SortByCustomerName);
            String miCustNameStr = miCustName.getTitle().toString();
            s = new SpannableString(miCustNameStr);
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), 0);
            miCustName.setTitle(s);
        } else {
            MenuItem miQADate = menu.findItem(R.id.SortByQADate);
            String miQADateStr = miQADate.getTitle().toString();
            s = new SpannableString(miQADateStr);
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, s.length(), 0);
            miQADate.setTitle(s);
        }

        return;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
        } else if (item.getItemId() == R.id.SortByCustomerName) {
            if (ischecked == true) {
                selectedMenuItem = "CustomerName";
                mOffsetStr = "0";
                new UpdateListing().execute(mOffsetStr);
                sortBy = "NAME";
                scrollview.scrollTo(0, View.FOCUS_UP);
                ischecked = false;
            }

        } else if (item.getItemId() == R.id.SortByQADate) {
            if (ischecked == false) {
                selectedMenuItem = "QADate";
                mOffsetStr = "0";
                new UpdateListing().execute(mOffsetStr);
                sortBy = "DATE";
                scrollview.scrollTo(0, View.FOCUS_UP);
                ischecked = true;
            }

        }

        ActivityCompat.invalidateOptionsMenu(getActivity());*/
        return super.onOptionsItemSelected(item);
    }

    private class UpdateListing extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog pd;
        List<Order> orderList;

        UpdateListing() {
            orderList = new ArrayList<>();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            if (pullToRefresh == true) {
                pd.dismiss();
                pullToRefresh = false;
            } else {
                pd.setMessage("Loading...");
                pd.show();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean success = false;
            try {

                AppClient client = AppClient.getAppClient(getActivity());
                orderList = client.fetchUserTaskList(selecetdUserId, "Allotement");

                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mAdapter = new OrderHistoryAdpater(orderList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            pd.dismiss();
        }
    }

    public class OrderHistoryAdpater extends RecyclerView.Adapter<OrderHistoryAdpater.MyViewHolder> {

        private List<Order> orderList;

        public OrderHistoryAdpater(List<Order> orderList) {
            this.orderList = orderList;
        }

        @Override
        public OrderHistoryAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_order_detail_listitem, parent, false);

            return new OrderHistoryAdpater.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final OrderHistoryAdpater.MyViewHolder holder, final int position) {

            final Order order = orderList.get(position);

            holder.tvCustomerName.setText(order.getFirstName() + " " + order.getLastName());
            holder.imMilestoneImage.setImageResource(R.mipmap.ic_residential);

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

            /*if (order.getPropertyAddress().equalsIgnoreCase("null") ||
                    order.getPropertyAddress().trim().isEmpty())
                holder.tvApartmentAddress.setText("No address available");
            else
                holder.tvApartmentAddress.setText(order.getPropertyAddress());
            holder.tvCustomerName.setText(order.getCustomerName());
            holder.tvSectionofHouse.setText(order.getQASectionOfHouse());
            holder.tvPlannedDate.setText(Utility_functions.GetFormatedDate(order.getQAPlannedDate()));
            if (order.getActualCompletionDate().length() > 0) {
                holder.tvActualCompletionDate.setVisibility(View.VISIBLE);
                holder.tvActualCompletionDate.setText(Utility_functions.GetFormatedDate(order.getActualCompletionDate()));

            } else {
                holder.tvActualCompletionDate.setVisibility(View.GONE);
            }*/
           /* Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 0);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String todayDateStr = format.format(c.getTime());
           // String plannedDate = order.getQAPlannedDate();

            Date strDate = null, srtTodydate = null;
            try {
               // strDate = format.parse(plannedDate);
                srtTodydate = format.parse(todayDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (srtTodydate.getTime() > strDate.getTime()) {
              //  holder.tvPlannedDate.setTextColor(getResources().getColor(red));
              //  holder.tvPlannedDateVal.setTextColor(getResources().getColor(red));
            }

            if (sortBy == "DATE") {
                holder.flCustomerInitialLetter.setVisibility(View.GONE);

                if (plannedDateChangesPosList.contains(position)) {
                    holder.flPlannedDateTitle.setVisibility(View.VISIBLE);
                    holder.query_view.setVisibility(View.GONE);
                  //  holder.tvPlannedDateTitle.setText(Utility_functions.GetFormatedDate(order.getQAPlannedDate()));
                } else {
                    holder.flPlannedDateTitle.setVisibility(View.GONE);
                }
            }

            if (sortBy == "NAME") {
                holder.flPlannedDateTitle.setVisibility(View.GONE);

                if (customerNameChangesPosList.contains(position)) {
                   *//* String custNameInitial = order.getCustomerName().substring(0, 1).toUpperCase();
                    holder.flCustomerInitialLetter.setVisibility(View.VISIBLE);
                    holder.query_view.setVisibility(View.GONE);
                    holder.tvCustomerInitialLetter.setText(custNameInitial);*//*
                } else {
                    holder.flCustomerInitialLetter.setVisibility(View.GONE);
                }
            }
*/

         /*   holder.tvActivityName.setText(order.getQAActivityName());
            String lobTitle = order.getQALobTitle();

            if (lobTitle == null)
                holder.imMilestoneImage.setVisibility(View.INVISIBLE);
            else if (lobTitle.equalsIgnoreCase("Institutional"))
                holder.imMilestoneImage.setImageResource(R.drawable.instituional);
            else if (lobTitle.equalsIgnoreCase("Project"))
                holder.imMilestoneImage.setImageResource(R.mipmap.ic_residential);
            else holder.imMilestoneImage.setVisibility(View.INVISIBLE);*/

          /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   *//* pref.saveOrderIdPref(order.getOrderId());
                    pref.saveCurrentTaskType("AllPreviousTask");
                    Intent intent = new Intent(getActivity(), Previous_Task_detail_activity.class);
                    intent.putExtra(AppConsts.QA_ORDER_ID, order.getOrderId());
                    intent.putExtra(AppConsts.QA_WORK_ORDER_ID, order.getWorkOrderId());
                    intent.putExtra(AppConsts.QA_MILESTONE_ID, order.getQAMilestoneId());
                    intent.putExtra(AppConsts.QA_CUSTOMER_NAME, order.getCustomerName());
                    intent.putExtra(AppConsts.QA_PROPERTY_ADDRESS, order.getPropertyAddress());*//*

                    //startActivity(intent);
                }
            });*/
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
            FrameLayout flPlannedDateTitle;
            FrameLayout flCustomerInitialLetter;
            TextView tvPlannedDateTitle, tvCustomerInitialLetter, tvApartmentAddress, tvCustomerName, tvTotalTime,
                    tvSectionofHouse, tvPlannedDateVal, tvMobileNumVal, tvCustomerAddress,
                    tvPlannedDate, tvActivityName, tvActualCompletionDate;
            ImageView imMilestoneImage;
            View query_view;
            LinearLayout llItem;

            public MyViewHolder(View rootView) {
                super(rootView);

                flPlannedDateTitle = (FrameLayout) rootView.findViewById(R.id.planned_date_title_frameLayout);
                tvPlannedDateTitle = (TextView) rootView.findViewById(R.id.planned_date_title_textView);

                flCustomerInitialLetter = (FrameLayout) rootView.findViewById(R.id.customer_name_initial_letter_frameLayout);
                tvCustomerInitialLetter = (TextView) rootView.findViewById(R.id.customer_name_initial_letter_title_textView);

                tvCustomerName = (TextView) rootView.findViewById(R.id.tvCustomerName);
                tvMobileNumVal = (TextView) rootView.findViewById(R.id.tvMobileNumVal);
                tvCustomerAddress = (TextView) rootView.findViewById(R.id.tvCustomerAddress);
                tvApartmentAddress = (TextView) rootView.findViewById(R.id.tvApartmentAddress);
                tvSectionofHouse = (TextView) rootView.findViewById(R.id.tvMobileNum);
                imMilestoneImage = (ImageView) rootView.findViewById(R.id.imMilestoneImage);
                query_view = (View) rootView.findViewById(R.id.query_view);
                tvPlannedDateVal = (TextView) rootView.findViewById(R.id.tvCustomerAddress);
                tvTotalTime = (TextView) rootView.findViewById(R.id.tvTotalTime);
                llItem = (LinearLayout) rootView.findViewById(R.id.llOrderListItem);
            }
        }

    }
}
