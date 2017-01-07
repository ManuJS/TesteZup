package com.manu.projeto.testezup.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.manu.projeto.testezup.R;
import com.manu.projeto.testezup.activity.DetalheActivity;
import com.manu.projeto.testezup.model.OmdbMovie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<OmdbMovie> movies;
    private int rowLayout;
    private Context mContext;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviesLayout = (LinearLayout) itemView.findViewById(R.id.movies_layout);
            movieTitle = (TextView) itemView.findViewById(R.id.title);
            data = (TextView) itemView.findViewById(R.id.subtitle);
            movieDescription = (TextView) itemView.findViewById(R.id.description);
            poster = (ImageView) itemView.findViewById(R.id.poster);
        }

    }

    public MoviesAdapter(List<OmdbMovie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        OmdbMovie items = movies.get(position);

        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getType());
        holder.itemView.setTag(items);

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "filme: " + movies.get(position).getTitle(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(mContext, DetalheActivity.class);
                i.putExtra("id_movie", movies.get(position).getId());
                i.putExtra("poster_movie", movies.get(position).getPosterPath());
                i.putExtra("title_movie", movies.get(position).getTitle());
                i.putExtra("year_movie", movies.get(position).getReleaseDate());
                i.putExtra("type_movie", movies.get(position).getType());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        String image_url = movies.get(position).getPosterPath();
        Picasso.with(mContext).load(image_url).fit().centerCrop().into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}