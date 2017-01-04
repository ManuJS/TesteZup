package com.manu.projeto.testezup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.manu.projeto.testezup.Models.Filme;
import com.manu.projeto.testezup.R;

import java.util.ArrayList;

/**
 * Created by emanu on 31/08/2016.
 */
public class FilmesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private static ArrayList<Filme> filmes;

    public FilmesAdapter(Context context, ArrayList<Filme> result) {
        //Itens do listview
        this.filmes = result;
        //Objeto responsável por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return filmes.size();
    }

    @Override
    public Object getItem(int i) {
        return filmes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            //infla o layout para podermos pegar as views
            view = mInflater.inflate(R.layout.item_filme, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();

            itemHolder.txtFilmeTitulo = ((TextView) view.findViewById(R.id.filme_titulo));
            itemHolder.txtFilmeSinopse = ((TextView) view.findViewById(R.id.filme_sinopse));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Filme filme = filmes.get(position);
        itemHolder.txtFilmeTitulo.setText(filme.getTituloFilme());
        itemHolder.txtFilmeSinopse.setText(filme.getSinopseFilme());

        //retorna a view com as informações
        return view;
    }

    /**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {

        TextView txtFilmeTitulo, txtFilmeSinopse;
        ImageView imageView;
    }
}
