package com.pp.bayiri.AdminFragment;

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

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.R;
import com.pp.bayiri.Suspend.TerSusp;
import com.pp.bayiri.VenteClass.TerrAdAdapter;
import com.pp.bayiri.VenteClass.TmClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdVenteTerrain extends Fragment {

    private Button btn;
    private TextView enreg, susp, disp, btn_rech;
    private SearchView rech;
    private RecyclerView recyclerView;
    private List<TmClass> list, list1;
    private TerrAdAdapter adArticle;
    private DatabaseReference userRef, reference;
    private String uName, ville;
    private long n;
    private LinearLayout suspLay;
    private int nbr2, nbr1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.ad_vente_terrain, container, false);

        btn= view.findViewById(R.id.id_vent_ter_aj);
        enreg= view.findViewById(R.id.idt_enreg);
        susp= view.findViewById(R.id.idt_susp);
        disp= view.findViewById(R.id.idt_dip);
        rech= view.findViewById(R.id.ad_t_rech);
        recyclerView= view.findViewById(R.id.ad_vent_ter_recycl);
        suspLay= view.findViewById(R.id.vt_susp_lay);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.vente).child("Terrain");

        reference= FirebaseDatabase.getInstance().getReference().child(Constant.User)
                .child(Constant.susp).child(Constant.vente).child("Terrain");


        uName= getArguments().getString("un");
        ville= getArguments().getString("v");

        LinearLayoutManager manager= new LinearLayoutManager(getActivity());
        // manager.setStackFromEnd(true);
        //manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        list= new ArrayList<>();
        list1= new ArrayList<>();
        adArticle= new TerrAdAdapter(getActivity(), list);
         loadPost();
        recyclerView.setAdapter(adArticle);

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
        enreg.setText(""+(nbr1+nbr2));

        btn.setOnClickListener(view1 -> {
            Intent intent =new Intent(getActivity(), AjoutTerrain.class);
            intent.putExtra("userName", uName);
            intent.putExtra("v", ville);
            intent.putExtra("maison", "Maison");
            startActivity(intent);
        });

        suspLay.setOnClickListener(view12 -> {
            Intent intent= new Intent(getActivity(), TerSusp.class);
            intent.putExtra("un", uName);
            startActivity(intent);
        });

        return view;
    }
    private void SearchPost(String query) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    TmClass articleClass= ds.getValue(TmClass.class);
                    if (articleClass.getUserName().equals(uName)){
                        if (articleClass.getCodeOffre().toLowerCase().contains(query.toLowerCase())||
                                articleClass.getVille().toLowerCase().contains(query.toLowerCase())||
                                articleClass.getQuartier().toLowerCase().contains(query.toLowerCase())||
                                articleClass.getType().toLowerCase().contains(query.toLowerCase())

                        ) {

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
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        list1.clear();
                        for (DataSnapshot ds:snapshot1.getChildren()){
                            TmClass hotelClass= ds.getValue(TmClass.class);
                            if (hotelClass.getUserName().equals(uName)){
                                list1.add(hotelClass);
                            }
                        }
                        susp.setText(""+list1.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    TmClass articleClass= ds.getValue(TmClass.class);
                    if (articleClass.getUserName().equals(uName)){
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
