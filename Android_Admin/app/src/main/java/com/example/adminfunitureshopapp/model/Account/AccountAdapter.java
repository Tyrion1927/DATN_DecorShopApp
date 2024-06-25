package com.example.adminfunitureshopapp.model.Account;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.adminfunitureshopapp.MainActivity;
import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.model.Categories.Categories;
import com.example.adminfunitureshopapp.ui.account.UpdateUserFragment;
import com.example.adminfunitureshopapp.ui.category.UpdateCategoryFragment;
import com.example.adminfunitureshopapp.viewmodel.AccountAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.Viewholder> {

    private static List<Account> accounts;
    private AccountAPIService accountAPIService;
    private FragmentActivity fragment;

    public AccountAdapter(List<Account> accountList, FragmentActivity fragment) {
        this.accounts = accountList;
        this.fragment = fragment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new AccountAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.Viewholder holder, int position) {
        Account account = accounts.get(position);
//        holder.id.setText(String.valueOf(account.getId()));
        holder.name.setText(account.getUsername());
        holder.role.setText(account.getRole());
        holder.phone.setText(account.getPhone());
        if (account.getRole().equals("admin")) holder.imageUrl.setImageResource(R.drawable.manager);
            else holder.imageUrl.setImageResource(R.drawable.profile);

        holder.itemAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account1 = accounts.get(holder.getAdapterPosition());
                UpdateUserFragment dialogFragment = new UpdateUserFragment(account1.getId() ,account1.getUsername(), account1.getPassword(), account1.getFullName(), account1.getDefaultAdress(), account1.getEmail(), account1.getPhone(), account1.getRole());
                dialogFragment.show(fragment.getSupportFragmentManager(), "Update User Account Dialog");
            }
        });

        holder.DeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accountId = account.getId();
                deleteAcc(accountId);
            }
        });

        holder.ResetPassAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accountId = account.getId();
                resetPass(accountId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageUrl;
        public TextView id;
        public TextView name;
        public TextView role;
        public TextView phone;
        public Button DeleteAcc;
        public Button ResetPassAcc;
        public LinearLayout itemAcc;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemAcc = itemView.findViewById(R.id.liner_acc_item);
            imageUrl = itemView.findViewById(R.id.img_account);
//            id = itemView.findViewById(R.id.tv_Acccount_ID);
            role = itemView.findViewById(R.id.tv_Acccount_role);
            name = itemView.findViewById(R.id.tv_Acccount_name);
            phone = itemView.findViewById(R.id.tv_Acccount_phone);
            DeleteAcc = itemView.findViewById(R.id.btn_delete_acc);
            ResetPassAcc = itemView.findViewById(R.id.btn_resetpass_acc);
        }
    }
    public void updateDataAcc(List<Account> newData){
        accounts = newData;
        notifyDataSetChanged();
    }
    public void deleteAcc(int id){
        accountAPIService = new AccountAPIService();
        Disposable disposable = accountAPIService.deleteAcc(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        Log.d("Delete", "SUCCESS");
                        List<Account> newData = new ArrayList<>(accounts);
                        for (Account account : accounts) {
                            if (account.getId() == id) {
                                newData.remove(account);
                                break;
                            }
                        }
                        updateDataAcc(newData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Xử lý khi xóa danh muc thất bại
                        List<Account> newData = new ArrayList<>(accounts);
                        for (Account account : accounts) {
                            if (account.getId() == id) {
                                newData.remove(account);
                                break;
                            }
                        }
                        updateDataAcc(newData);
                    }
                });
    }

    public void resetPass(int id){
        accountAPIService = new AccountAPIService();
        Disposable disposable = accountAPIService.resetPass(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        Log.d("ResetPass", "SUCCESS");
                        //Toast.makeText(getContext(), "Reset password Successful!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Xử lý khi xóa danh muc thất bại
                        Log.d("ResetPass", "Fail");
                    }
                });
    }
}
