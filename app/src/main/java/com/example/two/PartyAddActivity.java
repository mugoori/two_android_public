package com.example.two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.two.Api.ChatApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.adapter.ChatRoomAdapter;
import com.example.two.config.Config;
import com.example.two.fragment.PartyFragment;
import com.example.two.model.Chat;
import com.example.two.model.ChatRoomList;
import com.example.two.model.PartyRes;
import com.example.two.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyAddActivity extends AppCompatActivity {
    EditText txtPartyName;
    EditText txtOttName;
    EditText txtOttPassword;
    TextView txtEndDate;

    Spinner serviceSp;
    Button btnCreateParty;


    String date = "";

    User user;

    String partyName;

    ArrayList<Chat> chatArrayList = new ArrayList<>();
    int partyBoardId;
    String token;

    Chat chat;

    boolean isFirst= true; // 첫 실행을 구분하기위한 멤버변수
    boolean isChanged= false; //프로필 변경 확인을 위한 멤버변수

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_add);

        serviceSp = findViewById(R.id.serviceSp);
        txtPartyName=findViewById(R.id.txtPartyName);
        txtOttName=findViewById(R.id.txtOttName);
        txtOttPassword=findViewById(R.id.txtOttPassword);
        btnCreateParty=findViewById(R.id.btnCreateParty);
        txtEndDate = findViewById(R.id.txtEndDate);

        user = (User) getIntent().getSerializableExtra("user");


        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar current = Calendar.getInstance();

                new DatePickerDialog(
                        PartyAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Log.i("MEMO_APP", "년도 : " + i + ", 월 :"+i1+", 일 :"+i2);
                                // i : 년도, i1 : 월(0~11) , i2 : 일

                                int month = i1 + 1;
                                String strMonth;
                                if(month < 10){
                                    strMonth = "0"+month;
                                }else{
                                    strMonth = ""+month;
                                }

                                String strDay;
                                if(i2 < 10){
                                    strDay = "0"+i2;
                                }else{
                                    strDay= ""+i2;
                                }

                                date = i + "-" + strMonth + "-" + strDay;
                                txtEndDate.setText(date);


                            }
                        },
                        current.get(Calendar.YEAR),
                        current.get(Calendar.MONTH),
                        current.get(Calendar.DAY_OF_MONTH)
                ).show();

            }
        });


        // 파이어베이스 연결항목.
        btnCreateParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
//                getPartyData();

            }
        });


    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    void saveData(){
        //EditText의 닉네임 가져오기
        String service = serviceSp.getSelectedItem().toString();
        partyName = txtPartyName.getText().toString().trim();
        String imgUri =user.getProfileImgUrl();
        String nickname=user.getNickname();
        String serviceEmail = txtOttName.getText().toString().trim();
        String servicePassword = txtOttPassword.getText().toString().trim();
        chat = new Chat(service,partyName,serviceEmail,servicePassword,date);
        Retrofit retrofit = NetworkClient2.getRetrofitClient(PartyAddActivity.this);

        ChatApi api = retrofit.create(ChatApi.class);

        Log.i("AAA", api.toString());
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");
        Call<PartyRes> call =api.makeChating("Bearer "+token,chat);

        call.enqueue(new Callback<PartyRes>() {
            @Override
            public void onResponse(Call<PartyRes> call, Response<PartyRes> response) {
                if (response.code() == 200) {
                    Chat addChat = response.body().getParty();
                    Log.i("asdasd",addChat.getTitle());
                    Intent intent = new Intent();
                    intent.putExtra("chat",addChat);
                    setResult(100,intent);
                    finish();

                } else {
                    Toast.makeText(PartyAddActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<PartyRes> call, Throwable t) {

            }
        });




        //1. Firebase Database에 nickName, profileUrl을 저장
        //firebase DB관리자 객체 생성
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        //'profiles'라는 객체 생성
        DatabaseReference profileRef= firebaseDatabase.getReference("chatroom");

        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장


                        //2. 내 phone에 nickName, profileUrl을 저장
                        //저장이 완료되었으니 ChatActivity로 전

    }

    void getPartyData(){

        Retrofit retrofit = NetworkClient2.getRetrofitClient(PartyAddActivity.this);

        ChatApi api = retrofit.create(ChatApi.class);

        Log.i("AAA", api.toString());

        Call<ChatRoomList> call = api.getMyChatingList("Bearer "+token,0);

        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(Call<ChatRoomList> call, Response<ChatRoomList> response) {
                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요

                    // 데이터를 받았으니 리사이클러 표시

                    chatArrayList.addAll(response.body().getPartyBoard());
                    partyBoardId =chatArrayList.get(0).getPartyBoardId();

                    // 오프셋 처리하는 코드




                } else {
                    Toast.makeText(PartyAddActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChatRoomList> call, Throwable t) {

            }
        });

    }
}