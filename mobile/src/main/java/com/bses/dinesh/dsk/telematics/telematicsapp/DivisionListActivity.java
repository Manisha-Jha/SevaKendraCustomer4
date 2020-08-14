package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/*import com.jhamobi.brplapp.data.User;
import com.jhamobi.brplapp.remote.RetrofitInterface;
import com.jhamobi.brplapp.remote.RetrofitResponse;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.server.AppConstants;
import com.jhamobi.brplapp.utils.SharedPreferenceManager;*/

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.User;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DivisionListActivity extends BaseActivity {
    String detailRequired;
    private FetchDivisionListAsyncTask mFetchDivisionListAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division_list);
        detailRequired = getIntent().getExtras().getString("RequiredDetails");
        fetchDivisionData();
    }

    private void fetchDivisionData()
    {
/*
        mFetchDivisionListAsyncTask = new FetchDivisionListAsyncTask(detailRequired);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mFetchDivisionListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        else
            mFetchDivisionListAsyncTask.execute((Void) null);
*/
        getDivisionsList(SharedPreferenceManager.with(this).getLoggedInUser().getId(),detailRequired);

    }

    public class FetchDivisionListAsyncTask extends AsyncTask<Void, Void, Boolean> {
        String divisionList;
        String userId;
        String menuClicked;
        private ProgressDialog pdLoadQuestion;

        public FetchDivisionListAsyncTask(String menuClicked) {
            pdLoadQuestion = new ProgressDialog(DivisionListActivity.this);
            this.menuClicked = menuClicked;
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
                AppClient client = AppClient.getAppClient(DivisionListActivity.this);
                userId = SharedPreferenceManager.with(getApplicationContext()).getLoggedInUser().getId();
                divisionList = client.getDivisionList(userId);
                status = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pdLoadQuestion.dismiss();
            mFetchDivisionListAsyncTask = null;

            //String divList = getIntent().getStringExtra("division").toString();
            final String[] divisionArray = divisionList.split(",");
            //final String[] divisionArrayNew = new String[divisionArray.length + 1];
            //divisionArrayNew[0] = "All";
            //System.arraycopy(divisionArray, 0, divisionArrayNew, 1, divisionArray.length);

            ListView lvDivision = (ListView) findViewById(R.id.division_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, divisionArray);

            lvDivision.setAdapter(adapter);

            lvDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                          String divisionName = divisionArray[position];

                          if (menuClicked.equals(AppConstants.ORDER_MENU)) {

                                  startActivity(new Intent(getApplicationContext(), UserListForOrderActivity.class)
                                          .putExtra("userid", userId)
                                          .putExtra("division", divisionName));

                          } else if (menuClicked.equals(AppConstants.USER_LIST_MENU)) {

                                  startActivity(new Intent(getApplicationContext(), UserListByDivisionActivity.class)
                                          .putExtra("userid", userId)
                                          .putExtra("division", divisionName));

                          } else if (menuClicked.equals(AppConstants.USER_LOC_MENU)) {

                                  startActivity(new Intent(getApplicationContext(), UserLocByDivisionActivity.class)
                                          .putExtra("userid", userId)
                                          .putExtra("admin", "admin")
                                          .putExtra("division", divisionName));

                          } else if (menuClicked.equals(AppConstants.TRACKING_HISTORY_MENU)) {

                                  startActivity(new Intent(getApplicationContext(), UserListByDivisionActivity.class)
                                          .putExtra("userid", userId)
                                          .putExtra("admin", "admin")
                                          .putExtra("division", divisionName));

                          }

                      }
                  }
            );


        }

        @Override
        protected void onCancelled() {
            pdLoadQuestion.dismiss();
            mFetchDivisionListAsyncTask = null;
        }
    }


    private void getDivisionsList(final String userId, final String menuClicked)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://115.249.67.72:9880/DSK_telematic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(DivisionListActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("Division");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getDivisionList(userId);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("DIVISION",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getDivisionList().size() != 0)
                    {
                        progressDoalog.dismiss();
                        List<User> divList = jsonResponse.getDivisionList();
                        final String divisionList = divList.get(0).getDIVISION();
                        final String[] divisionArray = divisionList.split(",");
                        ListView lvDivision = (ListView) findViewById(R.id.division_listView);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, divisionArray);
                        lvDivision.setAdapter(adapter);
                        lvDivision.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                          {
                                                              @Override
                                                              public void onItemClick(AdapterView<?> adapter, View view, int position, long arg)
                                                              {
                                                                  String divisionName = divisionArray[position];
                                                                  if (menuClicked.equals(AppConstants.ORDER_MENU))
                                                                  {
                                                                      startActivity(new Intent(getApplicationContext(), UserListForOrderActivity.class)
                                                                              .putExtra("userid", userId)
                                                                              .putExtra("division", divisionName));
                                                                  }
                                                                  else if (menuClicked.equals(AppConstants.USER_LIST_MENU))
                                                                  {
                                                                      startActivity(new Intent(getApplicationContext(), UserListByDivisionActivity.class)
                                                                              .putExtra("userid", userId)
                                                                              .putExtra("division", divisionName));
                                                                  }
                                                                  else if (menuClicked.equals(AppConstants.USER_LOC_MENU))
                                                                  {
                                                                      startActivity(new Intent(getApplicationContext(), UserLocByDivisionActivity.class)
                                                                              .putExtra("userid", userId)
                                                                              .putExtra("admin", "admin")
                                                                              .putExtra("division", divisionName));
                                                                  } else if (menuClicked.equals(AppConstants.TRACKING_HISTORY_MENU)) {
                                                                      startActivity(new Intent(getApplicationContext(), UserListByDivisionActivity.class)
                                                                              .putExtra("userid", userId)
                                                                              .putExtra("admin", "admin")
                                                                              .putExtra("division", divisionName));
                                                                  }
                                                              }
                                                          }
                        );
                    }
                    else
                    {
                        progressDoalog.dismiss();
                        Toast.makeText(DivisionListActivity.this, "No division found", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(DivisionListActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.e("ERROR",t.getMessage()+"");
            }
        });
    }
}
