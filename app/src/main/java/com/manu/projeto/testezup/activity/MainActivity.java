package com.manu.projeto.testezup.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import com.manu.projeto.testezup.R;
import com.manu.projeto.testezup.adapter.MoviesAdapter;
import com.manu.projeto.testezup.model.OmdbMovie;
import com.manu.projeto.testezup.model.OmdbMoviesResponse;
import com.manu.projeto.testezup.rest.ApiClientOmdb;
import com.manu.projeto.testezup.rest.ApiInterfaceOmdb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        //metodo que inicia a busca
        handleIntent(getIntent());

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        final Button button = (Button) findViewById(R.id.btn_favorito);
        button.setText("Favoritos");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavoritoActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // exibe a barra de busca no topo
        getMenuInflater().inflate(R.menu.menu_main, menu);



        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void handleIntent(Intent intent) {
        //esse metodo pega do OnCreatOptionsMenu os valores que foram inseridos na barra de pesquisa
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //ativa o metodo onQueryTextSubmit com os valores encontrados na barra de busca
            onQueryTextSubmit(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        String s = query; //esse foi o parametro enviado para o metodo
        String format = "json";//essa variavel permanecerá estatica porque eu quero que o formato do arquivo seja json
        //e também na classe ApiInterfaceOmdb me pede dois parametros para busca

        ApiInterfaceOmdb apiService =
                ApiClientOmdb.getClient().create(ApiInterfaceOmdb.class);


        Call<OmdbMoviesResponse> call = apiService.search(s, format);
        call.enqueue(new Callback<OmdbMoviesResponse>() {
            @Override
            public void onResponse(Call<OmdbMoviesResponse> call, Response<OmdbMoviesResponse> response) {
                TextView textView = (TextView) findViewById(R.id.erro_na_busca);
                List<OmdbMovie> movies = response.body().getResults();
                if (movies != null) {
                    textView.setText("");
                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                } else {
                    textView.setText(R.string.erro_busca);
                }
            }

            @Override
            public void onFailure(Call<OmdbMoviesResponse> call, Throwable t) {
                // Log erro caso a busca retorne algum erro como 400 ou  404
                Log.e(TAG, t.toString());
            }
        });
        return false;
    }

    boolean isConnected;


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}

