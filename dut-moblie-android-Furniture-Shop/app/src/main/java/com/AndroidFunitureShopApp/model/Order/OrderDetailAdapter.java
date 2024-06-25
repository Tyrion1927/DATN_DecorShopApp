package com.AndroidFunitureShopApp.model.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AndroidFunitureShopApp.R;
import com.AndroidFunitureShopApp.model.Item.Item;
import com.bumptech.glide.Glide;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.Viewholder> {
    Context context;
    List<Item> itemList;

    public OrderDetailAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.Viewholder holder, int position) {
        Item item = itemList.get(position);
        holder.txtNameProduct.setText(item.getName() + "");
        holder.txtQuantity.setText("Quantity: " + item.getQuantity());
        Glide.with(context).load(item.getImageUrl()).into(holder.imgDetail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgDetail;
        TextView txtNameProduct, txtQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgDetail = itemView.findViewById(R.id.item_img_detail);
            txtNameProduct = itemView.findViewById(R.id.item_name_product_detail);
            txtQuantity = itemView.findViewById(R.id.item_quantity_detail);
        }
    }
}
