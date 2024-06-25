package com.example.adminfunitureshopapp.ui.category;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentUpdateCategoryBinding;
import com.example.adminfunitureshopapp.databinding.FragmentUpdateProductBinding;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateCategoryFragment extends DialogFragment {
    private int categoryId;
    private FragmentUpdateCategoryBinding binding;
    private String nameCategory;
    private String imageUrlCategory;
    private CategoriesAPIService categoriesAPIService;

    @Override
    public void onStart() {
        super.onStart();
        // Set the width of the dialog to match the parent
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public UpdateCategoryFragment(int id, String nameCategory, String imageUrlCategory) {
        this.categoryId = id;
        this.nameCategory = nameCategory;
        this.imageUrlCategory = imageUrlCategory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentUpdateCategoryBinding.inflate(inflater, container, false);

        // Set the values of the product to the views using data binding
        binding.etNameCategory.setText(nameCategory);
        binding.etImageCategory.setText(imageUrlCategory);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSubmitCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idCategory = categoryId;
                String nameCategory = binding.etNameCategory.getText().toString().trim();
                String imageUrlCategory = binding.etImageCategory.getText().toString().trim();
                UpdateCategory(idCategory, nameCategory, imageUrlCategory);
            }
        });
    }
    private void UpdateCategory(int id, String name, String imageUrl) {
        categoriesAPIService = new CategoriesAPIService();
        categoriesAPIService.updateCategory(id, name, imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Integer productId) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Category Success", Toast.LENGTH_SHORT).show();
                        dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new CategoryFragment())
                                .commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Category Success", Toast.LENGTH_SHORT).show();
                        dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new CategoryFragment())
                                .commit();
                    }
                });

    }
}
