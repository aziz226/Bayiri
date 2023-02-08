package com.pp.bayiri.Modif;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Admin.AjouterVente;
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

public class ModifTerrain extends AppCompatActivity {

    private Button btn_save;
    private ImageView img, img1, img2, img3, img4;
    private TextInputEditText quart, geograph, ville , prix, marg, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
    private final static int gallery_pick= 20;
    private AutoCompleteTextView type;
    private String c, un;
    private DatabaseReference userRef;
    private int etat= 0;
    private StorageReference ref;
    private String[] Type= {"Habitation","Industriel", "Commercial"};
    private String selectType= "null";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modif_terrain);

        btn_save= findViewById(R.id.tmo_aj_save);
        img= findViewById(R.id.tmo_img);
        img1= findViewById(R.id.tmo_img1);
        img2= findViewById(R.id.tmo_img2);
        img3= findViewById(R.id.tmo_img3);
        img4= findViewById(R.id.tmo_img4);

        quart= findViewById(R.id.tmo_aj_quart);
        geograph= findViewById(R.id.tmo_aj_geograph);
        ville= findViewById(R.id.tmo_aj_vill);
        prix= findViewById(R.id.tmo_aj_pri);
        marg= findViewById(R.id.tmo_aj_neg);
        type= findViewById(R.id.tmo_aj_type);

        d1= findViewById(R.id.tmo_aj_des1);
        d2= findViewById(R.id.tmo_aj_des2);
        d3= findViewById(R.id.tmo_aj_des3);
        d4= findViewById(R.id.tmo_aj_des4);
        d5= findViewById(R.id.tmo_aj_des5);
        d6= findViewById(R.id.tmo_aj_des6);
        d7= findViewById(R.id.tmo_aj_des7);
        d8= findViewById(R.id.tmo_aj_des8);
        d9= findViewById(R.id.tmo_aj_des8);
        d10= findViewById(R.id.tmo_aj_des10);

        Bundle b= getIntent().getExtras();
        c= b.getString("c");

        ArrayAdapter<String> adap= new ArrayAdapter<>(this, R.layout.drop_down_item, Type);
        type.setAdapter(adap);

        type.setOnItemClickListener((adapterView, view, i, l) -> selectType= type.getText().toString());

        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.vente).child("Terrain").child(""+c);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    ville.setText(""+snapshot.child("ville").getValue().toString());
                    quart.setText(""+snapshot.child("quartier").getValue().toString());
                    type.setText(""+snapshot.child("Type").getValue().toString());
                    prix.setText(""+snapshot.child("prix").getValue().toString());
                    marg.setText(""+snapshot.child("marge").getValue().toString());
                    geograph.setText(""+snapshot.child("situationGeog").getValue().toString());

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

                    un= snapshot.child("userName").getValue().toString();

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
            userMap.put("Type", selectType);
            userMap.put("prix", prix.getText().toString()+" F CFA");
            userMap.put("marge", marg.getText().toString()+"%");

            userMap.put("description1", d1.getText().toString());
            userMap.put("description2", d2.getText().toString());
            userMap.put("description3", d3.getText().toString());
            userMap.put("description4", d4.getText().toString());
            userMap.put("description5", d5.getText().toString());
            userMap.put("description6", d6.getText().toString());
            userMap.put("description7", d7.getText().toString());
            userMap.put("description8", d8.getText().toString());
            userMap.put("description9", d9.getText().toString());
            userMap.put("description10", d10.getText().toString());

            userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(this, "Modification reussie!", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(this, AddLocatHouse.class));
                    Intent intent=new Intent(this, AjouterVente.class);
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
            case R.id.tmo_img:
                openGallery();
                etat= 0;
                break;
            case R.id.tmo_img1:
                openGallery();
                etat=1;
                break;
            case R.id.tm_img2:
                openGallery();
                etat=2;
                break;
            case R.id.hm_img3:
                openGallery();
                etat=3;
                break;
            case R.id.tmo_img4:
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
                    .child(Constant.vente).child("image"+imageUri.getLastPathSegment());

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