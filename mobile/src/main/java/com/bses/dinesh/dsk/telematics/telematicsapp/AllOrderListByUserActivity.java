package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.TextView;


/*import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;*/


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bses.dinesh.dsk.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AllOrderListByUserActivity extends AppCompatActivity {
    TabsPagerAdapter adapter;
    String selectedUserId;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_tabview);
        selectedUserId = getIntent().getExtras().getString("userid");
        adapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        createViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
        int tabIndex = getIntent().getIntExtra("index", 0);
        TabLayout.Tab tab = tabLayout.getTabAt(tabIndex);
        if (tab != null)
            tab.select();
        tabLayout.setupWithViewPager(viewPager);
        setTabText();
        return;
    }

    private void setTabText() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        TextView tabOne = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.order_custom_tab, null);
        TextView tabTwo = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.order_custom_tab, null);

        tabOne.setText("Pending Orders");
        tabTwo.setText("History Orders");

        tabOne.setTextSize(10);
        tabTwo.setTextSize(10);


      /*  switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                tabOne.setTextSize(10);
                tabTwo.setTextSize(10);
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                tabOne.setTextSize(10);
                tabTwo.setTextSize(10);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                tabOne.setTextSize(11);
                tabTwo.setTextSize(11);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                tabOne.setTextSize(10);
                tabTwo.setTextSize(10);
                break;
            case DisplayMetrics.DENSITY_560:
                tabOne.setTextSize(16);
                tabTwo.setTextSize(16);
                break;
        }*/

        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }

    private void createViewPager(ViewPager viewPager) {

        PendingOrderByUserFragment f1 = new PendingOrderByUserFragment();
        HistoryOrderByUserFragment f2 = new HistoryOrderByUserFragment();
        Bundle b = new Bundle();
        b.putString("user_id", selectedUserId);
        f1.setArguments(b);
        f2.setArguments(b);


        //adapter.addFrag(new PendingOrderByUserFragment(selectedUserId), "Pending Orders" );
        //adapter.addFrag(new HistoryOrderByUserFragment(selectedUserId), "History Orders");
        adapter.addFrag(f1, "Pending Orders");
        adapter.addFrag(f2, "History Orders");

        viewPager.setAdapter(adapter);
    }

    class TabsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

