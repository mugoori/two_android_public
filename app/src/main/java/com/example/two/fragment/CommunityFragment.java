package com.example.two.fragment;

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

import com.example.two.Api.CommunityApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.CommunityAddActivity;
import com.example.two.MainActivity;
import com.example.two.R;
import com.example.two.adapter.CommunityAdapter;
import com.example.two.model.Community;
import com.example.two.model.CommunityRes;
import com.example.two.model.User;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {
    MainActivity activity;
    User user;
    RecyclerView communityRecyclerView;

    // 서버에서 받아올 데이터
    List<Community> communityList;

    // 페이징 번호
    int page = 0;

    // 어댑터
    CommunityAdapter communityAdapter;

    Button communityBtn;

    Community community;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                            if(result.getResultCode() == 101){
                                Intent intent = result.getData();
                                community = (Community) intent.getSerializableExtra("community");
                                Log.i("정상작동?2",community.getNickname());
                                communityList.add(0,community);
                                communityAdapter.notifyDataSetChanged();
                            }
                        }
                    });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        Bundle bundle = getArguments();
        if(bundle != null ){
            user = bundle.getParcelable("user");}
        communityBtn = view.findViewById(R.id.communityBtn);
        communityRecyclerView = view.findViewById(R.id.communityRecyclerView);
        communityRecyclerView.setHasFixedSize(true);
        communityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        communityRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();
                if (lastPosition + 1 == totalCount ) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!
                    page +=1;
                    addCommunityData();
                }
            }
        });
        getCommunityData();

        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityAddActivity.class);
                intent.putExtra("user",(Serializable) user);
                launcher.launch(intent);
            }
        });


        return view;
    }
    public void getCommunityData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());
        CommunityApi api = retrofit.create(CommunityApi.class);
        Call<CommunityRes> call = api.getCommunityAllData(page);
        call.enqueue(new Callback<CommunityRes>() {
            @Override
            public void onResponse(Call<CommunityRes> call, Response<CommunityRes> response) {
                if(response.code() == 200) {
                    communityList = response.body().getCommunityList();
                    communityAdapter = new CommunityAdapter(getActivity(),communityList,user);
                    communityRecyclerView.setAdapter(communityAdapter);
                }
            }

            @Override
            public void onFailure(Call<CommunityRes> call, Throwable t) {

            }
        });
    }
    public void addCommunityData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());
        CommunityApi api = retrofit.create(CommunityApi.class);
        Call<CommunityRes> call = api.getCommunityAllData(page);
        call.enqueue(new Callback<CommunityRes>() {
            @Override
            public void onResponse(Call<CommunityRes> call, Response<CommunityRes> response) {
                if(response.code() == 200) {

                    communityList.addAll(response.body().getCommunityList());
                    communityAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CommunityRes> call, Throwable t) {

            }
        });
    }
}