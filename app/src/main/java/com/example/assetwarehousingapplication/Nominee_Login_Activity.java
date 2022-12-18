package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Nominee_Login_Activity extends AppCompatActivity {

    EditText Nom_loginUsername, Nom_loginPassword;
    Button Nom_loginButton;
    TextView Nom_signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_login);

        Nom_loginUsername = findViewById(R.id.nomiee_login_username);
        Nom_loginPassword = findViewById(R.id.nominee_login_password);
        Nom_signupRedirectText = findViewById(R.id.nominee_signupRedirectText);
        Nom_loginButton = findViewById(R.id.nominee_login_button);

        Nom_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Nom_validateUsername() | !Nom_validatePassword()){

                } else {
                    checkUser();
                }
            }
        });

        Nom_signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nominee_Login_Activity.this, Nominee_SignUp_Activity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean Nom_validateUsername(){
        String val = Nom_loginUsername.getText().toString();
        if (val.isEmpty()){
            Nom_loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            Nom_loginUsername.setError(null);
            return true;
        }
    }

    public Boolean Nom_validatePassword(){
        String val = Nom_loginPassword.getText().toString();
        if (val.isEmpty()){
            Nom_loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            Nom_loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String Nom_Username = Nom_loginUsername.getText().toString().trim();
        String Nom_Password = Nom_loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("nominee");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(Nom_Username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Nom_loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(Nom_Username).child("password").getValue(String.class);

                    if (passwordFromDB.equals(Nom_Password)){
                        Nom_loginUsername.setError(null);

                        //Pass the data using intent

                        String nameFromDB = snapshot.child(Nom_Username).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(Nom_Username).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(Nom_Username).child("username").getValue(String.class);

                        Intent intent = new Intent(Nominee_Login_Activity.this, Nominee_DashBoard.class); // Nominee DashBoard.class;

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        Nom_loginPassword.setError("Invalid Credentials");
                        Nom_loginPassword.requestFocus();
                    }
                } else {
                    Nom_loginUsername.setError("User does not exist");
                    Nom_loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}