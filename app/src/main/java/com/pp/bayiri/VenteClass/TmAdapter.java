package com.pp.bayiri.VenteClass;

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

import com.pp.bayiri.Details.VentMaisDetail;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TmAdapter extends RecyclerView.Adapter<TmAdapter.MyHolder> {
    Context context;
    List<TmClass> list;

    public TmAdapter(Context context, List<TmClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.vent_item, parent, false);
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

        holder.car.setOnClickListener(view -> {
            Intent intent= new Intent(context, VentMaisDetail.class);
            intent.putExtra("c", c);
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

        TextView code, prix, marg,ville, quartier, type;
        ImageView img, img1, img2, img3, img4, appel, whats, email, teleg;
        CardView car;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            code= itemView.findViewById(R.id.v_cod);
            prix= itemView.findViewById(R.id.v_pri);
            marg= itemView.findViewById(R.id.v_marg);
            ville= itemView.findViewById(R.id.v_vil);
            quartier= itemView.findViewById(R.id.v_quar);
            img= itemView.findViewById(R.id.v_img);
            img1= itemView.findViewById(R.id.v_img1);
            img2= itemView.findViewById(R.id.v_img2);
            img3= itemView.findViewById(R.id.v_img3);
            img4= itemView.findViewById(R.id.v_img4);
            type= itemView.findViewById(R.id.v_dispo);
            car= itemView.findViewById(R.id.v_card);

            appel= itemView.findViewById(R.id.item_appelvt);
            whats= itemView.findViewById(R.id.item_whatsvt);
            email= itemView.findViewById(R.id.item_emailvt);
            teleg= itemView.findViewById(R.id.item_telegvt);
        }
    }
}
