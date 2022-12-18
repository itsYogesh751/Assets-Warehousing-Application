package com.example.assets_warehousing_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddAssets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets);
        Intent i=getIntent();
        String username=i.getExtras().getString("username");
        AutoCompleteTextView spintan=findViewById(R.id.spintanauto);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.tanassetName,R.layout.dropdowntan);

        adapter.setDropDownViewResource(R.layout.dropdowntan);
        spintan.setAdapter(adapter);

        spintan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("Property"))
                {
                    Intent intent1=new Intent(getApplicationContext(),Addproperty.class);
                    intent1.putExtra("username",username);
                    Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                    startActivity(intent1);
                }
                else if(item.equals("Gold"))
                {
                    Intent intent1=new Intent(getApplicationContext(),AddGold.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
                else if(item.equals("Gem"))
                {
                    Intent intent1=new Intent(getApplicationContext(),AddGem.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
                else if(item.equals("Bank"))
                {
                    Intent intent1=new Intent(getApplicationContext(),AddBankAccount.class);
                    intent1.putExtra("username",username);
                    Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                    startActivity(intent1);
                }
            }
        });
        AutoCompleteTextView spinintan=findViewById(R.id.spinintanauto);
        ArrayAdapter<CharSequence> inadapter=ArrayAdapter.createFromResource(this,
                R.array.intanassetName,R.layout.dropdowntan);
        //  db=new DatabaseHelper(getApplicationContext());
        adapter.setDropDownViewResource(R.layout.dropdowntan);
        spinintan.setAdapter(inadapter);
        spinintan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("Trademark"))
                {
                    Intent intent1=new Intent(getApplicationContext(),AddTrademark.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
                else if(item.equals("Copyright"))
                {
                    Intent intent1=new Intent(getApplicationContext(),AddCopyright.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
                else
                {
                    Intent intent1=new Intent(getApplicationContext(),AddPatent.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                }
            }
        });
    }
}