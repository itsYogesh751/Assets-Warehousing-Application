package com.example.assets_warehousing_app;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    DatabaseReference reference;
    int x;
    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        System.out.println("hello");
        username k=new username();
        String user=k.getUser();
        System.out.println(user);
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            System.out.println(smsMessageStr);
            //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
            String str=smsMessageStr;
            //this will update the UI with message
            //SmsActivity inst = SmsActivity.instance();
            //inst.updateList(smsMessageStr);
            reference = FirebaseDatabase.getInstance().getReference("users");
            boolean check=false;
            x=0;
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
            String Amount="";
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
                int rsindex=0;

                for(int i=2;i<str.length();i++)
                {
                    if(str.charAt(i-2)=='R' && str.charAt(i-1)=='s' && str.charAt(i)=='.')
                    {
                        rsindex=i+1;
                    }
                }
                boolean f=false;
                for(int i=rsindex+1;i<str.length();i++)
                {
                    if(Character.isDigit(str.charAt(i)))
                    {
                        f=true;
                        Amount+=str.charAt(i);
                    }
                    else{
                        break;
                    }
                }
                System.out.println("ammount"+Amount);
                int amt=Integer.parseInt(Amount);
                reference=reference.child("yog1").child("asset").child("tangible").child("bank_assets");
                String y=String.valueOf(val);
                StringBuilder l=new StringBuilder();
                l.append(y).reverse();
                Query q=reference.orderByChild("accshort").equalTo(String.valueOf(val));
                System.out.println(val+reference.toString()+val+"\uf8ff");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            System.out.println("cac"+snapshot.child(String.valueOf(val)));
                            // Toast.makeText(getApplicationContext(),String.valueOf(snapshot.child("account_number").child("balance").getValue(Integer.class)),Toast.LENGTH_LONG).show();
                            Integer balA=snapshot.child(String.valueOf(val)).child("balance").getValue(Integer.class);
                            System.out.println(balA);
                           // int bala=Integer.parseInt(balA);
                            int bal;
                            if(x==-1)
                            {
                                bal=balA-amt;
                            }
                            else {
                                bal = balA + amt;
                            }
                            HashMap mp=new HashMap();
                            mp.put("balance",bal);
                            reference.child(String.valueOf(val)).updateChildren(mp);
                        }
                        else{
                            System.out.println("not working");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }
        }
    }