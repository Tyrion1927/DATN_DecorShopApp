package com.example.adminfunitureshopapp.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentOrderBinding;
import com.example.adminfunitureshopapp.databinding.FragmentProductBinding;
import com.example.adminfunitureshopapp.model.Categories.Categories;
import com.example.adminfunitureshopapp.model.Categories.CategoriesAdapter;
import com.example.adminfunitureshopapp.model.Order.Order;
import com.example.adminfunitureshopapp.model.Order.OrdersAdapter;
import com.example.adminfunitureshopapp.ui.category.AddCategoryFragment;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;
import com.example.adminfunitureshopapp.viewmodel.OrdersAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private OrdersAPIService OrderAPIService;
    private RecyclerView rvOder;
    private ArrayList<Order> order;
    private ArrayList<Order> newOrder;
    private OrdersAdapter OrdersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentOrderBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        order = new ArrayList<Order>();
        newOrder = new ArrayList<Order>();
        FragmentManager fragmentManager = getFragmentManager();
        OrdersAdapter = new OrdersAdapter(newOrder, fragmentManager);
        binding.rvOrder.setAdapter(OrdersAdapter);
        binding.rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderAPIService = new OrdersAPIService();
        OrderAPIService.getOrders()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Order>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Order> OrderList) {
                        Log.d("DEBUG", "get Oder Success");
                        for (Order order : OrderList) {
                            newOrder.add(order);
                        }
                        OrdersAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "get order Fail : " + e.getMessage());
                    }
                });

    }
}
