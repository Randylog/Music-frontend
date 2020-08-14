package com.example.muscinfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muscinfinder.R;
import com.example.muscinfinder.modal.GetFavouriteModal;

import java.util.List;

public class ViewFavouriteAdapter extends RecyclerView.Adapter<ViewFavouriteAdapter.MyViewHolder>{

    Context context;
    List<GetFavouriteModal> productModals;

    public ViewFavouriteAdapter(Context context, List<GetFavouriteModal> productModals) {
        this.context = context;
        this.productModals = productModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //// getting data according to position
        final GetFavouriteModal GetFavouriteModal = productModals.get(position);
        holder.txt_item_product_name.setText(GetFavouriteModal.getProductName());
        holder.txt_item_product_price.setText(GetFavouriteModal.getLocation());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                String itemLabel = String.valueOf(productModals.get(position));
                productModals.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,productModals.size());
                Toast.makeText(context,"Removed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_item_product_name,txt_item_product_price;
        ImageView img_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_item_product_name = itemView.findViewById(R.id.productName);
            txt_item_product_price = itemView.findViewById(R.id.productPrice);
            img_delete = itemView.findViewById(R.id.delete);
        }
    }
}