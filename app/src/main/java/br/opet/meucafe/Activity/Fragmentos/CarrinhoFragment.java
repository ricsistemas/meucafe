package br.opet.meucafe.Activity.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


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
import br.opet.meucafe.Model.Itens;
import br.opet.meucafe.Model.Pedido;
import br.opet.meucafe.Model.Produto;
import br.opet.meucafe.Model.Usuario;
import br.opet.meucafe.R;
import br.opet.meucafe.databinding.FragmentCarrinhoBinding;

public class CarrinhoFragment extends Fragment {


    private FragmentCarrinhoBinding binding;
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth autentica;
    private ArrayAdapter<Itens> ItensArrayAdapter;
    private String usu_id;
    private List<Itens> listaItens = new ArrayList<>() ;
    private TextView textQtd;
    private TextView textTotal;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCarrinhoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.viewCarrinho);
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        autentica = FirebaseAuth.getInstance();
        usu_id = autentica.getCurrentUser().getUid();
        textQtd =  root.findViewById(R.id.textQtd);
        textTotal =  root.findViewById(R.id.textTotal);



        recuperaDadosUsuario();
        recuperarPedido();

        return root;
    }
    private void recuperaDadosUsuario() {
        databaseReference.child("Usuarios").child(usu_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                //        String sapo = "sapo";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void recuperarPedido() {

        databaseReference.child("Pedidos").child(usu_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot!=null) {

                  Pedido p = snapshot.getValue(Pedido.class);
                  listaItens = p.getItens();
                  ItensArrayAdapter = new ArrayAdapter<Itens>(getContext(), android.R.layout.simple_list_item_1, listaItens);
                  listView.setAdapter(ItensArrayAdapter);
                  //  textTotal.setText(Double.toString(p.getTotal()));
                  //   textQtd.setText(p.getQtditens());
              }

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