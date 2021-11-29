package br.opet.meucafe.Activity;

import static br.opet.meucafe.Util.ValidaEmail;
import static br.opet.meucafe.Util.isSenhaValid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.opet.meucafe.MenuActivity;
import br.opet.meucafe.R;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogin;
    private Button buttonRegistrar;
    private EditText textEmail;
    private EditText textSenha;
    private TextView textRecuperar;
    private ProgressBar progresBarLogin;
    private FirebaseAuth autentica;
    private boolean home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //=========== pega os id dos objetos ============
        autentica = FirebaseAuth.getInstance();
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        textEmail = findViewById(R.id.textLoginEmail);
        textSenha = findViewById(R.id.textLoginSenha);
        textRecuperar = findViewById(R.id.textRecuperar);
        progresBarLogin = findViewById(R.id.barraLogin);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            //apenas para evitar redundancia,
            //quando a chamada vem de home ou de outro lugar.
            home =  extras.getBoolean("home");
        }

        //=========== pega os id dos objetos ============
        if (autentica.getCurrentUser() != null && !home) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

            startActivity(intent);
            finish();
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int erro = ValidaEmail(textEmail);
                erro = erro + isSenhaValid(textSenha);
                if (erro != 0) return;
                progresBarLogin.setVisibility(View.VISIBLE);
                autentica.signInWithEmailAndPassword(textEmail.getText().toString(), textSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Login efetuado suscesso", Toast.LENGTH_SHORT);
                            toast.show();
                            progresBarLogin.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.putExtra("usuario",true);
                            progresBarLogin.setVisibility(View.GONE);
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

                            } catch (Exception e) {
                                excecao = "Erro desconhecido ao tentar logar: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),
                                    excecao,
                                    Toast.LENGTH_SHORT).show();
                        }
                        progresBarLogin.setVisibility(View.GONE);
                    }
                });
            }
        });
       buttonRegistrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               progresBarLogin.setVisibility(View.GONE);
               Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
               startActivity(intent);
              // finish();
           }
       });
        textRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidaEmail(textEmail) != 0) return;

                progresBarLogin.setVisibility(View.VISIBLE);
                FirebaseAuth recupera = FirebaseAuth.getInstance();
                recupera.sendPasswordResetEmail(textEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                progresBarLogin.setVisibility(View.GONE);

            }
        });

    }
}
