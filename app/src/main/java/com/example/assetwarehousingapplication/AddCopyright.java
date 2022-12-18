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

public class AddCopyright extends AppCompatActivity {
    TextView copyrightNumber,diarynumber,copyrightdate,copyrightdesc;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_copyright);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        copyrightNumber=findViewById(R.id.copyrightRegnumber);
        diarynumber=findViewById(R.id.diarynumber);
        copyrightdate=findViewById(R.id.copyrightdate);
        copyrightdesc=findViewById(R.id.copyrightdesc);
        Button addCopyrightbtn=findViewById(R.id.addcopyrightbtn);
        addCopyrightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference= FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference=reference.child(username).child("asset").child("Intangible").child("Copyright").child(copyrightNumber.getText().toString());
                        HashMap mp=new HashMap();
                        mp.put("Copyright Number",copyrightNumber.getText().toString());
                        mp.put("diarynumber",diarynumber.getText().toString());
                        mp.put("copyright date",copyrightdate.getText().toString());
                        mp.put("copyright description",copyrightdesc.getText().toString());
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