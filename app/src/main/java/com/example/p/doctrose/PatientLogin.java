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

public class PatientLogin extends AppCompatActivity {
    EditText editTextemail,editTextpassword;
    Button buttonlogin;
    TextView textViewsignup;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String doctorsname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        buttonlogin = (Button) findViewById(R.id.loginnow);
        textViewsignup = (TextView) findViewById(R.id.signup);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        doctorsname = bundle.getString("doctor");
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null) {
            Intent intentregistered = new Intent(PatientLogin.this, PatientInfo.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("doctor",doctorsname);
            intent.putExtras(bundle1);
            startActivity(intentregistered);
        }
        progressDialog = new ProgressDialog(this);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        textViewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignup = new Intent(PatientLogin.this,PatientRegistration.class);
                startActivity(intentsignup);
            }
        });
    }

    private void userLogin() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Signing in.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            finish();
                            Intent intentregistered = new Intent(PatientLogin.this, PatientInfo.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("doctor",doctorsname);
                            intentregistered.putExtras(bundle1);
                            startActivity(intentregistered);
                        }
                    }
                });
    }
}
