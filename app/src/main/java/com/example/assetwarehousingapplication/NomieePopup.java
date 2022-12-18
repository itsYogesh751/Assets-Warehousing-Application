package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NomieePopup extends AppCompatActivity {

    TextView Nominee_name;
    Button AddNominee;
    Map<String, Object> mp;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomiee_popup);

        Nominee_name = findViewById(R.id.popup_nominee_username);
        AddNominee = findViewById(R.id.popup_add_nominee_button);
        Intent i=getIntent();
        String username=i.getExtras().getString("username");

        AddNominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String N_username = Nominee_name.getText().toString().trim();
                reference = FirebaseDatabase.getInstance().getReference("nominee");
                Query ch=reference.orderByChild("username").equalTo(N_username);

                DatabaseReference referenceuser = FirebaseDatabase.getInstance().getReference("users");
                Query chuser=referenceuser.orderByChild("username").equalTo(username);

                chuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mp = (Map<String, Object>) snapshot.child(username).child("asset").getValue();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                ch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println(snapshot.child(N_username));
                        reference=reference.child(N_username).child("asset");
                        reference.updateChildren(mp);

                        if(snapshot.exists()){
                            Nominee_name.setError(null);
                            Toast.makeText(getApplicationContext(),"Nominee Added",Toast.LENGTH_SHORT).show();
                        } else{
                            Nominee_name.setError("Nominee does not exist");
                            Nominee_name.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}