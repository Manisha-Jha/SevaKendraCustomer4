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
import com.bses.dinesh.dsk.telematics.interfaces.ClickEventPosValue;

import java.util.List;

/*import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.interfaces.ClickEventPosValue;

import java.util.List;*/

public class AssignTaskAdapter extends RecyclerView.Adapter<AssignTaskAdapter.ViewHolder> {
    private List<TasksData> mItems;
    private Context mContext;
    private ClickEventPosValue eventPosValue;


    public AssignTaskAdapter(Context context, List<TasksData> taskList, ClickEventPosValue eventPosValue) {
        mItems = taskList;
        mContext = context;
        this.eventPosValue = eventPosValue;
    }

    @Override
    public AssignTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.list_item_tasks, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        final TasksData item = mItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvAddress.setText("Address :  " + item.getAddress());
        if (item.getRequestNo() != null)
            holder.tvRequestNo.setText("Request No. :  " + item.getRequestNo());
        if (item.getMobile() != null)
            holder.tvMobile.setText("Mobile :  " + item.getMobile());
        if (item.getRequestTypeName() != null)
            holder.tvRequestType.setText("Request Type :  " + item.getRequestTypeName());
        if (item.getEmail() != null)
            holder.tvEmail.setText("Email :  " + item.getEmail());
        if (item.getCreateOn() != null)
            holder.tvDate.setText(item.getCreateOn().toString());
//        holder.product_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // listItemClick.onItemClick(position,"");
//
//            }
//        });
        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventPosValue.onClickPosValue(item.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

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
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }

}


