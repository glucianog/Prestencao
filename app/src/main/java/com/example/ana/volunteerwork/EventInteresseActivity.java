package com.example.ana.volunteerwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabri on 05/12/2017.
 */

public class EventInteresseActivity extends AppCompatActivity {
    TextView nomeEventoTV;
    TextView desEventoTV;
    TextView endEventoTV;
    //TextView dataEventoTV;
    TextView horaEventoTV;

    DatabaseReference db;
    Database helper;

    String nomeEvento,
            desEvento,
            endEvento,
            dataEventoIni,
            dataEventoFim,
            horaIniEvento,
            horaEndEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participarevento);

        // INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();
        helper = new Database(db);


        nomeEventoTV = (TextView) findViewById(R.id.textViewNomeEvento);
        //dataEventoTV = (TextView) findViewById(R.id.textViewDataEvento);
        horaEventoTV = (TextView) findViewById(R.id.textViewHoraEvento);
        desEventoTV = (TextView) findViewById(R.id.textViewDescricaoEvento);
        endEventoTV = (TextView) findViewById(R.id.textViewEnderecoEvento);

        Intent intent = getIntent();
        if ( intent != null ) {
            Bundle params = intent.getExtras();
            if (params != null ) {
                Evento evento = (Evento) params.getSerializable("evento");
                nomeEvento = evento.getNome();
                desEvento = evento.getDescricao();
                endEvento = evento.getEndereco();
                dataEventoIni = evento.getDatIni();
                dataEventoFim = evento.getDatFim();
                horaIniEvento = evento.getHoraIni();
                horaEndEvento = evento.getHoraFim();

                nomeEventoTV.setText(nomeEvento);
                desEventoTV.setText(desEvento);
                endEventoTV.setText(endEvento);



                String [] dataIni = dataEventoIni.split("/");
                String [] dataFim = dataEventoFim.split("/");
                String mesIni = retornaMes(Integer.parseInt(dataIni[1]));
                String mesFim = retornaMes(Integer.parseInt(dataFim[1]));

                horaEventoTV.setText(dataIni[0] + " de " + mesIni + " às "+ horaIniEvento+ " até " + dataFim[0] + " de " + mesFim + " às " + horaEndEvento);

            }
        }

    }

    public String retornaMes ( int mes ) {
        String retorno = "";
        if ( mes == 1 ) {
            retorno = "janeiro";
        } else if ( mes == 2 ) {
            retorno = "fevereiro";
        } else if ( mes == 3 ) {
            retorno = "março";
        } else if ( mes == 4 ) {
            retorno = "abril";
        } else if ( mes == 5 ) {
            retorno = "maio";
        } else if ( mes == 6 ) {
            retorno = "junho";
        } else if ( mes == 7 ) {
            retorno = "julho";
        } else if ( mes == 8 ) {
            retorno = "agosto";
        } else if ( mes == 9 ){
            retorno = "setembro";
        } else if ( mes == 10 ) {
            retorno = "outubro";
        } else if ( mes == 11 ) {
            retorno = "novembro";
        } else  {
            retorno = "dezembro";
        }
        return retorno;
    }

    public void manifestarInteresse(View view) {
        Intent intent = getIntent();
        if ( intent != null ) {
            Bundle params = intent.getExtras();
            if (params != null) {
                Evento evento = (Evento) params.getSerializable("evento");
                if(helper.save(evento,"Participando")) {
                    Toast.makeText(this, "Cadastrado no Evento!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "NÃO SALVOU NO BANCO!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        finish();

    }
}
