package com.example.steambrowser;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class SteamUtils {
    //querying with tag:
    //steamspy.com/api.php?request=tag&tag={genreID}
    private final static String STEAM_GENRE_BASE_URL = "https://steamspy.com/api.php?request=tag";
    private final static String STEAM_GENRE_QUERY_PARAM = "tag";
    private final static String STEAM_GENRE_SORT_PARAM = "sort";  

    //appid (int), name (String), positive (int), average_forever (int), average_2weeks (int), price (String), discount (String)
    public static class Game {
        public int appid;
        public String name;
        public int positive;
        public int average_forever;
        public int average_2weeks;
        public String price;
        public String discount;
    }



    public static String buildSteamGenreURL(String genre) {
        return Uri.parse(STEAM_GENRE_BASE_URL).buildUpon()
                .appendQueryParameter(STEAM_GENRE_QUERY_PARAM, genre)
                .build()
                .toString();
    }

    public static String buildSteamGenreURL(String genre, String sort) {
        Uri.Builder builder = Uri.parse(STEAM_GENRE_BASE_URL).buildUpon();
        builder.appendQueryParameter(STEAM_GENRE_QUERY_PARAM, genre);
        if (!sort.equals("")) {
            builder.appendQueryParameter(STEAM_GENRE_SORT_PARAM, sort);
        }

        return builder.build().toString();
    }

    public static Game[] parseSteamGenreResults(String json) {
        Gson gson = new Gson();
        Map<String, Game> results = gson.fromJson(json, new TypeToken<Map<String,Game>>(){}.getType());
        if (results != null) {
            Game[] games = results.values().toArray(new Game[results.size()]);
            return games;
        }
        else {
            return null;
        }
    }
}
