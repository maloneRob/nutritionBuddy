package com.G26.fitnessandnutritionbuddy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.G26.fitnessandnutritionbuddy.data.model.UserLocationInfo;
import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.G26.fitnessandnutritionbuddy.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int FAST_UPDATE_INTERVAL = 5;
    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    EditText userNameText;
    Bundle saveData;
    Bundle restaurauntData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean updateOn= false;
        // the use of FusedLocation object to get the location
        //setting the location properties
//        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
//                .setWaitForAccurateLocation(false)
//                .setMinUpdateIntervalMillis(500)
//                .setMaxUpdateDelayMillis(1000)
//                .build();
        locationRequest = LocationRequest.create()
                .setInterval(1000 * DEFAULT_UPDATE_INTERVAL)
                .setFastestInterval(1000 * FAST_UPDATE_INTERVAL)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .setMaxWaitTime(100);

        // The main function that we care about
        updateGPS();

        System.out.println("[MethodCheck] MainActivity onCreate");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        //get username from textview at login screen
//        userNameText = (EditText) findViewById(R.id.editTextTextPersonName);

    } @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            // the same number that we passed to requestPermissions

            case 99:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    updateGPS();
                }
                else
                {
                    // Post a message for the user in case the permissions is not given
                    // then stops the program
                    Toast.makeText(this,"This app requires Needs permission to granted in order to work properly", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void updateGPS() {
        //get permission from the user
        // get the current location from the fused client
        //update the UI to reflect on the location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // user gave the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got the permissions,setting hte values of location. XXX into the UI/Class object
                    update_location_values(location);

                }
            });
        }else
        {
            // permissions is not granted yet
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // note that the number 99 could be any number
                Log.i("[permissions requested]","getting  permissions");
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }

    }

    private void update_location_values(Location location) {
        //just for testing to ensure that everything is working fine
        Log.i("TestingMaps",String.valueOf(location.getLongitude()));
        Log.i("TestingMaps",String.valueOf(location.getLatitude()));
        //This is is setting the user information
        UserLocationInfo.setLatitude(location.getLatitude());
        UserLocationInfo.setLongitude(location.getLongitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //these classes save the object between fragments
    public void saveData(Bundle data){
        saveData = data;
        UserProfile temp = data.getParcelable("profile");
        Log.d("print save", temp.getDisplayName());
        Log.d("print save calories", temp.getNutrientGoal("calories").toString());
        Log.d("print save carbs", temp.getNutrientGoal("carbohydrates").toString());
        Log.d("print save fats",temp.getNutrientGoal("fats").toString());
        Log.d("print save protein",temp.getNutrientGoal("protein").toString());
    }
    // get the user's profile to pass to new fragment
    public Bundle getSaveData(){
        return saveData;
    }


    // save the list of restaurants to main activity to pass between fragments

    public void saveRestaurants(Bundle data){
        Log.d("[Method check]", "in saveRestaurants method");
        //save restaurant bundle so we can just pass it back when called
        restaurauntData = data;
        //test print restaurant menu's
        ArrayList<Restaurant> restaurants = data.getParcelableArrayList("restaurants");
        for (Restaurant rest : restaurants){
            for (Food food : rest.getMenu()){
                Log.d("printing saved menu items name", food.getName());
                Log.d("printing saved menu items cal", String.valueOf(food.getCalories()));
                Log.d("printing saved menu items carbs", String.valueOf(food.getCarbohydrates()));
                Log.d("printing saved menu items fats", String.valueOf(food.getFats()));
                Log.d("printing saved menu items protein", String.valueOf(food.getProtein()));
            }
        }
    }
    public Bundle getRestaurants(){return restaurauntData;}

}