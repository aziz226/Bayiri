package com.pp.bayiri.VenteClass;

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

import com.pp.bayiri.Admin.AjouterVente;
import com.pp.bayiri.AdminFragment.AdVenteMaison;
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

public class VmAdapter extends RecyclerView.Adapter<VmAdapter.MyHolder> {
    Context context;
    List<TmClass> list;

    public VmAdapter(Context context, List<TmClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.vent_susp_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String c= list.get(position).getCodeOffre();
        String prix= list.get(position).getPrix();
        String marg= list.get(position).getMarge();
        String vil= list.get(position).getVille();
        String quar= list.get(position).getQuartier();
        String im= list.get(position).getImg();
        String im1= list.get(position).getImg1();
        String im2= list.get(position).getImg2();
        String im3= list.get(position).getImg3();
        String im4= list.get(position).getImg4();
        String tp= list.get(position).getType();

        holder.code.setText(c);
        holder.prix.setText(prix);
        holder.marg.setText(marg);
        holder.ville.setText(vil);
        holder.quartier.setText(quar);
        holder.type.setText(tp);

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


        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.vente).child("Maison").child(c);

        holder.btn.setOnClickListener(view -> {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                    .child(Constant.susp).child(Constant.vente).child("Maison").child(""+c);

            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setTitle("Voulez vous vraiment rendre cette maison disponible?");
            builder.setMessage("Une fois rendue disponible, cette maison sera visible pour les utilisateurs...");
            builder.setNegativeButton("Non", (dialogInterface, i) -> {

                    })
                    .setPositiveButton("Oui", (dialogInterface, i) -> {
                        ProgressDialog d= new ProgressDialog(context);
                        d.setTitle("Suspression en cours...");
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
                                    userMap.put("codeOffre", snapshot.child("codeOffre").getValue().toString());
                                    userMap.put("userName", snapshot.child("userName").getValue().toString());
                                    userMap.put("prix", snapshot.child("prix").getValue().toString());
                                    //  userMap.put("periode", snapshot.child("periode").getValue().toString());
                                    userMap.put("marge", snapshot.child("marge").getValue().toString());

                                    userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()){
                                            reference.removeValue().addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()){
                                                    Toast.makeText(context, "Maison disponible!", Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, AjouterVente.class));
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

        TextView code, prix, marg,ville, quartier, btn, type;
        ImageView img, img1, img2, img3, img4;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            code= itemView.findViewById(R.id.vms_code);
            prix= itemView.findViewById(R.id.vms_prix);
            marg= itemView.findViewById(R.id.vms_marg);
            ville= itemView.findViewById(R.id.vms_ville);
            quartier= itemView.findViewById(R.id.vms_quartier);
            btn= itemView.findViewById(R.id.vms_susp);
            img= itemView.findViewById(R.id.vms_img);
            img1= itemView.findViewById(R.id.vms_img1);
            img2= itemView.findViewById(R.id.vms_img2);
            img3= itemView.findViewById(R.id.vms_img3);
            img4= itemView.findViewById(R.id.vms_img4);
            type= itemView.findViewById(R.id.vms_type);
        }
    }
}
