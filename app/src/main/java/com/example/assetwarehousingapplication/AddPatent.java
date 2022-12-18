package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddPatent extends AppCompatActivity {
    TextView patentApplicationNumber,patentNumber,dateofgrant,patentdesc;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patent);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        patentApplicationNumber=findViewById(R.id.patentApplicationnumber);
        patentNumber=findViewById(R.id.patentNumber);
        dateofgrant=findViewById(R.id.dateOfGrant);
        patentdesc=findViewById(R.id.patentdesc);
        Button addPatenttbtn=findViewById(R.id.addpatentbtn);
        addPatenttbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference= FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference=reference.child(username).child("asset").child("Intangible").child("Patent").child(patentApplicationNumber.getText().toString());
                        HashMap mp=new HashMap();
                        mp.put("Patent Application Number",patentApplicationNumber.getText().toString());
                        mp.put("Patent Number",patentNumber.getText().toString());
                        mp.put("date of grant",dateofgrant.getText().toString());
                        mp.put("Patent description",patentdesc.getText().toString());
                        reference.updateChildren(mp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}