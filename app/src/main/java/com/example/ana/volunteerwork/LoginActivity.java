package com.example.ana.volunteerwork;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by tulio on 09/12/17.
 */

public class LoginActivity extends AppCompatActivity{
    private EditText emailTxt;
    private EditText passWd;
    private Button logBtn;
    private TextView signUp;
    private ProgressDialog progDia;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent mainArea = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(mainArea);
        }


        progDia = new ProgressDialog(this);
        emailTxt = (EditText) findViewById(R.id.email);
        passWd = (EditText) findViewById(R.id.senha);
        logBtn = (Button) findViewById(R.id.fazerlogin);
        signUp = (TextView) findViewById(R.id.cadastrar);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password= passWd.getText().toString().trim();

                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Favor inserir nome e e-mail ",Toast.LENGTH_LONG).show();
                }else {
                    progDia.setMessage("Entrando...");
                    progDia.show();
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                //@Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progDia.dismiss();
                                    if(task.isSuccessful()){
                                        finish();
                                        Intent mainArea = new Intent(LoginActivity.this,MainActivity.class);


                                        startActivity(mainArea);
                                    }else {
                                        Toast.makeText(LoginActivity.this,"Dados incorretos, favor tentar novamente.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent regIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(regIntent);
            }
        });

    }

}
