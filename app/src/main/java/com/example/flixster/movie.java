package com.example.flixster;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class movie {

    String posterPath;
    String title;
    String overview;

    public movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }
    public static List<movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new movie(movieJsonArray.getJSONObject(i)));
        }
    return movies;
    }

    public String getOverview() {
        return overview;
    }
    public String getTitle() {
        return title;
    }
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w32/%s",posterPath);
    }


}
