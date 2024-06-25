package com.AndroidFunitureShopApp.model.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AndroidFunitureShopApp.Interface.IImageClickListenner;
import com.AndroidFunitureShopApp.R;
import com.AndroidFunitureShopApp.model.Product.Product;
import com.AndroidFunitureShopApp.viewmodel.Utils;
import com.AndroidFunitureShopApp.viewmodel.EventBus.TinhTongEvent;
import com.AndroidFunitureShopApp.viewmodel.productsAPIService;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.Viewholder> {

    private static List<CartItem> cartItemList;
    private static List<CartItem> cartItemListcopy;
    private productsAPIService apiServices;
    private List<Product> itemProducts;
    private Context mContext;


    public CartItemAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        this.cartItemListcopy = cartItemList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.tvName.setText(" " + cartItem.getName());
        holder.tvQuantity.setText(cartItem.getQuantity() + "");
//        holder.tvPrice.setText(cartItem.getPrice() + "$");
        long totalprice = cartItem.getQuantity() * cartItem.getPrice();
        holder.tvTotalCartPrice.setText(totalprice + "$");
        Picasso.get().load(cartItem.getImageUrl()).into(holder.imageUrl);

        holder.cartCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.addToBuyCart(cartItem);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                } else {
                    for (int i = 0; i < Utils.cartItemBuyList.size(); i++) {
                        if (Utils.cartItemBuyList.get(i).getId() == cartItem.getId()) {
                            Utils.cartItemBuyList.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }

        });

        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                if (value == 1) {
                    if (cartItemList.get(pos).getQuantity() < cartItemList.get(pos).getproductQuantity()) {
                        int newQuantity = cartItemList.get(pos).getQuantity() + 1;
                        cartItemList.get(pos).setQuantity(newQuantity);
                        int idProduct = cartItemList.get(pos).getId();
                        Log.d("ID product", String.valueOf(idProduct));
//                        Utils.cartItemBuyList.get(pos).setQuantity(newQuantity);
//                        EventBus.getDefault().postSticky(new TinhTongEvent());
                        for (int i = 0; i < Utils.cartItemBuyList.size(); i++) {
                            if(Utils.cartItemBuyList.get(i).getId() == cartItemList.get(pos).getId()){
                                Utils.cartItemBuyList.get(pos).setQuantity(newQuantity);
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        }
                    }

//                    holder.tvQuantity.setText(cartItemList.get(pos).getQuantity() + "");
//                    long totalprice = cartItemList.get(pos).getQuantity() * cartItemList.get(pos).getPrice();
//                    holder.tvTotalCartPrice.setText(totalprice + "$");
//                    EventBus.getDefault().postSticky(new TinhTongEvent());

                } else if (value == 2) {
                    if (cartItemList.get(pos).getQuantity() > 1) {
                        int newQuantity = cartItemList.get(pos).getQuantity() - 1;
                        cartItemList.get(pos).setQuantity(newQuantity);
                        for (int i = 0; i < Utils.cartItemBuyList.size(); i++) {
                            if(Utils.cartItemBuyList.get(i).getId() == cartItemList.get(pos).getId()){
                                Utils.cartItemBuyList.get(pos).setQuantity(newQuantity);
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        }
                    } else if (cartItemList.get(pos).getQuantity() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Inform");
                        builder.setMessage("Do you want to remove this product out of your cart?");
                        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.cartItemList.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }

                holder.tvQuantity.setText(cartItemList.get(pos).getQuantity() + "");
                long totalprice = cartItemList.get(pos).getQuantity() * cartItemList.get(pos).getPrice();
                holder.tvTotalCartPrice.setText(totalprice + "$");
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageUrl;
        public TextView tvName;
        public int tvDetail;
        public TextView tvQuantity;
        public TextView tvPrice;
        public TextView tvTotalCartPrice;
        public Button btnAdd;
        public Button btnSub;
        public ImageView btnAddDrag;
        public IImageClickListenner listenner;
        public CheckBox cartCheckbox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_carturl);
            tvName = itemView.findViewById(R.id.txt_cartname);
            tvQuantity = itemView.findViewById(R.id.txt_cartquantity);
            //tvPrice = itemView.findViewById(R.id.txt_cartprice);
            btnAdd = (Button) itemView.findViewById(R.id.btn_cartadd);
            btnSub = (Button) itemView.findViewById(R.id.btn_cartremove);
            //btnAddDrag = (ImageView) itemView.findViewById(R.id.btn_cartdrag);
            cartCheckbox = itemView.findViewById(R.id.cart_checkbox);

            tvTotalCartPrice = itemView.findViewById(R.id.txt_carttotalprice);

            btnAdd.setOnClickListener(this);
            btnSub.setOnClickListener(this);
        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View v) {
            if (v == btnAdd) {
                listenner.onImageClick(v, getAdapterPosition(), 1);
            } else if (v == btnSub) {
                listenner.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }

}
