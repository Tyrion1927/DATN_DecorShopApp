package com.example.adminfunitureshopapp.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentOrderDetailBinding;
import com.example.adminfunitureshopapp.model.Item.Item;
import com.example.adminfunitureshopapp.model.Item.ItemAdapter;
import com.example.adminfunitureshopapp.model.Order.Order;
import com.example.adminfunitureshopapp.ui.product.ProductFragment;
import com.example.adminfunitureshopapp.viewmodel.OrdersAPIService;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailFragment extends Fragment {
    private FragmentOrderDetailBinding binding;
    private Spinner spinnerStatus;
    private ArrayAdapter<String> statusAdapter;
    private Integer Order_Status;
    private Integer idOrder;
    private OrdersAPIService APIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        Bundle args = getArguments();

        spinnerStatus = binding.spOderStatus;
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.status_array,
                android.R.layout.simple_spinner_item
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateOrder(idOrder, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (args != null) {
            int id = args.getInt("id");
            idOrder = id;
            int idUser = args.getInt("idUser");
            String address = args.getString("address");
            String phone = args.getString("phone");
            String email = args.getString("email");
            String fullname = args.getString("fullname");
            long totalPrice = args.getLong("totalPrice");
            int status = args.getInt("status");
            ArrayList<Item> itemList = (ArrayList<Item>) args.getSerializable("itemList");
            binding.tvId.setText(String.valueOf(id));
            binding.tvAddress.setText(address);
            binding.tvPhone.setText(phone);
            binding.tvEmail.setText(email);
            binding.tvTotalprice.setText(String.valueOf(totalPrice));
            binding.spOderStatus.setSelection(status);
            binding.tvName.setText(fullname);
            RecyclerView recyclerView = binding.rvProducts;
            ItemAdapter adapter = new ItemAdapter(itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return binding.getRoot();
    }
    private void updateOrder(int id, int orderStatus){
        APIService = new OrdersAPIService();
        APIService.updateOrder( id, orderStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(Integer productId) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

