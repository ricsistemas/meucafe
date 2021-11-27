package br.opet.meucafe.Model;

import java.io.Serializable;
import java.util.Date;

public class Pedido implements Serializable {
    private int Ativo;
    private String id;
    private Usuario usuario;
    private Date DataPedido;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataPedido() {
        return DataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        DataPedido = dataPedido;
    }
}
