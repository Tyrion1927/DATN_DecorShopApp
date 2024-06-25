package com.example.adminfunitureshopapp.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentCategoryBinding;
import com.example.adminfunitureshopapp.databinding.FragmentProductBinding;
import com.example.adminfunitureshopapp.model.Categories.Categories;
import com.example.adminfunitureshopapp.model.Categories.CategoriesAdapter;
import com.example.adminfunitureshopapp.model.Product.Product;
import com.example.adminfunitureshopapp.model.Product.productsAdapter;
import com.example.adminfunitureshopapp.ui.product.AddProductFragment;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoriesAPIService categoriesAPIService;
    private RecyclerView rvCategoryies;
    private ArrayList<Categories> categories;
    private ArrayList<Categories> categoriesCopy;
    private ArrayList<Categories> newCategory;
//    private com.example.adminfunitureshopapp.model.Categories.CategoriesAdapter categoriesAdapter;
    private CategoriesAdapter categoriesAdapter;

    public CategoryFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCategoryBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = binding.getRoot();
        return root;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories = new ArrayList<Categories>();
        newCategory = new ArrayList<Categories>();
        categoriesAdapter = new CategoriesAdapter(newCategory, getActivity());
        binding.rvCategories.setAdapter(categoriesAdapter);
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of the AddCategoryFragment

                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new AddProductFragment
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, addCategoryFragment);

                // Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });


        categoriesAPIService = new CategoriesAPIService();
        categoriesAPIService.getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Categories>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Categories> categoriesList) {
                        Log.d("DEBUG", "get Category Success");
                        for (Categories category : categoriesList) {
                            newCategory.add(category);
                        }
                        categoriesAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "get category Fail : " + e.getMessage());
                    }
                });

    }
}
