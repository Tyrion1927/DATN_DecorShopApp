package com.example.adminfunitureshopapp.model.Product;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

//import com.AdminFunitureShopApp.ProductDetail;
import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.ui.product.UpdateProductFragment;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.Viewholder> implements Filterable {

    private List<Product> products;
    private List<Product> productsCopy;
    private productsAPIService APIService;
    private FragmentActivity fragment;
    private OnCloseListener mListener;

    public productsAdapter(List<Product> productList, FragmentActivity fragment) {
        this.products = productList;
        this.fragment = fragment;
        this.productsCopy = productList;
        notifyDataSetChanged();
    }


    public interface OnCloseListener {
        void onClose();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment)
                .inflate(R.layout.product_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvAmount.setText(String.valueOf(product.getQuantity()));
        holder.tvPrice.setText(String.valueOf(product.getPrice()) + "$");
        Picasso.get().load(product.getImageUrl()).into(holder.imageUrl);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = product.getId();
                deleteProduct(productId);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = products.get(holder.getAdapterPosition());
                UpdateProductFragment dialogFragment = new UpdateProductFragment(product.getId(), product.getName(), product.getQuantity(),
                        product.getImageUrl(), product.getOriginalPrice(),
                        product.getDiscount(), product.getPrice(), product.getDetail(), product.getType());
                dialogFragment.show(fragment.getSupportFragmentManager(), "Update Product Dialog");
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageUrl;
        public TextView tvName;
        public TextView tvAmount;
        public TextView tvPrice;
        public Button btnDelete;
        public Button btnEdit;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    public void updateData(List<Product> newData) {
        products = newData;
        notifyDataSetChanged();
    }

    public void deleteProduct(int productId) {
            APIService = new productsAPIService();
            Disposable disposable = APIService.deleteProduct(productId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Integer>() {
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                            Log.d("Delete", "SUCCESS");
                            List<Product> newData = new ArrayList<>(products);
                            for (Product product : products) {
                                if (product.getId() == productId) {
                                    newData.remove(product);
                                    break;
                                }
                            }
                            updateData(newData);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO: Xử lý khi xóa sản phẩm thất bại
                            List<Product> newData = new ArrayList<>(products);
                            for (Product product : products) {
                                if (product.getId() == productId) {
                                    newData.remove(product);
                                    break;
                                }
                            }
                            updateData(newData);
                        }
                    });
        }
    }




