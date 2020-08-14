package com.bses.dinesh.dsk.telematics.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Order;

import java.util.List;

/*
import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.Order;

import java.util.List;
*/

public class TodayTaskAdpater extends RecyclerView.Adapter<TodayTaskAdpater.MyViewHolder> {
    private List<Order> order_List;

    public TodayTaskAdpater(List<Order> order_List) {
        this.order_List = order_List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_order_detail_listitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = order_List.get(position);
        holder.tvCustomerName.setText(order.getOrderNum());
        holder.tvSectionofHouse.setText(order.getOrderNum());
        holder.imMilestoneImage.setImageResource(R.mipmap.ic_residential);

           /* holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    *//*Intent intent = new Intent(getActivity(),
                            Task_Approve_Step_1_Activity.class);
                    intent.putExtra("activity_log_status", "1");
                    startActivity(intent);*//*
                }
            });*/
    }

    @Override
    public int getItemCount() {
        return order_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvSectionofHouse;
        ImageView imMilestoneImage;
        Button btnSubmit;
        LinearLayout llItem;

        public MyViewHolder(View rootView) {
            super(rootView);
            llItem = (LinearLayout) rootView.findViewById(R.id.llItem);
            tvCustomerName = (TextView) rootView.findViewById(R.id.tvCustomerName);
            tvSectionofHouse = (TextView) rootView.findViewById(R.id.tvMobileNumVal);
            imMilestoneImage = (ImageView) rootView.findViewById(R.id.imMilestoneImage);
            btnSubmit = rootView.findViewById(R.id.btnSubmit);
        }
    }
}
