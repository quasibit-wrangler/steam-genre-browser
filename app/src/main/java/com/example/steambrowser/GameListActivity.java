package com.example.steambrowser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    private static final String TAG = GameListActivity.class.getSimpleName();
    static final String GENRE_EXTRA_KEY = "key for intent.containsExtra(key)";

    private RecyclerView mGameListRecyclerView;
    private GameListAdapter mGameListAdapter;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        mGameListRecyclerView = findViewById(R.id.rv_game_list);
        mGameListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGameListRecyclerView.setHasFixedSize(true);

        mGameListAdapter = new GameListAdapter();
        mGameListRecyclerView.setAdapter(mGameListAdapter);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_game_list_error_msg);

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sort = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
        String url = SteamUtils.buildSteamGenreURL(genre, sort);
        Log.d(TAG, "querying for: " + url);
        new SteamSpySearchTask().execute(url);
    }

    public class SteamSpySearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

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
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if( s != null) {
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                mGameListRecyclerView.setVisibility(View.VISIBLE);
                SteamUtils.Game[] gameList = SteamUtils.parseSteamGenreResults(s);
                mGameListAdapter.updateGameList(gameList);
            }
            else {
                mGameListRecyclerView.setVisibility(View.INVISIBLE);
                mErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }


    }


}
