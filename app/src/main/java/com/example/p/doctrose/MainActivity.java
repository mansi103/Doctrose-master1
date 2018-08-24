package com.example.p.doctrose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButtondoctor,imageButtonpatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButtondoctor = (ImageButton) findViewById(R.id.doctorentrance);
        imageButtonpatient = (ImageButton) findViewById(R.id.patiententrance);
        imageButtondoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdoctor = new Intent(MainActivity.this,DoctorRegistration.class);
                startActivity(intentdoctor);
            }
        });
        imageButtonpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpatient = new Intent(MainActivity.this,ListOfDoctors.class);
                startActivity(intentpatient);
            }
        });
    }
}
