package com.pp.bayiri.BottomNavFrag;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pp.bayiri.R;
import com.pp.bayiri.VentFragment.MaisVentFrag;
import com.pp.bayiri.VentFragment.TerrainFrag;

public class VenteFrag extends Fragment {

   // private TabLayout tabLayout;
   // private ViewPager viewPager;

    private RelativeLayout l1, l2;
    private TextView mais, ter;
    private View v1, v2;


    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.vente_frag, container, false);

       /* tabLayout= view.findViewById(R.id.vent_tab_layout);
        viewPager= view.findViewById(R.id.vent_view_pager);

        ArrayList<String > arrayList= new ArrayList<>();
        arrayList.add("Maisons");
        arrayList.add("Terrains");

        prepareViewPager(viewPager, arrayList);
        tabLayout.setupWithViewPager(viewPager); */
        l1= view.findViewById(R.id.vm_lay);
        l2= view.findViewById(R.id.ter_lay);
        mais= view.findViewById(R.id.vent_mais);
        ter= view.findViewById(R.id.vent_ter);
        v1= view.findViewById(R.id.vv1);
        v2= view.findViewById(R.id.vv2);

        ReplaceFrag(new MaisVentFrag());

       l1.setOnClickListener(view1 -> {
           mais.setTextColor(Color.BLACK);
           ter.setTextColor(R.color.text_normal_color);
           v1.setVisibility(View.VISIBLE);
           v2.setVisibility(View.GONE);

           ReplaceFrag(new MaisVentFrag());
       });
        l2.setOnClickListener(view12 -> {
            mais.setTextColor(R.color.text_normal_color);
            ter.setTextColor(Color.BLACK);
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.VISIBLE);

            ReplaceFrag(new TerrainFrag());
        });

        return view;
    }

    private void ReplaceFrag(Fragment fragment){
        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.vent_framL, fragment);
        transaction.commit();
    }

    /*private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter= new MainAdapter(getActivity().getSupportFragmentManager());

        MaisonFrag fragment= new MaisonFrag();
        ResidentFrag resid= new ResidentFrag();


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
            }
        }

        viewPager.setAdapter(adapter);
    } */
}
