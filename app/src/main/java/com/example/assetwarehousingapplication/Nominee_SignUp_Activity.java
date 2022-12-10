package com.example.assetwarehousingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nominee_SignUp_Activity extends AppCompatActivity {

    EditText Nom_signupName, Nom_signupEmail, Nom_signupUsername, Nom_signupPassword;
    TextView Nom_loginRedirectText;
    Button Nom_signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_sign_up);

        Nom_signupName = findViewById(R.id.nominee_signup_name);
        Nom_signupEmail = findViewById(R.id.nominee_signup_email);
        Nom_signupUsername = findViewById(R.id.nominee_signup_username);
        Nom_signupPassword = findViewById(R.id.nominee_signup_password);
        Nom_signupButton = findViewById(R.id.nominee_signup_button);
        Nom_loginRedirectText = findViewById(R.id.nominee_loginRedirectText);

        Nom_signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("nominee");

                String name = Nom_signupName.getText().toString();
                String email = Nom_signupEmail.getText().toString();
                String username = Nom_signupUsername.getText().toString();
                String password = Nom_signupPassword.getText().toString();

                HelperClass helperClass = new HelperClass(name, email, username, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(Nominee_SignUp_Activity.this, "You have Signed Up successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Nominee_SignUp_Activity.this, Nominee_Login_Activity.class);
                startActivity(intent);
            }
        });

        Nom_loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nominee_SignUp_Activity.this, Nominee_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}