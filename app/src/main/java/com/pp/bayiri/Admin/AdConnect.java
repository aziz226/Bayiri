package com.pp.bayiri.Admin;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;

public class AdConnect extends AppCompatActivity {

    private TextInputEditText adUsername, co2;// co3;
    private Button btn;
   // private TextView btnOpen;

    private String ad_username, cod1, cod2;
    private DatabaseReference userRef;
    private ProgressDialog loadingBar;
    private String nom;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_connect);

        adUsername= findViewById(R.id.ad_uname);
        co2= findViewById(R.id.admi_co2);
       // co3= findViewById(R.id.admi_co3);
        btn= findViewById(R.id.open_ad_btn);
       // btnOpen= findViewById(R.id.btn_open);

        loadingBar= new ProgressDialog(this);

        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User);

     //   ad_username= adUsername.getText().toString();

        btn.setOnClickListener(view -> {

            /*Intent intent= new Intent(AdConnect.this, Categorie.class);
            intent.putExtra("userName", "Admin01");
            startActivity(intent); */

                if (TextUtils.isEmpty(adUsername.getText().toString().trim())){
                    makeText(AdConnect.this, "Veuillez entrer votre nom d'utilisateur...", LENGTH_SHORT).show();
                    adUsername.setError("Veuillez enter votre nom d'utilisateur");
                }else if (TextUtils.isEmpty(co2.getText().toString().trim())){
                    makeText(AdConnect.this, "Veuillez entrer votre code 1", LENGTH_SHORT).show();
                    co2.setError("Veuillez entrer votre code 1");
                } else {
                    loadingBar.setTitle("Connexion en cours");
                    loadingBar.setMessage("Veuillez patienter...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    userRef.child(adUsername.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                String c1= snapshot.child(Constant.Code1).getValue().toString();
                                //String c2= snapshot.child(Constant.Code2).getValue().toString();

                                if (!c1.equals(co2.getText().toString().trim())){
                                    makeText(AdConnect.this, "Code1 incorrect", LENGTH_SHORT).show();
                                    co2.setError("Code1 incorrect, veuillez reessayer!");
                                    loadingBar.dismiss();
                                }/*else if (!c2.equals(co3.getText().toString())){
                                    makeText(AdConnect.this, "Code2 incorrect", LENGTH_SHORT).show();
                                    co3.setError("Code2 incorrect, veuillez reessayer!");
                                    loadingBar.dismiss();
                                } */else {
                                    Intent intent= new Intent(AdConnect.this, Categorie.class);
                                    intent.putExtra("userName", ""+adUsername.getText().toString());
                                    startActivity(intent);
                                    adUsername.setText("");
                                    co2.setText("");
                                }
                            }else{
                                makeText(AdConnect.this, "nom d'utilisateur incorrect",
                                        LENGTH_SHORT).show();
                                adUsername.setError("nom d'utilisateur incorrect");
                                loadingBar.dismiss();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            makeText(AdConnect.this, "Erreur: "+error.getMessage(), LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
                }

        });
     
    }

}