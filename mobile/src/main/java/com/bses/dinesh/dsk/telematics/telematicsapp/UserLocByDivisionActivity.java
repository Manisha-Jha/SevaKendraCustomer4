package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.User;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.interfaces.ClickEventValue;
import com.bses.dinesh.dsk.telematics.remote.RetrofitInterface;
import com.bses.dinesh.dsk.telematics.remote.RetrofitResponse;
import com.bses.dinesh.dsk.telematics.server.AppClient;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.utils.RouteDistanceText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/*import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.server.AppClient;
import com.jhamobi.brplapp.server.AppConstants;
import com.jhamobi.brplapp.utils.RouteDistanceText;*/

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserLocByDivisionActivity extends BaseActivity implements ClickEventValue {
    //FetchUserListAsyncTask mFetchUserListAsyncTask;
    //FetchUserListByAllDivisionAsyncTask mFetchUserListByAllDivisionAsyncTask;
    private RecyclerView recyclerViewTask;
    private Context mContext;
    private List<Users> UsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mContext = UserLocByDivisionActivity.this;
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

    private void fetchDivisionData(String divName)
    {
        if (divName.contains(AppConstants.COMMA_SEPERATOR))
        {
            /*
            mFetchUserListByAllDivisionAsyncTask = new FetchUserListByAllDivisionAsyncTask(divName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserListByAllDivisionAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserListByAllDivisionAsyncTask.execute((Void) null);
            */
        }
        else
        {
  /*
            mFetchUserListAsyncTask = new FetchUserListAsyncTask(divName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mFetchUserListAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            else
                mFetchUserListAsyncTask.execute((Void) null);
*/
            getUsersList(divName);
        }

    }

    private void selectOptions(final String strUserId) {
        final AppCompatDialog dialog = new AppCompatDialog(this, R.style.dialog_light);
        dialog.setContentView(R.layout.layout_dialog_user_options);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvTaskList = dialog.findViewById(R.id.tvTaskList);
        TextView tvTaskListHistory = dialog.findViewById(R.id.tvTaskListHistory);
        TextView tvTrackHistory = dialog.findViewById(R.id.tvTrackHistory);
        TextView tvCompletedTask = dialog.findViewById(R.id.tvCompletedTask);
        tvTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderListByDivisionActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        tvTaskListHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryMapsActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        tvTrackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        tvCompletedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CompletedTasksActivity.class).putExtra("userid", strUserId).putExtra("admin", "admin"));
                dialog.cancel();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClickValue(String value) {
        if (value != null) {
            selectOptions(value);
        } else {
            Log.e("ATG", "id-null");
        }
    }

    public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
        private List<User> mItems;

        public UserListAdapter(Context context, List<User> userList, ClickEventValue clickEventValue) {
            mItems = userList;
            mContext = context;
        }

        @Override
        public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View postView = inflater.inflate(R.layout.user_detail_listitem, parent, false);

            UserListAdapter.ViewHolder viewHolder = new UserListAdapter.ViewHolder(postView);
            postView.setTag(viewHolder);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final UserListAdapter.ViewHolder holder, final int position) {
            final User item = mItems.get(position);

            holder.tvName.setText(item.getNAME());
            holder.tvEmail.setText(item.getPHONE_NUMBER());
            holder.llUserDetailItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), FieldUserCurrentLocationActivity.class);
                    intent.putExtra("userid", item.getUSER_ID());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName, tvEmail;
            private LinearLayout llUserDetailItem;

            private ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                tvEmail = itemView.findViewById(R.id.tv_phonenumber);
                llUserDetailItem = itemView.findViewById(R.id.llUserDetailItem);

            }
        }
    }


    private void getUsersList(final String divName)
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
        progressDoalog = new ProgressDialog(UserLocByDivisionActivity.this);
        progressDoalog.setMessage("Fetching....");
        progressDoalog.setTitle("Users");
        progressDoalog.show();

        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<RetrofitResponse> call = request.getUserListOnDivisionBased(divName);
        call.enqueue(new Callback<RetrofitResponse>()
        {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response)
            {
                Log.e("user",response.raw()+"");
                if(response.body() != null)
                {
                    RetrofitResponse jsonResponse = response.body();
                    if(jsonResponse.getUser().size() != 0)
                    {
                        progressDoalog.dismiss();
                        List<User> userList = jsonResponse.getUser();

                        if (userList != null && userList.size() > 0)
                        {
                            UserListAdapter userListAdapter = new UserListAdapter(mContext, userList,
                                    UserLocByDivisionActivity.this);
                            Log.e("TAG", UsersList.size() + "");
                            recyclerViewTask.setAdapter(userListAdapter);
                        }
                    }
                    else
                    {
                        progressDoalog.dismiss();
                        Toast.makeText(UserLocByDivisionActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(UserLocByDivisionActivity.this, "Server down", Toast.LENGTH_SHORT).show();
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
