package com.example.smarthealthcare;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
  //  private GoogleMap mMap;
    CheckBox checkBox1,checkBox2;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;


    double curr_lat;
    double curr_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
checkBox2=findViewById(R.id.qc);
checkBox1=findViewById(R.id.vc);

checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(checkBox1.isChecked())
        {
          Place("VCLOC");
        }
    }
});
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
       {
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
       }
       else
       {
           mMap.setMyLocationEnabled(true);
           covidloc();
           currloc();

       }

    }

    private void currloc() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //Toast.makeText(getApplicationContext(),Double.toString(latitude)+"     "+Double.toString(longitude),Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(MapsActivity.this).requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        LocationServices.getFusedLocationProviderClient(MapsActivity.this).removeLocationUpdates(this);
                        if(locationResult!=null && locationResult.getLocations().size()>0){
                            int latestlocindex=locationResult.getLocations().size()-1;
                       curr_lat=locationResult.getLocations().get(latestlocindex).getLatitude();
                            curr_lng=locationResult.getLocations().get(latestlocindex).getLongitude();
                            Toast.makeText(getApplicationContext(),  String.valueOf(curr_lat),Toast.LENGTH_LONG).show();

                        }
                      //  Toast.makeText(getApplicationContext(),Double.toString(latitude)+"/l"+Double.toString(longitude),Toast.LENGTH_LONG).show();
                    }

                }
                , Looper.myLooper());
    }

    private void Place(String VCLOC) {


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(VCLOC);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {

                        double latitude = (double) dataSnapshot.child("latitude").getValue();
                        double longitude = (double) dataSnapshot.child("longitude").getValue();
                        String Name= (String) dataSnapshot.child("name").getValue();
                       // Toast.makeText(getApplicationContext(),latitude+" "+longitude+" "+Name,Toast.LENGTH_LONG).show();
String str=dist(curr_lat,curr_lng,latitude,longitude);
                        LatLng latLng = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(Name+" "+str);

                      //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vc4));
                        mMap.addMarker(markerOptions);
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(200));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    private String dist(double lat1, double lon1, double lat2, double lon2) {

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c =  (2 * Math.asin(Math.sqrt(a)));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6400/2;

        // calculate the result
        NumberFormat formatter = new DecimalFormat("#.##");
        String dis=c*r+"KM";
       dis= formatter.format(c*r);

                return dis+"KM";
      //  return(c * r);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
     /*   mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
lati=location.getLatitude();
        lng=location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }

      */


    } public void covidloc() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    double latitude = (double) dataSnapshot.child("latitude").getValue();
                    double longitude = (double) dataSnapshot.child("longitude").getValue();
                    String Name= (String) dataSnapshot.child("name").getValue();
                    LatLng latLng = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(Name);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}