package com.example.two.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.R;
import com.example.two.config.Config;
import com.example.two.model.ContentWatch;
import com.example.two.model.ContentWatchList;
import com.example.two.model.Movie;
import com.example.two.model.Seach;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<ContentWatch> contentWatchArrayList;

    int path;

    public MyAdapter(Context context, ArrayList<ContentWatch> contentWatchArrayList) {
        this.context = context;
        this.contentWatchArrayList = contentWatchArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serach_row,parent,false);

        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        ContentWatch contentWatch = contentWatchArrayList.get(position);
        holder.textView10.setText(contentWatch.getTitle());
        holder.textView11.setText(contentWatch.getContentRating());

        Glide.with(context)
                .load(contentWatch.getImgUrl())
                .placeholder(R.drawable.baseline_person_outline_24)
                .override(300,300)
                .into(holder.searchPoster);

    }

    @Override
    public int getItemCount() {
        return contentWatchArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView searchPoster;
        TextView textView10;
        TextView textView11;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            searchPoster = itemView.findViewById(R.id.searchPoster);
            textView10 = itemView.findViewById(R.id.textView10);
            textView11 = itemView.findViewById(R.id.textView11);

        }
    }
}
