package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.Users;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
/*import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.Users;

import java.util.List;*/

public class UserLiveInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    private View myContentsView;
    private LayoutInflater inflater;
    private TextView tvName, tv_Div,tv_phone;
    private List<Users> mItems;
    private Context mContext;

    public UserLiveInfoWindowAdapter(Context context, List<Users> mItems)
    {
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myContentsView = this.inflater.inflate(R.layout.layout_live_user_infowindow, null);
        this.mItems = mItems;

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker)
    {
        if (marker.getTag() != null)
        {
            Users item = getMarkerData(marker.getTag().toString());
            tvName = myContentsView.findViewById(R.id.tv_name);
            tv_Div = myContentsView.findViewById(R.id.tv_Div);
            tv_phone = myContentsView.findViewById(R.id.tv_phone);
            tvName.setText(item.getName());
            tv_Div.setText(item.getDivName());
            tv_phone.setText(item.getPhoneNum());
            return myContentsView;
        }
        return null;
    }

    public Users getMarkerData(String id) {
        for (Users users : mItems) {
            if (users.getId().equalsIgnoreCase(id))
                return users;
        }
        return new Users();
    }
}
