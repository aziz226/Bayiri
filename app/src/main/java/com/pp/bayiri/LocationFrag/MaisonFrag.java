package com.pp.bayiri.LocationFrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pp.bayiri.Class.Constant;
import com.pp.bayiri.Class.HomeAdapteur;
import com.pp.bayiri.Class.HomeClass;
import com.pp.bayiri.Class.ScrollAdapter;
import com.pp.bayiri.Class.ScrollClass;
import com.pp.bayiri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MaisonFrag extends Fragment {

    private AutoCompleteTextView ville, type;
    private CardView rechCard;
    private RecyclerView horiRecy, MainRecy;
    private TextView mainPay, mainVill, mainType;
    private DatabaseReference userRef;

    private List<HomeClass> list;
    private HomeAdapteur adapteur;
    private List<ScrollClass> classList;
    private ScrollAdapter scrollAdapter;
    private String[] Type= {"Habitation","Industriel", "Commercial"};
    private String[] Ville= {"Ouagadougou", "Tenkodogo"};
    private String selectType="Tous", selectVille="Toutes";

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.maison_frag, container, false);

        ville= view.findViewById(R.id.spinner_ville);
        type= view.findViewById(R.id.spinner_type);
       // rechCard= view.findViewById(R.id.searc_card);
        horiRecy= view.findViewById(R.id.hori_recyc);
        MainRecy= view.findViewById(R.id.main_recyc);
        mainPay= view.findViewById(R.id.main_pays);
        mainVill= view.findViewById(R.id.main_ville);
        mainType= view.findViewById(R.id.main_maisn_typ);
       // horiRecy= view.findViewById(R.id.hori_recyc);

        //Methode pour les 2 spinner(ville et type)
        ArrayAdapter<String> adapter= new ArrayAdapter<>(
                getActivity(), R.layout.drop_down_item, Ville
        );
        ArrayAdapter<String> adap= new ArrayAdapter<>(
                getActivity(), R.layout.drop_down_item, Type
        );
         ville.setAdapter(adapter);
         type.setAdapter(adap);

        userRef= FirebaseDatabase.getInstance().getReference()
                .child(Constant.User).child(Constant.location).child("Maison");

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        //layoutManager.canScrollHorizontally();
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horiRecy.setLayoutManager(layoutManager);

        LinearLayoutManager manager= new LinearLayoutManager(getActivity() );
        //manager.setStackFromEnd(true);
       // manager.setReverseLayout(true);
        MainRecy.setLayoutManager(manager);

        classList= new ArrayList<>();
        scrollAdapter= new ScrollAdapter(getActivity(), classList);
        horiRecy.setAdapter(scrollAdapter);

        list= new ArrayList<>();
        adapteur= new HomeAdapteur(getActivity(), list);
        MainRecy.setAdapter(adapteur);

        mainType.setText(""+selectType);
        mainVill.setText(""+selectVille);

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
        });

      /*  rechCard.setOnClickListener(view13 -> {
            Toast.makeText(getActivity(), ""+selectVille, Toast.LENGTH_SHORT).show();

        }); */

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    HomeClass homeClass= ds.getValue(HomeClass.class);
                    list.add(homeClass);
                }
                adapteur.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ville.setOnItemClickListener((adapterView, view1, i, l) -> {
            list.clear();
            classList.clear();
            selectVille= ville.getText().toString();
            Toast.makeText(getActivity(), ""+selectVille, Toast.LENGTH_SHORT).show();
            mainVill.setText(""+selectVille);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        HomeClass homeClass= ds.getValue(HomeClass.class);
                        ScrollClass scrollClass= ds.getValue(ScrollClass.class);
                        if (homeClass.getVille().equals(selectVille)|| homeClass.getType().equals(selectType)){
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
        });

        type.setOnItemClickListener((adapterView, view12, i, l) -> {
            list.clear();
            classList.clear();
            selectType= type.getText().toString();
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()){
                        HomeClass homeClass= ds.getValue(HomeClass.class);
                        ScrollClass scrollClass= ds.getValue(ScrollClass.class);
                        if (homeClass.getVille().equals(selectVille)|| homeClass.getType().equals(selectType)){
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
        });

        return view;
    }


}
