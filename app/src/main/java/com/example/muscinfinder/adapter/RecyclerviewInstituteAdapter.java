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
import com.example.muscinfinder.activity.MapsActivity;
import com.example.muscinfinder.activity.InstituteDetailActivity;
import com.example.muscinfinder.modal.InstituteModal;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecyclerviewInstituteAdapter extends RecyclerView.Adapter<RecyclerviewInstituteAdapter.MyViewHolder>{

    Context context;
    List<InstituteModal> productModals;

    public RecyclerviewInstituteAdapter(Context context, List<InstituteModal> productModals) {
        this.context = context;
        this.productModals = productModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_institute,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //// getting data according to position
        final InstituteModal InstituteModal = productModals.get(position);

        holder.txt_in_name.setText(InstituteModal.getName());
        holder.txt_in_location.setText(InstituteModal.getLocation());
        holder.txt_in_description.setText(InstituteModal.getDescription());
        holder.txt_in_cost.setText("Enroll Course: " + InstituteModal.getCost());
        Picasso.get()
                .load(InstituteModal.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .resize(420, 420)
                .centerCrop()
                .into(holder.in_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstituteDetailActivity.productID = InstituteModal.get_id();
                Intent intent = new Intent(context, InstituteDetailActivity.class);
                intent.putExtra("_name",InstituteModal.getName());
                intent.putExtra("_desc",InstituteModal.getDescription());
                intent.putExtra("_location",InstituteModal.getLocation());
                intent.putExtra("_cost", InstituteModal.getCost());
                intent.putExtra("_img", InstituteModal.getImage());
                intent.putExtra("_st", "1");
                MapsActivity.storedName = InstituteModal.getName();
                MapsActivity.storedLatitude = InstituteModal.getLatitude();
                MapsActivity.storedLongitude = InstituteModal.getLongitude();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_in_name, txt_in_location, txt_in_description, txt_in_cost;
        ImageView in_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_in_name = itemView.findViewById(R.id.inName);
            txt_in_location = itemView.findViewById(R.id.inLocation);
            txt_in_description = itemView.findViewById(R.id.inDesc);
            txt_in_cost = itemView.findViewById(R.id.inCost);
            in_image = itemView.findViewById(R.id.inImage);
        }
    }
}