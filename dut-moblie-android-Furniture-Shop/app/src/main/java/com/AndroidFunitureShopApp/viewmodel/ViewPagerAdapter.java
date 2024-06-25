package com.AndroidFunitureShopApp.viewmodel;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.AndroidFunitureShopApp.MainActivity;
import com.AndroidFunitureShopApp.view.AccountFragment;
import com.AndroidFunitureShopApp.view.CartsFragment;
import com.AndroidFunitureShopApp.view.CategoriesFragment;
import com.AndroidFunitureShopApp.view.HomeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new CategoriesFragment();
            case 2:
                return new AccountFragment();
            case 3:
                return new CartsFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
