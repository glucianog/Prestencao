package com.example.ana.volunteerwork;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ana.volunteerwork.database.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    MarkerOptions marker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ArrayList<Evento> listaDeEventos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference();

        databaseReference.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children) {
                    Evento t = child.getValue(Evento.class);
                    listaDeEventos.add(t);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children) {
                    Evento evento = child.getValue(Evento.class);
                    //Toast.makeText(getApplicationContext(), evento.getNome(), Toast.LENGTH_SHORT).show();
                    Log.d("TESTE",evento.getNome());
                    listaDeEventos.add(evento);
                    try {
                        Geocoder geocoder = new Geocoder(getContext());
                        //List<Address> enderecos = geocoder.getFromLocationName("Av. Sampaio Vidal, Centro, Marília, SP", 1);
                        List<Address> enderecos = geocoder.getFromLocationName(evento.getEndereco(), 1);
                        Log.d("TESTE", evento.getEndereco());
                        //if (enderecos.size() > 0) {

                        LatLng nova = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
                        marker = new MarkerOptions();
                        marker.position(nova);
                        marker.title(evento.getNome());
                        mMap.addMarker(marker);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(nova));

                        Log.d("TESTE", "PASSEI AQUUIIII");

                        //}
                    } catch ( Exception e ) {
                        Log.d("TESTE", "PASSEI AQUUIIII AAFFFFFF");
                    }
                }
                //Log.d("TESTE",listaDeEventos.toString());
                /*for ( int i = 0; i < listaDeEventos.size(); i ++ ) {

                    try {
                        Geocoder geocoder = new Geocoder(getContext());
                        //List<Address> enderecos = geocoder.getFromLocationName("Av. Sampaio Vidal, Centro, Marília, SP", 1);
                        List<Address> enderecos = geocoder.getFromLocationName(listaDeEventos.get(i).getEndereco(), 1);
                        if (enderecos.size() > 0) {

                            LatLng novaLat = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
                            MarkerOptions marker2 = new MarkerOptions();
                            marker.position(novaLat);
                            marker.title(listaDeEventos.get(i).getNome());
                            mMap.addMarker(marker2);

                            Log.v("tag", "coordenadas " + enderecos.get(0).getLatitude() + ", " + enderecos.get(0).getLongitude());
                        }
                    } catch (Exception e) {

                    }

                }*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);


        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);

            mMap = googleMap;
            //mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException ex) {
            /*
            ActivityCompat.checkPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);*/

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            PermissionUtils.requestPermission(activity, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);

            mMap = googleMap;
            //mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);

            //Log.e(TAG, "Error", ex);
        }
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-33.87365, 151.20689);

        MarkerOptions marker = new MarkerOptions();
        marker.position(sydney);
        marker.title("Marker in Sydney");
        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        /*LatLng nova = new LatLng(-19.8566726, -43.9869368);

        marker = new MarkerOptions();
        marker.position(nova);
        marker.title("Marker in Sydney");

        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(nova));*/

        // Retorna o endereço de uma lat e long
        // new Conexao().execute( String.valueOf(nova.latitude), String.valueOf(nova.longitude) );

        try {
            Geocoder geocoder = new Geocoder(getContext());
            //List<Address> enderecos = geocoder.getFromLocationName("Av. Sampaio Vidal, Centro, Marília, SP", 1);

            List<Address> enderecos = geocoder.getFromLocationName("Rua Pistóia, Pampulha, Bandeirantes, BH", 1);
            if (enderecos.size() > 0) {
                Log.v("tag", "coordenadas " + enderecos.get(0).getLatitude() + ", " + enderecos.get(0).getLongitude());

                /*LatLng nova = new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
                marker = new MarkerOptions();
                marker.position(nova);
                marker.title("Marker in Pistoia");
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(nova));*/
            }
        } catch (Exception e) {

        }
    }



    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(getActivity(), "Posição Alterada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(getActivity(), "O status do Provider foi alterado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getActivity(), "Provider Habilitado", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getActivity(), "Provider Desabilitado", Toast.LENGTH_SHORT).show();

    }

    class Conexao extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url = "http://maps.googleapis.com/maps/api/geocode/xml?latlng="+ params[0] + "," + params[1];
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                InputStream in = httpclient.execute(request).getEntity().getContent();
                BufferedReader br = null;
                StringBuilder sb = new StringBuilder();
                br = new BufferedReader(new InputStreamReader(in));
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }

                String resposta = sb.toString();
                 return resposta.substring(resposta.indexOf( "<formatted_address>" ) + 19, resposta.indexOf( "</formatted_address>" ));
                //return sb.toString();
            } catch ( Exception e ) {
                return "Erro: " + e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String msg) {
            //tvDescricao.setText( msg );
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
            System.out.println(msg);
        }
    }
}
