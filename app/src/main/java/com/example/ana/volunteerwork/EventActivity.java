package com.example.ana.volunteerwork;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ana.volunteerwork.database.Evento;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    TextView nomeEventoTV;
    TextView desEventoTV;
    TextView endEventoTV;
    TextView dataEventoTV;
    TextView horaEventoTV;


    String nomeEvento,
            desEvento,
            endEvento,
            dataEvento,
            horaEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        nomeEventoTV = (TextView) findViewById(R.id.textViewNomeEvento);
        dataEventoTV = (TextView) findViewById(R.id.textViewDataEvento);
        horaEventoTV = (TextView) findViewById(R.id.textViewHoraEvento);
        desEventoTV =  (TextView) findViewById(R.id.textViewDescricaoEvento);
        endEventoTV =  (TextView) findViewById(R.id.textViewEnderecoEvento);

        Intent intent = getIntent();
        if ( intent != null ) {
            Bundle params = intent.getExtras();
            if (params != null ) {
                Evento evento = (Evento) params.getSerializable("evento");
                nomeEvento = evento.getNome();
                desEvento = evento.getDescricao();
                endEvento = evento.getEndereco();
                dataEvento = evento.getData();
                horaEvento = evento.getHora();
                nomeEventoTV.setText(nomeEvento);
                desEventoTV.setText(desEvento);
                endEventoTV.setText(endEvento);
                horaEventoTV.setText(horaEvento);
                dataEventoTV.setText(dataEvento);

            }
        }

    }


    public void enviarFacebook ( View view ) {
        String mensagem = "";
        mensagem = nomeEvento + ", " + horaEvento + ", " + dataEvento + ", " + endEvento;
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type","books.book")
                .putString("og:title","Alerta!!!")
                .putString("og:description",mensagem)
                .build();

        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book",object)
                .build();

        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")
                .setAction(action)
                .build();

        ShareDialog.show(this,content);
    }



    public void enviarWpp ( View view) {
        String mensagem = "Fique Atento! Ocorreu um acidente na" + endEvento + ", às " + horaEvento;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mensagem);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
    }
}
