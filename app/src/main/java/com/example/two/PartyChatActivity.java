package com.example.two;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.two.Api.ChatApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.PartyApi;
import com.example.two.Api.UserApi;
import com.example.two.adapter.ChatAdapter;
import com.example.two.adapter.DrawerAdapter;
import com.example.two.config.Config;
import com.example.two.fragment.PartyFragment;
import com.example.two.model.MessageItem;
import com.example.two.model.Party;
import com.example.two.model.PartyCheckRes;
import com.example.two.model.User;
import com.example.two.model.UserList;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;


import kr.co.bootpay.android.Bootpay;
import kr.co.bootpay.android.BootpayAnalytics;
import kr.co.bootpay.android.events.BootpayEventListener;
import kr.co.bootpay.android.models.BootItem;
import kr.co.bootpay.android.models.BootUser;
import kr.co.bootpay.android.models.Payload;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class PartyChatActivity extends AppCompatActivity {

    EditText editMsg;
    ListView listView;

    Button btn;
    ArrayList<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;

    // 사이드바 관련 멤버 변수
    DrawerLayout drawerLayout;
    View drawerView;

    int partyBoardId;


    int index;
    User user;
    RecyclerView drawerRecyclerView;
    DrawerAdapter drawerAdapter;

    Button btnId;
    Button btnPay;
    Button btnfinish;
    PartyCheckRes partyCheckRes;
    HashSet<HashMap<String,String>> hash = new HashSet<>();
    String captainEmail;

    String serviceId;
    String servicePassword;
    String AccessToken;
    int captainId;
    String finishedAt;
    double price;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_chat);
        if(getSupportActionBar()!=null) {

            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }

        Intent intent = getIntent();
        captainEmail = intent.getStringExtra("captainEmail");
        serviceId = intent.getStringExtra("serviceId");
        servicePassword = intent.getStringExtra("servicePassword");
        user = (User) intent.getSerializableExtra("user");
        captainId = intent.getIntExtra("userId",0);
        Log.i("captain", String.valueOf(captainId));
        finishedAt = intent.getStringExtra("finishedAt");

        HashMap<String,String> captaindata = new HashMap<>();
        captaindata.put("nickname",intent.getStringExtra("captainnickname"));
        captaindata.put("profileUrl",intent.getStringExtra("captainprofileImgUrl"));
        captaindata.put("userEmail",captainEmail);
        hash.add(captaindata);

        HashMap<String,String> data = new HashMap<>();
        data.put("nickname",user.getNickname());
        data.put("profileUrl",user.getProfileImgUrl());
        data.put("userEmail",user.getUserEmail());
        hash.add(data);
        //부트패이 초기화
        BootpayAnalytics.init(this, Config.access_key);

        editMsg = findViewById(R.id.editMsg);
        listView = findViewById(R.id.listview);
        partyBoardId = getIntent().getIntExtra("partyBoardId",0);


        // 사이드 바 연결
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        drawerRecyclerView = findViewById(R.id.drawerRecyclerView);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        drawerAdapter = new DrawerAdapter(PartyChatActivity.this,hash,user,captainEmail);
        drawerRecyclerView.setAdapter(drawerAdapter);

        btnId = findViewById(R.id.btnId);
        btnPay = findViewById(R.id.btnPay);
        btnfinish = findViewById(R.id.btnfinish);
        btn = findViewById(R.id.btn);
        adapter = new ChatAdapter(messageItems,getLayoutInflater(),user);
        listView.setAdapter(adapter);
        partycheked();
        firebaseDatabase= firebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference("chatroom"+"/"+partyBoardId);
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageItem messageItem= snapshot.getValue(MessageItem.class);
                messageItems.add(messageItem);
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동

                HashMap<String,String> data = new HashMap<>();
                data.put("nickname",messageItem.getNickname());
                data.put("profileUrl",messageItem.getProfileUrl());
                data.put("userEmail",messageItem.getEmail());
                hash.add(data);
                drawerAdapter.updatedata(hash);
                partycheked();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nickName= user.getNickname();
                String message= editMsg.getText().toString();
                String pofileUrl= user.getProfileImgUrl();
                String userEmail = user.getUserEmail();
                Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
                String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

                MessageItem messageItem= new MessageItem(nickName,message,time,pofileUrl,userEmail);
                //'char'노드에 MessageItem객체를 통해
                chatRef.push().setValue(messageItem);
                editMsg.setText("");

                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });

        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PartyChatActivity.this);
                builder.setTitle("서비스 계정 확인");
                builder.setMessage("서비스 ID :"+serviceId+"\n서비스 PW :"+servicePassword);
                builder.setPositiveButton("확인",null);
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment();
            }
        });

    }

    private void payment() {
        String service = partyCheckRes.getService();
        String[] list = getResources().getStringArray(R.array.ott);

        String id;
        if(list[0].equals(service)){
            price = 4750d;
            id = "1";
        }else if (list[1].equals(service)){
            price = 4500d;
            id = "2";
        }else if(list[2].equals(service)){
            price =4750d;
            id = "3";
        }else{
            price = 3000d;
            id = "4";
        }

        //유저 정보
        BootUser bootUser = new BootUser();
        bootUser.setUsername(user.getName());
        bootUser.setEmail(user.getUserEmail());
        //아이템 정보
        BootItem bootItem = new BootItem();
        bootItem.setName(service);
        bootItem.setPrice(price);
        bootItem.setId(id);

        Payload payload = new Payload();
        payload.setApplicationId(Config.access_key);
        payload.setOrderName(bootItem.getName());
        payload.setPrice(bootItem.getPrice());
        payload.setUser(bootUser);
        payload.setOrderId(bootItem.getId());


        Bootpay.init(getSupportFragmentManager(),PartyChatActivity.this)
                .setPayload(payload)
                .setEventListener(new BootpayEventListener() {
                    @Override
                    public void onCancel(String data) {
                        Log.i("payment","cancel");
                        Bootpay.removePaymentWindow();
                    }

                    @Override
                    public void onError(String data) {
                        Log.i("bootpay_error",data);
                    }

                    @Override
                    public void onClose() {
                        Log.i("bootpay_close","닫힘!");
                        try {
                            savepayment();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onIssued(String data) {
                        Log.i("bootpay_issued",data);
                    }

                    @Override
                    public boolean onConfirm(String data) {
                        return false;
                    }

                    @Override
                    public void onDone(String data) {
                        Log.i("bootpay_done",data);
                    }
                }).requestPayment();
    }

    private void savepayment() throws JSONException {
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken", "");

        Party party = new Party();
        party.setPartyBoardId(String.valueOf(partyBoardId));
        party.setCaptain(String.valueOf(captainId));
        party.setPay(String.valueOf(price));
        party.setFinishedAt(finishedAt);

        Retrofit retrofit = NetworkClient2.getRetrofitClient(PartyChatActivity.this);
        PartyApi api = retrofit.create(PartyApi.class);
        Call<HashMap<String,String>> call = api.setParty("Bearer "+AccessToken,party);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.code() == 200){
                    String[] list = partyCheckRes.getMemberEmail();
                    String useremail = user.getUserEmail();
                    String[] emailist = new String[list.length+1];
                    for(int i = 0;i<list.length;i++){
                        emailist[i] = list[i];
                    }
                    emailist[list.length] = useremail;
                    partyCheckRes.setMemberEmail(emailist);

                    drawerAdapter.setPartyCheckRes(partyCheckRes);
                    payedcheck();

                    AlertDialog.Builder builder = new AlertDialog.Builder(PartyChatActivity.this);
                    builder.setTitle("완료");
                    builder.setMessage("결제가 완료되었습니다.");
                    builder.setPositiveButton("확인",null);

                    AlertDialog ad = builder.create();
                    ad.show();

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });
    }

    private void payedcheck() {
        String[] partymemeber = partyCheckRes.getMemberEmail();
        int memCnt = Integer.parseInt(partyCheckRes.getMemberCnt());
        if(memCnt<3) {

            if ( !user.getUserEmail().equals(captainEmail)){
                Boolean check = false;

                for (int i = 0; i < partymemeber.length; i++) {
                    String member = partymemeber[i].replaceAll("\"", "");

                    if (user.getUserEmail().equals(member)) {
                        check = true;
                    }
                }

                if (check) {
                    btnPay.setVisibility(View.GONE);
                    btnId.setVisibility(View.VISIBLE);
                    btnfinish.setVisibility(View.GONE);
                } else {
                    btnPay.setVisibility(View.VISIBLE);
                    btnId.setVisibility(View.GONE);
                    btnfinish.setVisibility(View.GONE);
                }
            }

        }else {
                btnfinish.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.GONE);
                btnId.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnCheck){
            drawerLayout.openDrawer(drawerView);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(100,intent);
        finish();
    }

    public void partycheked(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getApplicationContext());

        ChatApi api = retrofit.create(ChatApi.class);

        Call<PartyCheckRes> call = api.getCheckParty(partyBoardId);
        call.enqueue(new Callback<PartyCheckRes>() {
            @Override
            public void onResponse(Call<PartyCheckRes> call, Response<PartyCheckRes> response) {
                if(response.code() == 200 ){
                    partyCheckRes = response.body();
                    drawerAdapter.setPartyCheckRes(partyCheckRes);
                    payedcheck();
                }
            }

            @Override
            public void onFailure(Call<PartyCheckRes> call, Throwable t) {

            }
        });
    }
}
