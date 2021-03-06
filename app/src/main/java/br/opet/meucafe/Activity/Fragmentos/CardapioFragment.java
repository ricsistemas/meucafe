package br.opet.meucafe.Activity.Fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import br.opet.meucafe.Activity.ProdutoActivity;
import br.opet.meucafe.Activity.RegistroActivity;
import br.opet.meucafe.Model.Itens;
import br.opet.meucafe.Model.Pedido;
import br.opet.meucafe.Model.Produto;
import br.opet.meucafe.Model.Usuario;
import br.opet.meucafe.R;
import br.opet.meucafe.databinding.FragmentCardapioBinding;

public class CardapioFragment extends Fragment {

    //private PedidoViewModel dashboardViewModel;
    private FragmentCardapioBinding binding;

    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth autentica;

    private List<Produto> ListaProduto = new ArrayList<>();
    private List<Itens> itemsCarinho = new ArrayList<>();
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private Produto produtoSelecionado;
    private Pedido pedido;
    private Usuario usuario = new Usuario();
    private String usu_id;
    private Button buttonFinalizar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardapioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.viewProduto);
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        autentica = FirebaseAuth.getInstance();
        usu_id = autentica.getCurrentUser().getUid();
        CarregarDados();
        recuperaDadosUsuario();
        recuperarPedido();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                produtoSelecionado = (Produto) adapterView.getItemAtPosition(i);
                //   edDescricao.setText(produtoSelecionado.getDescricao());
                AlertDialog.Builder buider = new AlertDialog.Builder(getContext());
                buider.setTitle(produtoSelecionado.getDescricao());
                buider.setMessage("Digite a quantidade");
                EditText edQuant = new EditText(getContext());
                edQuant.setText("1");
                buider.setView(edQuant);
                edQuant.setInputType(InputType.TYPE_CLASS_NUMBER);
                edQuant.requestFocus();
                buider.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Itens itemPedido = new Itens();
                        itemPedido.setProduto_id(produtoSelecionado.getCodigo());
                        itemPedido.setProduto_descricao(produtoSelecionado.getDescricao().toString());
                        itemPedido.setQuantidade(Integer.parseInt(edQuant.getText().toString()));
                        itemPedido.setProduto_preco(produtoSelecionado.getPreco());
                        if (pedido == null) {
                            pedido = new Pedido();
                            pedido.setCliente_Nome(usuario.getNome());
                        }
                        pedido.AddItens(itemPedido);
                        pedido.salvar(usu_id);

                    }
                });
                buider.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = buider.create();
                dialog.show();
            }
        });


        return root;
    }



    private void recuperarPedido() {

        databaseReference.child("Pedidos").child(usu_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    pedido = snapshot.getValue(Pedido.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void recuperaDadosUsuario() {
        databaseReference.child("Usuarios").child(usu_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CarregarDados() {

        databaseReference.child("Produtos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListaProduto.clear();
                if (snapshot.getValue() != null) {

                    for (DataSnapshot obj : snapshot.getChildren()) {
                        Produto p = obj.getValue(Produto.class);
                        ListaProduto.add(p);
                    }
                    Collections.sort(ListaProduto);
                    produtoArrayAdapter = new ArrayAdapter<Produto>(getContext(), android.R.layout.simple_list_item_1, ListaProduto);
                    listView.setAdapter(produtoArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}