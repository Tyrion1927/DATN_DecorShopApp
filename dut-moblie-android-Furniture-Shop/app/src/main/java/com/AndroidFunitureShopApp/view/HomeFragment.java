package com.AndroidFunitureShopApp.view;

import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.AndroidFunitureShopApp.R;
import com.AndroidFunitureShopApp.databinding.FragmentHomeBinding;
import com.AndroidFunitureShopApp.model.Product.Product;
import com.AndroidFunitureShopApp.model.Product.productsAdapter;
import com.AndroidFunitureShopApp.viewmodel.productsAPIService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private productsAPIService apiServices;
    private RecyclerView rvDogs;
    private ArrayList<Product> products;
    private ArrayList<Product> productsCopy;
    private ArrayList<Product> newProducts;
    private productsAdapter productsAdapter;

    public HomeFragment() {
    }

    private void ActionSlider() {
        List<String> sliders = new ArrayList<>();
        sliders.add("https://www.vmcdn.ca/f/files/shared/business-directory/share_guelphtoday/1200x628_homedecor.jpg");
        sliders.add("https://theme.hstatic.net/200000584705/1001023835/14/home_product_pos_image.png?v=8");
        sliders.add("https://livingdunes.net/myrtle-beach/wp-content/uploads/2021/08/live-local-shop-local_Best-Home-Decor.png");
        sliders.add("https://hstatic.net/336/1000150336/1000203404/slideshow_3.jpg?v=24");
        sliders.add("https://hstatic.net/336/1000150336/1000203404/slideshow_2.jpg?v=24");
        sliders.add("https://hstatic.net/336/1000150336/1000203404/slideshow_1.jpg?v=24");
        sliders.add("https://theme.hstatic.net/200000584705/1000969925/14/home_slider_item_image_2.png?v=2524");
        for (int i = 0; i < sliders.size(); i++) {
            ImageView imgView = new ImageView(requireContext());
            Picasso.get().load(sliders.get(i)).into(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgView.setClipToOutline(true);
            imgView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int size = getResources().getDimensionPixelSize(R.dimen.image_rounding_size);
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), size);
                }
            });
            binding.vfSlider.addView(imgView);
        }
        binding.vfSlider.setFlipInterval(3000);
        binding.vfSlider.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(requireContext(), R.anim.slider_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(requireContext(), R.anim.slider_out_right);

        binding.vfSlider.setInAnimation(slide_in);
        binding.vfSlider.setOutAnimation(slide_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
        ActionSlider();
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

        products = new ArrayList<Product>();
        newProducts = new ArrayList<Product>();
        productsAdapter = new productsAdapter(newProducts);
        binding.rvProducts.setAdapter(productsAdapter);
        binding.rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));


        apiServices = new productsAPIService();
        apiServices.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Product>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        Log.d("DEBUG", "Success");
                        for (Product dog : products) {
                            newProducts.add(dog);
                        }
                        productsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });

    }
    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search products");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}