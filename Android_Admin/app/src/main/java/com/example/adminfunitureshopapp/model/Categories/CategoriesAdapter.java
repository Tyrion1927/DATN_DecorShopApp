package com.example.adminfunitureshopapp.model.Categories;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.model.Product.Product;
import com.example.adminfunitureshopapp.ui.category.UpdateCategoryFragment;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Viewholder> {
    private List<Categories> categories;
    private List<Categories> categoriesCopy;
    private CategoriesAPIService categoriesAPIService;
    private FragmentActivity fragment;

    public CategoriesAdapter(List<Categories> categoriesList, FragmentActivity fragment) {
        this.categories = categoriesList;
        this.fragment = fragment;
        this.categoriesCopy = categoriesList;
        notifyDataSetChanged();
    }
    public interface OnCloseListener {
        void onClose();
    }
    @NonNull
    @Override
    public CategoriesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment)
                .inflate(R.layout.category_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.Viewholder holder, int position) {
         Categories category = categories.get(position);
         holder.tvNameCategory.setText(category.getName());
         Picasso.get().load(category.getImageUrl()).into(holder.imageUrl);

         holder.btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Categories category = categories.get(holder.getAdapterPosition());
                 UpdateCategoryFragment dialogFragment = new UpdateCategoryFragment(category.getId(), category.getName(), category.getImageUrl());
                 dialogFragment.show(fragment.getSupportFragmentManager(), "Update Category Dialog");
             }
         });

         holder.btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int categoryId = category.getId();
                 deleteCategory(categoryId);
             }
         });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageUrl;
        public TextView tvNameCategory;
        public Button btnDeleteCategory;
        public Button btnUpdateCategory;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_category);
            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            btnDeleteCategory = itemView.findViewById(R.id.btn_delete_category);
            btnUpdateCategory = itemView.findViewById(R.id.btn_edit_category);
        }
    }

    public void updateDataCategory(List<Categories> newData) {
        categories = newData;
        notifyDataSetChanged();
    }
    public void deleteCategory(int categoryId) {
        categoriesAPIService = new CategoriesAPIService();
        Disposable disposable = categoriesAPIService.deleteCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        Log.d("Delete", "SUCCESS");
                        List<Categories> newData = new ArrayList<>(categories);
                        for (Categories category : categories) {
                            if (category.getId() == categoryId) {
                                newData.remove(category);
                                break;
                            }
                        }
                        updateDataCategory(newData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Xử lý khi xóa danh muc thất bại
                        List<Categories> newData = new ArrayList<>(categories);
                        for (Categories category : categories) {
                            if (category.getId() == categoryId) {
                                newData.remove(category);
                                break;
                            }
                        }
                        updateDataCategory(newData);
                    }
                });
    }
}
