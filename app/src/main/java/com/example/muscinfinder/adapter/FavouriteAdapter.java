package com.example.muscinfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muscinfinder.R;
import com.example.muscinfinder.modal.RecommendModal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>{

    Context context;
    List<RecommendModal> recommendModals;

    public FavouriteAdapter(Context context, List<RecommendModal> recommendModals) {
        this.context = context;
        this.recommendModals = recommendModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //// getting data according to position
        final RecommendModal recommendModal = recommendModals.get(position);

        holder.txt_item_product_name.setText(recommendModal.getName());
        holder.txt_item_product_price.setText(recommendModal.getPrice());

        // get Product Image
        Picasso.get()
                .load(recommendModal.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .resize(220, 220)
                .centerCrop()
                .into(holder.item_product_image);
    }

    @Override
    public int getItemCount() {
        return recommendModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_item_product_name,txt_item_product_price;
        ImageView item_product_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_item_product_name = itemView.findViewById(R.id.productName);
            txt_item_product_price = itemView.findViewById(R.id.productPrice);
        }
    }
}