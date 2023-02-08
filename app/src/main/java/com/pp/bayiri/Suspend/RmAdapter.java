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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pp.bayiri.Admin.AddLocatHouse;
import com.pp.bayiri.Class.AddArticleClass;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class RmAdapter extends RecyclerView.Adapter<RmAdapter.MyHolder> {

    Context context;
    List<AddArticleClass> list;

    public RmAdapter(Context context, List<AddArticleClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_ad_vent, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String c= list.get(position).getCodeOffre();
        String typ= list.get(position).getType();
        String loy= list.get(position).getLoyer();
        String caut= list.get(position).getCaution();
        String vil= list.get(position).getVille();
        String quar= list.get(position).getQuartier();
        String im= list.get(position).getImg();
        String im1= list.get(position).getImg1();
        String im2= list.get(position).getImg2();
        String im3= list.get(position).getImg3();
        String im4= list.get(position).getImg4();

        holder.code.setText(c);
        holder.type.setText(typ);
        holder.loyer.setText(loy);
        holder.caution.setText(caut);
        holder.ville.setText(vil);
        holder.quartier.setText(quar);

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
                .child(Constant.susp).child(Constant.location).child("Residences").child(""+c);
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Residences").child(""+c);

        holder.suspend.setOnClickListener(view -> {

            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Voulez vous vraiment rendre cette résidence disponible?");
            builder.setMessage("Une fois rendue disponible, cette résidence sera visible pour les utilisateurs...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

                    })
                    .setPositiveButton("Oui", (dialogInterface, i) -> {
                        ProgressDialog d= new ProgressDialog(context);
                        d.setTitle("Mise en ligne en cours...");
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
                                    userMap.put("situationGeog", snapshot.child("situationGeog").getValue().toString());
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

                                    String ville= snapshot.child("ville").getValue().toString();
                                     String uName= snapshot.child("userName").getValue().toString();

                                     userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                                         if (task.isSuccessful()){
                                             Toast.makeText(context, "Residence mise en ligne!", Toast.LENGTH_SHORT).show();
                                             reference.removeValue().addOnCompleteListener(task1 -> {
                                                 if (task1.isSuccessful()){
                                                     Intent intent= new Intent(context, ResidSusp.class);
                                                     intent.putExtra("un", uName);
                                                     // intent.putExtra("v", ville);
                                                     context.startActivity(intent);
                                                     // Toast.makeText(context, "Cool!", Toast.LENGTH_SHORT).show();
                                                     d.dismiss();
                                                 }
                                             });
                                            // d.dismiss();
                                         }
                                     });



                                    // reference.push().equals(null);

                                   /* userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context, "Maison disponible!", Toast.LENGTH_SHORT).show();

                                            d.dismiss();
                                        }else {
                                            Toast.makeText(context, "Erreur: "+task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    });

                                    reference.removeValue();

                                    reference.removeValue().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()){
                                            Intent intent= new Intent(context, AddLocatHouse.class);
                                            intent.putExtra("un", uName);
                                            intent.putExtra("v", ville);
                                            context.startActivity(intent);

                                        }else {
                                            Toast.makeText(context, "Erreur: "+task1.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            d.dismiss();
                                        }
                                    }); */
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

        TextView code, type, loyer, caution,ville, quartier, modif, sup, suspend;
        ImageView img, img1, img2, img3, img4;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            code= itemView.findViewById(R.id.hms_code);
            type= itemView.findViewById(R.id.hms_type);
            loyer= itemView.findViewById(R.id.hms_loyer);
            caution= itemView.findViewById(R.id.hms_caution);
            ville= itemView.findViewById(R.id.hms_ville);
            quartier= itemView.findViewById(R.id.hms_quartier);

            suspend= itemView.findViewById(R.id.hms_susp);

            img= itemView.findViewById(R.id.hms_img);
            img1= itemView.findViewById(R.id.hms_img1);
            img2= itemView.findViewById(R.id.hms_im2);
            img3= itemView.findViewById(R.id.hms_img3);
            img4= itemView.findViewById(R.id.hms_img4);

        }
    }
}
