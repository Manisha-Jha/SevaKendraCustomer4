package com.bses.dinesh.dsk.telematics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.OrderInformation;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
/*import com.jhamobi.brplapp.R;
import com.jhamobi.brplapp.data.OrderInformation;
import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.utils.CommonUtilities;

import java.util.List;*/

public class HistoryTasksInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View myContentsView;
    private LayoutInflater inflater;
    private TextView tvName, tvAddress, tvDate, tvLatLngAddress, tvSubmitDateTime;
    private List<TasksData> mItems;
    private List<OrderInformation> orderInformations;
    private Context mContext;

    public HistoryTasksInfoWindowAdapter(Context context, List<OrderInformation> orderInformation)
    {
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myContentsView = this.inflater.inflate(R.layout.layout_history_infowindow, null);
        this.orderInformations = orderInformation;
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
            //TasksData item = getMarkerData(marker.getTag().toString());
            //OrderInformation order_info = orderInformations.get(0);
            OrderInformation order_info = getMarkerData(marker.getTag().toString());

            tvName = myContentsView.findViewById(R.id.tv_name);
            tvAddress = myContentsView.findViewById(R.id.tv_address);
            tvDate = myContentsView.findViewById(R.id.tv_Date);
            tvLatLngAddress = myContentsView.findViewById(R.id.tv_latlng_address);
            tvSubmitDateTime = myContentsView.findViewById(R.id.tv_submit_datetime);

            tvName.setText(order_info.getUSER_ID()+"\n"+order_info.getORDER_NO());
            tvAddress.setText(order_info.getCUSTOMER_ADDRESS());
            if (order_info.getORDER_SUBMIT_DATE() != null)
                tvDate.setText(order_info.getORDER_SUBMIT_DATE());
            String strAddress = CommonUtilities.getAddressStringFromLatLng(mContext,
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);
            if (strAddress != null)
                tvLatLngAddress.setText(marker.getPosition() + "\n" + strAddress);
            if (order_info.getORDER_SUBMIT_DATE() != null)
                tvSubmitDateTime.setText(order_info.getORDER_SUBMIT_TIME());

            return myContentsView;
        }
        return null;
    }

    public OrderInformation getMarkerData(String order_no)
    {
        for (OrderInformation orderInformation : orderInformations)
        {
            if (orderInformation.getORDER_NO().equalsIgnoreCase(order_no))
                return orderInformation;
        }
        return new OrderInformation();
    }

}
