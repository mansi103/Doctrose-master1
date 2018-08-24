package com.example.p.doctrose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfQuestions extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_questions);
        listView = (ListView) findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList arr = new ArrayList();
                Log.d("dataSnapshot", dataSnapshot.toString());
                for (DataSnapshot k : dataSnapshot.child("expertadvice").getChildren()){
                    arr.add(k.child("name").getValue()+"\n"+k.child("address").getValue());
                    ArrayAdapter ad = new ArrayAdapter(ListOfQuestions.this, android.R.layout.simple_list_item_1, arr);
                    listView.setAdapter(ad);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListOfQuestions.this,databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
