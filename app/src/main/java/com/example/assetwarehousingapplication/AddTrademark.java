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

public class AddTrademark extends AppCompatActivity {
    DatabaseReference reference;
    TextView trademarknumber,trademarkdate,trademarkdesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trademark);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        trademarknumber=findViewById(R.id.trademarknumber);
        trademarkdate=findViewById(R.id.dateofApplication);
        trademarkdesc=findViewById(R.id.trademarkdesc);
        Button addTrademarkbtn=findViewById(R.id.addtrademarkbtn);
        addTrademarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference= FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference=reference.child(username).child("asset").child("Intangible").child("Trademark").child(trademarknumber.getText().toString());
                        HashMap mp=new HashMap();
                        mp.put("Trademark Number",trademarknumber.getText().toString());
                        mp.put("Trademark date of application",trademarkdate.getText().toString());
                        mp.put("Trademark description",trademarkdesc.getText().toString());
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