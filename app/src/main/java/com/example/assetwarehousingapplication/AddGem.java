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

public class AddGem extends AppCompatActivity {
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gem2);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        TextView price=findViewById(R.id.gemprice2);
        TextView weight=findViewById(R.id.gemweight2);
        TextView desc=findViewById(R.id.gemdesc2);
        TextView gem_name=findViewById(R.id.gem_name2);
        TextView gembanknametext=findViewById(R.id.gembanknametext2);
        TextView gembankname=findViewById(R.id.gembankname2);
        TextView gemifsccodetext=findViewById(R.id.gemifsccodetext2);
        TextView gembankifsccode=findViewById(R.id.gembankifsccode2);
        TextView gemlockernumber=findViewById(R.id.gemlockernumber2);
        TextView gemlockernumbertext=findViewById(R.id.gemlockernumbertext2);
        CheckBox locker=findViewById(R.id.gemcheckbox2);
        gembankifsccode.setVisibility(View.INVISIBLE);
        gembankname.setVisibility(View.INVISIBLE);
        gembanknametext.setVisibility(View.INVISIBLE);
        gemifsccodetext.setVisibility(View.INVISIBLE);
        gemlockernumber.setVisibility(View.INVISIBLE);
        gemlockernumbertext.setVisibility(View.INVISIBLE);
        locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locker.isChecked())
                {
                    gembankifsccode.setVisibility(View.VISIBLE);
                    gembankname.setVisibility(View.VISIBLE);
                    gembanknametext.setVisibility(View.VISIBLE);
                    gemifsccodetext.setVisibility(View.VISIBLE);
                    gemlockernumber.setVisibility(View.VISIBLE);
                    gemlockernumbertext.setVisibility(View.VISIBLE);
                }
                else{
                    gembankifsccode.setVisibility(View.INVISIBLE);
                    gembankname.setVisibility(View.INVISIBLE);
                    gembanknametext.setVisibility(View.INVISIBLE);
                    gemifsccodetext.setVisibility(View.INVISIBLE);
                    gemlockernumber.setVisibility(View.INVISIBLE);
                    gemlockernumbertext.setVisibility(View.INVISIBLE);
                }
            }
        });
        Button addgembtn=findViewById(R.id.addgembtn2);
        addgembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("users");
                // Toast.makeText(getApplicationContext(),reference.toString(),Toast.LENGTH_LONG).show();
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference=reference.child(username).child("asset").child("tangible").child("Gem").child(gem_name.getText().toString());
                        HashMap mp=new HashMap();
                        mp.put("gem unique id",gem_name.getText().toString());
                        mp.put("gem price",Integer.parseInt(price.getText().toString()));
                        mp.put("gem weight",weight.getText().toString());

                        if(locker.isChecked())
                        {
                            mp.put("Bank name",gembankname.getText().toString());
                            mp.put("bank ifsc code",gembankifsccode.getText().toString());
                            mp.put("gem locker number",gemlockernumber.getText().toString());
                        }
                        mp.put("gem description",desc.getText().toString());
                        reference.updateChildren(mp);
                        Intent i=new Intent(AddGem.this,ManageAssets.class);
                        i.putExtra("priceofgem",price.getText().toString());
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