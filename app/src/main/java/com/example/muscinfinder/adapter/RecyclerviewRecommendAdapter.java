package com.example.muscinfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muscinfinder.R;
import com.example.muscinfinder.activity.InstituteDetailActivity;
import com.example.muscinfinder.modal.RecommendModal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerviewRecommendAdapter extends RecyclerView.Adapter<RecyclerviewRecommendAdapter.MyViewHolder>{

    Context context;
    List<RecommendModal> recommendModals;

    public RecyclerviewRecommendAdapter(Context context, List<RecommendModal> recommendModals) {
        this.context = context;
        this.recommendModals = recommendModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //// getting data according to position
        final RecommendModal recommendModal = recommendModals.get(position);

        holder.txt_restaurant_name.setText(recommendModal.getName());
        holder.txt_restaurant_desc.setText("Location: " + recommendModal.getLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstituteDetailActivity.productID = recommendModal.get_id();
                Intent intent = new Intent(context, InstituteDetailActivity.class);
                intent.putExtra("_name", recommendModal.getName());
                intent.putExtra("_desc", recommendModal.getDescription());
                intent.putExtra("_location", recommendModal.getLocation());
                intent.putExtra("_img", recommendModal.getImage());
                intent.putExtra("_st", "0");
                context.startActivity(intent);
            }
        });



        // get Product Image
        Picasso.get()
                .load(recommendModal.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .resize(220, 220)
                .centerCrop()
                .into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return recommendModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_restaurant_name,txt_restaurant_desc;
        ImageView img_photo;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_restaurant_name = itemView.findViewById(R.id.txtProductName);
            txt_restaurant_desc = itemView.findViewById(R.id.txtPrice);
            img_photo = itemView.findViewById(R.id.imgProduct);
        }
    }
}