package com.AndroidFunitureShopApp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.AndroidFunitureShopApp.PaymentActivity;
import com.AndroidFunitureShopApp.ViewOrderActivity;
import com.AndroidFunitureShopApp.databinding.FragmentCartsBinding;
import com.AndroidFunitureShopApp.model.Cart.CartItem;
import com.AndroidFunitureShopApp.model.Cart.CartItemAdapter;
import com.AndroidFunitureShopApp.viewmodel.EventBus.TinhTongEvent;
import com.AndroidFunitureShopApp.viewmodel.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class CartsFragment extends Fragment {
    private FragmentCartsBinding binding;
    private ArrayList<CartItem> cartItems;
    private CartItemAdapter cartItemAdapter;
    private long totalPrice;

    private void tinhTongTien() {
        totalPrice = 0;

        for (CartItem item : Utils.cartItemBuyList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        binding.tvTotalCart.setText(String.valueOf(totalPrice) + "$");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvCartitems.setHasFixedSize(true);
        binding.rvCartitems.setLayoutManager(new LinearLayoutManager(getContext()));
        if (Utils.cartItemList.size() > 0) {

            cartItemAdapter = new CartItemAdapter(Utils.cartItemList);
            binding.rvCartitems.setAdapter(cartItemAdapter);

            tinhTongTien();
        } else {
            binding.txtNote.setVisibility(View.VISIBLE);
        }

        binding.btnMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long price = totalPrice;
                totalPrice = 0;
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("totalPrice", price);
                startActivity(intent);
            }
        });

        binding.btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewOrderActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event) {
        if (event != null) {
            tinhTongTien();
        }
    }
}