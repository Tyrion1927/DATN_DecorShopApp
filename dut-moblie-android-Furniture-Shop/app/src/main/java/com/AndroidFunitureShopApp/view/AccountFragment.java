package com.AndroidFunitureShopApp.view;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.AndroidFunitureShopApp.LoginActivity;
import com.AndroidFunitureShopApp.MainActivity;
import com.AndroidFunitureShopApp.R;
import com.AndroidFunitureShopApp.databinding.FragmentAccountBinding;
import com.AndroidFunitureShopApp.model.Account.Account;
import com.AndroidFunitureShopApp.model.Account.AccountModel;
import com.AndroidFunitureShopApp.model.Account.UserInfo;
import com.AndroidFunitureShopApp.viewmodel.AccountAPIService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private Context context;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_GALLERY_PERMISSION = 200;


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AccountAPIService accountAPIService;
    int id = UserInfo.userInfo.getId();
    public AccountFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo dialog cho phép người dùng lựa chọn mở camera hoặc thư viện
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn ảnh từ:");
                String[] options = {"Máy ảnh", "Thư viện"};
                builder.setItems(options, (dialog, which) -> {
                    // Kiểm tra lựa chọn của người dùng
                    if (which == 0) {
                        // Chọn mở camera
                        // Yêu cầu cấp quyền truy cập camera và/hoặc thư viện
                        requestCameraPermission();
                    } else if (which == 1) {
                        // Chọn mở thư viện
                        // Yêu cầu cấp quyền truy cập thư viện
                        requestGalleryPermission();
                    }
                });
                builder.show();
            }
        });
        ShowInfo();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo(id);
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // Phương thức yêu cầu cấp quyền truy cập camera và/hoặc thư viện
    private void requestCameraPermission() {
        // Yêu cầu cấp quyền truy cập camera và/hoặc thư viện
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            // Nếu đã được cấp quyền truy cập camera và/hoặc thư viện
            // Mở camera hoặc thư viện để lấy ảnh
            dispatchTakePictureIntent(); // Mở camera
            // pickImageFromGallery(); // Mở thư viện
        }
    }

    // Phương thức yêu cầu cấp quyền truy cập thư viện
    private void requestGalleryPermission() {
        // Yêu cầu cấp quyền truy cập thư viện
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_GALLERY_PERMISSION);
        } else {
            // Nếu đã được cấp quyền truy cập thư viện
            // Mở thư viện để chọn ảnh
            pickImageFromGallery();
        }
    }

    public void ShowInfo(){
        if(UserInfo.userInfo.getImageAva().length() >= 10) {
            byte[] decodedString = Base64.decode(UserInfo.userInfo.getImageAva(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            binding.ciAvatar.setImageBitmap(decodedBitmap);
        }
        if(UserInfo.userInfo.getUsername() != null) binding.etUsername.setText(UserInfo.userInfo.getUsername());
        if(UserInfo.userInfo.getFullName() != null) binding.etFullName.setText(UserInfo.userInfo.getFullName());
        if(UserInfo.userInfo.getPhone() != null) binding.etPhone.setText(UserInfo.userInfo.getPhone());
//        if(UserInfo.userInfo.getEmail() != null) binding.etEmail.setText(UserInfo.userInfo.getEmail());
        if(UserInfo.userInfo.getDefaultAdress() != null) binding.etAddress.setText(UserInfo.userInfo.getDefaultAdress());
        binding.etPassword.setText("");
    }

    // Phương thức chụp ảnh
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Phương thức chọn ảnh từ thư viện
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Nhận ảnh đã chụp và hiển thị trên ImageView
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            binding.ciAvatar.setImageBitmap(imageBitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // Nhận ảnh được chọn từ thư viện và hiển thị trên ImageView
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver()
                        .openInputStream(uri);
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                binding.ciAvatar.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void UpdateInfo(int Uid){
        accountAPIService = new AccountAPIService();
        String password = binding.etPassword.getText().toString().trim();
        String newpass = binding.etNewPassword.getText().toString().trim();
        String fullname = binding.etFullName.getText().toString().trim();
//        String email = binding.etEmail.getText().toString().trim();
        String defaultAdress = binding.etAddress.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String imageAva = "";
        if(binding.ciAvatar.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) binding.ciAvatar.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            imageAva = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("ImageAva: ", imageAva);
        }
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(context, "Please, enter your Fullname!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(password)){
            Toast.makeText(context, "Please, enter your password!", Toast.LENGTH_SHORT).show();
        }
//        else if(TextUtils.isEmpty(email)){
//            Toast.makeText(context, "Please, enter your email!", Toast.LENGTH_SHORT).show();
//        }
        else if(TextUtils.isEmpty(defaultAdress)){
            Toast.makeText(context, "Please, enter your address!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(phone)){
            Toast.makeText(context, "Please, enter your phone!", Toast.LENGTH_SHORT).show();
        }   else {
            if(!password.isEmpty() && newpass.isEmpty() && password.equals(UserInfo.userInfo.getPassword())){
                compositeDisposable.add(accountAPIService.UpdateUser(id, password, fullname, imageAva, defaultAdress, "email@gmail.com", phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                accountModel -> {
                                    if (accountModel.isSuccess()){
                                        Account account = accountModel.getResult().get(0);
                                        UserInfo.userInfo = account;
                                        ShowInfo();
                                        Toast.makeText(context, "Update user information Success!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
            else if(!password.isEmpty() && !newpass.isEmpty() && password.equals(UserInfo.userInfo.getPassword())){
                compositeDisposable.add(accountAPIService.UpdateUser(id, newpass, fullname, imageAva, defaultAdress, "email@gmail.com", phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                accountModel -> {
                                    if (accountModel.isSuccess()){
                                        Account account = accountModel.getResult().get(0);
                                        UserInfo.userInfo = account;
                                        ShowInfo();
                                        Toast.makeText(context, "Update user information Success!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }   else {
                Toast.makeText(context, "Password is not correct!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        binding = null;
    }
    //Menu Logout Action
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}