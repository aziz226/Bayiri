package com.pp.bayiri.LocationFrag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HotelClass;
import com.pp.bayiri.Class.ScrollClass;
import com.pp.bayiri.Class.ScrollHotAdapter;
import com.pp.bayiri.Details.HotelDetail;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HotelFrag extends Fragment {

    private SearchView rechCard;
    private RecyclerView horiRecy, MainRecy;
    private TextView mainPay, mainVill, mainType;
    private DatabaseReference userRef;

    private ArrayList<HotelClass> list;
   // private HotAdapter adapteur;
    private ArrayList<ScrollClass> classList;
    private ScrollHotAdapter scrollAdapter;
    private HotelAdap adapteur;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.hotel_frag, container, false);

        rechCard= view.findViewById(R.id.hotel_rech);
        horiRecy= view.findViewById(R.id.hori_h_recyc);
        MainRecy= view.findViewById(R.id.h_recyc);
        mainPay= view.findViewById(R.id.h_pays);
        mainVill= view.findViewById(R.id.h_ville);
        //mainType= view.findViewById(R.id.main_maisn_typ);
        //horiRecy= view.findViewById(R.id.hori_recyc);


        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Hotels");

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        //layoutManager.canScrollHorizontally();
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horiRecy.setLayoutManager(layoutManager);

        LinearLayoutManager manager= new LinearLayoutManager(getActivity() );
        //manager.setStackFromEnd(true);
        // manager.setReverseLayout(true);
        MainRecy.setLayoutManager(manager);

        classList= new ArrayList<>();
        scrollAdapter= new ScrollHotAdapter(getActivity(), classList);
        horiRecy.setAdapter(scrollAdapter);

        list= new ArrayList<>();
        //adapteur= new HotAdapter(getActivity(), list);
        adapteur= new HotelAdap(list, getActivity());
        MainRecy.setAdapter(adapteur);



        rechCard.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)){
                    resarchPost(query);
                }else {
                    loadPost();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    resarchPost(newText);
                }else {
                    loadPost();
                }
                return false;
            }
        });

       /* userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    ScrollClass scroll= ds.getValue(ScrollClass.class);
                    classList.add(scroll);
                }
                scrollAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */

        loadPost();

        return view;
    }

    private void resarchPost(String query) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                classList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    HotelClass homeClass= ds.getValue(HotelClass.class);
                    ScrollClass scroll= ds.getValue(ScrollClass.class);

                    if (homeClass.getNomHotel().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getCodeOffre().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getVille().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getQuartier().toLowerCase().contains(query.toLowerCase())

                    ) {

                        list.add(homeClass);
                        classList.add(scroll);
                    }
                }
                adapteur.notifyDataSetChanged();
                scrollAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPost() {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                   // list.clear();
                   // classList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){

                        try{
                            HotelClass homeClass= ds.getValue(HotelClass.class);

                            ScrollClass scroll= ds.getValue(ScrollClass.class);

                            list.add(homeClass);
                            classList.add(scroll);

                       }catch (Exception e){
                           Toast.makeText(getActivity(), "Erreur de chargement", Toast.LENGTH_SHORT).show();
                       }

                    }

                    adapteur.notifyDataSetChanged();
                    scrollAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private class HotelAdap extends RecyclerView.Adapter<HotelAdap.MyHolder>{

        List<HotelClass> list;
        Context context;

        public HotelAdap(List<HotelClass> list, Context context) {
            this.list = list;
            this.context = context;
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

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder{

            TextView code, nom, loyer, caution,ville, quartier;
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

            }
        }

    }

}
