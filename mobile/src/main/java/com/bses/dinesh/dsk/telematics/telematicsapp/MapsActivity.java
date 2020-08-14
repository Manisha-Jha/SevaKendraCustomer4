package com.bses.dinesh.dsk.telematics.telematicsapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.telematics.data.TasksData;
import com.bses.dinesh.dsk.telematics.data.UserLocationData;
import com.bses.dinesh.dsk.telematics.utils.CommonUtilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
/*import com.jhamobi.brplapp.data.TasksData;
import com.jhamobi.brplapp.data.UserLocationData;
import com.jhamobi.brplapp.utils.CommonUtilities;*/

import java.util.ArrayList;
import java.util.List;

import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_FIELD_LOCATION;
import static com.bses.dinesh.dsk.telematics.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;

/*import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_FIELD_LOCATION;
import static com.jhamobi.brplapp.utils.Constant.DatabaseTable.TABLE_TASKS_DATA;*/

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.design_default_color_primary, R.color.colorAccent, R.color.primary_dark_material_light};
    private GoogleMap mMap;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private List<TasksData> TasksList;
    private List<UserLocationData> LocList;
    private Context mContext;
    private String userID;
    private boolean isFirst = false;
    private List<Polyline> polylines;
    private String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = MapsActivity.this;
        TasksList = new ArrayList<>();
        LocList = new ArrayList<>();
        polylines = new ArrayList<>();
        strDate = CommonUtilities.todayDate();
        userID = getIntent().getStringExtra("userid");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //getKeyHash("SHA");

        initialiseDatabase();
        initialiseDatabase1();
        //queryExample();
    }

    private void queryExample() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //ref.where("timestamp", ">=", "2017-11").where("timestamp", "<", "2017-12")
        //Query query = mFirebaseInstance.getReference("Tasks").orderByValue().where("timestamp", ">=", "2017-11").where("timestamp", "<", "2017-12")
        Query query = mFirebaseInstance.getReference("Tasks").child("userid").equalTo("1").orderByChild("timestamp");
        Query query2 = mFirebaseInstance.getReference("Users").child("userid").equalTo("1");
    }

//    private void callRoute(List<LatLng> latlngList) {
//        Routing routing = new Routing.Builder()
//                .travelMode(AbstractRouting.TravelMode.DRIVING)
//                .withListener(this)
//                .alternativeRoutes(true)
//                .key(getResources().getString(R.string.google_maps_key))
//                .waypoints(latlngList)
//                .build();
//        routing.execute();
//    }

    private void initialiseDatabase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_TASKS_DATA);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            //mFirebaseDatabase.child("userid").equalTo(userID).orderByChild("userid").equalTo("Yes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                TasksList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    TasksData tasksData = postSnapshot12.getValue(TasksData.class);
                    if (tasksData.getDate() != null && tasksData.getDate().equalsIgnoreCase(strDate) && userID.equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
                        TasksList.add(tasksData);
                }
                //setPolylineData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initialiseDatabase1() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(TABLE_FIELD_LOCATION);
        mFirebaseDatabase.orderByChild("userid").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                LocList.clear();
                for (DataSnapshot postSnapshot12 : dataSnapshot.getChildren()) {
                    UserLocationData tasksData = postSnapshot12.getValue(UserLocationData.class);
                    //if (userID.equalsIgnoreCase(tasksData.getUserid()) && tasksData.getStatus().equalsIgnoreCase("Yes"))
                    if (tasksData.getDate() != null && tasksData.getDate().equalsIgnoreCase(strDate))
                        LocList.add(tasksData);
                }
                setPolylineData1();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setPolylineData() {
        if (mMap != null) mMap.clear();

        if (mMap != null && TasksList != null && TasksList.size() > 0) {
            List<LatLng> latlngList = new ArrayList<>();
            for (TasksData tasksData : TasksList) {
                if (tasksData.getLat() != null && tasksData.getLng() != null && !tasksData.getLat().equalsIgnoreCase("") && !tasksData.getLng().equalsIgnoreCase(""))
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()), Double.parseDouble(tasksData.getLng())));
            }
            setMarkerToLatLng(latlngList, TasksList);
        }
    }

    private void setPolylineData1() {
        if (mMap != null) mMap.clear();

        if (mMap != null && LocList != null && LocList.size() > 0) {
            List<LatLng> latlngList = new ArrayList<>();
            for (UserLocationData tasksData : LocList) {
                if (tasksData.getLat() != null && tasksData.getLng() != null && !tasksData.getLat().equalsIgnoreCase("") && !tasksData.getLng().equalsIgnoreCase(""))
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()), Double.parseDouble(tasksData.getLng())));
            }
            setPolylineFromLatLng(latlngList);
        }
        setMarkerData(); // Added for marker
    }

    private void setMarkerData() {
        if (mMap != null && TasksList != null && TasksList.size() > 0) {
         //   mMap.setInfoWindowAdapter(new HistoryTasksInfoWindowAdapter(this, TasksList)); // Added custom marker window
            for (TasksData tasksData : TasksList) {
                if (tasksData.getLat() != null && tasksData.getLng() != null && !tasksData.getLat().equalsIgnoreCase("") && !tasksData.getLng().equalsIgnoreCase(""))
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_marker_done)).position(new LatLng(Double.parseDouble(tasksData.getLat()), Double.parseDouble(tasksData.getLng()))).title(tasksData.getName()).snippet(tasksData.getAddress())).setTag(tasksData.getId());
            }

            /*
            // Created for Routing
            ArrayList<LatLng> latlngList = new ArrayList<>();
            for (TasksData tasksData : TasksList) {
                if (tasksData.getLat() != null && tasksData.getLng() != null && !tasksData.getLat().equalsIgnoreCase("") && !tasksData.getLng().equalsIgnoreCase(""))
                    latlngList.add(new LatLng(Double.parseDouble(tasksData.getLat()), Double.parseDouble(tasksData.getLng())));
            }
            callRoute(latlngList);
            // End Created for Routing
            */

        }
    }

    private void setMarkerToLatLng(List<LatLng> latlngList, List<TasksData> tasksList) {
        if (mMap != null) mMap.clear();
        int pos = 0;
        if (latlngList != null && latlngList.size() > 0)
            for (LatLng latLng : latlngList) {
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_marker_done)).position(latLng).title(tasksList.get(pos).getName()).snippet(tasksList.get(pos).getAddress()));
                pos++;
            }
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions().clickable(true).geodesic(true).addAll(latlngList));

        if (latlngList != null && latlngList.size() > 0) {
            if (!isFirst)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 15));

            isFirst = true;
        }

        polyline1.setEndCap(new RoundCap());
        polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline1.setColor(Color.RED);
        polyline1.setJointType(JointType.ROUND);
        polyline1.setGeodesic(true);
    }

    private void setPolylineFromLatLng(List<LatLng> latlngList) {
        if (mMap != null) mMap.clear();
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions().clickable(true).geodesic(true).addAll(latlngList));

        if (latlngList != null && latlngList.size() > 0) {
            if (!isFirst)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 15));

            isFirst = true;
            String distance = "Distance: " + CommonUtilities.getKmFromLatLongList(latlngList) + "km";
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_marker_walk)).position(latlngList.get(latlngList.size() - 1)).title("Current Position").snippet(distance));

            // Set Start Location
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_start)).position(latlngList.get(0)).title("START"));
        }

        polyline1.setEndCap(new RoundCap());
        polyline1.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline1.setColor(Color.RED);
        polyline1.setJointType(JointType.ROUND);
        polyline1.setGeodesic(true);


    }

