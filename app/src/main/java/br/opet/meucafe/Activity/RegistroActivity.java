package br.opet.meucafe.Activity;

import static br.opet.meucafe.Util.ValidaEmail;
import static br.opet.meucafe.Util.isCelualarValid;
import static br.opet.meucafe.Util.isSenhaValid;
import static br.opet.meucafe.Util.isValido;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import br.opet.meucafe.Model.Usuario;
import br.opet.meucafe.R;

public class RegistroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean insert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
    }

    public void onEfetuaRegistro(View view) {
        int erro;


        EditText edNome = findViewById(R.id.edNome);
        EditText edEmail = findViewById(R.id.edEmail);
        EditText edCelular = findViewById(R.id.edCelular);
        EditText edSenha = findViewById(R.id.edSenha);
        // EditText edSenha2 = findViewById(R.id.edESenha2);

        Usuario usuario = new Usuario(
                edNome.getText().toString(),
                edEmail.getText().toString(),
                edCelular.getText().toString());
        ProgressBar barraregistro = findViewById(R.id.barraRegistro);

        erro = isValido(edNome);
        erro = +isCelualarValid(edCelular);
        erro = +ValidaEmail(edEmail);
        erro = +isSenhaValid(edSenha);
        if (erro != 0) return;

        Toast.makeText(RegistroActivity.this,
                "Tentando Validar",
                Toast.LENGTH_SHORT).show();

        barraregistro.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(), edSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase.getInstance().
                                    getReference("Usuarios")
                                    .child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid())
                                    .setValue(usuario)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            insert = task.isSuccessful();
                                            Toast.makeText(RegistroActivity.this,
                                                    "Usuário Registrado com suscesso",
                                                    Toast.LENGTH_SHORT).show();
                                        }// 2 conseguiu salvar login
                                    });

                            barraregistro.setVisibility(View.GONE);
                            Intent intent = new Intent(RegistroActivity.this, Principal.class);
                            startActivity(intent);
                            barraregistro.setVisibility(View.GONE);
                            finish();

                        } // 1 conseguiu salvar login
                        else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Por favor, digite um e-mail válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "Este usuário já foi cadastrado";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(RegistroActivity.this,
                                    excecao,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        barraregistro.setVisibility(View.GONE);
    }

}