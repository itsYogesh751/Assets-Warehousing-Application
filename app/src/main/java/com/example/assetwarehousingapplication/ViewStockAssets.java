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

public class ViewStockAssets extends AppCompatActivity {
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
                Map<String, HashMap> mp = (Map<String, HashMap>) snapshot.child(username).child("asset").child("tangible").child("Stock").getValue();
                StringBuilder j = new StringBuilder();
                ListView listasset = findViewById(R.id.bank_list);
                for (Map.Entry<String, HashMap> entry : mp.entrySet()) {
                    j = new StringBuilder(" Stock ID= " + entry.getValue().get("Stock ID") +
                            "\n Stock exchange= " + entry.getValue().get("Stock exchange") +
                            "\n Stock Price= " + entry.getValue().get("Stock Price")+
                            "\n Company name= " + entry.getValue().get("Company name") +
                            "\n Stock Quantity= " + entry.getValue().get("Stock Quantity") +
                            "\n Demat Account Id= " + entry.getValue().get("Demat Account Id"));
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