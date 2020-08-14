package com.bses.dinesh.dsk.fragMain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bses.dinesh.dsk.R;


/**
 * Created by Krishna on 4/29/2016.
 */
public class AboutUs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);


        return rootView;
    }


}
