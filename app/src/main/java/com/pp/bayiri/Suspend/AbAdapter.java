package com.pp.bayiri.Suspend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HotelClass;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class AbAdapter extends RecyclerView.Adapter<AbAdapter.MyHolder> {
    Context context;
    List<HotelClass> list;

    public AbAdapter(Context context, List<HotelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_hos, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String c= list.get(position).getCodeOffre();
        String n= list.get(position).getNomHotel();
        String loy= list.get(position).getPrixHeur();
        String caut= list.get(position).getPrixJour();
        String vil= list.get(position).getVille();
        String quar= list.get(position).getQuartier();
        String im= list.get(position).getImg();
        String im1= list.get(position).getImg1();
        String im2= list.get(position).getImg2();
        String im3= list.get(position).getImg3();
        String im4= list.get(position).getImg4();

        holder.code.setText(c);
        holder.loyer.setText(loy);
        holder.caution.setText(caut);
        holder.ville.setText(vil);
        holder.quartier.setText(quar);
        holder.nom.setText(n);

        try{
            Picasso.get().load(im).placeholder(R.drawable.ic_panorama).into(holder.img);
        }catch (Exception e){}
        try{
            Picasso.get().load(im1).placeholder(R.drawable.ic_panorama).into(holder.img1);
        }catch (Exception e){}
        try{
            Picasso.get().load(im2).placeholder(R.drawable.ic_panorama).into(holder.img2);
        }catch (Exception e){}
        try{
            Picasso.get().load(im3).placeholder(R.drawable.ic_panorama).into(holder.img3);
        }catch (Exception e){}
        try{
            Picasso.get().load(im4).placeholder(R.drawable.ic_panorama).into(holder.img4);
        }catch (Exception e){}

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.location).child("Auberges").child(c);


        holder.suspend.setOnClickListener(view -> {

            DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                    .child(Constant.susp).child(Constant.location).child("Auberges").child(""+c);

            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Voulez vous vraiment rendre disponible cette auberge?");
            builder.setMessage("Une fois rendu disponible, cette auberge sera visible pour les utilisateurs...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

                    })
                    .setPositiveButton("Oui", (dialogInterface, i) -> {
                        ProgressDialog d= new ProgressDialog(context);
                        d.setTitle("Mise en ligne en cours...");
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
                                   // userMap.put("situationGeog", snapshot.child("situationGeog").getValue().toString());
                                    userMap.put("codeOffre", snapshot.child("codeOffre").getValue().toString());
                                    userMap.put("userName", snapshot.child("userName").getValue().toString());
                                    userMap.put(Constant.pHeur, snapshot.child(Constant.pHeur).getValue().toString());
                                  //  userMap.put("periode", snapshot.child("periode").getValue().toString());
                                    userMap.put(Constant.pJour, snapshot.child(Constant.pJour).getValue().toString());
                                    userMap.put("nomHotel", snapshot.child("nomHotel").getValue().toString());

                                    String ville= snapshot.child("ville").getValue().toString();
                                    String uName= snapshot.child("userName").getValue().toString();

                                    reference.updateChildren(userMap).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context, "auberge mise en ligne!", Toast.LENGTH_SHORT).show();

                                            userRef.removeValue().addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()){

                                                    //Toast.makeText(context, "Cool", Toast.LENGTH_SHORT).show();
                                                    Intent intent= new Intent(context, AubSusp.class);
                                                    intent.putExtra("un", uName);
                                                    // intent.putExtra("v", ville);
                                                    context.startActivity(intent);
                                                    d.dismiss();
                                                }else {
                                                    Toast.makeText(context, "Erreur: "+task1.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                    d.dismiss();
                                                }
                                            });

                                           // d.dismiss();
                                        }else {
                                            Toast.makeText(context, "Erreur: "+task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    });

                                   /* userRef.removeValue().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()){

                                            Toast.makeText(context, "Cool", Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(context, AubSusp.class);
                                            intent.putExtra("un", uName);
                                            // intent.putExtra("v", ville);
                                            context.startActivity(intent);

                                        }else {
                                            Toast.makeText(context, "Erreur: "+task1.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    });*/
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    });

            builder.create().show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView code, nom, loyer, caution,ville, quartier, modif, sup, suspend;
        ImageView img, img1, img2, img3, img4;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nom= itemView.findViewById(R.id.hos_aub_nom);
            code= itemView.findViewById(R.id.hos_code);
            loyer= itemView.findViewById(R.id.hos_prix_heur);
            caution= itemView.findViewById(R.id.hos_prix_jour);
            ville= itemView.findViewById(R.id.hos_ville);
            quartier= itemView.findViewById(R.id.hos_quartier);
            suspend= itemView.findViewById(R.id.hos_susp);
            img= itemView.findViewById(R.id.hos_img);
            img1= itemView.findViewById(R.id.hos_img1);
            img2= itemView.findViewById(R.id.hos_im2);
            img3= itemView.findViewById(R.id.hos_img3);
            img4= itemView.findViewById(R.id.hos_img4);

        }
    }
}
