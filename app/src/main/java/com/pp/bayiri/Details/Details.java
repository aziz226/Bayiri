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

public class Details extends AppCompatActivity {

    private ImageView img, img1, img2, img3, img4;
    private TextView d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
    private TextView loy, caution, obli, obli_pri, code, ville, quart;
    private DatabaseReference userRef;
    private String c;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle intent= getIntent().getExtras();
        String cod= intent.getString("code");

        img= findViewById(R.id.id_detail_img);
        img1= findViewById(R.id.id_detail_mini_img1);
        img2= findViewById(R.id.id_detail_mini_img2);
        img3= findViewById(R.id.id_detail_mini_img3);
        img4= findViewById(R.id.id_detail_mini_img4);

        d1= findViewById(R.id.id_des1);
        d2= findViewById(R.id.id_des2);
        d3= findViewById(R.id.id_des3);
        d4= findViewById(R.id.id_des4);
        d5= findViewById(R.id.id_des5);
        d6= findViewById(R.id.id_des6);
        d7= findViewById(R.id.id_des7);
        d8= findViewById(R.id.id_des8);
        d9= findViewById(R.id.id_des9);
        d10= findViewById(R.id.id_des10);

        loy= findViewById(R.id.id_detail_loyer);
        caution= findViewById(R.id.id_detail_caution);
        obli= findViewById(R.id.id_detail_obli);
        obli_pri= findViewById(R.id.id_detail_obli_pri);
        code= findViewById(R.id.code_loffr);
        ville= findViewById(R.id.id_mais_ville);
        quart= findViewById(R.id.id_mais_quartier);

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
                    ville.setText(""+snapshot.child("ville").getValue().toString());
                    quart.setText(""+snapshot.child("quartier").getValue().toString());

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
        Intent intent= new Intent(Details.this, NousContacter.class);
        intent.putExtra("c", c);
        intent.putExtra("cx", "la location de la maison");
        startActivity(intent);
    }
}