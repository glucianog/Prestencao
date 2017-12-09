package com.example.ana.volunteerwork.database;

import android.service.autofill.Dataset;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by gabri on 03/12/2017.
 */

public class Database {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Evento> eventos = new ArrayList<>();


    /**
     * PASS DATA BASE REFERENCE
     */

    public Database (DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL

    /**
     *
     * @param evento objeto a ser salvo
     * @return
     */
    public Boolean save(Evento evento, String child) {
        if (evento == null ) {
            saved = false;
        } else {
            try {
                db.child(child).push().setValue(evento);
                saved=true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    // IMPLEMENT FETCH DATA AND FILL ARRAYLIST

    private void fetchData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Evento evento = ds.getValue(Evento.class);
            eventos.add(evento);
        }
    }

    // RETRIEVE
    public ArrayList<Evento> retrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return eventos;
    }




}
