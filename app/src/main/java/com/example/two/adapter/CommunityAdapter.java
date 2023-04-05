package com.example.two.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.R;
import com.example.two.model.Community;
import com.example.two.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    Context context;

    List<Community> communityList;

    User user;

    public CommunityAdapter(Context context, List<Community> communityList, User user) {
        this.context = context;
        this.communityList = communityList;
        this.user = user;
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.community_row,parent,false);
        return new CommunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        Community community = communityList.get(position);

        // 프로필 이미지
        String imgProfile = community.getProfileImgUrl();
        Glide.with(context).load(imgProfile).into(holder.imgProfile);

        // 프로필 닉네임
        String nickname = community.getNickname();
        holder.profileNickname.setText(nickname);

        // 컨텐츠 이미지
        String contentImg = community.getImgUrl();
        Glide.with(context).load(contentImg).into(holder.contentImage);

        // 제목
        String title = community.getTitle();
        holder.title.setText(title);

        // 콘텐츠 내용
        String content = community.getContent();
        holder.content.setText(content);


    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgProfile;
        TextView profileNickname;
        ImageView contentImage;
        TextView title;
        TextView content;
        ImageView like;
        ImageView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            profileNickname = itemView.findViewById(R.id.profileNickname);
            contentImage = itemView.findViewById(R.id.contentImage);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);



        }
    }

}
