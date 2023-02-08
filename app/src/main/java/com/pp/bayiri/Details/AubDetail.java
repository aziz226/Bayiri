package com.pp.bayiri.Details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.NousContacter;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

public class AubDetail extends AppCompatActivity {

    private ImageView img, img1, img2, img3, img4;
    private TextView loy, caution, ville, quar, code, nom;
    private DatabaseReference userRef;
    private String c;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aub_detail);

        Bundle intent= getIntent().getExtras();
        String cod= intent.getString("code");

        img= findViewById(R.id.au_detail_img);
        img1= findViewById(R.id.au_detail_mini_img1);
        img2= findViewById(R.id.au_detail_mini_img2);
        img3= findViewById(R.id.au_detail_mini_img3);
        img4= findViewById(R.id.au_detail_mini_img4);

        loy= findViewById(R.id.au_detail_hr);
        caution= findViewById(R.id.au_detail_jr);
        quar= findViewById(R.id.id_aub_quartier);
        ville= findViewById(R.id.id_aub_ville);
        code= findViewById(R.id.au_code_loffr);
        nom= findViewById(R.id.au_detail_nom);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Auberges").child(""+cod);

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
                    c= snapshot.child("codeOffre").getValue().toString();

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

    }

    public void NousContacter(View view) {
        Intent intent= new Intent(AubDetail.this, NousContacter.class);
        intent.putExtra("c", c);
        intent.putExtra("cx", "l'auberge");
        startActivity(intent);
    }
}