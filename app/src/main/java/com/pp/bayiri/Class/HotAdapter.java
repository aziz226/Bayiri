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


import com.pp.bayiri.Details.HotelDetail;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyHolder> {
        Context context;
        List<HotelClass> list;

public HotAdapter(Context context, List<HotelClass> list) {
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_auberg, parent, false);

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

        holder.cv.setOnClickListener(view -> {
            Intent intent= new Intent(context, HotelDetail.class);
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

class MyHolder extends RecyclerView.ViewHolder{

    TextView code, nom, loyer, caution,ville, quartier,appel, whats, email, teleg;
    ImageView img, img1, img2, img3, img4;
    CardView cv;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        nom= itemView.findViewById(R.id.id_item_loc_aub_nom);
        code= itemView.findViewById(R.id.id_item_loc_aub_code);
        loyer= itemView.findViewById(R.id.id_item_aub_prix_heur);
        caution= itemView.findViewById(R.id.id_item_aub_prix_jour);
        ville= itemView.findViewById(R.id.id_item_aub_ville);
        quartier= itemView.findViewById(R.id.id_item_aub_quartier);
        cv= itemView.findViewById(R.id.id_cv);

        img= itemView.findViewById(R.id.id_item_loc_aub_img);
        img1= itemView.findViewById(R.id.id_item_loc_aub_h_img1);
        img2= itemView.findViewById(R.id.id_item_loc_aub_h_im2);
        img3= itemView.findViewById(R.id.id_item_loc_aub_h_img3);
        img4= itemView.findViewById(R.id.id_item_loc_aub_h_img4);

        appel= itemView.findViewById(R.id.item_appelab);
        whats= itemView.findViewById(R.id.item_whatsab);
        email= itemView.findViewById(R.id.item_emailab);
        teleg= itemView.findViewById(R.id.item_telegab);

    }
}
}
