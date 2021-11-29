package br.opet.meucafe.Model;

import java.io.Serializable;

public class Itens implements Serializable {
    private String produto_descricao;
    private String produto_id;
    private double produto_preco;
    private int quantidade;

    public double getProduto_preco() {
        return produto_preco;
    }

    public String toString() {
        return produto_descricao;
    }

    public void setProduto_preco(double produto_preco) {
        this.produto_preco = produto_preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getProduto_descricao() {
        return produto_descricao;
    }

    public void setProduto_descricao(String produto_descricao) {
        this.produto_descricao = produto_descricao;
    }

    public String getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(String produto_id) {
        this.produto_id = produto_id;
    }



}


