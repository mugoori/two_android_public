package com.example.two.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.R;
import com.example.two.SearchContentActivity;
import com.example.two.config.Config;
import com.example.two.model.ContentWatch;
import com.example.two.model.Seach;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SeachAdapter extends RecyclerView.Adapter<SeachAdapter.ViewHolder> {
    Context context;

    ArrayList<Seach> seachArrayList;

    Seach selectSearch;
    String AccessToken;

    int path;

    User user;

    public SeachAdapter(Context context, ArrayList<Seach> seachArrayList,User user) {
        this.context = context;
        this.seachArrayList = seachArrayList;
        this.user = user;
    }

    @NonNull
    @Override
    public SeachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchmovie_row,parent,false);

        return new SeachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeachAdapter.ViewHolder holder, int position) {
        Seach seach = seachArrayList.get(position);
        Log.i("SIGN","OK");
        Glide.with(context)
                .load(seach.getImgUrl())
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(holder.searchMoviePoster);

        holder.titleSearchMovie1.setText(seach.getTitle());
        holder.rateSearchMovie1.setText(seach.getContentRating().toString());
        String[] splitText = seach.getGenre().replace("'","").split(",");
        holder.genreSearchMovie1.setText(splitText[0]);
        String[] splitText2 = seach.getCreatedYear().split("-");
        holder.dateSearchMovie1.setText(splitText2[0]);

    }

    @Override
    public int getItemCount() {
        return seachArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView searchMoviePoster ;
        TextView titleSearchMovie1;
        TextView rateSearchMovie1;
        TextView genreSearchMovie1;
        TextView dateSearchMovie1;

        LinearLayout layoutMovie1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchMoviePoster = itemView.findViewById(R.id.searchMoviePoster);
            titleSearchMovie1 = itemView.findViewById(R.id.titleSearchMovie1);
            rateSearchMovie1 = itemView.findViewById(R.id.rateSearchMovie1);
            genreSearchMovie1 = itemView.findViewById(R.id.genreSearchMovie1);
            dateSearchMovie1 = itemView.findViewById(R.id.dateSearchMovie1);
            layoutMovie1 = itemView.findViewById(R.id.layoutMovie1);

            layoutMovie1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    selectSearch = seachArrayList.get(index);
                    Intent intent = new Intent(context, SearchContentActivity.class);
                    intent.putExtra("Id",seachArrayList.get(index).getId());
                    intent.putExtra("ImgUrl",seachArrayList.get(index).getImgUrl());
                    intent.putExtra("title",seachArrayList.get(index).getTitle());
                    intent.putExtra("content",seachArrayList.get(index).getContent());
                    intent.putExtra("year",seachArrayList.get(index).getCreatedYear());
                    intent.putExtra("rating",seachArrayList.get(index).getContentRating());
                    intent.putExtra("genre",seachArrayList.get(index).getGenre());
                    intent.putExtra("tmdbcontentId",seachArrayList.get(index).getTmdbcontentId());
                    intent.putExtra("user",(Serializable) user);
                    (context).startActivity(intent);

                    path = selectSearch.getId();
                    Log.i("path", String.valueOf(selectSearch.getId()));

                    Retrofit retrofit = NetworkClient2.getRetrofitClient(context);

                    ContentApi api = retrofit.create(ContentApi.class);

                    SharedPreferences sp = context.getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
                    AccessToken = sp.getString("AccessToken","");
                    Call<ContentWatch> call = api.contentWatch("Bearer " + AccessToken, path);

                    call.enqueue(new Callback<ContentWatch>() {
                        @Override
                        public void onResponse(Call<ContentWatch> call, Response<ContentWatch> response) {

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("path",path);
                            editor.apply();

                        }

                        @Override
                        public void onFailure(Call<ContentWatch> call, Throwable t) {

                        }
                    });

                }
            });

        }
    }
}
