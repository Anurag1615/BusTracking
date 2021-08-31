package com.example.smarthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout=findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        Intent main_Intent= new Intent(MainActivity.this,MapsActivity.class);
        startActivity(main_Intent);




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.d2d:
                //Toast.makeText(getBaseContext(),"Profile Clicked",Toast.LENGTH_LONG).show();
                 Intent profile_Intent = new Intent(MainActivity.this,Door_To_Door_Testing.class);
                 startActivity(profile_Intent);

                break;

            case R.id.map:
//                Toast.makeText(getBaseContext(),"Show Appointment Clicked",Toast.LENGTH_LONG).show();
                   Intent map_intent = new Intent(MainActivity.this,MapsActivity.class);
                  startActivity(map_intent);
                break;

            case R.id.nav_bookedAppointment:
//                Toast.makeText(getBaseContext(),"Booked Appointment Clicked",Toast.LENGTH_LONG).show();
                  Intent bookedAppointment_Intent = new Intent(MainActivity.this,Vaccination_centres.class);
                  startActivity(bookedAppointment_Intent);
                break;

            case R.id.nav_login:
                Intent login_Intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_Intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                onStart();

                Toast.makeText(getBaseContext(),"Successfully Logged Out",Toast.LENGTH_LONG).show();
                break;

            case R.id.lab:

                    startActivity(new Intent(MainActivity.this,TestResult.class));
                break;

           /* case R.id.nav_aboutapp:
//                Toast.makeText(getBaseContext(),"About Us Clicked",Toast.LENGTH_LONG).show();
                // startActivity(new Intent(MainActivity.this,About_App.class));
                break;

            */

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Menu menuNav = navigationView.getMenu();
        final MenuItem h = menuNav.findItem(R.id.nav_bookedAppointment);
        final MenuItem home_Testing= menuNav.findItem(R.id.d2d);
        final MenuItem Labtesting = menuNav.findItem(R.id.lab);


        MenuItem nav_logOut = menuNav.findItem(R.id.nav_logout);
        MenuItem nav_logIn = menuNav.findItem(R.id.nav_login);
      //  h.setVisible(false);
       // nav_profile.setVisible(false);
       // home_Testing.setVisible(false);
        //Labtesting.setVisible(false);
        nav_logIn.setVisible(true);
        //nav_logOut.setVisible(false);


        if(currentUser==null){
            nav_logIn.setVisible(true);
            Labtesting.setVisible(false);
            home_Testing.setVisible(false);
        }else {
            nav_logOut.setVisible(true);
            // checkType();
            Labtesting.setVisible(true);
            home_Testing.setVisible(true);

        }

    }
}