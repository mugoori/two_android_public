package com.example.two.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.R;
import com.example.two.config.Config;
import com.example.two.model.Choice;
import com.example.two.model.Res;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {
    Context context;

    ArrayList<Choice> choiceArrayList;

    int Id;

    String token;

    public ChoiceAdapter(Context context, ArrayList<Choice> choiceArrayList) {
        this.context = context;
        this.choiceArrayList = choiceArrayList;
    }

    @NonNull
    @Override
    public ChoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likecontent_row,parent,false);

        return new ChoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceAdapter.ViewHolder holder, int position) {
        Choice choice = choiceArrayList.get(position);
        Log.i("SIGN","OK");
        Glide.with(context)
                .load(choice.getImgUrl())
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(holder.searchMoviePoster);

        holder.titleSearchMovie1.setText(choice.getTitle());
        holder.txtcontent.setText(choice.getContent());
    }

    @Override
    public int getItemCount() {
        return choiceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView searchMoviePoster ;
        TextView titleSearchMovie1;

        TextView txtcontent;
        LinearLayout layoutMovie1;

        ImageView btnLikeDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            searchMoviePoster = itemView.findViewById(R.id.searchMoviePoster);
            titleSearchMovie1 = itemView.findViewById(R.id.titleSearchMovie1);
            txtcontent = itemView.findViewById(R.id.txtcontent);
            btnLikeDelete = itemView.findViewById(R.id.btnLikeDelete);
            layoutMovie1 = itemView.findViewById(R.id.layoutMovie1);

            btnLikeDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    Choice choice = choiceArrayList.get(index);
                    Id=choice.getContentId();
                    disLike();


                }
            });
        }

        public void disLike(){

            Retrofit retrofit = NetworkClient2.getRetrofitClient(context);

            ContentApi api = retrofit.create(ContentApi.class);

            Log.i("AAA", api.toString());
            SharedPreferences sp = context.getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
            token = sp.getString("AccessToken","");
            Call<Res> call =api.contentDisLike("Bearer "+token,Id);

            call.enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Call<Res> call, Response<Res> response) {
                    if (response.isSuccessful()) {

                        return;



                    } else {
                        Toast.makeText(context, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Res> call, Throwable t) {

                }
            });



        }
    }


}
