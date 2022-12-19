package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddGold extends AppCompatActivity {
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gold2);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        TextView price=findViewById(R.id.goldprice2);
        TextView weight=findViewById(R.id.goldweight2);
        TextView desc=findViewById(R.id.golddesc2);
        TextView goldtype=findViewById(R.id.typeofgold2);
        TextView goldname=findViewById(R.id.goldname2);
        TextView goldbanknametext=findViewById(R.id.goldbanknametext2);
        TextView goldbankname=findViewById(R.id.goldbankname2);
        TextView goldifsccodetext=findViewById(R.id.goldifsccodetext2);
        TextView goldbankifsccode=findViewById(R.id.goldbankifsccode2);
        TextView goldlockernumber=findViewById(R.id.goldlockernumber2);
        TextView goldlockernumbertext=findViewById(R.id.goldlockernumbertext2);
        CheckBox locker=findViewById(R.id.goldcheckbox2);
        goldbankifsccode.setVisibility(View.INVISIBLE);
        goldbankname.setVisibility(View.INVISIBLE);
        goldbanknametext.setVisibility(View.INVISIBLE);
        goldifsccodetext.setVisibility(View.INVISIBLE);
        goldlockernumber.setVisibility(View.INVISIBLE);
        goldlockernumbertext.setVisibility(View.INVISIBLE);
        locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locker.isChecked())
                {
                    goldbankifsccode.setVisibility(View.VISIBLE);
                    goldbankname.setVisibility(View.VISIBLE);
                    goldbanknametext.setVisibility(View.VISIBLE);
                    goldifsccodetext.setVisibility(View.VISIBLE);
                    goldlockernumber.setVisibility(View.VISIBLE);
                    goldlockernumbertext.setVisibility(View.VISIBLE);
                }
                else{
                    goldbankifsccode.setVisibility(View.INVISIBLE);
                    goldbankname.setVisibility(View.INVISIBLE);
                    goldbanknametext.setVisibility(View.INVISIBLE);
                    goldifsccodetext.setVisibility(View.INVISIBLE);
                    goldlockernumber.setVisibility(View.INVISIBLE);
                    goldlockernumbertext.setVisibility(View.INVISIBLE);
                }
            }
        });

        Button addgoldbtn=findViewById(R.id.addgoldbtn2);
        addgoldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("users");
                // Toast.makeText(getApplicationContext(),reference.toString(),Toast.LENGTH_LONG).show();
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference=reference.child(username).child("asset").child("tangible").child("Gold").child(goldname.getText().toString());
                        HashMap mp=new HashMap();
                        mp.put("gold_name",goldname.getText().toString());
                        mp.put("gold price",Integer.parseInt(price.getText().toString()));
                        mp.put("gold weight",weight.getText().toString());
                        mp.put("Type of gold investment",goldtype.getText().toString());

                        if(locker.isChecked())
                        {
                            mp.put("Bank name",goldbankname.getText().toString());
                            mp.put("bank ifsc code",goldbankifsccode.getText().toString());
                            mp.put("gold locker number",goldlockernumber.getText().toString());
                        }
                        mp.put("gold description",desc.getText().toString());
                        reference.updateChildren(mp);
                        Intent i=new Intent(AddGold.this,ManageAssets.class);
                        i.putExtra("priceofgold",price.getText().toString());
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

    }
}