//    private void getKeyHash(String hashStretagy) {
//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance(hashStretagy);
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                Log.e("KeyHash  -->>>>>>>>>>>>" , something);
//
//                // Notification.registerGCM(this);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found" , e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm" , e.toString());
//        } catch (Exception e) {
//            Log.e("exception" , e.toString());
//        }
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5946, 78.3391), 5)); // Default zoom by SM

        //setPolylineData();
        setPolylineData1();

    }

    private void setMarkerToLatLng(List<LatLng> latlngList) {
        if (latlngList != null && latlngList.size() > 0)
            for (LatLng latLng : latlngList) {
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_marker_done)).position(latLng).title(latLng.toString()));
            }
    }

//    @Override
//    public void onRoutingFailure(RouteException e) {
//        e.printStackTrace();
//        Log.e("==RouteException==","==RouteException=="+e.getMessage().toString());
//    }
//    @Override
//    public void onRoutingStart() {
//        Log.e("==onRoutingStart==","==onRoutingStart==");
//    }
//    @Override
//    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
//        Log.e("==onRoutingSuccess==","==onRoutingSuccess==");
//        if(polylines.size()>0) {
//            for (Polyline poly : polylines) {
//                poly.remove();
//            }
//        }
//        polylines = new ArrayList<>();
//        //add route(s) to the map.
//        for (int i = 0; i <route.size(); i++) {
//            //In case of more than 5 alternative routes
//            int colorIndex = i % COLORS.length;
//            PolylineOptions polyOptions = new PolylineOptions();
//            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
//            polyOptions.width(10 + i * 3);
//            polyOptions.addAll(route.get(i).getPoints());
//            Polyline polyline = mMap.addPolyline(polyOptions);
//            polylines.add(polyline);
//            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
//        }
//
////        // Start marker
////        MarkerOptions options = new MarkerOptions();
////        options.position(start);
////        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
////        mMap.addMarker(options);
////
////        // End marker
////        options = new MarkerOptions();
////        options.position(end);
////        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
////        mMap.addMarker(options);
//
//    }
//    @Override
//    public void onRoutingCancelled() {
//        Log.e("==onRoutingCancelled==","==onRoutingCancelled==");
//    }


}
