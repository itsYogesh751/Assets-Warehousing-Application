package com.example.assets_warehousing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// User & Nominee DashBoard;
// User -> Add Assets,View Assets(CURD Ops), Add Nominee , Allocate SOP's , Value Generation for liquidation
//            , Document Upload(PDFs) ;
// Nominee -> View Allocated Assets , Contact SOPs;

public class MainActivity extends AppCompatActivity {

    Button AdminloginButton , NomineeloginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdminloginButton = findViewById(R.id.admin);
        NomineeloginButton = findViewById(R.id.nominee);

        AdminloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        NomineeloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Nominee_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}