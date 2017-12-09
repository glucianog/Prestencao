package com.example.ana.volunteerwork;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CadastroEventoActivity extends AppCompatActivity {

    DatabaseReference db;
    Database helper;


    EditText nomeEventoET;
    EditText desEventoET;
    EditText endEventoET;

    Calendar nCurrentDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);


        // INITIALIZE FIREBASE DB
        db = FirebaseDatabase.getInstance().getReference();
        helper = new Database(db);


        nomeEventoET = (EditText) findViewById(R.id.nomeEvento);
        desEventoET = (EditText) findViewById(R.id.desEvento);
        endEventoET = (EditText) findViewById(R.id.endEvento);
    }

    /*
    private void updateDate ( int aux ) {

    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            datFimET.setText(selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear);
            nCurrentDate.set(selectedYear,selectedMonth,selectedDay);
        }
    };
    DatePickerDialog.OnDateSetListener g = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            datIniET.setText(selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear);
            nCurrentDate.set(selectedYear,selectedMonth,selectedDay);
        }
    };


    private void updateTime ( int horario ) {

        hour = nCurrentDate.get(Calendar.HOUR_OF_DAY);
        minute = nCurrentDate.get(Calendar.MINUTE);

        if ( horario == 1 ) {
            new TimePickerDialog(this, t, hour, minute, true).show();
        } else {
            new TimePickerDialog(this, s, hour, minute, true).show();
        }


    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {

            if (hourOfDay < 10 ) {
                String aux = "0" + Integer.toString(hourOfDay);
                hourOfDay = Integer.parseInt(aux);
            }
            if (minuteOfDay < 10) {
                String aux = "0" + Integer.toString(minuteOfDay);
                minuteOfDay = Integer.parseInt(aux);
            }
            horaIniET.setText(hourOfDay+":"+minuteOfDay);

        }
    };

    TimePickerDialog.OnTimeSetListener s = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
            if (hourOfDay < 10 ) {
                String aux = "0" + Integer.toString(hourOfDay);
                hourOfDay = Integer.parseInt(aux);
            }
            if (minuteOfDay < 10) {
                String aux = "0" + Integer.toString(minuteOfDay);
                minuteOfDay = Integer.parseInt(aux);
            }
            horaFimET.setText(hourOfDay+":"+minuteOfDay);

        }
    };

    */
    public void cadastrarEvento (View view) {


        if ( validaCampo() == false) {

            String nome = nomeEventoET.getText().toString();
            String descricao = desEventoET.getText().toString();
            String endEvento = endEventoET.getText().toString();
            SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy / MM/ dd");
            String strDate = mdFormat.format(nCurrentDate.getTime());

            SimpleDateFormat mtFormat = new SimpleDateFormat("KK:mm");
            String strHora = mtFormat.format(nCurrentDate.getTime());



            Evento novoEvento = new Evento (nome, descricao,endEvento,strDate,strHora);

            if(helper.save(novoEvento,"Registros")) {
                Toast.makeText(this, "Evento criado!", Toast.LENGTH_SHORT).show();
                try {
                    db.child("Ocorrencias").push().setValue(novoEvento);

                } catch (DatabaseException e) {
                    Toast.makeText(this, "ERRO NA INSERÇÃO!", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }



            } else {
                Toast.makeText(this, "NÃO SALVOU NO BANCO!", Toast.LENGTH_SHORT).show();
            }

            setResult(RESULT_OK);
            finish();

        } else {
            Toast.makeText(CadastroEventoActivity.this, "Favor preencher todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validaCampo ( ) {
        boolean retorno = false;
        if (nomeEventoET.getText().toString().trim().equals("") ||
                desEventoET.getText().toString().trim().equals("") ||
                endEventoET.getText().toString().trim().equals("") ) {
            retorno = true;
        }
        return retorno;
    }
}
