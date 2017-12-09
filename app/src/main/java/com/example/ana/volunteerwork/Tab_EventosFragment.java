package com.example.ana.volunteerwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gabri on 05/12/2017.
 */

public class Tab_EventosFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Evento> listaDeEventos =  new ArrayList<>();
    public static final int RESULT_OK = 0;

    DatabaseReference db;
    Database helper;


    public Tab_EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab__eventos, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(listaDeEventos);
        recyclerView.setAdapter(recyclerViewAdapter);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new Database(db);

        db.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                listaDeEventos.clear();
                for(DataSnapshot child : children) {
                    Evento evento = child.getValue(Evento.class);
                    //Toast.makeText(getApplicationContext(), evento.getNome(), Toast.LENGTH_SHORT).show();
                    listaDeEventos.add(evento);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





//        listaDeEventos = new ArrayList<Evento>();
//        Evento evento = new Evento ("Festa da Ana", "Melhor Festa!", "04/12/2017","05/12/2017", "10:00", "10:00", "Rua Pistóia, 325");
//        Evento eventoNovo = new Evento ("Festa do Gabriel", "Uhuuul!", "10/12/2017","11/12/2017", "10:00", "10:00", "Rua Gongalves Dias, 2132");
//        listaDeEventos.add(evento);
//        listaDeEventos.add(eventoNovo);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(listaDeEventos);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                // do whatever
                Bundle params = new Bundle();
                params.putSerializable("evento",listaDeEventos.get(position));
                //params.putString("nomeEvento", listaDeEventos.get(position));
                //params.putString("dataEventoIni", nDataEditText.getText().toString());
                //params.putString("dataEventoFim", nDataEndEditText.getText().toString());
                //params.putString("horaIniEvento", nTimeEditText.getText().toString());
                //params.putString("horaEndEvento", nTimeEndEditText.getText().toString());


                Intent intent = new Intent(getContext(), EventInteresseActivity.class);
                intent.putExtras(params);
                startActivity(intent);
            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference();

        databaseReference.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children) {
                    Evento evento = child.getValue(Evento.class);
                    //Toast.makeText(getApplicationContext(), evento.getNome(), Toast.LENGTH_SHORT).show();
                    listaDeEventos.add(evento);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(listaDeEventos.isEmpty()) {
            System.out.println("TA VAZIOOOO");
        } else {
            System.out.println(listaDeEventos.get(0).getNome());
        }


        recyclerViewAdapter = new RecyclerViewAdapter(listaDeEventos);
        recyclerView.setAdapter(recyclerViewAdapter);
        // ATUALIZAR O ARRAYLIST DE EVENTOS
        // GERAR NOVA RECYCLER VIEW
        //recyclerViewAdapter = new RecyclerViewAdapter(this, listaDeConvidados);
        //recyclerView.setAdapter(recyclerViewAdapter);
    }
}

