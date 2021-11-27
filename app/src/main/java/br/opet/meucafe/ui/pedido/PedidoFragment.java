package br.opet.meucafe.ui.pedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.opet.meucafe.Activity.ProdutoActivity;
import br.opet.meucafe.Model.Produto;
import br.opet.meucafe.R;
import br.opet.meucafe.databinding.FragmentPedidoBinding;

public class PedidoFragment extends Fragment {

    private PedidoViewModel dashboardViewModel;
    private FragmentPedidoBinding binding;
    EditText edDescricao;
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Produto> ListaProduto = new ArrayList<>();
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private Produto produtoSelecionado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PedidoViewModel.class);

        binding = FragmentPedidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        edDescricao = root.findViewById(R.id.edProduto);
        listView = root.findViewById(R.id.viewProduto);
        FirebaseApp.initializeApp(getContext());
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









        return root;
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
                produtoArrayAdapter = new ArrayAdapter<Produto>(getContext(), android.R.layout.simple_list_item_1, ListaProduto);
                listView.setAdapter(produtoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}