package com.pp.bayiri.AdminFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pp.bayiri.Admin.AjouterVente;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AjoutTerrain extends AppCompatActivity {

    private Button btn_save;
    private ImageView img, img1, img2, img3, img4;
    private TextInputEditText quart, geograph, code, marg, prix;
    private TextInputEditText desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8, desc9, desc10;
    private AutoCompleteTextView type;
    private String quar, geo, typ, cod, per, caut, lo, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
    private String susp= "false";
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private static int galeryPick= 10, i;
    private Uri imageUri;
    private StorageReference ref;
    private String imgUrl, imgUrl1, imgUrl2, imgUrl3, imgUrl4;
    private ArrayList<Uri> mArrayUri;
    // private String uName, ville, maison;
    private int count= 0;
    private String[] Type= {"Habitation","Industriel", "Commercial"};
    private String selectType= "null";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_terrain);

        Intent intent1= getIntent();
        String uName= intent1.getStringExtra("userName");
        //String ville= intent1.getStringExtra("idVille");
        String maison= intent1.getStringExtra("maison");
        //Bundle b= getIntent().getExtras();
        String ville= intent1.getStringExtra("v");

        btn_save= findViewById(R.id.tr_aj_save);
        img= findViewById(R.id.t_img);
        img1= findViewById(R.id.tr_img1);
        img2= findViewById(R.id.tr_img2);
        img3= findViewById(R.id.tr_img3);
        img4= findViewById(R.id.tr_img4);

        //userN= findViewById(R.id.user_name);
        quart= findViewById(R.id.tr_aj_quart);
        geograph= findViewById(R.id.tr_aj_geograph);
        type= findViewById(R.id.tr_aj_type);
        code= findViewById(R.id.tr_aj_code);
        marg= findViewById(R.id.tr_aj_neg);
        prix= findViewById(R.id.tr_aj_pri);

        desc1= findViewById(R.id.tr_aj_des1);
        desc2= findViewById(R.id.tr_aj_des2);
        desc3= findViewById(R.id.tr_aj_des3);
        desc4= findViewById(R.id.tr_aj_des4);
        desc5= findViewById(R.id.tr_aj_des5);
        desc6= findViewById(R.id.tr_aj_des6);
        desc7= findViewById(R.id.tr_aj_des7);
        desc8= findViewById(R.id.tr_aj_des8);
        desc9= findViewById(R.id.tr_aj_des9);
        desc10= findViewById(R.id.tr_aj_des10);

        ArrayAdapter<String> adap= new ArrayAdapter<>(this, R.layout.drop_down_item, Type);
        type.setAdapter(adap);

        type.setOnItemClickListener((adapterView, view, i, l) -> selectType= type.getText().toString());

        btn_save.setOnClickListener(view -> {

           /* if(TextUtils.isEmpty(userN.getText().toString())){
                Toast.makeText(this, "Veuillez entrer votre nom d'utilisateur", Toast.LENGTH_SHORT).show();
            }else */
            if (TextUtils.isEmpty(quart.getText().toString())){
                Toast.makeText(this, "Veuillez préciser le quartier", Toast.LENGTH_SHORT).show();
                quart.setError("Veuillez préciser le quartier");
            }else if (TextUtils.isEmpty(geograph.getText().toString())){
                Toast.makeText(this, "Veuillez décrire la situation géographie", Toast.LENGTH_SHORT).show();
            }else if (selectType.equals("null")){
                Toast.makeText(this, "Choisissez le type du terrain", Toast.LENGTH_SHORT).show();
                type.setError("Veuillez selectionner le type");
            }else if (TextUtils.isEmpty(code.getText().toString())){
                Toast.makeText(this, "Entrer le code l'offre", Toast.LENGTH_SHORT).show();
                code.setError("Entrer le code de l'offre");
            }else if (TextUtils.isEmpty(prix.getText().toString())){
                Toast.makeText(this, "Entrer le prix du terrain", Toast.LENGTH_SHORT).show();
                prix.setError("Veuillez donner le prix");
            }else if (TextUtils.isEmpty(marg.getText().toString())){
                Toast.makeText(this, "Entrer la marge de négociation", Toast.LENGTH_SHORT).show();
                marg.setError("Entrer la marge de négociation");
            }if (TextUtils.isEmpty(desc1.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 1", Toast.LENGTH_SHORT).show();
                desc1.setError("Veuillez la description");
            } else if (TextUtils.isEmpty(desc2.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 2", Toast.LENGTH_SHORT).show();
                desc2.setError("Veuillez la description2");
            } else if (TextUtils.isEmpty(desc3.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 3", Toast.LENGTH_SHORT).show();
                desc3.setError("Veuillez la description3");
            } else if (TextUtils.isEmpty(desc4.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 4", Toast.LENGTH_SHORT).show();
                desc4.setError("Veuillez la description4");
            }else if (TextUtils.isEmpty(desc5.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 5", Toast.LENGTH_SHORT).show();
                desc5.setError("Veuillez la description5");
            }else if (TextUtils.isEmpty(desc6.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 6", Toast.LENGTH_SHORT).show();
                desc6.setError("Veuillez la description6");
            }else if (TextUtils.isEmpty(desc7.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 7", Toast.LENGTH_SHORT).show();
                desc7.setError("Veuillez la description7");
            }else if (TextUtils.isEmpty(desc8.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 8", Toast.LENGTH_SHORT).show();
                desc8.setError("Veuillez la descriptio8");
            }else if (TextUtils.isEmpty(desc9.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 9", Toast.LENGTH_SHORT).show();
                desc9.setError("Veuillez la description9");
            }else if (TextUtils.isEmpty(desc10.getText().toString())){
                Toast.makeText(this, "Veuillez donner la description 10", Toast.LENGTH_SHORT).show();
                desc10.setError("Veuillez la description10");
            }else{
                ProgressDialog dialog= new ProgressDialog(this);
                dialog.setTitle("Ajout de "+maison+" en cours");
                dialog.setMessage("Veuillez patienter...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                        .child(Constant.vente).child(Constant.terrain).child(code.getText().toString());

                HashMap userMap= new HashMap();

                userMap.put("img", imgUrl);
                userMap.put("img1", imgUrl1);
                userMap.put("img2", imgUrl2);
                userMap.put("img3", imgUrl3);
                userMap.put("img4", imgUrl4);
                // userMap.put("userName", userN.getText().toString());
                userMap.put("ville", ville);
                userMap.put("quartier", quart.getText().toString());
                userMap.put("situationGeog", geograph.getText().toString());
                userMap.put("Type", selectType);
                userMap.put("codeOffre", code.getText().toString());
                userMap.put("userName", uName);
                userMap.put("prix", prix.getText().toString()+" F CFA");
                userMap.put("marge", marg.getText().toString()+"%");
                userMap.put("description1", desc1.getText().toString());
                userMap.put("description2", desc2.getText().toString());
                userMap.put("description3", desc3.getText().toString());
                userMap.put("description4", desc4.getText().toString());
                userMap.put("description5", desc5.getText().toString());
                userMap.put("description6", desc6.getText().toString());
                userMap.put("description7", desc7.getText().toString());
                userMap.put("description8", desc8.getText().toString());
                userMap.put("description9", desc9.getText().toString());
                userMap.put("description10", desc10.getText().toString());
                //userMap.put("susp", susp);

                reference.updateChildren(userMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(AjoutTerrain.this, "Vous venez d'ajouter une "+maison+" en location.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(AjoutTerrain.this, AjouterVente.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //intent.putExtra("un", uName);
                        intent.putExtra("un", uName);
                        intent.putExtra("v", ville);
                        startActivity(intent);
                        //uRef.removeValue();
                        dialog.dismiss();
                    }else {
                        String msg= task.getException().getMessage();
                        Toast.makeText(AjoutTerrain.this, "Erreur: "+msg, Toast.LENGTH_SHORT).show();
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
                    .child("Vente");
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