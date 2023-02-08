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

public class SupHome extends AppCompatActivity {

    private ImageView img, img1, img2, img3, img4;
    private TextView d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
    private TextView loy, caution, obli, obli_pri, code;
    private DatabaseReference userRef;
    private TextView susp, sup;
    private String ville, uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_home);

        Bundle intent= getIntent().getExtras();
        String cod= intent.getString("c");

        img= findViewById(R.id.sh_img);
        img1= findViewById(R.id.sh_img1);
        img2= findViewById(R.id.sh_img2);
        img3= findViewById(R.id.sh_img3);
        img4= findViewById(R.id.sh_img4);

        d1= findViewById(R.id.sh_des1);
        d2= findViewById(R.id.sh_des2);
        d3= findViewById(R.id.sh_des3);
        d4= findViewById(R.id.sh_des4);
        d5= findViewById(R.id.sh_des5);
        d6= findViewById(R.id.sh_des6);
        d7= findViewById(R.id.sh_des7);
        d8= findViewById(R.id.sh_des8);
        d9= findViewById(R.id.sh_des9);
        d10= findViewById(R.id.sh_des10);

        loy= findViewById(R.id.sh_loyer);
        caution= findViewById(R.id.sh_caution);
        obli= findViewById(R.id.sh_obli);
        obli_pri= findViewById(R.id.sh_obli_pri);
        code= findViewById(R.id.sh_code);
        sup= findViewById(R.id.sh_sup);
        susp= findViewById(R.id.sh_susp);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Maison").child(""+cod);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    d1.setText(""+snapshot.child("description1").getValue().toString());
                    d2.setText(""+snapshot.child("description2").getValue().toString());
                    d3.setText(""+snapshot.child("description3").getValue().toString());
                    d4.setText(""+snapshot.child("description4").getValue().toString());
                    d5.setText(""+snapshot.child("description5").getValue().toString());
                    d6.setText(""+snapshot.child("description6").getValue().toString());
                    d7.setText(""+snapshot.child("description7").getValue().toString());
                    d8.setText(""+snapshot.child("description8").getValue().toString());
                    d9.setText(""+snapshot.child("description9").getValue().toString());
                    d10.setText(""+snapshot.child("description10").getValue().toString());

                    loy.setText(""+snapshot.child("loyer").getValue().toString());
                    caution.setText(""+snapshot.child("caution").getValue().toString());
                    obli.setText(""+snapshot.child("periode").getValue().toString());
                    obli_pri.setText(""+snapshot.child("caution").getValue().toString());
                    code.setText(""+snapshot.child("codeOffre").getValue().toString());

                     ville= snapshot.child("ville").getValue().toString();
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
            builder.setTitle("Voulez vous vraiment suprimer cette maison?");
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
                        Toast.makeText(this, "Maison supprimée!", Toast.LENGTH_SHORT).show();
                        Intent inten= new Intent(this, AddLocatHouse.class);
                        inten.putExtra("un", uName);
                        inten.putExtra("v", ville);
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
                    .child(Constant.susp).child(Constant.location).child("Maison").child(""+cod);

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Voulez vous vraiment suspendre cette maison?");
            builder.setMessage("Une fois suspendue, cette maison ne sera plus visible pour les utilisateurs...");
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
                            userMap.put("Type", snapshot.child("Type").getValue().toString());
                            userMap.put("codeOffre", snapshot.child("codeOffre").getValue().toString());
                            userMap.put("userName", snapshot.child("userName").getValue().toString());
                            userMap.put("loyer", snapshot.child("loyer").getValue().toString());
                            userMap.put("periode", snapshot.child("periode").getValue().toString());
                            userMap.put("caution", snapshot.child("caution").getValue().toString());

                            userMap.put("description1", snapshot.child("description1").getValue().toString());
                            userMap.put("description2", snapshot.child("description2").getValue().toString());
                            userMap.put("description3", snapshot.child("description3").getValue().toString());
                            userMap.put("description4", snapshot.child("description4").getValue().toString());
                            userMap.put("description5", snapshot.child("description5").getValue().toString());
                            userMap.put("description6", snapshot.child("description6").getValue().toString());
                            userMap.put("description7", snapshot.child("description7").getValue().toString());
                            userMap.put("description8", snapshot.child("description8").getValue().toString());
                            userMap.put("description9", snapshot.child("description9").getValue().toString());
                            userMap.put("description10", snapshot.child("description10").getValue().toString());

                            reference.updateChildren(userMap).addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    userRef.removeValue().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()){
                                            Toast.makeText(SupHome.this, "Maison supprimée!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SupHome.this, AddLocatHouse.class));
                                            d.dismiss();
                                        }else {
                                            Toast.makeText(SupHome.this, "Erreur: "+task1.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    });
                                }else {
                                    Toast.makeText(SupHome.this, "Erreur: "+task.getException().getMessage(),
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