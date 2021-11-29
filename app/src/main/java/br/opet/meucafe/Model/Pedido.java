package br.opet.meucafe.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private int Ativo;
    private String id;
    private String usuario_id;

    public String getDataPedido() {
        return DataPedido;
    }

    public void setDataPedido(String dataPedido) {
        DataPedido = dataPedido;
    }

    private String Cliente_Nome;
    private String DataPedido;
    private String fila_id;
    private Double total;

    public int getQtditens() {
        return qtditens;
    }

    public void setQtditens(int qtditens) {
        this.qtditens = qtditens;
    }

    private int qtditens;
    private String mesa;
    private String observacao;
    private List<Itens> itens = new ArrayList<>();
    private String Status = "pendente";


    public List<Itens> getItens() {
        return itens;
    }

    public void setItens(List<Itens> itens) {
        this.itens = itens;
    }

    public void AddItens(Itens item) {
        if (itens.size() == 0) {
            itens.add(item);
            return;

        }
        for (int x = 0; x < itens.size(); x++) {
            if (itens.get(x).getProduto_id() == item.getProduto_id()) {
                Itens trocaitem = itens.get(x);
                trocaitem.setQuantidade(trocaitem.getQuantidade() + item.getQuantidade());
                itens.set(x, trocaitem);
                return;
            }
            ;

        }
        itens.add(item);
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCliente_Nome() {
        return Cliente_Nome;
    }

    public void setCliente_Nome(String cliente_Nome) {
        Cliente_Nome = cliente_Nome;
    }


    private FirebaseDatabase firebaseRef = FirebaseDatabase.getInstance();

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Pedido(String userid) {
        setUsuario_id(userid);
        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("Pedidos");
        DatabaseReference pedidoRef = firebaseRef;
        setId(pedidoRef.push().getKey());
        firebaseRef.child(userid).child(getId());

    }

    public void salvar() {

        double valor = 0.0;
        int qtd=0;


        for (int x = 0; x < itens.size(); x++) {
            Itens i = itens.get(x);
            valor =+ i.getQuantidade() * i.getProduto_preco();
            qtd =+ i.getQuantidade();
        }
        setTotal(valor);
        setQtditens(qtd);

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference("Pedidos");
        DatabaseReference pedidoRef = firebaseRef;
        pedidoRef.child(getUsuario_id()).child(getId()).setValue(this);


    }


    public int getAtivo() {
        return Ativo;
    }

    public void setAtivo(int ativo) {
        Ativo = ativo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFila_id() {
        return fila_id;
    }

    public void setFila_id(String fila_id) {
        this.fila_id = fila_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


}
