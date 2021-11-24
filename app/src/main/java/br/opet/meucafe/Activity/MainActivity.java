package br.opet.meucafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.opet.meucafe.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FIREBIRD:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth autenticacao = FirebaseAuth.getInstance();
        ;
        // autenticacao.signOut();
        if (autenticacao.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, Principal.class);
            startActivity(intent);
            finish();

        }


        DatabaseReference myRef = database.getReference("Mensagem");
        //   myRef.setValue("Bem Vindos ao Meu Café!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                DatabaseReference myRef = database.getReference("Mensagem");
                String value = snapshot.getValue(String.class);
                TextView lbmensag = findViewById(R.id.lbmensagem);
                lbmensag.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", error.getMessage());
            }
        });

    }


    public void onLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onRegistro(View view) {
        startActivity(new Intent(this, RegistroActivity.class));
    }


}
