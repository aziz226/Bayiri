package com.pp.bayiri.Class;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Details.Details;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapteur extends RecyclerView.Adapter<HomeAdapteur.MyHolder> {

    Context context;
    List<HomeClass> list;

    public HomeAdapteur(Context context, List<HomeClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_home_loc, parent, false);

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
        String ob= list.get(position).getPeriode();
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
        holder.oblig.setText(ob);

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

        holder.cardV.setOnClickListener(view -> {
            Intent intent= new Intent(context, Details.class);
            intent.putExtra("code", c);
            context.startActivity(intent);
        });

        holder.appel.setOnClickListener(v -> {
            Intent intent= new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:+22675677838"));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},
                        1);
            }else {
                context.startActivity(intent);
            }
        });
        holder.email.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "tpplusservice@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "");
            email.putExtra(Intent.EXTRA_TEXT, "");

            //need this to prompts email client only
            email.setType("message/rfc822");

            context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
        });
        holder.whats.setOnClickListener(v -> {
            Intent in= new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/message/7XTQ3RXFJBRCH1"));
            context.startActivity(in);
        });
        holder.teleg.setOnClickListener(v -> {
            Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/ximmo0022675677838"));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView code, type, loyer, caution,ville, quartier, oblig;
        ImageView img, img1, img2, img3, img4, appel, whats, email, teleg;
        CardView cardV;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            code= itemView.findViewById(R.id.id_item_code);
            type= itemView.findViewById(R.id.id_item_type);
            loyer= itemView.findViewById(R.id.id_item_loyer);
            caution= itemView.findViewById(R.id.id_item_caution);
            ville= itemView.findViewById(R.id.id_item_ville);
            quartier= itemView.findViewById(R.id.id_item_quartier);
            oblig= itemView.findViewById(R.id.id_item_obli);
            img= itemView.findViewById(R.id.id_item_hous_img);
            img1= itemView.findViewById(R.id.id_item_h_img1);
            img2= itemView.findViewById(R.id.id_item_h_img2);
            img3= itemView.findViewById(R.id.id_item_h_img3);
            img4= itemView.findViewById(R.id.id_item_h_img4);
            cardV= itemView.findViewById(R.id.cv);

            appel= itemView.findViewById(R.id.item_appel);
            whats= itemView.findViewById(R.id.item_whats);
            email= itemView.findViewById(R.id.item_email);
            teleg= itemView.findViewById(R.id.item_teleg);
        }
    }
}
