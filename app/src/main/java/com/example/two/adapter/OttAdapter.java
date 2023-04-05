package com.example.two.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.two.PartyChatActivity;
import com.example.two.R;
import com.example.two.model.Chat;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class OttAdapter extends RecyclerView.Adapter<OttAdapter.ViewHolder> {

    Context context;
    ArrayList<Chat> arrayList;
    User user;

    public OttAdapter(Context context, ArrayList<Chat> arrayList, User user) {
        this.context = context;
        this.arrayList = arrayList;
        this.user = user;
    }

    @NonNull
    @Override
    public OttAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.useott_row,parent,false);
        return new OttAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OttAdapter.ViewHolder holder, int position) {
        Chat chat = arrayList.get(position);
        String service = chat.getService();

        if (service.equals("넷플릭스")){
            holder.imageView.setImageResource(R.drawable.netflix);
        }else if (service.equals("훌루")){
            holder.imageView.setImageResource(R.drawable.hulu);
        }else if(service.equals("아마존 프라임")){
            holder.imageView.setImageResource(R.drawable.amazonprime);
        }else{
            holder.imageView.setImageResource(R.drawable.disney);
        }

        holder.ottTitle.setText(chat.getTitle());
        holder.ottService.setText(chat.getService());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView ottService;
        TextView ottTitle;
        CardView cardView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterView);
            ottService = itemView.findViewById(R.id.txtOtt);
            ottTitle = itemView.findViewById(R.id.txtContent );
            cardView = itemView.findViewById(R.id.card);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    Chat chat = arrayList.get(index);

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
