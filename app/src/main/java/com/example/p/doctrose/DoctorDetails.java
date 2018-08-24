package com.example.p.doctrose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDetails extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String name,address,qualification,speciality;
    String  rate;
    private EditText editTextname,editTextaddress,editTextqualification,editTextspeciality;
    private Button buttonsaveinfo,buttonlogout,buttonexpertanswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        editTextname = (EditText) findViewById(R.id.name);
        editTextaddress = (EditText) findViewById(R.id.address);
        editTextqualification = (EditText) findViewById(R.id.qualification);
        editTextspeciality = (EditText) findViewById(R.id.speciality);
        buttonsaveinfo = (Button) findViewById(R.id.saveinfo);
        buttonlogout = (Button) findViewById(R.id.logout);
        buttonexpertanswer = (Button) findViewById(R.id.expertanswer);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Intent intent = new Intent(DoctorDetails.this,DoctorLogin.class);
            startActivity(intent);
        }
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
                Intent intentlogin = new Intent(DoctorDetails.this,DoctorLogin.class);
                startActivity(intentlogin);
            }
        });
        buttonexpertanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDetails.this,ListOfQuestions.class);
                startActivity(intent);
            }
        });
        buttonsaveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserinfo();
            }
        });
    }

    private void saveuserinfo() {
        name = editTextname.getText().toString().trim();
        address = editTextaddress.getText().toString().trim();
        qualification = editTextqualification.getText().toString().trim();
        speciality = editTextspeciality.getText().toString().trim();
        rate = "0.0";

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(qualification)){
            Toast.makeText(this, "Please fill the fields!!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(speciality)){
            Toast.makeText(this, "Please fill the fields!!!",Toast.LENGTH_SHORT).show();
            return;
        }
        DoctorInfo doctorInfo = new DoctorInfo(name,address,qualification,speciality,rate);
        name = editTextname.getText().toString().trim();
        address = editTextaddress.getText().toString().trim();
        qualification = editTextqualification.getText().toString().trim();
        speciality = editTextspeciality.getText().toString().trim();
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
        databaseReference.child("Doctor").child(firebaseUser.getUid()).setValue(doctorInfo);
        Toast.makeText(this,"INFORMATION SAVED!!!",Toast.LENGTH_SHORT).show();
    }
}
