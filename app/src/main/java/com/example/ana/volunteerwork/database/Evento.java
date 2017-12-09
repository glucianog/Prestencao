package com.example.ana.volunteerwork.database;

import java.io.Serializable;

/**
 * Created by gabri on 04/12/2017.
 */

public class Evento implements Serializable {
    String nome,descricao, datIni, datFim, horaIni, horaFim, endereco;

    public Evento() {
    }

    public Evento(String nome, String descricao, String datIni, String datFim, String horaIni, String horaFim, String endereco) {
        this.nome = nome;
        this.descricao = descricao;
        this.datIni = datIni;
        this.datFim = datFim;
        this.horaIni = horaIni;
        this.horaFim = horaFim;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDatIni() {
        return datIni;
    }

    public String getDatFim() {
        return datFim;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDatIni(String datIni) {
        this.datIni = datIni;
    }

    public void setDatFim(String datFim) {
        this.datFim = datFim;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
