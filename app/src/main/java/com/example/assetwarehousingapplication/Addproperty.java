package com.example.assets_warehousing_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Addproperty extends AppCompatActivity {
    StorageReference storeference;
    DatabaseReference reference;
    ActivityResultLauncher<String> mTakepdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property2);

        TextView regNumber = findViewById(R.id.propertyregnumber1);
        TextView price = findViewById(R.id.PropertyPrice1);
        TextView Address = findViewById(R.id.propertyAddress1);
        TextView desc = findViewById(R.id.propertydesc1);
        //TextView reraRegistration = findViewById(R.id.reraRegistration);
        TextView reraRegistrationtxt = findViewById(R.id.reraRegistrationText1);
        //TextView reraName = findViewById(R.id.reraName);
        TextView reraNametext = findViewById(R.id.reraNametext1);
        CheckBox rera = findViewById(R.id.reraCheckbox1);
        //reraRegistration.setVisibility(View.INVISIBLE);
        //reraName.setVisibility(View.INVISIBLE);
        LinearLayout dislinear=findViewById(R.id.dislinear);
        dislinear.setVisibility(View.INVISIBLE);
        //reraNametext.setVisibility(View.INVISIBLE);
        //reraRegistrationtxt.setVisibility(View.INVISIBLE);
        rera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rera.isChecked()) {
//                    reraRegistration.setVisibility(View.VISIBLE);
//                    reraName.setVisibility(View.VISIBLE);
//                    reraNametext.setVisibility(View.VISIBLE);
//                    reraRegistrationtxt.setVisibility(View.VISIBLE);
                    dislinear.setVisibility(View.VISIBLE);


                } else {
//                    reraRegistration.setVisibility(View.INVISIBLE);
//                    reraName.setVisibility(View.INVISIBLE);
//                    reraNametext.setVisibility(View.INVISIBLE);
//                    reraRegistrationtxt.setVisibility(View.INVISIBLE);
                    dislinear.setVisibility(View.INVISIBLE);

                }
            }
        });


        Button addpropertybtn = findViewById(R.id.addpropertybtn1);
        Button uploadbtn = findViewById(R.id.uploadfilebtn1);

        Intent i = getIntent();
        String username = i.getExtras().getString("username");

        mTakepdf=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

            }
        });
        TextView uploadfiletext=findViewById(R.id.uploadproptext);
        uploadfiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Addproperty.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                storeference= FirebaseStorage.getInstance().getReference();
                reference=FirebaseDatabase.getInstance().getReference("users");
            }
        });

        addpropertybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eAddress = Address.getText().toString();
                String eprice = price.getText().toString();
                String edesc = desc.getText().toString();
                String regnumber = regNumber.getText().toString();
                String reraNumber = reraRegistrationtxt.getText().toString();
                String reraname = reraNametext.getText().toString();

                reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference = reference.child(username).child("asset").child("tangible").child("Property").child(regnumber);
                        HashMap mp = new HashMap();
                        mp.put("Property Registration Number", regnumber);
                        mp.put("Property Address", eAddress);
                        mp.put("Property Price", eprice);
                        if (rera.isChecked()) {
                            mp.put("Rera registration number", reraNumber);
                            mp.put("Property name under rera", reraname);
                        }
                        mp.put("Property Description", edesc);
                        reference.updateChildren(mp);
                        Intent i=new Intent(Addproperty.this,ManageAssets.class);
                        i.putExtra("priceofproprty",eprice);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }

            ;
        });

    }

    }


















