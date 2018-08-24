package com.example.p.doctrose;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOfDoctors extends AppCompatActivity {
    Location src_Location,myLoc;
    String s;

    DatabaseReference databaseReference;
    String doctorsname="";
    LocationManager locationManager;
    String markeratdestination,details;
    private static final int REQUEST_CODE_PERMISSION = 2;
    int REQUEST_LOCATION = 1;
    double calc = 0.0;
    String mPermission= android.Manifest.permission.ACCESS_FINE_LOCATION;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_doctors);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listView = (ListView) findViewById(R.id.list_item);
        try
        {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList arr = new ArrayList();

//                Toast.makeText(ProfileActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                Log.d("dataSnapshot", dataSnapshot.toString());
                for (DataSnapshot k : dataSnapshot.child("Doctor").getChildren()) {
                    //dataTV.append("\n"+k.child("address").getValue()+" "+k.child("name").getValue());
try {
    myAddress();
    s = k.child("address").getValue().toString();
    distAddress();
    calc = (myLoc.distanceTo(src_Location))/1000;
    arr.add(k.child("name").getValue() + "\n" +k.child("address").getValue() + "\n"+"QUALIFICATION: "+k.child("qualification").getValue() + "\t" +"SPECIALITY: "+k.child("speciality").getValue() + "\t\t\t" +"RATING:"+ k.child("rate").getValue() + "\n" +"DISTANCE BETWEEN: "+ calc);
    ArrayAdapter ad = new ArrayAdapter(ListOfDoctors.this, android.R.layout.simple_list_item_1, arr);
    listView.setAdapter(ad);
}catch (Exception e){
    e.printStackTrace();
}

                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        details = parent.getItemAtPosition(position).toString();
                        String k[] =details.split("\n");
                        markeratdestination = k[1];
                        doctorsname = k[0];
                        Toast.makeText(getApplicationContext(),doctorsname,Toast.LENGTH_LONG).show();
                        Intent intenttomaps = new Intent(ListOfDoctors.this,MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("doctors",doctorsname);
                        bundle.putString("markeratdest",markeratdestination);
                        intenttomaps.putExtras(bundle);
                        startActivity(intenttomaps);
                    }
                });
            }
            void myAddress() {
                if (ActivityCompat.checkSelfPermission(ListOfDoctors.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ListOfDoctors.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListOfDoctors.this, new String[]
                            {
                                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latti = location.getLatitude();
                    double longit = location.getLongitude();
                    myLoc = new Location("");
                    myLoc.setLatitude(latti);
                    myLoc.setLongitude(longit);

                }
            }

            void distAddress(){
                src_Location = new Location("");
                Geocoder geocoder = new Geocoder(ListOfDoctors.this);
                try{
                    List<Address> list = geocoder.getFromLocationName(s,1);
                    Address address = list.get(0);
                    double lat = address.getLatitude();
                    double longit = address.getLongitude();
                    src_Location.setLatitude(lat);
                    src_Location.setLongitude(longit);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListOfDoctors.this,databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
