package com.example.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.example.flixster.databinding.ItemMovieBinding;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
   Context context;
   List<movie> movies;

   public MovieAdapter(Context context, List<movie> movies){
        this.context = context;
        this.movies = movies;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     movie movie = movies.get(position);
     holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       ItemMovieBinding binding;
       public ViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(movie movie) {
            binding.title.setText(movie.getTitle());
            binding.overview.setText(movie.getOverview());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        movie movie = movies.get(position);
                        // create intent for the new activity
                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                        // serialize the movie using parceler, use its short name as a key
                        intent.putExtra(movie.class.getSimpleName(), Parcels.wrap(movie));
                        // show the activity
                        context.startActivity(intent);
                    }

                }

            });

            binding.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncHttpClient client = new AsyncHttpClient();
                }
            });

            int orientation = context.getResources().getConfiguration().orientation;
            int radius = 40; // corner radius, higher value = more rounded
            int margin = 5;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(radius, margin)).into(binding.poster);
                // ...
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(radius, margin)).into(binding.poster);
                // ...
            }
        }


    }
}
