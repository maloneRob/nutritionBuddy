package com.G26.fitnessandnutritionbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.G26.fitnessandnutritionbuddy.data.model.UserLocationInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;


public class UserLocation extends AppCompatActivity {
    public static final int FAST_UPDATE_INTERVAL = 5;
    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);


        boolean updateOn= false;
        // the use of FusedLocation object to get the location
        //setting the location properties

        locationRequest = LocationRequest.create()
                .setInterval(1000 * DEFAULT_UPDATE_INTERVAL)
                .setFastestInterval(1000 * FAST_UPDATE_INTERVAL)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .setMaxWaitTime(100);

        // The main function that we care about
        updateGPS();
    }// end of onCreate Met
    @Override
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
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }

    }
 /// This will update the UserLocationInfo with the information needed
    private void update_location_values(Location location) {
        //just for testing to ensure that everything is working fine
        Log.i("TestingMaps",String.valueOf(location.getLongitude()));
        Log.i("TestingMaps",String.valueOf(location.getLatitude()));
        //This is is setting the user information
        UserLocationInfo.setLatitude(location.getLatitude());
        UserLocationInfo.setLongitude(location.getLongitude());


    }
}
