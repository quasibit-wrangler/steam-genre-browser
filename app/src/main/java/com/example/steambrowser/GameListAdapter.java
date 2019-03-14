package com.example.steambrowser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private SteamUtils.Game[] mGames;

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
        gameListViewHolder.bind(mGames[pos]);
    }

    public void updateGameList(SteamUtils.Game[] games) {
        mGames = games;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(mGames != null) {
            return mGames.length;
        }
        else {
            return 0;
        }
    }

    class GameListViewHolder extends RecyclerView.ViewHolder {

        private TextView mGameTV;
        private TextView mPriceTV;
        private TextView mVotesTV;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameTV = itemView.findViewById(R.id.tv_game_text);
            mPriceTV = itemView.findViewById(R.id.tv_price);
            mVotesTV = itemView.findViewById(R.id.tv_votes);
        }

        void bind(SteamUtils.Game game) {
            mGameTV.setText(game.name);
            mPriceTV.setText("$" + game.price);
            mVotesTV.setText("Positive: " + Integer.toString(game.positive));
        }
    }
}
