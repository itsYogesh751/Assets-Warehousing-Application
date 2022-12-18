package com.example.assets_warehousing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ManageAssets extends AppCompatActivity {

    TextView TotalAsts, TotalTanAsts , TotalIntanAsts , BankAsts, StockAsts, GoldAsts, GemAsts , PropertyAsts , InsuranceAsts, MutualFundAsts, TrademrkAsts , CpyAsts , PtntAsts;
    ImageView VBankAsts, VStockAsts, VGoldAsts, VGemAsts , VPropertyAsts , VInsuranceAsts, VMutualFundAsts, VTrademrkAsts , VCpyAsts , VPtntAsts;
    ImageView ABankAsts, AStockAsts, AGoldAsts, AGemAsts , APropertyAsts , AInsuranceAsts, AMutualFundAsts, ATrademrkAsts , ACpyAsts , APtntAsts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_assets);

        Intent i=getIntent();
        String username=i.getExtras().getString("username");

        String balance=i.getExtras().getString("balance");
        TextView total_balance=findViewById(R.id.user_total_bank_assets);
        total_balance.setText(balance);
        String pricegold=i.getExtras().getString("priceogold");
        TextView total_gold_price=findViewById(R.id.user_total_gold_assets);
        total_gold_price.setText(pricegold);
        String pricegem=i.getExtras().getString("priceofgem");
        TextView total_gem_price=findViewById(R.id.user_total_gem_assets);
        total_gem_price.setText(pricegem);

        ABankAsts=findViewById(R.id.user_add_bank_assets);
        APtntAsts=findViewById(R.id.user_add_patent_assets);
        AStockAsts=findViewById(R.id.user_add_stock_assets);
        AGemAsts=findViewById(R.id.user_add_gem_assets);
        AGoldAsts=findViewById(R.id.user_add_gold_assets);
        APropertyAsts=findViewById(R.id.user_add_property_assets);
        ATrademrkAsts=findViewById(R.id.user_add_trademark_assets);
        ACpyAsts=findViewById(R.id.user_add_copyright_assets);
        ABankAsts.setClickable(true);
        APtntAsts.setClickable(true);
        ACpyAsts.setClickable(true);
        ATrademrkAsts.setClickable(true);
        APropertyAsts.setClickable(true);
        AGoldAsts.setClickable(true);
        AGemAsts.setClickable(true);
        AStockAsts.setClickable(true);
        ABankAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddBankAccount.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        AStockAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddStocks.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        AGoldAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddGold.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        AGemAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddGem.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        APropertyAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,Addproperty.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        ATrademrkAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddTrademark.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        ACpyAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddCopyright.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        APtntAsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageAssets.this,AddPatent.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });



    }
}