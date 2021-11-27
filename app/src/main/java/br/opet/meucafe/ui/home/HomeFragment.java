package br.opet.meucafe.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.opet.meucafe.Activity.MainActivity;
import br.opet.meucafe.Activity.ProdutoActivity;
import br.opet.meucafe.Activity.RegistroActivity;
import br.opet.meucafe.R;
import br.opet.meucafe.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView textPedido;
    private Button buttonSair;
    private Button buttonProduto;
    private Button buttonRegistrar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textPedido = root.findViewById(R.id.textPedido);
        buttonSair = root.findViewById(R.id.buttonSair);
        buttonProduto = root.findViewById(R.id.buttonProdutos);
        buttonRegistrar = root.findViewById(R.id.buttonRegistrar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensagem");
        //   myRef.setValue("Bem Vindos ao Meu Café!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                DatabaseReference myRef = database.getReference("Mensagem");
                String value = snapshot.getValue(String.class);
                textPedido.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", error.getMessage());
            }
        });
        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        buttonProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ProdutoActivity.class);
                startActivity(intent);

            }
        });
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}