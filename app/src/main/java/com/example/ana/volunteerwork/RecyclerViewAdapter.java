package com.example.ana.volunteerwork;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ana.volunteerwork.database.Database;
import com.example.ana.volunteerwork.database.Evento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ana on 14/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Evento> listaDeEventos;
    View view;
    ViewHolder viewHolder;
    FragmentManager fragmentManager;
    Database helper;
    final static ArrayList<Evento> eventos = new ArrayList<>();

    public RecyclerViewAdapter ( ArrayList<Evento> listaDeEventos) {
        this.listaDeEventos = listaDeEventos;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
        Evento evento = listaDeEventos.get(position);
        String var =  evento.getNome();
        holder.nomeEventoTV.setText(var);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return listaDeEventos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomeEventoTV;

        public ViewHolder(View itemView) {
            super(itemView);
            nomeEventoTV = (TextView) itemView.findViewById(R.id.nomeEvento);
        }

    }
}
