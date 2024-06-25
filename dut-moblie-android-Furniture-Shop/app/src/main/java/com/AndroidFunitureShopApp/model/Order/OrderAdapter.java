package com.AndroidFunitureShopApp.model.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AndroidFunitureShopApp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Order> listOrder;

    public OrderAdapter(Context context, List<Order> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Order order = listOrder.get(position);
        holder.txtOrder.setText("Order: " + order.getId() + "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvDetail.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItem().size());

        // Adapter detail
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(context, order.getItem());
        holder.rvDetail.setLayoutManager(layoutManager);
        holder.rvDetail.setAdapter(orderDetailAdapter);
        holder.rvDetail.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView txtOrder;
        RecyclerView rvDetail;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.id_order);
            rvDetail = itemView.findViewById(R.id.rc_details);
        }
    }
}
