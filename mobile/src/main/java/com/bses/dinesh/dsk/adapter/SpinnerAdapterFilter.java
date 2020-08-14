package com.bses.dinesh.dsk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bses.dinesh.dsk.R;


/**
 * Created by Krishna on 4/29/2016.
 */
public class SpinnerAdapterFilter extends ArrayAdapter<String> {

    private String[] spinnervalues;
    private String[] spinnerSubs;


    public SpinnerAdapterFilter(Context ctx, int txtViewResourceId, String[] objects, String[] objectsSub) {
        super(ctx, txtViewResourceId, objects);

        this.spinnervalues = objects;
        this.spinnerSubs = objectsSub;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // LayoutInflater inflater = getLayoutInflater();
        View mySpinner = inflater.inflate(R.layout.custom_spinner_filter, parent, false);

        TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen);
        main_text.setText(spinnervalues[position]);

        TextView subSpinner = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
        subSpinner.setText(spinnerSubs[position]);

        return mySpinner;
    }

}
