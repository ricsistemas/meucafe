package br.opet.meucafe.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import br.opet.meucafe.Model.Produto;
import br.opet.meucafe.R;

public class Principal extends AppCompatActivity {
    EditText edDescricao;
    ListView listView;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Produto> ListaProduto = new ArrayList<>();
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private Produto produtoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        edDescricao = findViewById(R.id.edProduto);
        listView = findViewById(R.id.viewProduto);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        CarregarDados();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                produtoSelecionado = (Produto) adapterView.getItemAtPosition(i);
                edDescricao.setText(produtoSelecionado.getDescricao());
            }
        });
    }

    private void CarregarDados() {
        databaseReference.child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListaProduto.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    Produto p = obj.getValue(Produto.class);
                    ListaProduto.add(p);
                }
                Collections.sort(ListaProduto);
                produtoArrayAdapter = new ArrayAdapter<Produto>(Principal.this, android.R.layout.simple_list_item_1, ListaProduto);
                listView.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Produto prod = new Produto();
        prod.setCodigo(UUID.randomUUID().toString());
        prod.setDescricao(edDescricao.getText().toString());

        switch (item.getItemId()) {
            case R.id.menu_inserir:

                prod.setDescricao(edDescricao.getText().toString());
                databaseReference.child("Produtos").child(prod.getCodigo()).setValue(prod).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Principal.this,
                                    "Produto incluido com suscesso",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Principal.this,
                                    "Falha ao incluir",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                edDescricao.setText("");
                break;
            case R.id.menu_alterar:

                produtoSelecionado.setDescricao(edDescricao.getText().toString());

                databaseReference.child("Produtos").child(produtoSelecionado.getCodigo()).setValue(produtoSelecionado).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Principal.this,
                                    "Produto Alterado com suscesso",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Principal.this,
                                    "Falha na alteração",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                edDescricao.setText("");
                break;
            case R.id.menu_excluir:

                AlertDialog alerta;

                //Cria o gerador do AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
                //define o titulo
                builder.setTitle("Exclusão");
                //define a mensagem
                builder.setMessage("Excluir Produto \n"+ edDescricao.getText().toString()+" \n\nConfirma?");
                //define um botão como positivo
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        databaseReference.child("Produtos").child(produtoSelecionado.getCodigo()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Principal.this,
                                            "Excluido com suscesso",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Principal.this,
                                            "Falha na exclusão",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        edDescricao.setText("");

                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("NÂO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        return;
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();

                break;
            case R.id.menu_sair:
                FirebaseAuth sair = FirebaseAuth.getInstance();
                sair.signOut();
                finishAffinity();

                break;
            default:
                return false;
        }
        return true;
    }

}

