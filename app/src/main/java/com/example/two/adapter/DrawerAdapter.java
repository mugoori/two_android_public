package com.example.two.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.two.R;
import com.example.two.model.PartyCheckRes;
import com.example.two.model.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String,String>> set;

    PartyCheckRes partyCheckRes;

    User user;
    String captainEmail;
    public DrawerAdapter(Context context ,HashSet<HashMap<String,String>> hashdata ,User user,String captainEmail){
        this.context = context;
        set = new ArrayList<>(hashdata);
        this.user = user;
        this.captainEmail = captainEmail;
        Log.i("captain",captainEmail);
    }
    public void updatedata(HashSet<HashMap<String,String>> hashdata){
        set = new ArrayList<>(hashdata);
        notifyDataSetChanged();
        Log.i("setList",set.toString());
    }
    public void setPartyCheckRes(PartyCheckRes partyCheckRes){
        this.partyCheckRes = partyCheckRes;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.drawer_row,parent,false);
        return new DrawerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapter.ViewHolder holder, int position) {
        HashMap<String,String> dataset = set.get(position);

        String profileUrl = dataset.get("profileUrl");
        String nickname = dataset.get("nickname");
        String userEmail = dataset.get("userEmail");
        userEmail.replaceAll("\"","");

        holder.textView22.setText(nickname);
        Glide.with(context).load(profileUrl).into(holder.imgProfile);
        Boolean checker=false;
        if (partyCheckRes != null) {
            String[] partyEmail = partyCheckRes.getMemberEmail();
            for(int i = 0 ; i < partyEmail.length; i++){
                String mail = partyEmail[i];
                Log.i("drawermail",mail);
                if(userEmail.equals(mail) ){
                    checker = true;
                }
            }
            if (checker){
                holder.checkpay.setVisibility(View.VISIBLE);
            } else {
                if(userEmail.equals(captainEmail)){
                    // 방장
                    holder.checkpay.setVisibility(View.VISIBLE);

                }else {
                    holder.checkpay.setVisibility(View.GONE);
                }
            }
        }else{
            if(user.getUserEmail().equals(captainEmail)){
                // 방장
                holder.checkpay.setVisibility(View.VISIBLE);

            }else {
                holder.checkpay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return set.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgProfile;
        TextView textView22;
        ImageView checkpay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            textView22 = itemView.findViewById(R.id.textView22);
            checkpay = itemView.findViewById(R.id.checkpay);

        }
    }
}
