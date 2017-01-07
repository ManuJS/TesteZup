package com.manu.projeto.testezup.activity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.manu.projeto.testezup.R;
import com.manu.projeto.testezup.data.MovieContract;
import com.manu.projeto.testezup.model.OmdbMoviesResponseDetail;
import com.manu.projeto.testezup.rest.OmdbServiceDetail;
import com.manu.projeto.testezup.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by emanu on 05/01/2017.
 */

public class DetalheActivity extends AppCompatActivity {

    private static final String TAG = DetalheActivity.class.getSimpleName();

    ImageView posterMovie;
    TextView yearMovie;
    TextView typeMovie;
    TextView titleMovie;
    ImageView favoriteMovie;

    Toast mToast;

    OmdbMoviesResponseDetail movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhe_activity);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_button);

//        movies.getPlot();


        Bundle bundle = getIntent().getExtras();
        final String idOfMovie = (String) bundle.get("id_movie");
        final String nameOfMovie = (String) bundle.get("title_movie");
        final String posterOfMovie = (String) bundle.get("poster_movie");
        final String typeOfMovie = (String) bundle.get("type_movie");
        final String yearOfMovie = (String) bundle.get("year_movie");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Object, Object, Integer>() {

                    @Override
                    protected Integer doInBackground(Object... params) {

                        return Utility.isFavorited(DetalheActivity.this, movies.getImdbID());
                    }

                    @Override
                    protected void onPostExecute(Integer isFavorited) {
                        if (isFavorited == 1) {
                            fab.setImageResource(android.R.drawable.star_big_off);
                            // deleta da tabela
                            new AsyncTask<Void, Void, Integer>() {
                                @Override
                                protected Integer doInBackground(Void... params) {
                                    return getContentResolver().delete(
                                            MovieContract.MovieEntry.CONTENT_URI,
                                            MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                                            new String[]{movies.getImdbID()}
                                    );
                                }

                                @Override
                                protected void onPostExecute(Integer rowsDeleted) {

                                    mToast = Toast.makeText(DetalheActivity.this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT);
                                    mToast.show();
                                }
                            }.execute();
                        }
                        // se o filme nao estiver nos favoritos
                        else {
                            // adiciona o item
                            new AsyncTask<Void, Void, Uri>() {
                                @Override
                                protected Uri doInBackground(Void... params) {
                                    ContentValues values = new ContentValues();

                                    values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movies.getImdbID());
                                    values.put(MovieContract.MovieEntry.COLUMN_TITLE,movies.getTitle());
                                    values.put(MovieContract.MovieEntry.COLUMN_POSTER, movies.getPoster());
                                    values.put(MovieContract.MovieEntry.COLUMN_TYPE, movies.getType());
                                    values.put(MovieContract.MovieEntry.COLUMN_RELEASED, movies.getReleased());
                                    values.put(MovieContract.MovieEntry.COLUMN_PLOTER, R.drawable.cupcake);
                                    values.put(MovieContract.MovieEntry.COLUMN_FAVORITO, 1);


                                    return getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,
                                            values);
                                }

                                @Override
                                protected void onPostExecute(Uri returnUri) {
                                    fab.setImageResource(android.R.drawable.star_big_on);
                                    mToast = Toast.makeText(DetalheActivity.this, getString(R.string.add_to_favorites),
                                            Toast.LENGTH_SHORT);
                                    mToast.show();
                                }
                            }.execute();
                        }
                    }
                }.execute();
            }
        });

        posterMovie = (ImageView) findViewById(R.id.detail_poster_movie);
        titleMovie = (TextView) findViewById(R.id.detail_name_of_movie);
        typeMovie = (TextView) findViewById(R.id.detail_type_movie);
        yearMovie = (TextView) findViewById(R.id.detail_year);


        if (bundle != null) {
            Picasso.with(this).load(posterOfMovie).into(posterMovie);
            titleMovie.setText("Titulo do filme: " + nameOfMovie);
            typeMovie.setText("Filme ou série: " + typeOfMovie);
            yearMovie.setText("Ano de lançamento: " + yearOfMovie);

        }

        OmdbServiceDetail omdbServiceDetail = OmdbServiceDetail.retrofit.create(OmdbServiceDetail.class);
        Call<OmdbMoviesResponseDetail> call = omdbServiceDetail.repoContributors(idOfMovie, "json");

        call.enqueue(new Callback<OmdbMoviesResponseDetail>() {

            @Override
            public void onResponse(Call<OmdbMoviesResponseDetail> call, Response<OmdbMoviesResponseDetail> response) {
                TextView textView = (TextView) findViewById(R.id.detalhes_sinopse);
                movies = response.body();
                    textView.setText("Sinopse: " + movies.getPlot());

            }

            @Override
            public void onFailure(Call<OmdbMoviesResponseDetail> call, Throwable t) {
                final TextView textView = (TextView) findViewById(R.id.erro_na_busca);
                textView.setText("Something went wrong: " + t.getMessage());
            }

        });
    }
}
