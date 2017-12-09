package com.example.ana.volunteerwork;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_OrganizandoFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Evento> listaDeEventos =  new ArrayList<>();
    public static final int RESULT_OK = 0;

    DatabaseReference db;
    Database helper;


    public Tab_OrganizandoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab__organizando, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(listaDeEventos);
        recyclerView.setAdapter(recyclerViewAdapter);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new Database(db);

        db.child("Organizando").addValueEventListener(new ValueEventListener() {
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
                Intent intent = new Intent(getContext(), EventActivity.class);
                intent.putExtras(params);
                startActivity(intent);
            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CadastroEventoActivity.class);
                startActivityForResult(intent,RESULT_OK);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void startarValores() {

        db.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                listaDeEventos.clear();
                for(DataSnapshot child : children) {
                    Evento evento = child.getValue(Evento.class);
                    listaDeEventos.add(evento);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    }
}
