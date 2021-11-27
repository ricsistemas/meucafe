package br.opet.meucafe.Model;

import java.io.Serializable;

public class Itens implements Serializable {
 private String id;
 private Pedido pedido ;
 private Produto produto;
 private int quantidade;
 private int ordem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
}
