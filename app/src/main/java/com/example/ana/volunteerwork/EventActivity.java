package com.example.ana.volunteerwork;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ana.volunteerwork.database.Evento;

import java.util.ArrayList;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    TextView nomeEventoTV;
    TextView desEventoTV;
    TextView endEventoTV;
    //TextView dataEventoTV;
    TextView horaEventoTV;


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
        setContentView(R.layout.activity_event);

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

    public void cadastrarNaAgenda ( View view ) {

        String [] infosDataIni = dataEventoIni.split("/");
        int anoIni = Integer.parseInt(infosDataIni[0]);
        int mesIni = Integer.parseInt(infosDataIni[1]);
        int diaIni = Integer.parseInt(infosDataIni[2]);

        String [] infosHoraIni = horaIniEvento.split(":");
        int horaIni = Integer.parseInt(infosHoraIni[0]);
        int minutoIni = Integer.parseInt(infosHoraIni[1]);

        String [] infosDataFim = dataEventoFim.split("/");
        int anoFim = Integer.parseInt(infosDataFim[0]);
        int mesFim = Integer.parseInt(infosDataFim[1]);
        int diaFim = Integer.parseInt(infosDataFim[2]);

        String [] infosHoraFim = horaEndEvento.split(":");
        int horaFim = Integer.parseInt(infosHoraFim[0]);
        int minutoFim = Integer.parseInt(infosHoraFim[1]);



        Calendar calendarIni = Calendar.getInstance();
        calendarIni.set(anoIni,mesIni,diaIni,horaIni,minutoIni);

        Calendar calendarFim = Calendar.getInstance();
        calendarFim.set(anoFim,mesFim,diaFim,horaFim,minutoFim);

        Intent intent2 = new Intent(Intent.ACTION_INSERT);
        //intent2.setType("vnd.android.cursor.item/event");
        intent2.setData(CalendarContract.Events.CONTENT_URI);
        intent2.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calendarIni.getTimeInMillis());
        intent2.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarFim.getTimeInMillis());
        intent2.putExtra(CalendarContract.Events.TITLE, nomeEvento);

        startActivity(intent2);
    }


    public void enviarLinkedin ( View view ) {
        String mensagem= "";
        Intent intent = ShareCompat.IntentBuilder.from(EventActivity.this).setType("text/plain")
                .setText(mensagem).getIntent();
        intent.setPackage("com.linkedin.android");
        intent.setAction(Intent.ACTION_SEND);
        if ( intent.resolveActivity(getPackageManager()) != null ) {
            startActivity(intent);
        }

    }

    public void enviarWpp ( View view) {
        String mensagem = "Não se esqueça do meu evento no dia " + dataEventoIni + ", que começa às " + horaIniEvento + ", e vai até " + dataEventoFim + " às " + horaEndEvento + ".";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mensagem);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
    }
}
