package com.bses.dinesh.dsk.adapter;

/**
 * Created by Krishna on 4/27/2016.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.bean.McrOrederBean;
import com.bses.dinesh.dsk.fragMain.ChatHome;
import com.bses.dinesh.dsk.utils.UserPreferences;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> implements Filterable{


    private List<McrOrederBean> moviesList;
    private List<McrOrederBean> moviesListFiltered;
    private Context ctx;
    private UserPreferences userPreferences;
    private McrOrederBean selectedTask;
    private final static String TAG_FRAGMENT = "CHAT_FRAG";


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        public TextView ordernumber, stickernumber, meternumber, name, mobilenumber, address, textstatus, activitytype, pmactivity ;
        private CardView card;
        public RelativeLayout sideBar;
        private pl.droidsonroids.gif.GifTextView syncloading;
        private ClickListener clickListener;


        public MyViewHolder(View view) {
            super(view);
           // syncloading
            card = (CardView) view.findViewById(R.id.card_view);
            syncloading  = (pl.droidsonroids.gif.GifTextView) view.findViewById(R.id.syncloading);
            textstatus = (TextView) view.findViewById(R.id.textstatus);

            sideBar = (RelativeLayout) view.findViewById(R.id.rl2);
            ordernumber = (TextView) view.findViewById(R.id.ordernumber);
            stickernumber = (TextView) view.findViewById(R.id.stickernumber);
            meternumber = (TextView) view.findViewById(R.id.meternumber);
            name = (TextView) view.findViewById(R.id.name);
            mobilenumber = (TextView) view.findViewById(R.id.mobilenumber);
            activitytype = (TextView) view.findViewById(R.id.activitytype);
            pmactivity = (TextView) view.findViewById(R.id.pmactivity);
            address = (TextView) view.findViewById(R.id.address);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setClickListener(ClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition());
            return true;
        }


    }


    public ListViewAdapter(List<McrOrederBean> moviesList) {
        this.moviesList = moviesList;
    }
    public ListViewAdapter(List<McrOrederBean> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.moviesListFiltered = moviesList;
        this.ctx = ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        userPreferences = UserPreferences.getInstance(parent.getContext());

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            McrOrederBean message = moviesListFiltered.get(position);




        holder.ordernumber.setText(message.getORDERNO());
        //holder.stickernumber.setText(message.getSTICKERNO());
        holder.meternumber.setText(message.getREQ_TYPE());
        holder.name.setText(message.getNAME());
        holder.mobilenumber.setText(message.getMOBILENO());
        holder.activitytype.setText(message.getILART());
        holder.pmactivity.setText(message.getE_MAIL());
        holder.address.setText(message.getADDRESS());


        holder.setClickListener(new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                try {

                    selectedTask = moviesListFiltered.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putString("getORDERNO", selectedTask.getORDERNO());
                    bundle.putString("getSTICKERNO", selectedTask.getGSTRP()); // date 1
                    bundle.putString("getSTARTDATE", selectedTask.getAPPDT());  //appointment
                    bundle.putString("getFINISHDATE", selectedTask.getREAPPDT());  // re appointmemt


                    if (selectedTask.getNAME() != null) {
                        if (selectedTask.getNAME().length() > 0) {
                            bundle.putString("getNAME", selectedTask.getNAME());
                        }
                        else{
                            bundle.putString("getNAME", selectedTask.getNAME_FIRST() + " " + selectedTask.getNAME_LAST());
                        }
                    } else {
                        bundle.putString("getNAME", selectedTask.getNAME_FIRST() + " " + selectedTask.getNAME_LAST());
                    }



                    bundle.putString("getFATHERNAME", selectedTask.getFATHERNAME());
                    bundle.putString("getADDRESS", selectedTask.getADDRESS());

                    bundle.putString("getMOBILENO", selectedTask.getMOBILENO());
                    bundle.putString("getBP_NO", selectedTask.getEMAIL()); // emial
                    bundle.putString("getILART", selectedTask.getILART()); // request type
                    bundle.putString("getBUKRS", selectedTask.getBUKRS());
                    bundle.putString("getZZ_RLOAD", selectedTask.getZZ_RLOAD()); // required load kw
                    bundle.putString("getZZ_VKONT", selectedTask.getZZ_VKONT()); // CA number


                    bundle.putString("getTITLE", selectedTask.getTITLE());
                    bundle.putString("getNAME_FIRST", selectedTask.getNAME_FIRST());
                    bundle.putString("getNAME_LAST", selectedTask.getNAME_LAST());
                    bundle.putString("getSTREET", selectedTask.getSTREET());
                    bundle.putString("getHOUSE_NUM1", selectedTask.getHOUSE_NUM1());
                    bundle.putString("getSTR_SUPPL", selectedTask.getSTR_SUPPL());
                    bundle.putString("getSTR_SUPPL3", selectedTask.getSTR_SUPPL3());
                    bundle.putString("getPOST_CODE1", selectedTask.getPOST_CODE1());
                    bundle.putString("getPINCODE", selectedTask.getPINCODE());
                    bundle.putString("Existing_LOAD", selectedTask.getStrExistingLoad());

                    Fragment fragment = new ChatHome();
                    fragment.setArguments(bundle);

                    FragmentManager fm = ((Activity) ctx).getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                    fragmentTransaction.commit();

                }
                catch (Exception er){
                    er.printStackTrace();
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }


        });

    }

    @Override
    public int getItemCount() {
        return moviesListFiltered.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                try {
                    if (charString.isEmpty()) {
                        moviesListFiltered = moviesList;
                    } else {
                        List<McrOrederBean> filteredList = new ArrayList<>();
                        for (McrOrederBean row : moviesList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if(row.getORDERNO() != null && row.getMOBILENO() != null) {
                                if (row.getORDERNO().toLowerCase().contains(charString.toLowerCase()) || row.getMOBILENO().contains(charSequence)) {
                                    filteredList.add(row);
                                }
                            }
                            else{

                            }
                        }

                        moviesListFiltered = filteredList;
                    }

                }
                catch (Exception er){
                    er.printStackTrace();
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = moviesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ArrayList<McrOrederBean> al;
                if(filterResults.values != null) {
                     al = (ArrayList<McrOrederBean>) filterResults.values;
                }
                else{
                     al = new ArrayList<McrOrederBean>();
                }
                moviesListFiltered = al;

                notifyDataSetChanged();
            }
        };
    }

}