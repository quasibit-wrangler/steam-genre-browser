package com.example.steambrowser;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private SteamUtils.Game[] mGames;
    private onGameLongPressListener mOnGameLongPressListener;
    private GestureDetector mDetector;

    private Context context;

    public GameListAdapter(Context context,onGameLongPressListener listItemListener) {

        this.context = context;
        mDetector = new GestureDetector(new MyGestureListener());
        mOnGameLongPressListener =listItemListener;
    }
    public GameListAdapter(Context context) {

        this.context = context;
        mDetector = new GestureDetector(new MyGestureListener());
    }

    public interface onGameLongPressListener {
        void onGamePresss(SteamUtils.Game vg);
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_game, viewGroup, false);
        GameListViewHolder viewHolder = new GameListViewHolder(itemView,context);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder gameListViewHolder, final int pos) {
        gameListViewHolder.bind(mGames[pos]);
        // jump to the detail game activity when user click the item
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

    // In the SimpleOnGestureListener subclass you should override
    // onDown and any other gesture that you want to detect.
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("TAG", "onSingleTapConfirmed: ");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("TAG", "onLongPress: ");
        }

    }

    class GameListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        private int appId;
        private TextView mGameTV;
        private TextView mPriceTV;
        private TextView mVotesTV;
        private Context c;

        public GameListViewHolder(@NonNull View itemView,Context con) {
            super(itemView);
            mGameTV = itemView.findViewById(R.id.tv_game_text);
            mPriceTV = itemView.findViewById(R.id.tv_price);
            mVotesTV = itemView.findViewById(R.id.tv_votes);
            c = con;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // store appid to SQLite if this adapter belong to GameListActivity
            // avoid storing appid to SQLite again when clicking item in UserInfo
            SteamUtils.Game mGame = null;
            TextView tv =view.findViewById(R.id.tv_game_text);
            Log.d("one click event: ", "startd");

            for( SteamUtils.Game game : mGames) {
                if (game.name == tv.getText().toString()) {
                    mGame = game;
                    Log.d("one click event", "game found: " + mGame.name);
                }
            }
            if(mGame != null) {
                if (context instanceof GameListActivity) {
                    ContentValues values = new ContentValues();
                    values.put("appid", mGame.appid);
                    values.put("name", mGame.name);
                    values.put("positive", mGame.positive);
                    values.put("price", mGame.price);
                    MainActivity.db.insert("game", null, values);
                }

                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra("appid", appId);
                context.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            SteamUtils.Game mGame = null;
            TextView tv =view.findViewById(R.id.tv_game_text);
            Log.d("longpress click event: ", "startd");
            for( SteamUtils.Game game : mGames){
                if(game.name== tv.getText().toString()){
                    mGame = game;
                    Log.d("Longpress click event", "game found: " + mGame.name);
                }
            }
            if(mGame != null){
                mOnGameLongPressListener.onGamePresss(mGame);
            }
            return true;
        }

        void bind(SteamUtils.Game game) {
            appId = game.appid;
            mGameTV.setText(game.name);
            mPriceTV.setText("$" + game.price);
            mVotesTV.setText("Positive: " + Integer.toString(game.positive));

        }
    }
}
