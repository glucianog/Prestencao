package com.example.ana.volunteerwork;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by tulio on 09/12/17.
 */

public class CadastroActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button cadastrar;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.senha);
        editTextPhone = (EditText) findViewById(R.id.telefone);
        editTextName = (EditText) findViewById(R.id.nome);
        cadastrar = (Button) findViewById(R.id.cadastrar);
        progressDialog = new ProgressDialog(this);



        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String nome = editTextName.getText().toString().trim();
                String telefone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nome)|| TextUtils.isEmpty(telefone)){
                    Toast.makeText(CadastroActivity.this, "Favor inserir todos os dados", Toast.LENGTH_LONG).show();
                }
                else {
                    registerUser();
                }
            }
        });
    }

    public void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String pw    = editTextPassword.getText().toString().trim();

        firebaseAuth = FirebaseAuth.getInstance();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Favor inserir e-mail!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pw)) {
            Toast.makeText(this, "Favor inserir senha!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando Usuário...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Usuário Registrado!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });

    }

}
