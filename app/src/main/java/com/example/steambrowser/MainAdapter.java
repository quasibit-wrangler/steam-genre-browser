package com.example.steambrowser;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.genreViewHolder> {
    static final String GENRE_EXTRA_KEY = "key for intent.containsExtra(key)";

    private String[] genres;
    private String[] genreValues;
    Context context;

    public MainAdapter(Context context, List<String> data){
        this.context = context;
        genres =  context.getResources().getStringArray(R.array.genres_array);
        genreValues = context.getResources().getStringArray(R.array.genres_array_values);


    }


    // create ViewHolder
    @NonNull
    @Override
    public genreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        genreViewHolder vh = new genreViewHolder(view, this.context);
        return vh;
    }

    // bind ViewHolder
    @Override
    public void onBindViewHolder(genreViewHolder vh, int position) {
        final String genreValue=genreValues[adapterpositionToArrayPosition(position)];
        final String genreName=genres[adapterpositionToArrayPosition(position)];

        // jump to GameList Activity when you press the genre button
        vh.mbanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, GameListActivity.class);
                intent.putExtra(GENRE_EXTRA_KEY,genreValue);
                context.startActivity(intent);
            }
        });
        vh.bind(genreName,genreValue);
    }

    @Override
    public int getItemCount() {
        return genres.length;
    }
    public int adapterpositionToArrayPosition(int i){
        return getItemCount() - i - 1;
    }

    public static class genreViewHolder extends RecyclerView.ViewHolder {
        private ImageView mbanner;
        private TextView mname;
        private Context context;
        public genreViewHolder(View view,Context c){
            super(view);
                mname=view.findViewById(R.id.tv_genre);
                mbanner=view.findViewById(R.id.iv_genre);
                context = c;
        }
        void bind(String name,String value){
            mname.setText(name);
//            int imgId = context.getResources().getIdentifier("drawable/"+value+".png", null, null);

            Drawable myDrawable = context.getResources().getDrawable(R.drawable.action);
//            mbanner.setImageDrawable(res);
//            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
//            int imageResource = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+value+".png", null, context.getPackageName());
//            Drawable res = context.getResources().getDrawable(imageResource);

           mbanner.setImageDrawable(myDrawable);
//            mbanner.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),imgId));
            Log.d("Sbound value to: ", mname.getText().toString() + ": is the current value of the textview");
//            mbanner.setBackground(context.getResources(R.drawable.twod));
//            banner.setBackground(R.drawable[]);

        }
    }
}