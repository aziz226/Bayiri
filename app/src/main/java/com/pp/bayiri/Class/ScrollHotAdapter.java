package com.pp.bayiri.Class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Details.HotelDetail;
import com.pp.bayiri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScrollHotAdapter extends RecyclerView.Adapter<ScrollHotAdapter.MyHolder> {
    Context context;
    List<ScrollClass> list;

    public ScrollHotAdapter(Context context, List<ScrollClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_hori_scroll, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String im= list.get(position).getImg();
        String code= list.get(position).getCodeOffre();

        try{
            Picasso.get().load(im).placeholder(R.drawable.ic_panorama).into(holder.img);
        }catch (Exception e){}

        holder.img.setOnClickListener(view -> {
            Intent intent= new Intent(context, HotelDetail.class);
            intent.putExtra("code", ""+code);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        CircleImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.hor_item);
        }
    }
}
