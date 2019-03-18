package com.example.steambrowser;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // get all game from SQLite
        List<SteamUtils.Game> gameList = new ArrayList<SteamUtils.Game>();
        Cursor cursor = MainActivity.db.query("game", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            SteamUtils.Game game = new SteamUtils.Game();
            game.appid = cursor.getInt(1);
            game.name = cursor.getString(2);
            game.positive = cursor.getInt(3);
            game.price = cursor.getString(4);
            gameList.add(game);
        }

        // translate game List to game array
        SteamUtils.Game[] gameArray = new SteamUtils.Game[gameList.size()];
        for (int i = 0; i < gameList.size(); i++) {
            gameArray[i] = gameList.get(i);
        }

        // set adapter for RecyclerView
        GameListAdapter mGameListAdapter = new GameListAdapter(this);
        recyclerView.setAdapter(mGameListAdapter);
        mGameListAdapter.updateGameList(gameArray);
    }
}
