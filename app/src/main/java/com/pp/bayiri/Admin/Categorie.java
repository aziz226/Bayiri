package com.pp.bayiri.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.LocationFrag.MaisonFrag;
import com.pp.bayiri.MainActivity;
import com.pp.bayiri.R;

public class Categorie extends AppCompatActivity {

    private Button btnLoc, btnVent;
    private AlertDialog.Builder dialog;
    private String uName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        btnLoc= findViewById(R.id.btn_loc);
        btnVent= findViewById(R.id.btn_vent);

        Bundle in= getIntent().getExtras();
        uName= in.getString("userName");

        dialog= new AlertDialog.Builder(this);

        btnLoc.setOnClickListener(view -> {
            String ville[]={"Ouagadougou","Tenkodogo","Koudougou"};
            dialog.setTitle("Choisissez la ville");
            dialog.setItems(ville, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        PassIntent(ville[0], uName);
                        break;
                    case 1:
                        PassIntent(ville[1], uName);
                        break;
                    case 2:
                        PassIntent(ville[2], uName);
                        break;
                }
            });

            dialog.create().show();
        });

        btnVent.setOnClickListener(view -> {
            String ville[]={"Ouagadougou","Tenkodogo","Koudougou"};
            dialog.setTitle("Choisissez la ville");
            dialog.setItems(ville, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        PassIntent2(ville[0], uName);
                        break;
                    case 1:
                        PassIntent2(ville[1], uName);
                        break;
                    case 2:
                        PassIntent2(ville[2], uName);
                        break;
                }
            });

            dialog.create().show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void PassIntent(String vil, String un) {
        Intent intent= new Intent(Categorie.this, AddLocatHouse.class);
        intent.putExtra("v", vil);
        intent.putExtra("un", un);
        startActivity(intent);
    }

    private void PassIntent2(String vil, String un) {
        Intent intent= new Intent(Categorie.this, AjouterVente.class);
         intent.putExtra("v", vil);
        intent.putExtra("un", un);
        startActivity(intent);
    }


}