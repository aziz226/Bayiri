package com.pp.bayiri.Supprimer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SupHot extends AppCompatActivity {

    private ImageView img, img1, img2, img3, img4;
    private TextView loy, caution, ville, quar, code, nom, sup, susp;
    private DatabaseReference userRef;
    private String Ville, uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_hot);

        Bundle intent= getIntent().getExtras();
        String cod= intent.getString("c");

        img= findViewById(R.id.sho_img);
        img1= findViewById(R.id.sho_img1);
        img2= findViewById(R.id.sho_img2);
        img3= findViewById(R.id.sho_img3);
        img4= findViewById(R.id.sho_img4);

        loy= findViewById(R.id.sho_hr);
        caution= findViewById(R.id.sho_jr);
        quar= findViewById(R.id.sho_quartier);
        ville= findViewById(R.id.sho_ville);
        code= findViewById(R.id.sho_code_loffr);
        nom= findViewById(R.id.sho_nom);
        sup= findViewById(R.id.sho_sup);
        susp= findViewById(R.id.sho_susp);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Hotels").child(""+cod);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    loy.setText(""+snapshot.child("loyer").getValue().toString());
                    caution.setText(""+snapshot.child("caution").getValue().toString());
                    ville.setText(""+snapshot.child("ville").getValue().toString());
                    quar.setText(""+snapshot.child("quartier").getValue().toString());
                    code.setText(""+snapshot.child("codeOffre").getValue().toString());
                    nom.setText(""+snapshot.child("nomHotel").getValue().toString());

                     Ville= snapshot.child("ville").getValue().toString();
                     uName= snapshot.child("userName").getValue().toString();

                    try{
                        Picasso.get().load(""+snapshot.child("img").getValue()
                                .toString()).placeholder(R.drawable.ic_panorama).into(img);
                    }catch (Exception e){}
                    try{
                        Picasso.get().load(""+snapshot.child("img1").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img1);
                    }catch (Exception e){}
                    try{
                        Picasso.get().load(""+snapshot.child("img2").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img2);
                    }catch (Exception e){}
                    try{
                        Picasso.get().load(""+snapshot.child("img3").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img3);
                    }catch (Exception e){}
                    try{
                        Picasso.get().load(""+snapshot.child("img4").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img4);
                    }catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sup.setOnClickListener(view -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Voulez vous vraiment suprimer cette hotel?");
            builder.setMessage("L'action est irreversible...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

            }).setPositiveButton("Oui", (dialogInterface, i) -> {
                ProgressDialog d= new ProgressDialog(this);
                d.setTitle("Suspression en cours...");
                d.setMessage("Veuillez patienter");
                d.setCanceledOnTouchOutside(false);
                d.show();
                userRef.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Hotel supprimée!", Toast.LENGTH_SHORT).show();
                        Intent inten= new Intent(this, AddLocatHouse.class);
                        inten.putExtra("un", uName);
                        inten.putExtra("v", Ville);
                        startActivity(inten);
                        d.dismiss();
                    }else {
                        Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                });
            });

            builder.create().show();
        });

        susp.setOnClickListener(view -> {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                    .child(Constant.susp).child(Constant.location).child("Hotels").child(""+cod);

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Voulez vous vraiment suspendre cette hotel?");
            builder.setMessage("Une fois suspendue, cette hotel ne sera plus visible pour les utilisateurs...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

                    })
                    .setPositiveButton("Oui", (dialogInterface, i) -> {
                        ProgressDialog d= new ProgressDialog(this);
                        d.setTitle("Suspression en cours...");
                        d.setMessage("Veuillez patienter");
                        d.setCanceledOnTouchOutside(false);
                        d.show();

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    HashMap userMap= new HashMap();

                                    userMap.put("img", ""+snapshot.child("img").getValue().toString());
                                    userMap.put("img1", ""+snapshot.child("img1").getValue().toString());
                                    userMap.put("img2", ""+snapshot.child("img2").getValue().toString());
                                    userMap.put("img3", ""+snapshot.child("img3").getValue().toString());
                                    userMap.put("img4", ""+snapshot.child("img4").getValue().toString());

                                    userMap.put("ville", snapshot.child("ville").getValue().toString());
                                    userMap.put("quartier", snapshot.child("quartier").getValue().toString());
                                    userMap.put("situationGeo", snapshot.child("situationGeo").getValue().toString());
                                    userMap.put("codeOffre", snapshot.child("codeOffre").getValue().toString());
                                    userMap.put("userName", snapshot.child("userName").getValue().toString());
                                    userMap.put("loyer", snapshot.child("loyer").getValue().toString());
                                  //  userMap.put("periode", snapshot.child("periode").getValue().toString());
                                    userMap.put("caution", snapshot.child("caution").getValue().toString());
                                    userMap.put("nomHotel", snapshot.child("nomHotel").getValue().toString());

                                    reference.updateChildren(userMap).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            userRef.removeValue().addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()){
                                                    Toast.makeText(SupHot.this, "Maison supprimée!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(SupHot.this, AddLocatHouse.class));
                                                    d.dismiss();
                                                }else {
                                                    Toast.makeText(SupHot.this, "Erreur: "+task1.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                    d.dismiss();
                                                }
                                            });
                                        }else {
                                            Toast.makeText(SupHot.this, "Erreur: "+task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    });

            builder.create().show();
        });
    }
}