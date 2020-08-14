package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.adapter.UserListAdapter;
import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.server.AppConstants;*/

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.adapter.UserListAdapter;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class AllUserListActivity extends BaseActivity {
    FetchUserListAsyncTask mFetchUserListAsyncTask;
    FetchUserListByAllDivisionAsyncTask mFetchUserListByAllDivisionAsyncTask;
    private RecyclerView recyclerViewTask;
    private Context mContext;
    private List<Users> UsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mContext = AllUserListActivity.this;
        UsersList = new ArrayList<>();
        recyclerViewTask = findViewById(R.id.recyclerViewTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(layoutManager);
        recyclerViewTask.setNestedScrollingEnabled(false);
        recyclerViewTask.setHasFixedSize(true);
        //getUserListFromDatabase();
        String divName = getIntent().getExtras().getString("division");
        fetchDivisionData(divName);
    }

    private void fetchDivisionData(String divName) {
        if (divName.contains(AppConstants.COMMA_SEPERATOR)) {
            mFetchUserListByAllDivisionAsyncTask = new FetchUserListByAllDivisionAsyncTask(divName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserListByAllDivisionAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserListByAllDivisionAsyncTask.execute((Void) null);
        } else {
            mFetchUserListAsyncTask = new FetchUserListAsyncTask(divName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserListAsyncTask.execute((Void) null);
        }

    }

    final class FetchUserListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoadQuestion;
        private String divisionName;

        public FetchUserListAsyncTask(String divisionName) {
            pdLoadQuestion = new ProgressDialog(AllUserListActivity.this);
            this.divisionName = divisionName;
        }

        @Override
        protected void onPreExecute() {
            pdLoadQuestion.setMessage("Waiting ...");
            pdLoadQuestion.show();
            pdLoadQuestion.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                UsersList = client.getAllUserDetailByAllDivision(divisionName);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFetchUserListAsyncTask = null;
            pdLoadQuestion.dismiss();

            ArrayList<String> userNameList = new ArrayList<>();

            for (int ii = 0; ii < UsersList.size(); ii++) {
                userNameList.add(UsersList.get(ii).getName());
            }

            ListView lvDivision = (ListView) findViewById(R.id.division_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2, android.R.id.text1, userNameList);

            lvDivision.setAdapter(adapter);

            lvDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                    String userId = UsersList.get(position).getId();

                    startActivity(new Intent(getApplicationContext(), OrderListByFieldUserActivity.class)
                            .putExtra("userid", userId));
                }
            });
        }

        @Override
        protected void onCancelled() {
            mFetchUserListAsyncTask = null;
            pdLoadQuestion.dismiss();
        }
    }

    final class FetchUserListByAllDivisionAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pdLoadQuestion;
        private String divisionName;

        public FetchUserListByAllDivisionAsyncTask(String divisionName) {
            pdLoadQuestion = new ProgressDialog(AllUserListActivity.this);
            this.divisionName = divisionName;
        }

        @Override
        protected void onPreExecute() {
            pdLoadQuestion.setMessage("Waiting ...");
            pdLoadQuestion.show();
            pdLoadQuestion.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean status = false;
            try {
                AppClient client = AppClient.getAppClient(getApplicationContext());
                UsersList = client.getAllUserDetailByAllDivision(divisionName);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFetchUserListAsyncTask = null;
            pdLoadQuestion.dismiss();
            if (success == true) {
                if (UsersList != null && UsersList.size() > 0) {
                    UserListAdapter userListAdapter = new UserListAdapter(mContext, UsersList, null);
                    recyclerViewTask.setAdapter(userListAdapter);
                }
            }
        }

        @Override
        protected void onCancelled() {
            mFetchUserListAsyncTask = null;
            pdLoadQuestion.dismiss();
        }
    }
}