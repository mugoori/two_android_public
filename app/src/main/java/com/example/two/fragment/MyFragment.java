package com.example.two.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.ChoiceActivity;
import com.example.two.MyReviewActivity;
import com.example.two.R;
import com.example.two.UseOTTActivity;
import com.example.two.UserUpdateActivity;
import com.example.two.adapter.MyAdapter;
import com.example.two.config.Config;
import com.example.two.model.ContentWatch;
import com.example.two.model.ContentWatchList;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    private Parcelable recyclerViewState;
    CircleImageView imgProfile;
    TextView txtNickname;
    ImageView imgUpdate;

    User user;
    CardView cvChoice;
    CardView cvMyReview;
    CardView cvUseOTT;

    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<ContentWatch> contentWatchArrayList = new ArrayList<>();

    String AccessToken;

    int page;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // activity = (MainActivity) getActivity();



    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>(){
                        @Override
                        public void onActivityResult(ActivityResult result){
                            if(result.getResultCode() == 0){

                            }
                        }
                    }
            );

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my, container, false);

        Bundle bundle = getArguments();
        user = bundle.getParcelable("user");

        Log.i("몇번 호출","1");
        cvChoice = view.findViewById(R.id.cvChoice);
        cvMyReview = view.findViewById(R.id.cvMyReview);
        cvUseOTT = view.findViewById(R.id.cvUseOTT);

        txtNickname = view.findViewById(R.id.txtNickname);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgUpdate = view.findViewById(R.id.imgUpdate);


        cvChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChoiceActivity.class);
                startActivity(intent);
            }
        });
        cvMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyReviewActivity.class);
                intent.putExtra("user",(Serializable) user);
                startActivity(intent);
            }
        });
        cvUseOTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UseOTTActivity.class);
                intent.putExtra("user",(Serializable) user);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        getMyList();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

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


                // 스크롤을 데이터 맨 끝까지 한 상태.
                if (lastPosition + 1 == totalCount) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!

                    addMyList();

                }
            }
        });




        // 쉐어드 객체 생성
        SharedPreferences sp = this.getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);

        // 닉네임과 프로필 주소 가져옴
        String pofileUrl = user.getProfileImgUrl();
        String nickName = user.getNickname();
        String password = user.getPassword();
        String email = user.getUserEmail();
        String token = sp.getString("AccessToken","");


        // 프로필 사진과 닉네임 세팅
        Glide.with(getActivity()).load(pofileUrl).into(imgProfile);
        txtNickname.setText(nickName);

        // 유저 정보 업데이트 액티비티 넘어가기
        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserUpdateActivity.class);
                intent.putExtra("profile",pofileUrl);
                intent.putExtra("nickname",nickName);
                intent.putExtra("password",password);
                intent.putExtra("email",email);
                intent.putExtra("token",token);
                launcher.launch(intent);


            }
        });

        return view;
    }

    private void getMyList() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());

        ContentApi api = retrofit.create(ContentApi.class);

        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Call<ContentWatchList> call = api.getContentWatch("Bearer "+AccessToken,0);

        call.enqueue(new Callback<ContentWatchList>() {
            @Override
            public void onResponse(Call<ContentWatchList> call, Response<ContentWatchList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요
                    contentWatchArrayList.clear();

                    Log.i("SIGN","OK");

                    // 데이터를 받았으니 리사이클러 표시

                    contentWatchArrayList.addAll(response.body().getContentWatch_list());




                    adapter = new MyAdapter(getActivity(),contentWatchArrayList);

                    recyclerView.setAdapter(adapter);



                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ContentWatchList> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }


    private void addMyList() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());

        ContentApi api = retrofit.create(ContentApi.class);

        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Call<ContentWatchList> call = api.getContentWatch("Bearer "+AccessToken,page+1);

        call.enqueue(new Callback<ContentWatchList>() {
            @Override
            public void onResponse(Call<ContentWatchList> call, Response<ContentWatchList> response) {

                if (response.isSuccessful()) {

                    // 데이터를 받았으니 리사이클러 표시

                    contentWatchArrayList.addAll(response.body().getContentWatch_list());


                    // 리사이클러뷰 고정하는 코드 1
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();



                    adapter = new MyAdapter(getActivity(),contentWatchArrayList);
                    page = page + 1;
                    recyclerView.setAdapter(adapter);

                    // 리사이클러뷰 고정하는 코드 2
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);





                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ContentWatchList> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }


}