package com.chandra.gisandroid;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserPermission();
    }

    private void getUserPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(permission,0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng rumah = new LatLng(-0.669066, 119.741396);
        LatLng kantorbaupati = new LatLng(-0.677820, 119.752390);
        //Custom size marker
        int tinggi = 100;
        int lebar = 100;
        BitmapDrawable bitmapStart = (BitmapDrawable) getResources().getDrawable(R.drawable.gambar1);
        BitmapDrawable bitmapDes = (BitmapDrawable) getResources().getDrawable(R.drawable.gambar2);
        Bitmap s = bitmapStart.getBitmap();
        Bitmap d = bitmapDes.getBitmap();
        Bitmap markerStart = Bitmap.createScaledBitmap(s, lebar, tinggi, false);
        Bitmap markerDes = Bitmap.createScaledBitmap(d, lebar, tinggi, false);
        //Add marker to map
        mMap.addMarker(new MarkerOptions().position(rumah).title("Marker in Rumah")
                .snippet("Ini adalah Rumah Saya")
                .icon(BitmapDescriptorFactory.fromBitmap(markerStart)));
        mMap.addMarker(new MarkerOptions().position(kantorbaupati).title("Marker in Kantor Bupati Donggala")
                .snippet("Ini adalah Kantor Bupati Donggala")
                .icon(BitmapDescriptorFactory.fromBitmap(markerDes)));

        mMap.addPolyline(new PolylineOptions().add(
                rumah,
                new LatLng(-0.669350, 119.741383),
                new LatLng(-0.669698, 119.742014),
                new LatLng(-0.670992, 119.741272),
                new LatLng(-0.671240, 119.741700),
                new LatLng(-0.672040, 119.741756),
                new LatLng(-0.672298, 119.742327),
                new LatLng(-0.672129, 119.743580),
                new LatLng(-0.672759, 119.743524),
                new LatLng(-0.673808, 119.744865),
                new LatLng(-0.673786, 119.745235),
                new LatLng(-0.674478, 119.746605),
                new LatLng(-0.675270, 119.749043),
                new LatLng(-0.676703, 119.753085),
                kantorbaupati
                ).width(10)
                        .color(Color.BLUE)
        );

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rumah, 11.5f));

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient =
                new FusedLocationProviderClient(this);

        Task location = fusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Location currentLocation = (Location) task.getResult();
                mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Ini Adalah Lokasiku").snippet("Chandra Setiawan F55117023"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),11.5f));
            }
        });
    }

    public void Mylocation(View view) {
        getCurrentLocation();
    }
}

