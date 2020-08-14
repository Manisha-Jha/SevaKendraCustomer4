package com.bses.dinesh.dsk.sevakendra;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.fragMain.AboutUs;
import com.bses.dinesh.dsk.fragMain.ChangePassword;
import com.bses.dinesh.dsk.fragMain.Home;
import com.bses.dinesh.dsk.telematics.server.AppConstants;
import com.bses.dinesh.dsk.telematics.telematicsapp.FieldUserDashboardActivity;
import com.bses.dinesh.dsk.telematics.telematicsapp.ManagerDashboardActivity;
import com.bses.dinesh.dsk.telematics.telematicsapp.SplashScreenActivity;
import com.bses.dinesh.dsk.telematics.utils.SharedPreferenceManager;
import com.bses.dinesh.dsk.utils.ApplicationUtil;
import com.bses.dinesh.dsk.utils.UserPreferences;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    private View headerView;
    Fragment fragment = null;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    String currentAppVersion;
    //private ContactsAdapter mAdapter;

  /*  @OnClick(R.id.fab)
    public void fabClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            TextView txt_version = (TextView) findViewById(R.id.txt_version);
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            // versionNumber = pinfo.versionCode;
            currentAppVersion = pinfo.versionName;
            txt_version.setText("Version : " + currentAppVersion);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception er) {
            er.printStackTrace();
        }
        try {

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(0).setChecked(true);
            headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            UserPreferences pref = UserPreferences.getInstance(MainActivity.this);


            ImageView profileImage = (ImageView) headerView.findViewById(R.id.imageView);
            TextView profileName = (TextView) headerView.findViewById(R.id.profileName);
            TextView profileEmail = (TextView) headerView.findViewById(R.id.profileEmail);

            profileImage.setImageResource(R.drawable.dskapplogo);
            profileName.setText(pref.getFirst_name());
            profileEmail.setText(pref.getUserid());


            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, new Home());
            fragmentTransaction.addToBackStack("adminfrag");
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
            Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            if (myFragment != null && myFragment.isVisible()) {
                getFragmentManager().popBackStack();
            } else {
                backButtonHandler();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            fragment = new Home();
            replceFrag();
            // Handle the camera action
        }
        else if (id == R.id.nav_changepass) {
            fragment = new ChangePassword();
            replceFrag();

        }else if (id == R.id.nav_logout) {
            // fragment = new Logout();
            logoutButtonHandler();
        } else if (id == R.id.nav_help) {
            showHelp();
        } else if (id == R.id.nav_version) {
            showVersion();
        } else if (id == R.id.nav_aboutus) {
            fragment = new AboutUs();
            replceFrag();
        }
        else if (id == R.id.nav_live_tracking) {


           // Toast.makeText(MainActivity.this,"Add here tracking code",Toast.LENGTH_LONG).show();

            //Intent intent=new Intent(MainActivity.this, SplashScreenActivity.class);
           // startActivity(intent);

         String role= SharedPreferenceManager.with(this).getLoggedInUser().getUserRoleID();

            // Only Managers are able to login those have at least one Filed User (Role_ID = Seva_MA)
            if (SharedPreferenceManager.with(this).getLoggedInUser().getUserRoleID() .equalsIgnoreCase(AppConstants.MANAGER_USER_ROLE)
                   /* && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE)*/) {
                startActivity(new Intent(getApplicationContext(), ManagerDashboardActivity.class));
            }
            else  if(SharedPreferenceManager.with(this).getLoggedInUser().getUserRoleID().equals(AppConstants.FIELD_USER_ROLE)
                  /*  && user.getActiveStatus().equalsIgnoreCase(AppConstants.USER_ACTIVE)*/) {
                startActivity(new Intent(getApplicationContext(), FieldUserDashboardActivity.class));
            }
           /* else {
                Toast toast = Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT);
                toast.show();
            }*/




        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void replceFrag() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }

    public void showHelp() {

       // Intent intent = new Intent(MainActivity.this, Youtube.class);
       // startActivity(intent);


    }

    public void showVersion() {
        ApplicationUtil.getInstance().showVersion(MainActivity.this, "Application Version", "BSES SEVA-KENDRA CUNSUMER " + currentAppVersion);
    }


    public void logoutButtonHandler() {
        ApplicationUtil.getInstance().showLogoutDialog(MainActivity.this, "Logout ?", "Are you sure you want to Logout the Account?");
    }


    public void backButtonHandler() {
        ApplicationUtil.getInstance().showBackDialog(MainActivity.this, "Leave application?", "Are you sure you want to leave the application?");
    }

}
