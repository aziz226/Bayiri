package com.pp.bayiri.BottomNavFrag;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pp.bayiri.LocationFrag.AubergeFrag;
import com.pp.bayiri.LocationFrag.HotelFrag;
import com.pp.bayiri.LocationFrag.MaisonFrag;
import com.pp.bayiri.LocationFrag.ResidentFrag;
import com.pp.bayiri.R;

public class LocationFrag extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    private LinearLayout l1, l2, l3, l4;
    private TextView mais, red, hot, aub;
    private View v1, v2, v3, v4;


    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.location_frag, container, false);

      /*  tabLayout= view.findViewById(R.id.tab_layout);
        viewPager= view.findViewById(R.id.view_pager);

        ArrayList<String > arrayList= new ArrayList<>();
        arrayList.add("Maisons");
        arrayList.add("Résidences");
        arrayList.add("Hotels");
        arrayList.add("Auberges");

        MainAdapter adapter= new MainAdapter(getActivity().getSupportFragmentManager());
        MaisonFrag fragment= new MaisonFrag();
        adapter.addFragment(fragment, arrayList.get(0));
        fragment= new MaisonFrag();
        //adapter.addFragment(new MaisonFrag(), "Maisons");
        viewPager.setAdapter(adapter);

        prepareViewPager(viewPager, arrayList);
        tabLayout.setupWithViewPager(viewPager); */

        l1= view.findViewById(R.id.mais_lay);
        l2= view.findViewById(R.id.red_lay);
        l3= view.findViewById(R.id.hot_lay);
        l4= view.findViewById(R.id.aub_lay);

        mais= view.findViewById(R.id.loc_mais);
        red= view.findViewById(R.id.loc_red);
        hot= view.findViewById(R.id.loc_hot);
        aub= view.findViewById(R.id.loc_aub);

        v1= view.findViewById(R.id.v1);
        v2= view.findViewById(R.id.v2);
        v3= view.findViewById(R.id.v3);
        v4= view.findViewById(R.id.v4);

           ReplaceFrag(new MaisonFrag());

        l1.setOnClickListener(view1 -> {
            mais.setTextColor(Color.BLACK);
            red.setTextColor(R.color.text_normal_color);
            hot.setTextColor(R.color.text_normal_color);
            aub.setTextColor(R.color.text_normal_color);

            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);

            ReplaceFrag(new MaisonFrag());
        });
        l2.setOnClickListener(view12 -> {
            mais.setTextColor(R.color.text_normal_color);
            red.setTextColor(Color.BLACK);
            hot.setTextColor(R.color.text_normal_color);
            aub.setTextColor(R.color.text_normal_color);

            v1.setVisibility(View.GONE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);

            ReplaceFrag(new ResidentFrag());
        });
        l3.setOnClickListener(view13 -> {
            mais.setTextColor(R.color.text_normal_color);
            red.setTextColor(R.color.text_normal_color);
            hot.setTextColor(Color.BLACK);
            aub.setTextColor(R.color.text_normal_color);

            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.GONE);

            ReplaceFrag(new HotelFrag());
        });
        l4.setOnClickListener(view14 -> {
            mais.setTextColor(R.color.text_normal_color);
            red.setTextColor(R.color.text_normal_color);
            hot.setTextColor(R.color.text_normal_color);
            aub.setTextColor(Color.BLACK);

            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.VISIBLE);

            ReplaceFrag(new AubergeFrag());
        });

        return view;
    }

    /*  private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter= new MainAdapter(getActivity().getSupportFragmentManager());

        MaisonFrag fragment= new MaisonFrag();
        ResidentFrag resid= new ResidentFrag();
        HotelFrag hotel= new HotelFrag();
        AubergeFrag auberg= new AubergeFrag();

        for (int i=0; i<arrayList.size(); i++){
            if (i==0){
                Bundle bundle= new Bundle();
                bundle.putString("title", arrayList.get(i));

                fragment.setArguments(bundle);
                adapter.addFragment(fragment, arrayList.get(i));
                fragment= new MaisonFrag();
            }else if (i==1){
                Bundle bundle= new Bundle();
                bundle.putString("title", arrayList.get(i));

                resid.setArguments(bundle);
                adapter.addFragment(resid, arrayList.get(i));
                resid= new ResidentFrag();
            }else if (i==2){
                Bundle bundle= new Bundle();
                bundle.putString("title", arrayList.get(i));

                hotel.setArguments(bundle);
                adapter.addFragment(hotel, arrayList.get(i));
                hotel= new HotelFrag();
            }else if (i==3){
                Bundle bundle= new Bundle();
                bundle.putString("title", arrayList.get(i));

                auberg.setArguments(bundle);
                adapter.addFragment(auberg, arrayList.get(i));
                auberg= new AubergeFrag();
            }
        }

        viewPager.setAdapter(adapter);
    } */


    private void ReplaceFrag(Fragment fragment){
        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.loc_framL, fragment);
        transaction.commit();
    }


}
