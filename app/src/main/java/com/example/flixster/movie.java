package com.example.flixster;

import org.json.JSONException;
import org.json.JSONObject;

public class movie {
    String posterPath;
    String title;
    String overview;

    public movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("posterPath");
        title = jsonObject.getString("title");
        posterPath = jsonObject.getString("overview");
    }

}
