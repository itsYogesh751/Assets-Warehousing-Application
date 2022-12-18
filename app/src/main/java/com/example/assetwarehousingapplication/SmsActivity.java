package com.example.assets_warehousing_app;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SmsActivity extends Activity implements OnItemClickListener {
    DatabaseReference reference;
    private static SmsActivity inst;
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public static SmsActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(SmsActivity.this,
                new String[]{Manifest.permission.RECEIVE_SMS}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(SmsActivity.this,
                new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
        refreshSmsInbox();
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        ActivityCompat.requestPermissions(SmsActivity.this,
                new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        Cursor smsInboxCursor= contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference=reference.child("username").child("asset").child("tangible").child("bank_assets");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
           // arrayAdapter.add(str);
            System.out.println(str);
            boolean check=false;
            int x=0;
            if(str.contains("debited") || str.contains("a/c X")|| str.contains("credited"))
            {
                if(str.contains("debited"))
                {
                    x=-1;
                }
                else{
                    x=1;
                }
                check=true;
            }
            String value="";
            if(check==true)
            {
                for(int i=4;i<str.length();i++)
                {
                    if(str.charAt(i)=='X' && str.charAt(i-4)=='a'|| str.charAt(i-4)=='A' && str.charAt(i-3)=='/' && str.charAt(i-2)=='c')
                    {
                        for(int l=i+1;str.charAt(l)!=' ';l++)
                        {
                            if(Character.isDigit(str.charAt(l))) {
                                value += str.charAt(l);
                            }
                        }
                        break;
                    }
                }
                int val=Integer.parseInt(value);

                    Query q=reference.orderByChild("account_number").endAt(val);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                String x=String.valueOf(snapshot.child("account_number").child("balance").getValue(Integer.class));
                                System.out.println(x);
                                // Toast.makeText(getApplicationContext(),String.valueOf(snapshot.child("account_number").child("balance").getValue(Integer.class)),Toast.LENGTH_LONG).show();
                                int balA=snapshot.child("account_number").child("balance").getValue(Integer.class);
                                int bal=balA+val;
                                HashMap mp=new HashMap();
                                mp.put("balance",bal);
                                reference.child("account_number").setValue(mp);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}