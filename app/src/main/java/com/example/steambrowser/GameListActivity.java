package com.example.steambrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
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
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;


public class GameListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,
                                      GameListAdapter.onGameLongPressListener {

    private static final String TAG = GameListActivity.class.getSimpleName();
    static final String GENRE_EXTRA_KEY = "key for intent.containsExtra(key)";
    static final String GENRE_NAME_KEY = "GENRE NAME";

    private static final String GAMES_ARRAY_KEY = "steamGames";
    private static final String GENRE_URL_KEY = "steamGenreURL";

    private static final int STEAM_LOADER_ID = 0;

    private RecyclerView mGameListRecyclerView;
    private GameListAdapter mGameListAdapter;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;

    private SteamUtils.Game[] mGames;

    private String sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Genre");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_game_list);
        mGameListRecyclerView = findViewById(R.id.rv_game_list);
        mGameListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGameListRecyclerView.setHasFixedSize(true);

        mGameListAdapter = new GameListAdapter(this,this);
        mGameListRecyclerView.setAdapter(mGameListAdapter);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_game_list_error_msg);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sort = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));

        /*
         * Get genre from main activity
         */
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(GENRE_EXTRA_KEY)) {
            String genreToQuery = (String)intent.getSerializableExtra(GENRE_EXTRA_KEY);
            String genreTitle = (String)intent.getSerializableExtra(GENRE_NAME_KEY);
            getSupportActionBar().setTitle(genreTitle);
            getGameData(genreToQuery);
        }
        else {
            Log.d(TAG, "Did not get a genre");
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(GAMES_ARRAY_KEY)) {
            mGames = (SteamUtils.Game[]) savedInstanceState.getSerializable(GAMES_ARRAY_KEY);
            mGameListAdapter.updateGameList(mGames, sort);
        }

        getSupportLoaderManager().initLoader(STEAM_LOADER_ID, null, this);

    }

//    gets a video game mVG and opens up the url according to that
//    and Shares the game to anything that allows a string.
    @Override
    public void onGamePresss(SteamUtils.Game mVG){
        String from = "Steam-Browser App";
        if (mVG != null) {

            String shareText = "I would like to show you this sweet game I found from " + from + "\n";
            shareText+="\n Details: \n\t" + mVG.name;
            shareText+="\n\tCost: "+mVG.price;
            shareText+="\n\tVotes: "+mVG.positive;
            shareText+="\n\tWEBLINK: "+ "https://store.steampowered.com/app/"+mVG.appid;

            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareText)
                    .setChooserTitle("check out this steam game")
                    .startChooser();
        }
    }


    /**
     * Queries for the genre received from main activity
     */
    public void getGameData(String genre) {
        String url = SteamUtils.buildSteamGenreURL(genre);
        Log.d(TAG, "querying for: " + url);
        Bundle args = new Bundle();
        args.putString(GENRE_URL_KEY, url);
        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(STEAM_LOADER_ID, args, this);
        //new SteamSpySearchTask().execute(url);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGames != null) {
            outState.putSerializable(GAMES_ARRAY_KEY, mGames);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = null;
        if (bundle != null) {
            url = bundle.getString(GENRE_URL_KEY);
        }
        return new SteamLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.d(TAG, "Got results from the loader");
        if( s != null) {
            mErrorMessageTV.setVisibility(View.INVISIBLE);
            mGameListRecyclerView.setVisibility(View.VISIBLE);
            SteamUtils.Game[] gameList = SteamUtils.parseSteamGenreResults(s);
            mGameListAdapter.updateGameList(gameList, sort);
        }
        else {
            mGameListRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessageTV.setVisibility(View.VISIBLE);
        }
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Nothing to do here...
    }

}
