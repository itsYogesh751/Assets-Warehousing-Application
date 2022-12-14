package com.example.assetwarehousingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageAssets extends AppCompatActivity {

    TextView TotalAsts, TotalTanAsts , TotalIntanAsts , BankAsts, StockAsts, GoldAsts, GemAsts , PropertyAsts , InsuranceAsts, MutualFundAsts, TrademrkAsts , CpyAsts , PtntAsts;
    ImageView VBankAsts, VStockAsts, VGoldAsts, VGemAsts , VPropertyAsts , VInsuranceAsts, VMutualFundAsts, VTrademrkAsts , VCpyAsts , VPtntAsts;
    ImageView ABankAsts, AStockAsts, AGoldAsts, AGemAsts , APropertyAsts , AInsuranceAsts, AMutualFundAsts, ATrademrkAsts , ACpyAsts , APtntAsts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_assets);

        TotalAsts = findViewById(R.id.admin_user_total_assets);
        TotalTanAsts = findViewById(R.id.user_tangible_assets);
        TotalIntanAsts = findViewById(R.id.user_total_intangible_assets);
        BankAsts = findViewById(R.id.user_total_bank_assets);
        StockAsts = findViewById(R.id.user_total_stock_assets);
        GoldAsts = findViewById(R.id.user_total_gold_assets);
        GemAsts  = findViewById(R.id.user_total_gem_assets);
        PropertyAsts  = findViewById(R.id.user_total_property_assets);
        InsuranceAsts = findViewById(R.id.user_total_insurance_assets);
        MutualFundAsts = findViewById(R.id.user_total_mutual_fund_assets);
        TrademrkAsts = findViewById(R.id.user_total_trademark_assets);
        CpyAsts = findViewById(R.id.user_total_copyright_assets);
        PtntAsts = findViewById(R.id.user_total_patent_assets);

        VBankAsts = findViewById(R.id.user_view_bank_assets);
        VStockAsts = findViewById(R.id.user_view_stock_assets);
        VGoldAsts = findViewById(R.id.user_view_gold_assets);
        VGemAsts = findViewById(R.id.user_view_gem_assets);
        VPropertyAsts = findViewById(R.id.user_view_property_assets);
        VInsuranceAsts = findViewById(R.id.user_view_insurance_assets);
        VMutualFundAsts = findViewById(R.id.user_view_mutual_fund_assets);
        VTrademrkAsts = findViewById(R.id.user_view_trademark_assets);
        VCpyAsts = findViewById(R.id.user_view_copyright_assets);
        VPtntAsts = findViewById(R.id.user_view_patent_assets);
        ABankAsts = findViewById(R.id.user_add_bank_assets);
        AStockAsts =  findViewById(R.id.user_add_stock_assets);
        AGoldAsts = findViewById(R.id.user_add_gold_assets);
        AGemAsts =  findViewById(R.id.user_add_gem_assets);
        APropertyAsts =  findViewById(R.id.user_add_property_assets);
        AInsuranceAsts = findViewById(R.id.user_add_insurance_assets);
        AMutualFundAsts = findViewById(R.id.user_add_mutual_fund_assets);
        ATrademrkAsts = findViewById(R.id.user_add_trademark_assets);
        ACpyAsts =  findViewById(R.id.user_add_copyright_assets);
        APtntAsts =findViewById(R.id.user_add_patent_assets);









    }
}