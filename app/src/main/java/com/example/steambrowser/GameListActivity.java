package com.example.steambrowser;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity{

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        switch(id){
            case R.id.dummy_game_list:
                Intent gameListIntent = new Intent(this, GameListActivity.class);
                startActivity(gameListIntent);
                return true;
            default:
                break;

        }
        return true;
    }
}
