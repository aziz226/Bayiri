package com.pp.bayiri.Class;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp.bayiri.Modif.ModifHot;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHolder> {
    Context context;
    List<HotelClass> list;

    public HotelAdapter(Context context, List<HotelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
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

        holder.modif.setOnClickListener(view -> {
            Intent i= new Intent(context, ModifHot.class);
            i.putExtra("c", ""+c);
            context.startActivity(i);
        });

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Hotels").child(""+c);

        holder.sup.setOnClickListener(view -> {
            /*Intent i= new Intent(context, SupHot.class);
            i.putExtra("c", ""+c);
            context.startActivity(i); */
            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Voulez vous vraiment suprimer cette hotel?");
            builder.setMessage("L'action est irreversible...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

            }).setPositiveButton("Oui", (dialogInterface, i) -> {
                ProgressDialog d= new ProgressDialog(context);
                d.setTitle("Suspression en cours...");
                d.setMessage("Veuillez patienter");
                d.setCanceledOnTouchOutside(false);
                d.show();
                reference.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(context, "Hotel supprimée!", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }else {
                        Toast.makeText(context, "Erreur: "+task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                });
            });

            builder.create().show();
        });

        holder.suspend.setOnClickListener(view -> {
           /* Intent i= new Intent(context, SupHot.class);
            i.putExtra("c", ""+c);
            context.startActivity(i); */
            DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                    .child(Constant.susp).child(Constant.location).child("Hotels").child(""+c);

            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Voulez vous vraiment suspendre cette hotel?");
            builder.setMessage("Une fois suspendue, cette hotel ne sera plus visible pour les utilisateurs...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

                    })
                    .setPositiveButton("Oui", (dialogInterface, i) -> {
                        ProgressDialog d= new ProgressDialog(context);
                        d.setTitle("Suspenssion en cours...");
                        d.setMessage("Veuillez patienter");
                        d.setCanceledOnTouchOutside(false);
                        d.show();

                        reference.addValueEventListener(new ValueEventListener() {
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
                                    userMap.put(Constant.pJour, snapshot.child(Constant.pJour).getValue().toString());
                                    //  userMap.put("periode", snapshot.child("periode").getValue().toString());
                                    userMap.put(Constant.pHeur, snapshot.child(Constant.pHeur).getValue().toString());
                                    userMap.put("nomHotel", snapshot.child("nomHotel").getValue().toString());

                                    reference1.updateChildren(userMap).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            reference.removeValue().addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()){
                                                    Toast.makeText(context, "Hotel suspendue!", Toast.LENGTH_SHORT).show();
                                                    d.dismiss();
                                                }else {
                                                    Toast.makeText(context, "Erreur: "+task1.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                    d.dismiss();
                                                }
                                            });
                                        }else {
                                            Toast.makeText(context, "Erreur: "+task.getException().getMessage(),
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView code, nom, loyer, caution,ville, quartier, modif, sup, suspend;
        ImageView img, img1, img2, img3, img4;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nom= itemView.findViewById(R.id.id_item_loc_aub_nom);
            code= itemView.findViewById(R.id.id_ad_item_a_code);
            loyer= itemView.findViewById(R.id.id_item_loc_aub_prix_heur);
            caution= itemView.findViewById(R.id.id_item_loc_aub_prix_jour);
            ville= itemView.findViewById(R.id.id_item_loc_aub_ville);
            quartier= itemView.findViewById(R.id.id_item_loc_aub_quartier);
            modif= itemView.findViewById(R.id.id_ad_aub_mod);
            sup= itemView.findViewById(R.id.id_ad_aub_sup);
            suspend= itemView.findViewById(R.id.id_ad_aub_susp);
            img= itemView.findViewById(R.id.id_ad_item_aub_img);
            img1= itemView.findViewById(R.id.id_ad_item_a_img1);
            img2= itemView.findViewById(R.id.id_ad_item_a_im2);
            img3= itemView.findViewById(R.id.id_ad_item_a_img3);
            img4= itemView.findViewById(R.id.id_ad_item_a_img4);

        }
    }
}
