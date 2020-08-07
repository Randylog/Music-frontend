package com.example.muscinfinder.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muscinfinder.R;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recyclerView_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView_cart = findViewById(R.id.recyclerview_cart);
//        getProduct();
    }

//    public void getProduct(String product){
//        ApiInterface retrofitApiInterface = BaseURL.getInstance().create(ApiInterface.class);
//        Call<List<ProductModal>> modalClassCall = retrofitApiInterface.loadProduct(product);
//
//        modalClassCall.enqueue(new Callback<List<ProductModal>>() {
//            @Override
//            public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>>
//                    response) {
//                System.out.println("Review " + response.body());
//                CartAdapter recyclerviewAdapter = new CartAdapter
//                        (getApplicationContext(),response.body());
//                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(),
//                        LinearLayoutManager.VERTICAL, false);
//                recyclerView_product.setLayoutManager(horizontalLayoutManager);
//                recyclerView_product.setHasFixedSize(true);
//                recyclerView_product.setItemAnimator(new DefaultItemAnimator());
//                recyclerView_product.setAdapter(recyclerviewAdapter);
//                recyclerviewAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onFailure(Call<List<ProductModal>> call, Throwable t) {
//            }
//        });
//    }
}
