package com.example.p.doctrose;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientRegistration extends AppCompatActivity {

    EditText editTextemail,editTextpassword;
    Button buttonregister;
    TextView textViewalradyuser;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog progressDialog;
    String doctorsname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        buttonregister = (Button) findViewById(R.id.registernow);
        textViewalradyuser = (TextView) findViewById(R.id.alreadyuser);
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        doctorsname = bundle.getString("doctor");
        progressDialog = new ProgressDialog(this);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(PatientRegistration.this,PatientInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("doctor",doctorsname);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        };
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        textViewalradyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRegistration.this,PatientLogin.class);
                Bundle bundle = new Bundle();
                bundle.putString("doctor",doctorsname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(PatientRegistration.this,"Please enter email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(PatientRegistration.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    Intent intentprofile = new Intent(PatientRegistration.this,PatientInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("doctor",doctorsname);
                    intentprofile.putExtras(bundle);
                    startActivity(intentprofile);
                }else {
                    Toast.makeText(PatientRegistration.this,"COULD NOT REGISTER SUCCESSFULLY",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
