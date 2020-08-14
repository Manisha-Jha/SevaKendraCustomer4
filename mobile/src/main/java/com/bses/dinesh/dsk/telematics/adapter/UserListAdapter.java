package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.bses.dinesh.dsk.telematics.interfaces.ClickEventValue;

import java.util.List;

/*import androidx.recyclerview.widget.RecyclerView;

import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.Users;
import com.jhamobi.brplapp.interfaces.ClickEventValue;

import java.util.List;*/

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<Users> mItems;
    private Context mContext;
    private ClickEventValue clickEventValue;
    //private ListItemClickInterface listItemClick;

    public UserListAdapter(Context context, List<Users> userList, ClickEventValue clickEventValue) {
        mItems = userList;
        mContext = context;
        this.clickEventValue = clickEventValue;

        //this.listItemClick = listItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //View postView = inflater.inflate(R.layout.user_detail_listitem, parent, false);
        View postView = inflater.inflate(R.layout.user_detail_listitem, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Users item = mItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvEmail.setText(item.getPhoneNum());
        holder.llUserDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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