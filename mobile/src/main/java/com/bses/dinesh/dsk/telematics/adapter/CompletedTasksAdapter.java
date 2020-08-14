package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/*
import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.utils.CommonUtilities;*/

import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;

import java.util.List;

public class CompletedTasksAdapter extends RecyclerView.Adapter<CompletedTasksAdapter.ViewHolder> {
    private List<TasksData> mItems;
    private Context mContext;

    public CompletedTasksAdapter(Context context, List<TasksData> taskList) {
        mItems = taskList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.list_item_completed_tasklist, parent, false);

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
        if (item.getLat() != null && item.getLng() != null) {
            String strAddress = CommonUtilities.getAddressStringFromLatLng(mContext, Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
            if (strAddress != null)
                holder.tvLatLngAddress.setText(strAddress);
        }
        if (item.getDate() != null)
            holder.tvSubmitDateTime.setText(item.getDate() + "\n" + item.getTime());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress, tvDate, tvLatLngAddress, tvSubmitDateTime;
        private View product_card;
        private Button btnSubmit;

        private ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            //product_card = itemView.findViewById(R.id.product_card);
            //btnSubmit = itemView.findViewById(R.id.btnSubmit);
            tvDate = itemView.findViewById(R.id.tv_Date);
            tvLatLngAddress = itemView.findViewById(R.id.tv_latlng_address);
            tvSubmitDateTime = itemView.findViewById(R.id.tv_submit_datetime);
        }
    }

}

