package br.opet.meucafe.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String celular;
    private String cpf;
    private String CriadoEm;
    private int tipo;
    private int Ativo;


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public Usuario() {


    }


    public Usuario(String nome, String email, String celular) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.CriadoEm = now.toString();
        Ativo = 1;
    }


    public String getCriadoEm() {
        return CriadoEm;
    }

    public void setCriadoEm(String criadoEm) {
        CriadoEm = criadoEm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getAtivo() {
        return Ativo;
    }

    public void setAtivo(int ativo) {
        Ativo = ativo;
    }
}
