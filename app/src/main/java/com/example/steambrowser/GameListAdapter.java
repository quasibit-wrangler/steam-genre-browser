package com.example.steambrowser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private ArrayList<String> mGameList;

    public GameListAdapter() {
        //mGameList = new ArrayList<String>();
        mGameList = new ArrayList<String>() {{ // dummy data
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
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_game, viewGroup, false);
        GameListViewHolder viewHolder = new GameListViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder gameListViewHolder, int pos) {
        String gameName = mGameList.get(pos);
        gameListViewHolder.bind(gameName);
    }


    @Override
    public int getItemCount() {
        return mGameList.size();
    }

    class GameListViewHolder extends RecyclerView.ViewHolder {

        private TextView mGameTextView;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameTextView = (TextView)itemView.findViewById(R.id.tv_game_text);
        }

        void bind(String gameName) {
            mGameTextView.setText(gameName);
        }
    }
}
