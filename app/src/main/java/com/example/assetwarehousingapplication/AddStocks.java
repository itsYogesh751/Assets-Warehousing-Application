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

public class AddStocks extends AppCompatActivity {
    DatabaseReference reference;
    TextView stockid,compname,exhange,price,quantity,demataccId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stocks);
        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        Button Addstockbtn=findViewById(R.id.addstocksbtn);
        stockid=findViewById(R.id.stockid);
        compname=findViewById(R.id.stockcompname);
        exhange=findViewById(R.id.exchange);
        price=findViewById(R.id.stockprice);
        quantity=findViewById(R.id.stockquantity);
        demataccId=findViewById(R.id.demataccid);
        Addstockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estockid=stockid.getText().toString();
                String ecompname=compname.getText().toString();
                String eexhchange=exhange.getText().toString();
                String eprice=price.getText().toString();
                String equantity=quantity.getText().toString();
                String edemataccId=demataccId.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference = reference.child(username).child("asset").child("tangible").child("Stock").child(estockid);
                        HashMap mp = new HashMap();
                        mp.put("Stock ID", estockid);
                        mp.put("Stock exchange", eexhchange);
                        mp.put("Stock Price", eprice);
                        mp.put("Company name", ecompname);
                        mp.put("Stock Quantity",equantity);
                        mp.put("Demat Account Id",edemataccId);
                        reference.updateChildren(mp);
//                        Intent i=new Intent(AddStocks.this,ManageAssets.class);
//                        i.putExtra("priceofstock",eprice);
//                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}