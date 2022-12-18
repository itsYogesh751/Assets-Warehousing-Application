package com.example.assets_warehousing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class userProfile2 extends AppCompatActivity {
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);


        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        ImageView editpassword=findViewById(R.id.editpasswordbtn);
        TextView realnametext=findViewById(R.id.userprofilename);
        TextView usernameprofile=findViewById(R.id.usernameprofile);
        TextView useremail=findViewById(R.id.userEmail);
        TextView password=findViewById(R.id.userpassword);



        reference= FirebaseDatabase.getInstance().getReference("users");
        Query ch=reference.orderByChild("username").equalTo(username);

        ch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                realnametext.setText(snapshot.child(username).child("name").getValue(String.class));
                usernameprofile.setText(snapshot.child(username).child("username").getValue(String.class));
                useremail.setText(snapshot.child(username).child("email").getValue(String.class));
                password.setText(snapshot.child(username).child("password").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(userProfile2.this,editnamepopup.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

    }
}