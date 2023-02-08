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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HomeClass;
import com.pp.bayiri.Class.ResAdapter;
import com.pp.bayiri.Class.ScrollClass;
import com.pp.bayiri.Class.ScrollResAdapter;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResidentFrag extends Fragment {

    private CardView rechCard;
    private RecyclerView horiRecy, MainRecy;
    private TextView mainPay, mainVill, mainType;
    private DatabaseReference userRef;

    private List<HomeClass> list;
    private ResAdapter adapteur;
    private List<ScrollClass> classList;
    private ScrollResAdapter scrollAdapter;
    private SearchView searchView;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.resident_frag, container, false);


        //rechCard= view.findViewById(R.id.res_searc_card);
        horiRecy= view.findViewById(R.id.hori_res_recyc);
        MainRecy= view.findViewById(R.id.res_recyc);
        mainPay= view.findViewById(R.id.res_pays);
        mainVill= view.findViewById(R.id.res_ville);
        searchView= view.findViewById(R.id.research);
       // searchView.clearFocus();
        //mainType= view.findViewById(R.id.main_maisn_typ);
        //horiRecy= view.findViewById(R.id.hori_recyc);




        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Residences");

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        //layoutManager.canScrollHorizontally();
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horiRecy.setLayoutManager(layoutManager);

        LinearLayoutManager manager= new LinearLayoutManager(getActivity() );
        //manager.setStackFromEnd(true);
        // manager.setReverseLayout(true);
        MainRecy.setLayoutManager(manager);

        classList= new ArrayList<>();
        scrollAdapter= new ScrollResAdapter(getActivity(), classList);
        horiRecy.setAdapter(scrollAdapter);

        list= new ArrayList<>();
        adapteur= new ResAdapter(getActivity(), list);
        MainRecy.setAdapter(adapteur);

        /*
        userRef.addValueEventListener(new ValueEventListener() {
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        return view;
    }

    private void SearchPost(String query) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                classList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    HomeClass homeClass= ds.getValue(HomeClass.class);
                    ScrollClass scrollClass= ds.getValue(ScrollClass.class);

                    if (homeClass.getType().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getCodeOffre().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getVille().toLowerCase().contains(query.toLowerCase())||
                            homeClass.getQuartier().toLowerCase().contains(query.toLowerCase())
                    ){
                        list.add(homeClass);
                        classList.add(scrollClass);
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
                    HomeClass homeClass= ds.getValue(HomeClass.class);
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
