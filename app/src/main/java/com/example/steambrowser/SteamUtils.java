package com.example.steambrowser;

import android.net.Uri;

import com.google.gson.Gson;

public class SteamUtils {
    //steamspy.com/api.php?request=genre&genre={genreID}
    private final static String STEAM_GENRE_BASE_URL = "https://steamspy.com/api.php?request=genre";
    private final static String STEAM_GENRE_QUERY_PARAM = "genre";

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

    public static class SteamGenreResults {
        public Game[] games;
    }

    public static String buildSteamGenreURL(String genre) {
        return Uri.parse(STEAM_GENRE_BASE_URL).buildUpon()
                .appendQueryParameter(STEAM_GENRE_QUERY_PARAM, genre)
                .build()
                .toString();
    }

    public static Game[] parseSteamGenreResults(String json) {
        Gson gson = new Gson();
        SteamGenreResults results = gson.fromJson(json, SteamGenreResults.class);
        if (results != null && results.games != null) {
            return results.games;
        }
        else {
            return null;
        }
    }
}
