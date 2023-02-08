package com.pp.bayiri.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pp.bayiri.AdminFragment.AdAuberges;
import com.pp.bayiri.AdminFragment.AdHotel;
import com.pp.bayiri.AdminFragment.AdMaison;
import com.pp.bayiri.AdminFragment.AdResidences;
import com.pp.bayiri.R;

public class AddLocatHouse extends AppCompatActivity {

    private String ville, uName;
    private TextView idVille;
    private TextView maisn, maisnClik, resid, residClik, hotel, hotelClik, Auberg, AubergClik;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locat_house);

        Intent intent= getIntent();
        ville= intent.getStringExtra("v");
        Bundle in= getIntent().getExtras();
        uName= in.getString("un");



        idVille= findViewById(R.id.add_id_ville);
        idVille.setText(ville);

        maisn= findViewById(R.id.id_add_h);
        maisnClik= findViewById(R.id.id_add_h_clik);
        resid= findViewById(R.id.id_add_resid);
        residClik= findViewById(R.id.id_add_resid_clik);
        hotel= findViewById(R.id.id_add_hotel);
        hotelClik= findViewById(R.id.id_add_hotel_clik);
        Auberg= findViewById(R.id.id_add_aub);
        AubergClik= findViewById(R.id.id_add_aub_clik);

        Bundle bundle= new Bundle();
        Bundle b1= new Bundle();
        b1.putString("un", uName);
        b1.putString("v", ville);

        AdMaison maison= new AdMaison();
        maison.setArguments(b1);
        maison.setArguments(b1);
        ReplaceFrag(maison);

        maisn.setOnClickListener(view -> {
            maisn.setVisibility(View.GONE);
            maisnClik.setVisibility(View.VISIBLE);
            resid.setVisibility(View.VISIBLE);
            residClik.setVisibility(View.GONE);
            hotel.setVisibility(View.VISIBLE);
            hotelClik.setVisibility(View.GONE);
            Auberg.setVisibility(View.VISIBLE);
            AubergClik.setVisibility(View.GONE);

            AdMaison adMaison= new AdMaison();
            adMaison.setArguments(b1);
            adMaison.setArguments(b1);
            ReplaceFrag(adMaison);
        });

        resid.setOnClickListener(view -> {
            maisn.setVisibility(View.VISIBLE);
            maisnClik.setVisibility(View.GONE);
            resid.setVisibility(View.GONE);
            residClik.setVisibility(View.VISIBLE);
            hotel.setVisibility(View.VISIBLE);
            hotelClik.setVisibility(View.GONE);
            Auberg.setVisibility(View.VISIBLE);
            AubergClik.setVisibility(View.GONE);


            AdResidences adResidences= new AdResidences();
            adResidences.setArguments(b1);
            adResidences.setArguments(b1);
            ReplaceFrag(adResidences);
        });

        hotel.setOnClickListener(view -> {
            maisn.setVisibility(View.VISIBLE);
            maisnClik.setVisibility(View.GONE);
            resid.setVisibility(View.VISIBLE);
            residClik.setVisibility(View.GONE);
            hotel.setVisibility(View.GONE);
            hotelClik.setVisibility(View.VISIBLE);
            Auberg.setVisibility(View.VISIBLE);
            AubergClik.setVisibility(View.GONE);

            AdHotel adHotel= new AdHotel();
            adHotel.setArguments(b1);
            adHotel.setArguments(b1);
            ReplaceFrag(adHotel);
        });

        Auberg.setOnClickListener(view -> {
            maisn.setVisibility(View.VISIBLE);
            maisnClik.setVisibility(View.GONE);
            resid.setVisibility(View.VISIBLE);
            residClik.setVisibility(View.GONE);
            hotel.setVisibility(View.VISIBLE);
            hotelClik.setVisibility(View.GONE);
            Auberg.setVisibility(View.GONE);
            AubergClik.setVisibility(View.VISIBLE);

            AdAuberges adAuberges= new AdAuberges();
            adAuberges.setArguments(b1);
            adAuberges.setArguments(b1);
            ReplaceFrag(adAuberges);
        });

    }

    private void ReplaceFrag(Fragment fragment){
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.id_frame, fragment);
        transaction.commit();
    }
}