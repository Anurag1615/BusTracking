package com.example.smarthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class Vaccination_centres extends AppCompatActivity {
     double lat;
    double lng;
    //String name;
    TextInputEditText latitude,longitude,name;
VCloc VCloc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_centres);
        latitude=findViewById(R.id.name);
        longitude=findViewById(R.id.sid);
        name=findViewById(R.id.namee);
        covidloc();
    }
    public void covidloc() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //Toast.makeText(getApplicationContext(),Double.toString(latitude)+"     "+Double.toString(longitude),Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(Vaccination_centres.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        LocationServices.getFusedLocationProviderClient(Vaccination_centres.this).removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocindex = locationResult.getLocations().size() - 1;
                            lat = locationResult.getLocations().get(latestlocindex).getLatitude();
                            lng = locationResult.getLocations().get(latestlocindex).getLongitude();
                            latitude.setText(String.valueOf(lat));

                            longitude.setText(String.valueOf(lng));
                            Toast.makeText(getApplicationContext(), String.valueOf(longitude), Toast.LENGTH_LONG).show();
                            //     lat.setText("Latitude :-"+Double.toString(latitude));
                            //   lng.setText("Longitude :-"+Double.toString(longitude));
                          /*  Name=name.getText().toString();
                            ID=id.getText().toString();
                                covidLoc = new covidLoc(latitude, longitude,Name,ID);
                            FirebaseDatabase.getInstance().getReference().child("loc").push().setValue(covidLoc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"loc saved",Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(getApplicationContext(),"loc not saved",Toast.LENGTH_LONG).show();
                                    }


                                }
                            });



                          */
                        }
                        Toast.makeText(getApplicationContext(), Double.toString(lat) + "/l" + Double.toString(lng), Toast.LENGTH_LONG).show();
                    }

                }
                , Looper.myLooper());
    }
    public void update(View view)
    {

     String Name=name.getText().toString();

      VCloc= new VCloc(Double.parseDouble(String.valueOf(latitude.getText())),Double.parseDouble(String.valueOf(longitude.getText())),Name);
        FirebaseDatabase.getInstance().getReference().child("VCLOC").push().setValue(VCloc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Not not saved",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}