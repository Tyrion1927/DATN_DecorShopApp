package com.AndroidFunitureShopApp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.AndroidFunitureShopApp.databinding.ShipmentDetailsBinding;
import com.AndroidFunitureShopApp.model.Account.UserInfo;
import com.AndroidFunitureShopApp.viewmodel.DeliveryInformationAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EnterDeliveryInforActivity extends AppCompatActivity {
    private ShipmentDetailsBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DeliveryInformationAPIService deliveryInformationAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ShipmentDetailsBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        //System.out.print("Ủa alo");
        binding.btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int userId = UserInfo.userInfo.getId();
                String name = binding.etName.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String city = binding.etCity.getText().toString();
                String district = binding.etDistrict.getText().toString();
                String ward = binding.etWards.getText().toString();
                String street = binding.etStreet.getText().toString();
                String notes = binding.etNote.getText().toString();
                Log.d("DEBUG", String.valueOf(userId));
                Log.d("Name", name);
//                // Thêm thông tin giao hàng vào database
                addDeliveryInformation(userId, name, phone, city, district, ward, street, notes);

                // Kết thúc Activity và trở về màn hình trước đó
                finish();
            }
        });
    }

    private void addDeliveryInformation(int userId, String name, String phone, String city, String district, String ward, String street, String notes) {
        deliveryInformationAPIService = new DeliveryInformationAPIService();
        Log.d("DEBUG", "addDeliveryInformation");
        compositeDisposable.add(deliveryInformationAPIService.addDeliveryInformation(userId, name, phone, city, district, ward, street, notes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        deliveryInformationModel -> {
                            if (deliveryInformationModel.isSuccess()) {
                                // Xử lý khi thêm thông tin giao hàng thành công
                                Toast.makeText(getApplicationContext(), "Add delivery information success!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý khi thêm thông tin giao hàng thất bại
                                Toast.makeText(getApplicationContext(), deliveryInformationModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
    }

}