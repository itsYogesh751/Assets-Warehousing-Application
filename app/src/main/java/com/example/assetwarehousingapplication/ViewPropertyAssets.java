package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPropertyAssets extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bank_assets);
        Intent i = getIntent();
        String username = i.getExtras().getString("username");
        reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, HashMap> mp = (Map<String, HashMap>) snapshot.child(username).child("asset").child("tangible").child("Property").getValue();
                StringBuilder j = new StringBuilder();
                ListView listasset = findViewById(R.id.bank_list);
                for (Map.Entry<String, HashMap> entry : mp.entrySet()) {
                    if(entry.getValue().size()==4) {
                        j = new StringBuilder(" Property Registration Number= " + entry.getValue().get("Property Registration Number") +
                                "\n Property Address= " + entry.getValue().get("Property Address") +
                                "\n Property description= " + entry.getValue().get("Property Description")+
                                "\n Property Price= " + entry.getValue().get("Property Price"));
                    }
                    else{
                        j = new StringBuilder(" Property Registration Number= " + entry.getValue().get("Property Registration Number") +
                                "\n Property Address= " + entry.getValue().get("Property Address") +
                                "\n Property description= " + entry.getValue().get("Property Description")+
                                "\n Property Price= " + entry.getValue().get("Property Price")+
                                "\n Rera registration number= " + entry.getValue().get("Rera registration number")+
                                "\n Property name under rera= " + entry.getValue().get("Property name under rera"));
                    }
                    System.out.println(entry.getValue());
                    smsMessagesList.add(String.valueOf(j));
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, smsMessagesList);
                listasset.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
};