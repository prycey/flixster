package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.databinding.ActivityTrailerBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {
        // the movie to display
        Context context;
        movie movie;
        ImageView poster;
        TextView tvTitle;
        TextView tvOverview;
        RatingBar rbVoteAverage;
        TextView releaseDate;

        protected void onCreate(Bundle savedInstanceState) {
            context = this;
            super.onCreate(savedInstanceState);
            ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            tvTitle = binding.title;
            tvOverview =  binding.textView2;
            rbVoteAverage = binding.ratingBar;
            poster = binding.imageView;
            releaseDate = binding.releasedate;
            // unwrap the movie passed in via intent, using its simple name as a key
            movie = Parcels.unwrap(getIntent().getParcelableExtra(movie.class.getSimpleName()));
            // set the title and overview
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            releaseDate.setText(movie.getReleaseDate());
            // vote average is 0..10, convert to 0..5 by dividing by 2
            float voteAverage = movie.getVoteAverage().floatValue();
            rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
            int radius = 40; // corner radius, higher value = more rounded
            int margin = 5;
            Glide.with(context).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(radius, margin)).into(poster);
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String apiCallforVideo;
                    AsyncHttpClient client = new AsyncHttpClient();

                    // make sure the position is valid, i.e. actually exists in the view
                        // get the movie at the position, this won't work if the class is static

                        apiCallforVideo = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=c512b057a39b0cec359f25c7da4bf2aa&language=en-US";
                        // create intent for the new activity
                        final Intent intent = new Intent(context, trailerActivity.class);
                        // serialize the movie using parceler, use its short name as a key
                        // show the activity
                        client.get(apiCallforVideo, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                JSONObject jsonObject = json.jsonObject;
                                try {
                                    JSONArray results = jsonObject.getJSONArray("results");
                                    JSONObject index =  results.getJSONObject(0);
                                    String key = index.getString("key");
                                    intent.putExtra("key", Parcels.wrap(key));
                                    context.startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.e("failure message", response);
                            }
                        });

                    }


            });
        }


}