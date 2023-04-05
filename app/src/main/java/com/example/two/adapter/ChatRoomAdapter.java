package com.example.two.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.PartyChatActivity;
import com.example.two.R;
import com.example.two.model.Chat;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    Context context;

    ArrayList<Chat> chatArrayList;

    User user;


    public ChatRoomAdapter(Context context, ArrayList<Chat> chatArrayList,User user) {
        this.context = context;
        this.chatArrayList = chatArrayList;
        this.user=user;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_row,parent,false);


        return new ChatRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chatArrayList.get(position);
        holder.partyName.setText(chat.getTitle());
        holder.tag.setText(chat.getService());
        Glide.with(context).load(chat.getProfileImgUrl()).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView partyName;
        TextView headCount;
        TextView tag;

        CardView cardView;

        CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            partyName = itemView.findViewById(R.id.partyName);
            cardView = itemView.findViewById(R.id.cardView);
            headCount = itemView.findViewById(R.id.headCount);
            tag = itemView.findViewById(R.id.tag);
            circleImageView = itemView.findViewById(R.id.imgProfile);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    Chat chat = chatArrayList.get(index);

                    //파일 업로드
                    //1. Firebase Database에 nickName, profileUrl을 저장
                    //firebase DB관리자 객체 생성
//                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                    //'profiles'라는 객체 생성
//                    DatabaseReference profileRef = firebaseDatabase.getReference("chatroom");
//
//                    //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
//                    profileRef.child(partyName).setValue(partyName);
//                    profileRef.child(partyName).setValue(imgUri);
//                    profileRef.child(partyName).setValue(nickname);

                    Intent intent = new Intent(context, PartyChatActivity.class);
                    intent.putExtra("partyBoardId",chat.getPartyBoardId());
                    intent.putExtra("index", index);
                    intent.putExtra("title",chat.getTitle());
                    intent.putExtra("user",(Serializable) user);
                    intent.putExtra("captainEmail",chat.getUserEmail());
                    intent.putExtra("serviceId",chat.getServiceId());
                    intent.putExtra("servicePassword",chat.getServicePassword());
                    intent.putExtra("userId",chat.getUserId());
                    intent.putExtra("finishedAt",chat.getFinishedAt());
                    intent.putExtra("captainnickname",chat.getNickname());
                    intent.putExtra("captainprofileImgUrl",chat.getProfileImgUrl());
                    context.startActivity(intent);



                }

            });

        }
    }




}

