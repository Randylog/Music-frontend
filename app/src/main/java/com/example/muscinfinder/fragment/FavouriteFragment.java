package com.example.muscinfinder.fragment;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muscinfinder.R;
import com.example.muscinfinder.adapter.ViewFavouriteAdapter;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.api_classes.BaseURL;
import com.example.muscinfinder.modal.GetFavouriteModal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    RecyclerView recyclerView;
    public static String userID = null;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffb902"));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerView = view.findViewById(R.id.cart_recyclerView);
        get_cart(userID);
        return view;
    }

    public void get_cart(String _catId){
        ApiInterface retrofitApiInterface = BaseURL.getRetrofit().create(ApiInterface.class);
        Call<List<GetFavouriteModal>> modalClassCall = retrofitApiInterface.loadCartSession(_catId);

        modalClassCall.enqueue(new Callback<List<GetFavouriteModal>>() {
            @Override
            public void onResponse(Call<List<GetFavouriteModal>> call, Response<List<GetFavouriteModal>>
                    response) {
                ViewFavouriteAdapter recyclerviewAdapter = new ViewFavouriteAdapter
                        (getActivity(),response.body());
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerviewAdapter);
                recyclerviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<GetFavouriteModal>> call, Throwable t) {
            }
        });
    }
}