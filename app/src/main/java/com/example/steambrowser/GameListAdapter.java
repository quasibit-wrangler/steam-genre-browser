package com.example.steambrowser;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private SteamUtils.Game[] mGames;

    private Context context;

    public GameListAdapter(Context context) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull GameListViewHolder gameListViewHolder, final int pos) {
        gameListViewHolder.bind(mGames[pos]);
        // jump to the detail game activity when user click the item
        gameListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // store appid to SQLite if this adapter belong to GameListActivity
                // avoid storing appid to SQLite again when clicking item in UserInfo
                if(context instanceof GameListActivity) {
                    ContentValues values = new ContentValues();
                    values.put("appid", mGames[pos].appid);
                    values.put("name", mGames[pos].name);
                    values.put("positive", mGames[pos].positive);
                    values.put("price", mGames[pos].price);
                    MainActivity.db.insert("game", null, values);
                }

                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra("appid", mGames[pos].appid);
                context.startActivity(intent);
            }
        });
    }

    public void updateGameList(SteamUtils.Game[] games, String sort) {
        if(sort.equals("positive")) {
            //sort by positive
            Arrays.sort(games, new PositiveCmp());
        }

        else if(sort.equals("price")) {
            //sort by price
            Arrays.sort(games, new PriceCmp());
        }

        else if(sort.equals("discount")) {
            //sort by price
            Arrays.sort(games, new DiscountCmp());
        }

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
