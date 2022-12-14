package com.example.assetwarehousingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminDashBoard extends AppCompatActivity {

    String UsrName = "ath123";
    TextView TotAssets, LiqAssets, AdUsername;
    Button Report,GenLiq,Logout;
    ImageView editProfile , ViewLiq , ManageAssets , ManageNominee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        AdUsername = findViewById(R.id.admin_username);
        LiqAssets = findViewById(R.id.user_total_liquid_assets);
        TotAssets = findViewById(R.id.user_total_assets);
        ManageAssets = findViewById(R.id.user_manage_assets);
        editProfile = findViewById(R.id.user_edit_profile);
        ViewLiq = findViewById(R.id.user_view_liquid_assets);
        Report = findViewById(R.id.user_generate_annual_report);
        GenLiq = findViewById(R.id.user_generate_liquidation);
        Logout = findViewById(R.id.user_logout);
        ManageNominee = findViewById(R.id.user_manage_nominee);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashBoard.this,AdminUserProfile.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashBoard.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        ManageAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashBoard.this, ManageAssets.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(UsrName);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.child("username").getValue().toString();
                AdUsername.setText(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ImageView fetchSmsbtn=findViewById(R.id.button_fetch_last_transaction);
        fetchSmsbtn.setClickable(true);
        fetchSmsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AdminDashBoard.this,
                        new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

                cursor.moveToFirst();

                boolean check=true;
                while(check!=false)
                {
                    if(cursor.getString(12).contains("ICICI") || cursor.getString(12).contains("HDFC"))
                    {
                        if(cursor.getString(12).contains("credited") || cursor.getString(12).contains("debited")) {
                            check = false;
                            break;
                        }
                    }
                    cursor.moveToNext();
                }
                String sms=cursor.getString(12);
                int index=0;
                for(int i=0;i<sms.length();i++)
                {
                    if(sms.charAt(i)=='R' && sms.charAt(i+1)=='s' )
                    {
                        index=i+2;
                        break;
                    }
                }
                StringBuilder value= new StringBuilder();
                for(int i=index;sms.charAt(i)!='.';i++)
                {
                    value.append(sms.charAt(i));
                }
                StringBuilder val=new StringBuilder();
                val.append("Last transaction is : ");
                if(sms.contains("debited"))
                {
                    val.append("(-)");
                }
                else{
                    val.append("(+)");
                }
                val.append(value);
                TextView displaysms=findViewById(R.id.last_transaction);
                displaysms.setText(val);
            }
        });

        HashMap<String,Object> mp;

        ManageNominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,NomineePopUp.class));

            }
        });

        // AdUsername.setText(""+UsrName);
    }
}
