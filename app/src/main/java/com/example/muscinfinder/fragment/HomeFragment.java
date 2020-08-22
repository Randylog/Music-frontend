package com.example.muscinfinder.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muscinfinder.R;
import com.example.muscinfinder.adapter.MyAdapter;
import com.example.muscinfinder.adapter.RecyclerviewInstituteAdapter;
import com.example.muscinfinder.adapter.RecyclerviewRecommendAdapter;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.api_classes.BaseURL;
import com.example.muscinfinder.modal.InstituteModal;
import com.example.muscinfinder.modal.RecommendModal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewProduct, recyclerViewNearby;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] slideImage= {R.drawable.aa,R.drawable.bb, R.drawable.cc,
            R.drawable.dd};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffb902"));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerViewProduct = view.findViewById(R.id.recyclerView_product);
        recyclerViewNearby = view.findViewById(R.id.recyclerView_nearby);
        recyclerViewProduct.setNestedScrollingEnabled(false);
        recyclerViewNearby.setNestedScrollingEnabled(false);

        for(int i=0;i<slideImage.length;i++)
            XMENArray.add(slideImage[i]);

        mPager = view.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(),XMENArray));
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slideImage.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        getProduct();
        getInstitute();
        return view;
    }

    // restaurant json
    public void getProduct(){
        ApiInterface retrofitApiInterface = BaseURL.getRetrofit().create(ApiInterface.class);
        Call<List<RecommendModal>> productModalCall = retrofitApiInterface.getProduct();
        productModalCall.enqueue(new Callback<List<RecommendModal>>() {
            @Override
            public void onResponse(Call<List<RecommendModal>> call, Response<List<RecommendModal>> response) {
                RecyclerviewRecommendAdapter recyclerviewAdapter = new RecyclerviewRecommendAdapter(getActivity(),response.body());
                LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false);
                recyclerViewProduct.setLayoutManager(mlayoutManager);
                recyclerViewProduct.setHasFixedSize(true);
                recyclerViewProduct.setAdapter(recyclerviewAdapter);
                recyclerviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<RecommendModal>> call, Throwable t) {

            }
        });
    }

    // institute json
    public void getInstitute(){
        ApiInterface retrofitApiInterface = BaseURL.getRetrofit().create(ApiInterface.class);
        Call<List<InstituteModal>> instituteModal = retrofitApiInterface.getInstitute();
        instituteModal.enqueue(new Callback<List<InstituteModal>>() {
            @Override
            public void onResponse(Call<List<InstituteModal>> call, Response<List<InstituteModal>> response) {
                RecyclerviewInstituteAdapter recyclerviewInstituteAdapter = new RecyclerviewInstituteAdapter(getActivity(),response.body());
//                RecyclerView.LayoutManager mlayoutManager = new GridLayoutManager(getContext(), 1);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerViewNearby.setLayoutManager(horizontalLayoutManager);
                recyclerViewNearby.setHasFixedSize(true);
                recyclerViewNearby.setAdapter(recyclerviewInstituteAdapter);
                recyclerviewInstituteAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<InstituteModal>> call, Throwable t) {

            }
        });
    }
}