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

public class ViewBankAssets extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bank_assets);
        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> mp=(Map<String, Object>) snapshot.child(username).child("asset").child("tangible").child("bank_assets").getValue();
                // Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                ArrayList<Object> keys=new ArrayList<>();
                HashMap<String,Object> k=new HashMap();
                int x=0;
                for (Map.Entry<String,Object> entry : mp.entrySet()) {
                    //keys.add(entry.getValue());
                    k.put(String.valueOf(x),entry.getValue());
                    //System.out.println(entry.getValue());
                    x=x+1;
                }
                Toast.makeText(getApplicationContext(),String.valueOf(x)+mp.size(),Toast.LENGTH_LONG).show();
                StringBuilder j=new StringBuilder();
                HashMap<String,Object> fina=new HashMap();
                ListView listasset =findViewById(R.id.bank_list);
                int i=0;
                for (Map.Entry<String,Object> entry : k.entrySet())
                {
                    j= new StringBuilder("key= " + entry.getKey() + "value= " + entry.getValue());
                    System.out.println(entry.getValue());
                    smsMessagesList.add(String.valueOf(j));
                    i++;
                }
                Toast.makeText(getApplicationContext(),"done"+i+" "+smsMessagesList.size(),Toast.LENGTH_LONG).show();

                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, smsMessagesList);
                listasset.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}