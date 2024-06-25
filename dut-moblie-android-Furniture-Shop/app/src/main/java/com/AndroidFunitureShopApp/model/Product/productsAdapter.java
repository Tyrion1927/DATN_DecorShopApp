package com.AndroidFunitureShopApp.model.Product;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AndroidFunitureShopApp.ProductDetail;
import com.AndroidFunitureShopApp.R;
import com.AndroidFunitureShopApp.viewmodel.Utils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.Viewholder> implements Filterable {
    private static List<Product> products;
    private static List<Product> productsCopy;

    public productsAdapter(List<Product> userList) {
        this.products = userList;
        this.productsCopy = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.tvName.setText(products.get(position).getName());

        holder.tvOriginalPrice.setText(String.valueOf(products.get(position).getOriginalPrice()) + "$");
        holder.tvOriginalPrice.setPaintFlags(holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvPrice.setText(String.valueOf(products.get(position).getPrice()) + "$");

        Picasso.get().load(products.get(position).getImageUrl()).into(holder.imageUrl);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageUrl;
        public TextView tvName;
        public TextView tvDetail;
        public TextView tvOriginalPrice;
        public TextView tvPrice;
        public Button btnAddCart;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageUrl = (ImageView) itemView.findViewById(R.id.image_url);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            //tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            tvOriginalPrice = (TextView) itemView.findViewById(R.id.tv_original_price);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            btnAddCart = (Button) itemView.findViewById(R.id.btn_add_cart);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = products.get(getAdapterPosition());
                    Intent intent = new Intent(itemView.getContext(), ProductDetail.class);

                    intent.putExtra("product", (Serializable) product);
                    itemView.getContext().startActivity(intent);
                }
            });

            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = products.get(getAdapterPosition());
                    Utils.addToCart(product);
                    Toast.makeText(itemView.getContext(), "Add to cart successfuly !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();
                List<Product> StationstListFilter = new ArrayList<>();
                if (input.isEmpty()) {
                    StationstListFilter.addAll(productsCopy);
                } else {
                    for (Product dog : productsCopy) {
                        if (dog.getName().toLowerCase().contains(input.toLowerCase())) {
                            StationstListFilter.add(dog);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = StationstListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
