package com.example.ana.volunteerwork.database;

import java.io.Serializable;

/**
 * Created by gabri on 04/12/2017.
 */

public class Evento implements Serializable {
    String nome,descricao,endereco,data,hora;

    public Evento() {
    }

    public Evento(String nome, String descricao, String endereco, String data, String hora) {
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.data = data;
        this.hora = hora;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
