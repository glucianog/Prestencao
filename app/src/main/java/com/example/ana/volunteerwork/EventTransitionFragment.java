package com.example.ana.volunteerwork;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventTransitionFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    //private static final String ARG_PARAM = "";


    public EventTransitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_transition, container, false);

        //subjectName = this.getArguments().getString("subjectName");
        getActivity().setTitle("Ocorrências");

        //View contenedor = (View)container.getParent();
        //appBar = (AppBarLayout)contenedor.findViewById(R.id.appbar);
        appBar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        tabLayout = new TabLayout(getActivity());

        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(tabLayout);

        viewPager = (ViewPager)view.findViewById(R.id.pager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(tabLayout);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter (FragmentManager fragmentManager) {
            super (fragmentManager);
        }
        String [] tituloTabs= {"Feitos por Mim", "Ocorrências"};

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: return new Tab_OrganizandoFragment();
                case 1: return new Tab_EventosFragment();
                //case 2: return new Tab_EventosFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tituloTabs[position];
        }
    }

}
