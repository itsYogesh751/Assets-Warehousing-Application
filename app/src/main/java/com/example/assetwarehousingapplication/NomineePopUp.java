package com.example.assetwarehousingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class NomineePopUp extends AppCompatActivity {

    TextView Nominee_name;
    Button AddNominee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_pop_up);

        Nominee_name = findViewById(R.id.popup_nominee_username);
        AddNominee = findViewById(R.id.popup_add_nominee_button);

        AddNominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String N_username = Nominee_name.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("nominee");
                Query ch=reference.orderByChild("username").equalTo(N_username);

                ch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println(snapshot);

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