package com.AndroidFunitureShopApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.AndroidFunitureShopApp.databinding.FragmentCategoriesBinding;
import com.AndroidFunitureShopApp.model.Categories.Categories;
import com.AndroidFunitureShopApp.model.Categories.CategoriesAdapter;
import com.AndroidFunitureShopApp.viewmodel.CategoriesAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding binding;
    private RecyclerView recyclerView;
    private CategoriesAPIService categoriesAPIService;
    private ArrayList<Categories> categories;
    private ArrayList<Categories> newCategories;
    private CategoriesAdapter categoriesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCategoriesBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = binding.getRoot();
        return viewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories = new ArrayList<Categories>();
        newCategories = new ArrayList<Categories>();
        categoriesAdapter = new CategoriesAdapter(newCategories);
        binding.rvCategories.setAdapter(categoriesAdapter);
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        categoriesAPIService = new CategoriesAPIService();
        categoriesAPIService.getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Categories>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Categories> Categories1) {
                        Log.d("DEBUG", "Success");
                        for (Categories category : Categories1) {
                            Log.d("DEBUB", category.getName());
                            newCategories.add(category);
                        }
                        categoriesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });

    }


}