package com.example.adminfunitureshopapp.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentAddCategoryBinding;
import com.example.adminfunitureshopapp.databinding.FragmentAddProductBinding;
import com.example.adminfunitureshopapp.model.Categories.Categories;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCategoryFragment extends Fragment {
    private FragmentAddCategoryBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CategoriesAPIService categoriesAPIService;
    private List<String> newCategories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnSubmitCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCategory = binding.etNameCategory.getText().toString().trim();
                String imageUrl = binding.etImageCategory.getText().toString().trim();
                addCategory(nameCategory, imageUrl);
            }
        });
    }

    private void addCategory(String name, String imageUrl) {
        categoriesAPIService = new CategoriesAPIService();
        categoriesAPIService.addCategory(name,  imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: Xử lý khi đăng ký thành công
                    }

                    @Override
                    public void onSuccess(Integer productId) {
                        // TODO: Xử lý khi thêm danh mục thành công
                        Toast.makeText(getActivity().getApplicationContext(), "Add Category Success", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new CategoryFragment())
                                .commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Xử lý khi thêm danh mục thất bại
                        Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new CategoryFragment())
                                .commit();
                        Log.e("TAG", "Add Category Error: " + e.getMessage(), e);


                    }
                });

    }
}
