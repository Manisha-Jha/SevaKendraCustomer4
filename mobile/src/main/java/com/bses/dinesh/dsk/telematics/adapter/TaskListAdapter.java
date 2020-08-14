package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Customer;

import java.util.List;

/*import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.Customer;

import java.util.List;*/

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<Customer> mItems;
    private Context mContext;
    //private ListItemClickInterface listItemClick;


    public TaskListAdapter(Context context, List<Customer> custDataList) {
        mItems = custDataList;
        mContext = context;
        //this.listItemClick = listItemClick;
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.list_item_tasklist, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        final Customer item = mItems.get(position);

        holder.tvName.setText(item.getFirstName() + " " +item.getLastName());
        //holder.tvAddress.setText(item.getAddress());
//        holder.tvAddress.setText("Address :  "+item.getAddress());
//        if (item.getRequestNo()!=null)
//            holder.tvRequestNo.setText("Request No. :  "+item.getRequestNo());
//        if (item.getMobile()!=null)
//            holder.tvMobile.setText("Mobile :  "+item.getMobile());
//        if (item.getRequestTypeName()!=null)
//            holder.tvRequestType.setText("Request Type :  "+item.getRequestTypeName());
//        if (item.getEmail()!=null)
//            holder.tvEmail.setText("Email :  "+item.getEmail());
//        if (item.getCreateOn() != null)
//            holder.tvDate.setText(item.getCreateOn());
//        holder.product_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // listItemClick.onItemClick(position,"");
//
//            }
//        });
//        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submitTasks(item);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

//    private void submitTasks(final Customer data) {
//        Calendar cal = Calendar.getInstance(Locale.US);
//        cal.setTimeInMillis(System.currentTimeMillis());
//        // String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
//        final String date = DateFormat.format("dd-MM-yyyy", cal).toString();
//        final String time = DateFormat.format("HH:mm:ss", cal).toString();
//
//        // mFirebaseInstance = FirebaseDatabase.getInstance();
//        // get reference to 'users' node
//        final DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(TABLE_TASKS_DATA);
//        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
//                    //getting artist
//                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
//                    // Log.e("===tasksData====", "=====tasksData======" + tasksData.getAddress());
//                    if (data.getId().equalsIgnoreCase(tasksData.getId())) {
//                        //updateUserLatLng(postSnapshot12.getKey());
//                        //mFirebaseDatabase.child(postSnapshot12.getKey()).child("lat").setValue(SharedPreferenceManager.with(mContext).getLatitude());
//                        // mFirebaseDatabase.child(postSnapshot12.getKey()).child("lng").setValue(SharedPreferenceManager.with(mContext).getLongitude());
//                        // mFirebaseDatabase.child(postSnapshot12.getKey()).child("status").setValue("Yes");
//                        // mFirebaseDatabase.child(postSnapshot12.getKey()).child("timestamp").setValue(ServerValue.TIMESTAMP);
//                        Map map = new HashMap();
//                        map.put("lat", SharedPreferenceManager.with(mContext).getLatitude());
//                        map.put("lng", SharedPreferenceManager.with(mContext).getLongitude());
//                        map.put("status", "Yes");
//                        map.put("timestamp", ServerValue.TIMESTAMP);
//                        map.put("date", date);
//                        map.put("time", time);
//                        mFirebaseDatabase.child(postSnapshot12.getKey()).updateChildren(map);
//                        ;
//
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress, tvDate, tvRequestNo, tvMobile, tvEmail, tvRequestType;
        private View product_card;
        private Button btnSubmit;

        private ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvRequestNo = itemView.findViewById(R.id.tv_request_no);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvRequestType = itemView.findViewById(R.id.tv_request_type);
            //product_card = itemView.findViewById(R.id.product_card);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
            tvDate = itemView.findViewById(R.id.tv_Date);
        }
    }

}

