package com.example.assets_warehousing_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class editnamepopup extends AppCompatActivity {
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editpasswordpopup);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        Button editnamecancel=findViewById(R.id.editpasswordcancelbtn);
        editnamecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(editnamepopup.this,userProfile2.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

        Button editnameok=findViewById(R.id.editpasswordokbtn);
        editnameok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference= FirebaseDatabase.getInstance().getReference("users");
                Query ch=reference.orderByChild("username").equalTo(username);
                ch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TextView newpassword=findViewById(R.id.editpasswordtext);

                        HashMap mp=new HashMap();
                        mp.put("password",newpassword.getText().toString());
                        reference.child(username).updateChildren(mp);
                        Intent i=new Intent(editnamepopup.this,userProfile2.class);
                        i.putExtra("username",username);
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
