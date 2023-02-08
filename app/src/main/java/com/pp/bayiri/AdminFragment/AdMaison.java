package com.pp.bayiri.AdminFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp.bayiri.Class.AddArticleClass;
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.ModelAdArticle;
import com.pp.bayiri.R;
import com.pp.bayiri.Suspend.HomeSusp;

import java.util.ArrayList;
import java.util.List;

public class AdMaison extends Fragment {

    private Button btn;
    private TextView enreg, susp, disp, btn_rech;
    private SearchView rech;
    private RecyclerView recyclerView;
    private List<AddArticleClass> list, list1;
    private ModelAdArticle adArticle;
    private DatabaseReference userRef, reference;
    private String uName, ville;
    private long n;
    private LinearLayout suspLay;
    private int nbr2, nbr1;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.ad_maison, container, false);

        btn= view.findViewById(R.id.id_mais_aj);
        enreg= view.findViewById(R.id.id_enreg);
        susp= view.findViewById(R.id.id_susp);
        disp= view.findViewById(R.id.id_dip);
        rech= view.findViewById(R.id.ad_hom_rech);
        recyclerView= view.findViewById(R.id.ad_mais_recycl);
        suspLay= view.findViewById(R.id.susp_lay);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Maison");

         reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
         .child(Constant.susp).child(Constant.location).child("Maison");


        uName= getArguments().getString("un");
        ville= getArguments().getString("v");

        LinearLayoutManager manager= new LinearLayoutManager(getActivity());
       // manager.setStackFromEnd(true);
        //manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        list= new ArrayList<>();
        list1= new ArrayList<>();
         adArticle= new ModelAdArticle(getActivity(), list);
       // LoadHouse();
        recyclerView.setAdapter(adArticle);

        rech.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)){
                    SearcPost(query);
                }else {
                    loadPost();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    SearcPost(newText);
                }else {
                    loadPost();
                }
                return false;
            }
        });

        loadPost();
        enreg.setText(""+(nbr1+nbr2));

        btn.setOnClickListener(view1 -> {
            Intent intent =new Intent(getActivity(), AddArticles.class);
            intent.putExtra("userName", uName);
            intent.putExtra("v", ville);
            intent.putExtra("maison", "Maison");
            startActivity(intent);
        });

        suspLay.setOnClickListener(view12 -> {
            Intent intent= new Intent(getActivity(), HomeSusp.class);
            intent.putExtra("un", uName);
            startActivity(intent);
        });

        return view;
    }

    private void SearcPost(String query) {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    AddArticleClass articleClass= ds.getValue(AddArticleClass.class);

                    if (articleClass.getUserName().equals(uName)){
                        if (articleClass.getType().toLowerCase().contains(query.toLowerCase())||
                            articleClass.getCodeOffre().toLowerCase().contains(query.toLowerCase())||
                            articleClass.getVille().toLowerCase().contains(query.toLowerCase())||
                            articleClass.getQuartier().toLowerCase().contains(query.toLowerCase())){
                            list.add(articleClass);
                        }
                    }
                }
                adArticle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPost() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                list1.clear();
                for (DataSnapshot ds: snapshot1.getChildren()){
                    AddArticleClass a= ds.getValue(AddArticleClass.class);
                    if (a.getUserName().equals(uName)){
                        list1.add(a);
                    }
                }
                nbr2 = list1.size();
                susp.setText(""+ nbr2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    AddArticleClass articleClass= ds.getValue(AddArticleClass.class);
                    if (articleClass.getUserName().equals(uName)){
                    list.add(articleClass);
                    }
                }
                nbr1= list.size();
                disp.setText(""+nbr1);
                adArticle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
