package com.example.p.doctrose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientInfo extends AppCompatActivity {
    EditText editTextemail,editTextname,editTextaddress;
   // TextView textViewdoctorname;
    Button buttonsave,buttonlogout,buttonexpert;
    private FirebaseAuth firebaseAuth;
    EditText editTextdoctorname;
    private DatabaseReference databaseReference;
    String name="",address="",eMail="";
    String doctorsname,doct,doct1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        doct = bundle.getString("doctor");
        editTextemail = (EditText) findViewById(R.id.email);
        editTextaddress = (EditText) findViewById(R.id.address);
        editTextname = (EditText) findViewById(R.id.name);
        editTextdoctorname=(EditText)findViewById(R.id.doctorname);
        editTextdoctorname.setText(doct);
        // textViewdoctorname = (TextView) findViewById(R.id.doctorname);
        buttonsave = (Button) findViewById(R.id.saveinfo);
        buttonlogout = (Button) findViewById(R.id.logout);
        buttonexpert = (Button) findViewById(R.id.expertadvice);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Intent intentlogin = new Intent(PatientInfo.this,PatientLogin.class);
            startActivity(intentlogin);
        }
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    firebaseAuth.signOut();
                    finish();
                    Intent intentlogin = new Intent(PatientInfo.this, PatientRegistration.class);
                    startActivity(intentlogin);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserInformation();
            }
        });
        buttonexpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(PatientInfo.this, Question.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("patientname", name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
    }

    private void saveuserInformation() {
        name = editTextname.getText().toString().trim();
        address = editTextaddress.getText().toString().trim();
        eMail=editTextemail.getText().toString().trim();
        doct1 = editTextdoctorname.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(eMail)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(doct1)){
            Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        PatientDetails patientDetails = new PatientDetails(name,address,doct1,eMail);
        FirebaseUser firebaseUserbooking = firebaseAuth.getCurrentUser();
        databaseReference.child("Patient").child(doct1).child(firebaseUserbooking.getUid()).setValue(patientDetails);

        Toast.makeText(this,"Information saved...",Toast.LENGTH_LONG).show();
    }
}
