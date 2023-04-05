package com.example.two.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.two.R;
import com.example.two.model.MessageItem;
import com.example.two.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<MessageItem> messageItems;
    LayoutInflater layoutInflater;

    User user;
    public ChatAdapter(ArrayList<MessageItem> messageItems, LayoutInflater layoutInflater,User user) {
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
        this.user = user;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        MessageItem item = messageItems.get(position);

        //재활용할 뷰는 사용하지 않음!!
        View itemView=null;

        //메세지가 내 메세지인지??

        if(item.getNickname().equals(user.getNickname())){

            itemView= layoutInflater.inflate(R.layout.my_msgbox,viewGroup,false);
        }else{
            itemView= layoutInflater.inflate(R.layout.other_msgbox,viewGroup,false);
        }

        //만들어진 itemView에 값들 설정
        CircleImageView iv= itemView.findViewById(R.id.imgProfile);
        TextView tvName= itemView.findViewById(R.id.txtName);
        TextView tvMsg= itemView.findViewById(R.id.txtMsg);
        TextView tvTime= itemView.findViewById(R.id.txtTime);

        tvName.setText(item.getNickname());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());

        Glide.with(itemView).load(item.getProfileUrl()).into(iv);

        return itemView;
    }
}