package com.tejaandroid.medicare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Button med, appoint, prescribe, map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        med = findViewById(R.id.buttonMed);
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), MedActivity.class);
                startActivity(i1);
            }
        });
        
        appoint = findViewById(R.id.buttonAppoint);
        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), AppointActivity.class);
                startActivity(i2);
            }
        });
        prescribe = findViewById(R.id.buttonPrescribe);
        prescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getApplicationContext(), PrescribeActivity.class);
                startActivity(i3);
            }
        });

        map = findViewById(R.id.buttonMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocation();
            }
        });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            Log.d("Medicare", "Map Permission granted");
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                double lat = currentLocation.getLatitude();
                double lng = currentLocation.getLongitude();
                Log.d("map", "lat:"+lat+", lng:"+lng);
                Toast.makeText(getApplicationContext(), "Nearest medical stores and hospitals...", Toast.LENGTH_SHORT).show();
                String geoURI = String.format("geo:%f,%f?q=hospital+or+medical+store", lat, lng);
                Uri geo = Uri.parse(geoURI);
                Intent i = new Intent(Intent.ACTION_VIEW, geo);
                startActivity(i);
            }
        });
    }
}