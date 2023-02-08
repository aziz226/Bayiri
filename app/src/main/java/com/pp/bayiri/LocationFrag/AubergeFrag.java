package com.pp.bayiri.LocationFrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HotAubAdapter;
import com.pp.bayiri.Class.HotelClass;
import com.pp.bayiri.Class.ScrollAubAdapter;
import com.pp.bayiri.Class.ScrollClass;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AubergeFrag extends Fragment {

    private SearchView rechCard;
    private RecyclerView horiRecy, MainRecy;
    private TextView mainPay, mainVill, mainType;
    private DatabaseReference userRef;

    private List<HotelClass> list;
    private HotAubAdapter adapteur;
    private List<ScrollClass> classList;
    private ScrollAubAdapter scrollAdapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.auberge_frag, container, false);

        rechCard= view.findViewById(R.id.aub_rech);
        horiRecy= view.findViewById(R.id.hori_a_recyc);
        MainRecy= view.findViewById(R.id.a_recyc);
        mainPay= view.findViewById(R.id.a_pays);
        mainVill= view.findViewById(R.id.a_ville);
        //mainType= view.findViewById(R.id.main_maisn_typ);
        //horiRecy= view.findViewById(R.id.hori_recyc);


        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Auberges");

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        //layoutManager.canScrollHorizontally();
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horiRecy.setLayoutManager(layoutManager);

        LinearLayoutManager manager= new LinearLayoutManager(getActivity() );
        //manager.setStackFromEnd(true);
        // manager.setReverseLayout(true);
        MainRecy.setLayoutManager(manager);

        classList= new ArrayList<>();
        scrollAdapter= new ScrollAubAdapter(getActivity(), classList);
        horiRecy.setAdapter(scrollAdapter);

        list= new ArrayList<>();
        adapteur= new HotAubAdapter(getActivity(), list);
        MainRecy.setAdapter(adapteur);

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

        rechCard.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)){
                    SearchPost(query);
                }else {
                    loadPost();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    SearchPost(newText);
                }else {
                    loadPost();
                }
                return false;
            }
        });

        loadPost();

        return view;
    }

    private void SearchPost(String query) {
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
                list.clear();
                classList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    HotelClass homeClass= ds.getValue(HotelClass.class);
                    ScrollClass scroll= ds.getValue(ScrollClass.class);

                    classList.add(scroll);
                    list.add(homeClass);
                }
                adapteur.notifyDataSetChanged();
                scrollAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
