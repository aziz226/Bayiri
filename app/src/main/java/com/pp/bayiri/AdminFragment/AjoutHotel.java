package com.pp.bayiri.AdminFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class AjoutHotel extends AppCompatActivity {

    private Button btn_save;
    private ImageView img, img1, img2, img3, img4;
    private TextInputEditText quart, geograph, type, code, hotName, pjr, phr, userN;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String susp= "false";
    private static int galeryPick= 10, i;
    private Uri imageUri;
    private StorageReference ref;
    private String imgUrl, imgUrl1, imgUrl2, imgUrl3, imgUrl4;
    private ArrayList<Uri> mArrayUri;
    // private String uName, ville, maison;
    private int count= 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_hotel);

        btn_save= findViewById(R.id.hot_save);
        img= findViewById(R.id.id_art_hot_img);
        img1= findViewById(R.id.id_art_hot_mini_img1);
        img2= findViewById(R.id.id_art_hot_mini_img2);
        img3= findViewById(R.id.id_art_hot_mini_img3);
        img4= findViewById(R.id.id_art_hot_mini_img4);

        quart= findViewById(R.id.id_hot_art_quart);
        //geograph= findViewById(R.id.id_hot_art_geograph);
        code= findViewById(R.id.id_hot_art_code);
        pjr= findViewById(R.id.id_hot_art_caution);
        phr= findViewById(R.id.id_hot_art_loy);
        hotName= findViewById(R.id.hot_name);


        Bundle b= getIntent().getExtras();
        String ville= b.getString("v");
        String uName= b.getString("un");

        btn_save.setOnClickListener(view -> {

             if (TextUtils.isEmpty(hotName.getText().toString())){
                Toast.makeText(this, "Veuillez entrer le nom de l'Hotel ou l'Auberge", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(quart.getText().toString())){
                Toast.makeText(this, "Veuillez préciser le quartier", Toast.LENGTH_SHORT).show();
            }/*else if (TextUtils.isEmpty(geograph.getText().toString())){
                Toast.makeText(this, "Veuillez décrire la situation géographie", Toast.LENGTH_SHORT).show();
            } */else if (TextUtils.isEmpty(code.getText().toString())){
                Toast.makeText(this, "Entrer le code l'offre", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(pjr.getText().toString())){
                Toast.makeText(this, "Entrer la caution", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(phr.getText().toString())){
                Toast.makeText(this, "Entrer le loyer", Toast.LENGTH_SHORT).show();
            }else{
                ProgressDialog dialog= new ProgressDialog(this);
                dialog.setTitle("Ajout de résidence en cours");
                dialog.setMessage("Veuillez patienter...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                        .child(Constant.location).child(Constant.hotel).child(code.getText().toString());

                HashMap userMap= new HashMap();

                userMap.put(Constant.img, imgUrl);
                userMap.put(Constant.img1, imgUrl1);
                userMap.put(Constant.img2, imgUrl2);
                userMap.put(Constant.img3, imgUrl3);
                userMap.put(Constant.img4, imgUrl4);
                userMap.put(Constant.ville, ville);
                userMap.put(Constant.quartier, quart.getText().toString());
                userMap.put(Constant.codeOf, code.getText().toString());
                userMap.put(Constant.username, uName);
                userMap.put(Constant.nomHot, hotName.getText().toString());
                userMap.put(Constant.pHeur, phr.getText().toString()+" F CFA");
                userMap.put(Constant.pJour, pjr.getText().toString()+" F CFA");

                reference.updateChildren(userMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(AjoutHotel.this, "Vous venez d'ajouter une résidence en location.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(AjoutHotel.this, AddLocatHouse.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("un", uName);
                        intent.putExtra("v", ville);
                        startActivity(intent);
                        dialog.dismiss();
                    }else {
                        String msg= task.getException().getMessage();
                        Toast.makeText(AjoutHotel.this, "Erreur: "+msg, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    public void ImgClique(View view) {

        Intent intent = new Intent().setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, galeryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mArrayUri= new ArrayList<>();
        if (requestCode== galeryPick && resultCode== RESULT_OK && data.getClipData()!= null) {
            ProgressDialog dialog = new ProgressDialog(this);

            Intent intent= getIntent();
            //String uName= intent.getStringExtra("userName");
            //String ville= intent.getStringExtra("idVille");
            //String maison= intent.getStringExtra("maison");

            dialog.setTitle("Sauvegarde de l'image en cours");
            dialog.setMessage("Veuillez patienter");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            int nbrImg= data.getClipData().getItemCount();
            int imgSelect= 0;
            while (imgSelect < nbrImg){
                imageUri= data.getClipData().getItemAt(imgSelect).getUri();
                mArrayUri.add(imageUri);

                imgSelect= imgSelect+1;
            }
            img.setImageURI(mArrayUri.get(0));
            img1.setImageURI(mArrayUri.get(1));
            img2.setImageURI(mArrayUri.get(2));
            img3.setImageURI(mArrayUri.get(3));
            img4.setImageURI(mArrayUri.get(4));

            ref= FirebaseStorage.getInstance().getReference().child(Constant.User)
                    .child("Location");
            DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("imageUrl");

            StorageReference imgName= ref.child("Image"+ mArrayUri.get(0).getLastPathSegment());
            imgName.putFile(mArrayUri.get(0)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    imgUrl = downloadUrl.toString();

                }else {
                    String message= task.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            StorageReference imgName1= ref.child("Image"+ mArrayUri.get(1).getLastPathSegment());
            imgName1.putFile(mArrayUri.get(1)).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Task<Uri> uriTask = task1.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    imgUrl1 = downloadUrl.toString();

                }else {
                    String message= task1.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            StorageReference imgName2= ref.child("Image"+ mArrayUri.get(2).getLastPathSegment());
            imgName2.putFile(mArrayUri.get(2)).addOnCompleteListener(task2 -> {
                if (task2.isSuccessful()) {
                    Task<Uri> uriTask = task2.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    imgUrl2 = downloadUrl.toString();

                }else {
                    String message= task2.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            StorageReference imgName3= ref.child("Image"+ mArrayUri.get(3).getLastPathSegment());
            imgName3.putFile(mArrayUri.get(3)).addOnCompleteListener(task3 -> {
                if (task3.isSuccessful()) {
                    Task<Uri> uriTask = task3.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    imgUrl3 = downloadUrl.toString();

                }else {
                    String message= task3.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            StorageReference imgName4= ref.child("Image"+ mArrayUri.get(4).getLastPathSegment());
            imgName4.putFile(mArrayUri.get(4)).addOnCompleteListener(task4 -> {
                if (task4.isSuccessful()) {
                    Task<Uri> uriTask = task4.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();

                    Toast.makeText(this, "Sauvegarde terminé !", Toast.LENGTH_SHORT).show();
                    imgUrl4 = downloadUrl.toString();
                    dialog.dismiss();
                }else {
                    String message= task4.getException().getMessage();
                    Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        }

    }
}