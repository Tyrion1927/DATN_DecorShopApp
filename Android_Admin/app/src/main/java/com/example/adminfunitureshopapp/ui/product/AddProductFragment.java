package com.example.adminfunitureshopapp.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentAddProductBinding;
import com.example.adminfunitureshopapp.model.Categories.Categories;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends Fragment {
    private FragmentAddProductBinding binding;
    private CategoriesAPIService categoriesAPIService;
    private List<String> newCategories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private Spinner spinnerCategory;
    private productsAPIService APIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        spinnerCategory = binding.spinnerCategory;
        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, newCategories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoriesAPIService = new CategoriesAPIService();
        categoriesAPIService.getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Categories>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Categories> categories) {
                        for (Categories category : categories) {
                            String categoryName = category.getName();
                            newCategories.add(categoryName);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị của trường edittextName
                String name = binding.edittextName.getText().toString();
                Log.d("name", name);
                String quantity = binding.edittextQuantity.getText().toString();
                Log.d("quantity", quantity);
                String imageUrl = binding.edittextImageurl.getText().toString();
                Log.d("imageUrl", imageUrl);
                String originalPrice = binding.edittextOriginalprice.getText().toString();
                Log.d("originalPrice", originalPrice);
                String discount = binding.edittextDiscount.getText().toString();
                Log.d("discount", discount);
                String detail = binding.edittextDetail.getText().toString();
                Log.d("detail", detail);
                String type = binding.edittextType.getText().toString();
                Log.d("type", type);
                int position = spinnerCategory.getSelectedItemPosition();
                String categoryId = String.valueOf(position + 1); // tăng lên 1 để được id

                Log.d("categoryId", categoryId);
                addProduct(name, quantity, imageUrl, originalPrice, discount, detail, type, categoryId);
            }
        });
    }

    private void addProduct(String name, String quantity, String imageUrl, String originalPrice, String discount, String detail, String type, String categoryId) {
        APIService = new productsAPIService();
        APIService.addProduct(name, Integer.parseInt(quantity), imageUrl, Integer.parseInt(originalPrice), Integer.parseInt(discount), detail, type, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: Xử lý khi đăng ký thành công
                    }

                    @Override
                    public void onSuccess(Integer productId) {
                        // TODO: Xử lý khi thêm sản phẩm thành công
                        Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new ProductFragment())
                                .commit();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new ProductFragment())
                                .commit();
                    }
                });

    }
}
