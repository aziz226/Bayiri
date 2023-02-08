package com.pp.bayiri.AdminFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.ResidAdapter;
import com.pp.bayiri.Class.ResidClass;
import com.pp.bayiri.R;
import com.pp.bayiri.Suspend.ResidSusp;

import java.util.ArrayList;
import java.util.List;

public class AdResidences extends Fragment {

    private TextView tv;
    private TextView enreg, susp, disp;
    private SearchView rech;
    private RecyclerView recyclerView;
    private List<ResidClass> list, list1;
    private ResidAdapter adArticle;
    private DatabaseReference userRef, reference;
    private LinearLayout lay;
    private String uName;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.ad_residence, container, false);

        tv= view.findViewById(R.id.id_resid_aj);
        enreg= view.findViewById(R.id.id_resi_enreg);
        susp= view.findViewById(R.id.id_resid_susp);
        disp= view.findViewById(R.id.id_resid_dip);
        rech= view.findViewById(R.id.ad_resi_rech);
        recyclerView= view.findViewById(R.id.ad_res_recycl);
        lay= view.findViewById(R.id.rsp_lay);

        String ville= getArguments().getString("v");
         uName= getArguments().getString("un");

         userRef= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.location).child("Residences");

         reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.susp).child(Constant.location).child("Residences");

        LinearLayoutManager manager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        list= new ArrayList<>();
        list1= new ArrayList<>();
        adArticle= new ResidAdapter(getActivity(), list);
        recyclerView.setAdapter(adArticle);

        tv.setOnClickListener(view1 -> {
            Intent intent =new Intent(getActivity(), AjoutResid.class);

            intent.putExtra("v", ville);
            intent.putExtra("un", uName);
            startActivity(intent);
        });

        rech.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        lay.setOnClickListener(view12 -> {
            Intent i= new Intent(getActivity(), ResidSusp.class);
            i.putExtra("un", uName);
            startActivity(i);
        });

        return view;
    }

    private void SearchPost(String query) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ResidClass articleClass= ds.getValue(ResidClass.class);
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
                   ResidClass residClass= ds.getValue(ResidClass.class);
                   if (residClass.getUserName().equals(uName)){
                       list1.add(residClass);
                   }
               }
               susp.setText(""+list1.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ResidClass articleClass= ds.getValue(ResidClass.class);
                    if (articleClass.getUserName().equals(uName)) {
                        list.add(articleClass);
                    }
                }
                disp.setText(""+list.size());
                adArticle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
