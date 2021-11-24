package br.opet.meucafe.Activity;

import static br.opet.meucafe.Util.ValidaEmail;
import static br.opet.meucafe.Util.isSenhaValid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.opet.meucafe.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void ValidarLogin(View view) {
        EditText edEmail = findViewById(R.id.edEmailLogin);
        EditText edSenha = findViewById(R.id.EdSenha2);
        ProgressBar barra2 = findViewById(R.id.barraLogin);
        int erro;
        erro = ValidaEmail(edEmail);
        erro = +isSenhaValid(edSenha);
        if (erro != 0) return;

        barra2.setVisibility(View.VISIBLE);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Login efetuado suscesso", Toast.LENGTH_SHORT);
                    toast.show();
                    barra2.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, Principal.class);
                    startActivity(intent);
                    finish();

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Este conta já foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
                barra2.setVisibility(View.GONE);
            }
        });
    }

    public void entraSemLogin(View view) {
        Intent intent = new Intent(LoginActivity.this, Principal.class);
        startActivity(intent);
    }

    public void onEsqueciMinhaSenha(View view) {
        EditText edEmail = findViewById(R.id.edEmailLogin);

        if (ValidaEmail(edEmail) != 0) return;

        ProgressBar barra2 = findViewById(R.id.barraLogin);
        barra2.setVisibility(View.VISIBLE);

        FirebaseAuth recupera = FirebaseAuth.getInstance();
        recupera.sendPasswordResetEmail(edEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Verifique seu Email, um link foi enviado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Erro algo esta errado, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();

                }


            }
        });
        barra2.setVisibility(View.GONE);


    }
    public void onTest(View view) {


    // Confirma(null, "Confirma exclusão deste produto");


    }



}