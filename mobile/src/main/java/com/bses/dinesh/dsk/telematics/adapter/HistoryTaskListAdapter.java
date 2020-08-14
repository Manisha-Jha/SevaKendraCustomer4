package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.TasksData;

import java.util.List;

/*
import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.TasksData;

import java.util.List;
*/

public class HistoryTaskListAdapter extends RecyclerView.Adapter<HistoryTaskListAdapter.ViewHolder> {
    private List<TasksData> mItems;
    private Context mContext;
    //private ListItemClickInterface listItemClick;


    public HistoryTaskListAdapter(Context context, List<TasksData> taskList) {
        mItems = taskList;
        mContext = context;
        //this.listItemClick = listItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.list_item_history_tasklist, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        final TasksData item = mItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvAddress.setText(item.getAddress());
        if (item.getCreateOn() != null)
            holder.tvDate.setText(item.getCreateOn());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

//    private void submitTasks(final TasksData data) {
//        final DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Tasks");
//        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
//                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
//                    // Log.e("===tasksData====", "=====tasksData======" + tasksData.getAddress());
//                    if ( data.getId().equalsIgnoreCase(tasksData.getId())){
//                        //updateUserLatLng(postSnapshot12.getKey());
//                        mFirebaseDatabase.child(postSnapshot12.getKey()).child("lat").setValue(SharedPreferenceManager.with(mContext).getLatitude());
//                        mFirebaseDatabase.child(postSnapshot12.getKey()).child("lng").setValue(SharedPreferenceManager.with(mContext).getLongitude());
//                        mFirebaseDatabase.child(postSnapshot12.getKey()).child("status").setValue("Yes");
//                        break;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress, tvDate;
        private View product_card;
        private Button btnSubmit;

        private ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            //product_card = itemView.findViewById(R.id.product_card);
            //btnSubmit = itemView.findViewById(R.id.btnSubmit);
            tvDate = itemView.findViewById(R.id.tv_Date);
        }
    }

}

