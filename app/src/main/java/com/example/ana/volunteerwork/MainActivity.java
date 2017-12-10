package com.example.ana.volunteerwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    Database helper;
    final static ArrayList<Evento> eventos = new ArrayList<>();
    ArrayList<Evento> participando,organizando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // verifica login
        mAuth = FirebaseAuth.getInstance();
        //Log.d("oi", mAuth.getCurrentUser().getEmail().toString());

        if(mAuth.getCurrentUser() == null){
            Intent mainArea = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(mainArea);
        }


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // INITIALIZE FIREBASE DB
       FirebaseDatabase db = FirebaseDatabase.getInstance();
       DatabaseReference databaseReference = db.getReference();
       databaseReference.child("Ocorrencia").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Iterable<DataSnapshot> children = dataSnapshot.getChildren();
               for(DataSnapshot child : children) {
                   Evento evento = child.getValue(Evento.class);
                   //Toast.makeText(getApplicationContext(), evento.getNome(), Toast.LENGTH_SHORT).show();
                   eventos.add(evento);
               }
    }
           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



        /**
        //TESTE DE INSERIR NO BANCO, É SÓ SEGUIR ESSA ESTRUTURA QUE DÁ SUCESSO!!!

        // SAVE INTO FIREBASE
        Evento teste = new Evento("Evento doido", "Só vai dar nois",
                "21/10/2018","22/10/2018","22:00","05:00",
                "Rua da Zoeira 999");
        if(helper.save(teste)) {
            Toast.makeText(this, "SALVOU NO BANCO!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NÃO SALVOU NO BANCO!", Toast.LENGTH_SHORT).show();
        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

//
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_mapa) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frame_layout, new MapsActivity(), "MapsFragment");
            transaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_evento) {

            FragmentTransaction ft = fragmentManager.beginTransaction();//.replace(R.id.coordinator_layout, new TransitionFragment()).commit();
            Bundle args = new Bundle();
            //args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) eventos);
            //args.putString("subjectName",item.getTitle().toString());
            EventTransitionFragment fragment = new EventTransitionFragment();
            //fragment.setArguments(args);
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();

            //FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.frame_layout, new EventTransitionFragment()).commit();
        } /* else if (id == R.id.nav_profile) {

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
