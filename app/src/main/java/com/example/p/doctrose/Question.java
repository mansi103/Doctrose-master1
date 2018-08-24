package com.example.p.doctrose;

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

public class Question extends AppCompatActivity {
    String patient;

    EditText editTextquestion,editTextpatientname;
    Button buttonsubmit;
    String name1,question;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        editTextpatientname = (EditText) findViewById(R.id.patientname);
        editTextquestion = (EditText) findViewById(R.id.question);
        buttonsubmit = (Button) findViewById(R.id.submit);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepatientquestion();
            }
        });
    }

    private void savepatientquestion() {
            name1 = editTextpatientname.getText().toString().trim();
            question = editTextquestion.getText().toString().trim();
            //  eMail=entermail.getText().toString().trim();
            //  doct1 = textViewdoctorname.getText().toString().trim();
            if(TextUtils.isEmpty(name1)){
                Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(question)){
                Toast.makeText(this, "Please fill the fields!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            QuestionExpert questionExpert = new QuestionExpert(name1,question);
            FirebaseUser firebaseUserbooking = firebaseAuth.getCurrentUser();
            databaseReference.child("expertadvice").child(name1).child(firebaseUserbooking.getUid()).setValue(questionExpert);

            Toast.makeText(this,"Information saved...",Toast.LENGTH_LONG).show();
        }
    }
