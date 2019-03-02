package com.example.steambrowser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<String> data;

    Context context;

    public MainAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    // create ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // bind ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bt.setText(data.get(position));
        // jump to GameList Activity when you press the genre button
        viewHolder.bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, GameListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button bt;
        public ViewHolder(View view){
            super(view);
            bt = view.findViewById(R.id.bt);
        }
    }
}