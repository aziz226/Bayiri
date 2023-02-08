package com.pp.bayiri.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pp.bayiri.AdminFragment.AdVenteMaison;
import com.pp.bayiri.AdminFragment.AdVenteTerrain;
import com.pp.bayiri.R;

public class AjouterVente extends AppCompatActivity {

    private TextView maisn, maisnClik, resid, residClik;
    private String ville, uName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_vente);

        Bundle b= getIntent().getExtras();
        uName= b.getString("un");
        ville= b.getString("v");

        maisn= findViewById(R.id.id_add_vent_h);
        maisnClik= findViewById(R.id.id_add_vent_h_clik);
        resid= findViewById(R.id.id_add_resid_vent);
        residClik= findViewById(R.id.id_add_resid_vent_clik);



        Bundle bundle= new Bundle();
        bundle.putString("un", uName);
        bundle.putString("v", ville);

        AdVenteMaison maison= new AdVenteMaison();
        maison.setArguments(bundle);
        maison.setArguments(bundle);
        ReplaceFrag(maison);

        maisn.setOnClickListener(view -> {
            maisn.setVisibility(View.GONE);
            maisnClik.setVisibility(View.VISIBLE);
            resid.setVisibility(View.VISIBLE);
            residClik.setVisibility(View.GONE);


            AdVenteMaison adMaison= new AdVenteMaison();
            adMaison.setArguments(bundle);
            adMaison.setArguments(bundle);
            ReplaceFrag(adMaison);
        });

        resid.setOnClickListener(view -> {
            maisn.setVisibility(View.VISIBLE);
            maisnClik.setVisibility(View.GONE);
            resid.setVisibility(View.GONE);
            residClik.setVisibility(View.VISIBLE);



            AdVenteTerrain adResidences= new AdVenteTerrain();
            adResidences.setArguments(bundle);
            adResidences.setArguments(bundle);
            ReplaceFrag(adResidences);
        });
    }

    private void ReplaceFrag(Fragment fragment){
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.id_frame, fragment);
        transaction.commit();
    }
    public static Bundle CreateBudlle(String un, String v){
        Bundle buddle= new Bundle();
        buddle.putString("un", un);
        buddle.putString("v", v);

        return buddle;
    }
}