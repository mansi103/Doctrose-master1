package com.example.p.doctrose;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double destlat, destlongi;
    double mylat,mylongi;
    int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private GoogleMap mMap;
    String doctorsname,markersatdestination;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        doctorsname = bundle.getString("doctors");
        markersatdestination = bundle.getString("markeratdest");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        button = (Button) findViewById(R.id.buttonbook);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,PatientRegistration.class);
                Bundle bundle = new Bundle();
                bundle.putString("doctor",doctorsname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


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
        getLocationfromAddress();
        getLocation();
        // Add a marker in Sydney and move the camera
        LatLng destination = new LatLng(destlat, destlongi);
        mMap.addMarker(new MarkerOptions().position(destination).title("Marker in Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        LatLng my = new LatLng(mylat,mylongi);
        mMap.addMarker(new MarkerOptions().position(my).title("Marker in Source"));
        mMap.setTrafficEnabled(true);
    }
    private void getLocationfromAddress() {

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> list = geocoder.getFromLocationName(markersatdestination, 1);
            Address address = list.get(0);
            destlat = address.getLatitude();
            destlongi = address.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {
                            android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location!=null){
            mylat = location.getLatitude();
            mylongi = location.getLongitude();
        }
    }
}
