package com.pp.bayiri.Details;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.NousContacter;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class VentTerDetail extends AppCompatActivity {

    private ImageView img, img1, img2, img3, img4;
    private TextView d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
    private TextView loy, caution, obli, obli_pri, code;
    private DatabaseReference userRef;
    private String c;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vent_ter_detail);

        Bundle intent= getIntent().getExtras();
        String cod= intent.getString("c");

        img= findViewById(R.id.td_img);
        img1= findViewById(R.id.td_img1);
        img2= findViewById(R.id.td_img2);
        img3= findViewById(R.id.td_img3);
        img4= findViewById(R.id.td_img4);

        d1= findViewById(R.id.td_des1);
        d2= findViewById(R.id.td_des2);
        d3= findViewById(R.id.td_des3);
        d4= findViewById(R.id.td_des4);
        d5= findViewById(R.id.td_des5);
        d6= findViewById(R.id.td_des6);
        d7= findViewById(R.id.td_des7);
        d8= findViewById(R.id.td_des8);
        d9= findViewById(R.id.td_des9);
        d10= findViewById(R.id.td_des10);

        loy= findViewById(R.id.td_pri);
        caution= findViewById(R.id.td_marg);
        obli= findViewById(R.id.td_vil);
        obli_pri= findViewById(R.id.td_quart);
        code= findViewById(R.id.td_code_loffr);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.vente).child("Terrain").child(""+cod);

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

                    loy.setText(""+snapshot.child("prix").getValue().toString());
                    caution.setText(""+snapshot.child("marge").getValue().toString());
                    obli.setText(""+snapshot.child("ville").getValue().toString());
                    obli_pri.setText(""+snapshot.child("quartier").getValue().toString());
                    code.setText(""+snapshot.child("codeOffre").getValue().toString());

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
        Intent intent= new Intent(VentTerDetail.this, NousContacter.class);
        intent.putExtra("c", c);
        intent.putExtra("cx", "l'achat du terrain");
        startActivity(intent);
    }

}