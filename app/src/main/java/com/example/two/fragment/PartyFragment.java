package com.example.two.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.two.Api.NetworkClient2;
import com.example.two.MainActivity;
import com.example.two.PartyAddActivity;
import com.example.two.R;
import com.example.two.adapter.ChatRoomAdapter;
import com.example.two.Api.ChatApi;
import com.example.two.model.Chat;
import com.example.two.model.ChatRoomList;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyFragment extends Fragment {
    MainActivity activity;

    Button partyBtn;

    RecyclerView recyclerView;
    ChatRoomAdapter adapter;

    ArrayList<Chat> chatArrayList = new ArrayList<>();

    Context context;

    int page = 0;
    int partyBoardSize;
    User user;

    Chat chat;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyFragment newInstance(String param1, String param2) {
        PartyFragment fragment = new PartyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            getChatNetworkData();
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>(){
                        @Override
                        public void onActivityResult(ActivityResult result){
                            if(result.getResultCode() == 100){
                                Intent intent = result.getData();
                                chat = (Chat) intent.getSerializableExtra("chat");
                                Log.i("여기서 ",chat.getTitle());
                                chatArrayList.add(0,chat);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_party, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null ) {

            user = bundle.getParcelable("user");
//        Log.i("user",user.getUserEmail());

        }
        partyBtn = view.findViewById(R.id.partyBtn);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 맨 마지막 데이터가 화면에 보이면!!!!
                // 네트워크 통해서 데이터를 추가로 받아와라!!
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                Log.i("lastPosition" , String.valueOf(lastPosition));
                Log.i("totalcount", String.valueOf(totalCount));

                // 스크롤을 데이터 맨 끝까지 한 상태.
                if (lastPosition + 1 == totalCount) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!
                    page +=1;
                    addChatNetworkData();
                }
            }
        });

        partyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PartyAddActivity.class);
                intent.putExtra("user",(Serializable) user);
                launcher.launch(intent);

            }
        });

        return view;
    }

    private void getChatNetworkData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());

        ChatApi api = retrofit.create(ChatApi.class);

        Log.i("AAA", api.toString());

        Call<ChatRoomList> call = api.getChatingList(page);

        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(Call<ChatRoomList> call, Response<ChatRoomList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요
                    chatArrayList.clear();

                    // 데이터를 받았으니 리사이클러 표시

                    chatArrayList.addAll(response.body().getPartyBoard());

                    // 오프셋 처리하는 코드
                    int pageNum = Integer.parseInt(response.body().getPageNum());
                    page = pageNum;
                    partyBoardSize = Integer.parseInt(response.body().getPartyBoardSize());
                    adapter = new ChatRoomAdapter(getActivity(),chatArrayList,user);
                    recyclerView.setAdapter(adapter);
                    Log.i("RECYCLE", adapter.toString());

                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChatRoomList> call, Throwable t) {


            }


        });
    }

    private void addChatNetworkData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());

        ChatApi api = retrofit.create(ChatApi.class);

        Log.i("bbb", api.toString());

        Call<ChatRoomList> call = api.getChatingList(page);

        call.enqueue(new Callback<ChatRoomList>() {
            @Override
            public void onResponse(Call<ChatRoomList> call, Response<ChatRoomList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요


                    // 데이터를 받았으니 리사이클러 표시
                    int pageNum = Integer.parseInt(response.body().getPageNum());
                    chatArrayList.addAll(response.body().getPartyBoard());

                    partyBoardSize = Integer.parseInt(response.body().getPartyBoardSize());
                    // 오프셋 처리하는 코드

                    adapter.notifyDataSetChanged();
                    page = pageNum;
                    Log.i("RECYCLE", adapter.toString());

                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChatRoomList> call, Throwable t) {


            }


        });
    }

}