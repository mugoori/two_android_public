package com.example.two.adapter;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentReviewApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.MyReviewActivity;
import com.example.two.R;
import com.example.two.ReviewAddActivity;
import com.example.two.ReviewUpdateActivity;
import com.example.two.SearchContentActivity;
import com.example.two.config.Config;
import com.example.two.model.Community;
import com.example.two.model.ContentReview;
import com.example.two.model.User;
import com.example.two.model.reView;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContentReviewAdapter extends RecyclerView.Adapter<ContentReviewAdapter.ViewHolder> {

    Context context;

    ArrayList<ContentReview> contentReviewArrayList;
    User user;

    ContentReview contentReview;

    int activityNum;

    public ContentReviewAdapter(Context context , ArrayList<ContentReview> contentReviewArrayList, User user,int activityNum){
        this.context = context;
        this.contentReviewArrayList = contentReviewArrayList;
        this.user = user;
        this.activityNum = activityNum;
        // 0 : SearchContentActivity
        // 1 : MyReviewActivity
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.review_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentReview contentReview = contentReviewArrayList.get(position);

        // 프로필 이미지
        if(contentReview.getProfileImgUrl() != null) {
            String profileImg = contentReview.getProfileImgUrl();
            Glide.with(context).load(profileImg).into(holder.imgProfile);
        }
        // 닉네임 설정
        if(contentReview.getNickname() != null) {
            String nickname = contentReview.getNickname();
            holder.profileNickname.setText(nickname);
        }
        // 제목 설정
        if(contentReview.getTitle() != null) {
            String title = contentReview.getTitle();
            holder.title.setText(title);
        }
        // 내용 설정
        if(contentReview.getContent() != null) {
            String content = contentReview.getContent();
            holder.content.setText(content);
        }
        // 별점 설정
        if ( contentReview.getUserRating() != null ) {
            String rating = contentReview.getUserRating();
            holder.rating.setText(rating);
        }
    }

    @Override
    public int getItemCount() {
        return contentReviewArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView ReviewCardView;
        CircleImageView imgProfile;
        TextView profileNickname;
        TextView title;
        TextView content;
        TextView rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ReviewCardView = itemView.findViewById(R.id.ReviewCardView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            profileNickname = itemView.findViewById(R.id.profileNickname);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            rating = itemView.findViewById(R.id.rating);

            ReviewCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int index =getAdapterPosition();
                    contentReview = contentReviewArrayList.get(index);
                    if(user.getUserEmail().equals(contentReview.getUserEmail())) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle("변경하시겠습니까?");

                        builder.setItems(R.array.ud, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int pos) {
                                //수정
                                if(pos == 0) {
                                    Intent intent = new Intent(context, ReviewUpdateActivity.class);
                                    intent.putExtra("contentReview",(Serializable) contentReview);
                                    intent.putExtra("contentReviewPosition",index);
                                    if(activityNum == 0) {
                                        ((SearchContentActivity) context).launcher.launch(intent);
                                    }else if (activityNum == 1){
                                        ((MyReviewActivity)context).launcher.launch(intent);
                                    }
                                }else if(pos == 1){
                                    reviewDelete(Integer.parseInt(contentReview.getContentId()),
                                            Integer.parseInt(contentReview.getContentReviewId()),index);
                                }
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    return true;
                }
            });
        }
    }

    public void reviewDelete(int contentId,int reviewId,int index){

        SharedPreferences sp = context.getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        String token = sp.getString("AccessToken","");

        Retrofit retrofit = NetworkClient2.getRetrofitClient(context);

        ContentReviewApi api = retrofit.create(ContentReviewApi.class);

        Call<reView> call =api.DeleteReview("Bearer "+token,contentId,reviewId);

        call.enqueue(new Callback<reView>() {
            @Override
            public void onResponse(Call<reView> call, Response<reView> response) {
                contentReviewArrayList.remove(index);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<reView> call, Throwable t) {
                return;
            }
        });

    }

}
