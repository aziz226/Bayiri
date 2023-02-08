package com.pp.bayiri.Modif;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ModifHot extends AppCompatActivity {

    private Button btn_save;
    private ImageView img, img1, img2, img3, img4;
    private TextInputEditText quart, geograph, ville, caution, loy, nom;
    private final static int gallery_pick= 20;
    private String c, un;
    private DatabaseReference userRef;
    private int etat= 0;
    private StorageReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_hot);

        btn_save= findViewById(R.id.ho_modif);
        img= findViewById(R.id.ho_img);
        img1= findViewById(R.id.ho_img1);
        img2= findViewById(R.id.ho_img2);
        img3= findViewById(R.id.ho_img3);
        img4= findViewById(R.id.ho_img4);


        quart= findViewById(R.id.ho_quart);
        geograph= findViewById(R.id.ho_geograph);
        ville= findViewById(R.id.ho_ville);
        caution= findViewById(R.id.ho_caution);
        loy= findViewById(R.id.ho_loy);
        nom= findViewById(R.id.ho_name);

        Bundle b= getIntent().getExtras();
        c= b.getString("c");

        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.location).child("Hotels").child(""+c);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    ville.setText(""+snapshot.child("ville").getValue().toString());
                    quart.setText(""+snapshot.child("quartier").getValue().toString());
                    nom.setText(""+snapshot.child("nomHotel").getValue().toString());
                    caution.setText(""+snapshot.child(Constant.pJour).getValue().toString());
                    loy.setText(""+snapshot.child(Constant.pHeur).getValue().toString());
                   // geograph.setText(""+snapshot.child("situationGeog").getValue().toString());

                    un= snapshot.child("userName").toString();


                    try {
                        Picasso.get().load(""+snapshot.child("img").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img);
                    }catch (Exception e){}
                    try {
                        Picasso.get().load(""+snapshot.child("img1").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img1);
                    }catch (Exception e){}
                    try {
                        Picasso.get().load(""+snapshot.child("img2").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img2);
                    }catch (Exception e){}
                    try {
                        Picasso.get().load(""+snapshot.child("img3").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img3);
                    }catch (Exception e){}
                    try {
                        Picasso.get().load(""+snapshot.child("img4").getValue().toString())
                                .placeholder(R.drawable.ic_panorama).into(img4);
                    }catch (Exception e){}

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});

        btn_save.setOnClickListener(view -> {
            ProgressDialog loadingBar= new ProgressDialog(this);
            loadingBar.setTitle("Modification des données en cours...");
            loadingBar.setMessage("Veuillez patienter");
            loadingBar.show();

            HashMap userMap= new HashMap();

            userMap.put("ville", ville.getText().toString());
            userMap.put("quartier", quart.getText().toString());
            userMap.put("situationGeog", geograph.getText().toString());
            userMap.put("nomHotel", nom.getText().toString());
            userMap.put("loyer", loy.getText().toString()+" F CFA");
            userMap.put("caution", caution.getText().toString()+" F CFA");

            userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(this, "Modification reussie!", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(this, AddLocatHouse.class));
                    Intent intent=new Intent(this, AddLocatHouse.class);
                    intent.putExtra("v", ville.getText().toString());
                    intent.putExtra("un", un);
                    startActivity(intent);

                    loadingBar.dismiss();
                }else {
                    Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            });
        });
    }

    public void ImgClique(View view) {
        switch (view.getId()){
            case R.id.hm_img:
                openGallery();
                etat= 0;
                break;
            case R.id.hm_img1:
                openGallery();
                etat=1;
                break;
            case R.id.hm_img2:
                openGallery();
                etat=2;
                break;
            case R.id.hm_img3:
                openGallery();
                etat=3;
                break;
            case R.id.hm_img4:
                openGallery();
                etat=4;
                break;
        }
    }

    private void openGallery() {
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*");
        startActivityForResult(intent, gallery_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== gallery_pick && resultCode== RESULT_OK && data!= null){
            Uri imageUri=data.getData();

            ProgressDialog dialog= new ProgressDialog(this);
            dialog.setTitle("Modification de l'image en cours...");
            dialog.setMessage("Veuillez patienter");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            ref= FirebaseStorage.getInstance().getReference().child(Constant.User)
                    .child("Location").child("image"+imageUri.getLastPathSegment());

            switch (etat){
                case 0:
                    img.setImageURI(imageUri);
                    ref.putFile(imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uri= uriTask.getResult();
                            String imgUrl= uri.toString();

                            userRef.child("img").setValue(""+imgUrl).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Toast.makeText(this, "Image changée avec succès!",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(this, "Erreur: "+task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });

                        }else {
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    break;
                case 1:
                    img1.setImageURI(imageUri);

                    ref.putFile(imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uri= uriTask.getResult();
                            String imgUrl1= uri.toString();

                            userRef.child("img1").setValue(""+imgUrl1).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Toast.makeText(this, "Image changée avec succès!",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(this, "Erreur: "+task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case 2:
                    img2.setImageURI(imageUri);
                    ref.putFile(imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uri= uriTask.getResult();
                            String imgUrl2= uri.toString();

                            userRef.child("img2").setValue(""+imgUrl2).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Toast.makeText(this, "Image changée avec succès!",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(this, "Erreur: "+task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    break;
                case 3:
                    img3.setImageURI(imageUri);
                    ref.putFile(imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uri= uriTask.getResult();
                            String imgUrl3= uri.toString();

                            userRef.child("img3").setValue(""+imgUrl3).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Toast.makeText(this, "Image changée avec succès!",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(this, "Erreur: "+task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    break;
                case 4:
                    img4.setImageURI(imageUri);
                    ref.putFile(imageUri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uri= uriTask.getResult();
                            String imgUrl4= uri.toString();

                            userRef.child("img4").setValue(""+imgUrl4).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    Toast.makeText(this, "Image changée avec succès!",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(this, "Erreur: "+task1.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(this, "Erreur: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        }
    }
}