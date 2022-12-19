package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewGoldAssets extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_bank_assets);
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, HashMap> mp = (Map<String, HashMap>) snapshot.child(username).child("asset").child("tangible").child("Gold").getValue();
                StringBuilder j = new StringBuilder();
                ListView listasset = findViewById(R.id.bank_list);
                for (Map.Entry<String, HashMap> entry : mp.entrySet()) {
                    if(entry.getValue().size()==5) {
                        j = new StringBuilder(" gold unique name= " + entry.getValue().get("gold_name") + "Type of gold investment= " + entry.getValue().get("Type of gold investment") +
                                "\n gold price= " + entry.getValue().get("gold price") + " gold weight= " +
                                entry.getValue().get("gold weight") +
                                "\n gold description= " + entry.getValue().get("gold description"));
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