package com.example.two.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.two.model.Movie;
import com.example.two.model.MovieRank;
import com.example.two.model.MovieRankList;

import java.util.ArrayList;

public class RankAllAdapter extends RecyclerView.Adapter<RankAllAdapter.ViewHolder> {



    Context context;
    ArrayList<MovieRank> movieArrayList;




    public RankAllAdapter(Context context, ArrayList<MovieRank> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }


    @NonNull
    @Override
    public RankAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rank_row,parent,false);

        return new RankAllAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAllAdapter.ViewHolder holder, int position) {
        MovieRank movieRank = movieArrayList.get(position);
        Glide.with(context).load(movieRank.getImgUrl()).into(holder.poster1);
        holder.titleSearchMovie1.setText(movieRank.getTitle());
        holder.rateSearchMovie1.setText(movieRank.getContentRating());
        String genre1 = movieRank.getGenre().replace("'","");
        String [] genre2 = genre1.split(",");
        holder.textView7.setText(genre2[0]);
        holder.textView46.setText(movieRank.getCreatedYear().substring(0,4));

        String rank = String.valueOf(position+1);


        Log.i("position 1", String.valueOf(position));

        if (position == 0 || position == 1 || position == 2) {
            holder.txtRank.setText(rank);
            holder.txtRank.setTextSize(25);
            holder.txtRank.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.txtRank.setText(rank);
            holder.txtRank.setTextSize(18);
            holder.txtRank.setTextColor(Color.parseColor("#FFFFFF"));
        }





    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster1;
        TextView titleSearchMovie1;
        TextView rateSearchMovie1;
        // 장르
        TextView textView7;
        // 출시년도
        TextView textView46;

        TextView txtRank;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster1 = itemView.findViewById(R.id.poster1);
            titleSearchMovie1 = itemView.findViewById(R.id.titleSearchMovie1);
            rateSearchMovie1 = itemView.findViewById(R.id.rateSearchMovie1);
            textView7 = itemView.findViewById(R.id.textView7);
            textView46 = itemView.findViewById(R.id.textView46);
            txtRank = itemView.findViewById(R.id.txtRank);




        }
    }
}
