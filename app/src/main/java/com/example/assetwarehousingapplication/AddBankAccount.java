package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddBankAccount extends AppCompatActivity {
    DatabaseReference reference;
    TextView acc_number,acc_name,acc_type,ifsc,balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");

        Button addBankAccount=findViewById(R.id.addbankaccountbtn);
        addBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        reference = FirebaseDatabase.getInstance().getReference("users");
       // Toast.makeText(getApplicationContext(),reference.toString(),Toast.LENGTH_LONG).show();
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    acc_number=findViewById(R.id.bankaccountnumber);
                    acc_name=findViewById(R.id.bankname);
                    acc_type=findViewById(R.id.bankaccounttype);
                    ifsc=findViewById(R.id.ifsccode);
                    balance=findViewById(R.id.bankbalance);
                    StringBuilder s=new StringBuilder();
                    s.append(acc_number.getText().toString());
                    s.reverse();
                   // HelperClass h=new HelperClass("sbi","2373487","SBIN1000",374,"saving");
                    String acc=acc_number.getText().toString();
                    int n=acc.length();
                    StringBuilder d=new StringBuilder();
                    d.append(acc.charAt(n-4)).append(acc.charAt(n-3)).append(acc.charAt(n-2)).append(acc.charAt(n-1));
                    String accshort= String.valueOf(d);
                    reference=reference.child(username).child("asset").child("tangible").child("bank_assets").child(accshort);
                    HashMap mp=new HashMap();
                    mp.put("bank_name",acc_name.getText().toString());
                    mp.put("account_number",acc_number.getText().toString());
                    mp.put("account type",acc_type.getText().toString());
                    mp.put("Ifsc code",ifsc.getText().toString());
                 //   mp.put("account_number_reverse",String.valueOf(s));
                    mp.put("accshort",accshort);
                    mp.put("balance",Integer.parseInt(balance.getText().toString()));
                    reference.updateChildren(mp);
                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(AddBankAccount.this,ManageAssets.class);
                    i.putExtra("balance",balance.getText().toString());
                    startActivity(i);
                   // Toast.makeText(getApplicationContext(),"done"+h.account_number+reference,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"not done",Toast.LENGTH_LONG).show();
            }
            });
            }
        });
       // reference.getDatabase();
    }
}