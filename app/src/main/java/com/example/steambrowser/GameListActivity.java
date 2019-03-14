package com.example.steambrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.preference.PreferenceManager;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    private static final ArrayList<String> dummyGameList = new ArrayList<String>() {{ // dummy data
        add("GAME 1");
        add("GAME 2");
        add("GAME 3");
        add("GAME 4");
        add("GAME 5");
        add("GAME 6");
        add("GAME 7");
        add("GAME 8");
        add("GAME 9");
        add("GAME 10");
        add("GAME 11");
        add("GAME 12");
        add("GAME 13");
        add("GAME 14");
        add("GAME 15");
        add("GAME 16");
    }};

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
    }
}
