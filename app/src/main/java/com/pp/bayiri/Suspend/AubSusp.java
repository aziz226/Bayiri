package com.pp.bayiri.Suspend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HotelClass;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AubSusp extends AppCompatActivity {

    private SearchView rech;
    private RecyclerView recyclerView;
    private List<HotelClass> list;
    private AbAdapter modelAdArticle;
    private DatabaseReference reference;
    private String uName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aub_susp);

        rech= findViewById(R.id.ab_recher);
        recyclerView= findViewById(R.id.ab_mais_recycl);

        Bundle b= getIntent().getExtras();
        uName= b.getString("un");

        reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.susp).child(Constant.location).child("Auberges");

        LinearLayoutManager manager= new LinearLayoutManager(this);
        // manager.setStackFromEnd(true);
        //manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        list= new ArrayList<>();
        modelAdArticle= new AbAdapter(this, list);
        // LoadHouse();
        recyclerView.setAdapter(modelAdArticle);

        rech.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)){
                    SearchPost(query);
                }else{
                    loadPost();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    SearchPost(newText);
                }else{
                    loadPost();
                }
                return false;
            }
        });
        loadPost();

    }

    private void loadPost() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    HotelClass articleClass= ds.getValue(HotelClass.class);
                    if (articleClass.getUserName().equals(uName)){
                        list.add(articleClass);
                    }
                }
                modelAdArticle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SearchPost(String query) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HotelClass articleClass = ds.getValue(HotelClass.class);
                    if (articleClass.getUserName().equals(uName)) {
                        if (articleClass.getNomHotel().toLowerCase().contains(query.toLowerCase()) ||
                                articleClass.getCodeOffre().toLowerCase().contains(query.toLowerCase()) ||
                                articleClass.getVille().toLowerCase().contains(query.toLowerCase()) ||
                                articleClass.getQuartier().toLowerCase().contains(query.toLowerCase())
                        ) {
                            list.add(articleClass);
                        }
                    }
                }
                modelAdArticle.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}