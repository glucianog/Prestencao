package com.example.ana.volunteerwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tulio on 10/12/17.
 */

public class MotoristaFragment extends Fragment {



    public MotoristaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mmotorista, container, false);
        getActivity().setTitle("Modo Motorista");

        return view;
    }



}
