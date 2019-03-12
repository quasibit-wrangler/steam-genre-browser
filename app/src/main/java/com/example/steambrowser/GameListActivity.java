package com.example.steambrowser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    private static final String TAG = GameListActivity.class.getSimpleName();
    static final String GENRE_EXTRA_KEY = "key for intent.containsExtra(key)";

    private RecyclerView mGameListRecyclerView;
    private GameListAdapter mGameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        mGameListRecyclerView = findViewById(R.id.rv_game_list);
        mGameListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGameListRecyclerView.setHasFixedSize(true);

        mGameListAdapter = new GameListAdapter();
        mGameListRecyclerView.setAdapter(mGameListAdapter);

        /**
         * Get genre from main activity
         */
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(GENRE_EXTRA_KEY)) {
            String genreToQuery = (String)intent.getSerializableExtra(GENRE_EXTRA_KEY);
            getGameData(genreToQuery);
        }
        else {
            Log.d(TAG, "Did not get a genre");
        }

    }

    /**
     * Queries for the genre received from main activity
     */
    public void getGameData(String genre) {
        String url = SteamUtils.buildSteamGenreURL(genre);
        Log.d(TAG, "querying for: " + url);
        new SteamSpySearchTask().execute(url);
    }

    public class SteamSpySearchTask extends AsyncTask<String, Void, String> {

        //TODO: show progress bar

        @Override
        protected String doInBackground(String... urls) {
            String searchURL = urls[0];
            String results = null;
            try {
                results = NetworkUtils.doHTTPGet(searchURL);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if( s != null) {
                mGameListRecyclerView.setVisibility(View.VISIBLE);
                SteamUtils.Game[] gameList = SteamUtils.parseSteamGenreResults(s);
                mGameListAdapter.updateGameList(gameList);
            }
        }


    }


}
