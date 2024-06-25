package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.AndroidFunitureShopApp.databinding.ActivityMainBinding;
import com.AndroidFunitureShopApp.view.AccountFragment;
import com.AndroidFunitureShopApp.viewmodel.Utils;
import com.AndroidFunitureShopApp.viewmodel.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onResume();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        ChangeTab();

        if (Utils.cartItemList == null) {
            Utils.cartItemList = new ArrayList<>();
        }

        SetUserInfo();
//        binding.searchBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchActivityProcess();
//            }
//        });
//

    }

    private void searchActivityProcess() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void SetUserInfo() {
        AccountFragment fragment = new AccountFragment();
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("account_ID");
        Bundle bundle = new Bundle();
        bundle.putString("account_ID", user_id);
        fragment.setArguments(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
//        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//        if (!isLoggedIn) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    //Click vào mỗi item dưới navigation bottom thì hiện fragment tương ứng
    private void ChangeTab() {
        bottomNavigationView = binding.bottomNav;
        viewPager = binding.viewPager;
        binding.bottomNav.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    Log.d("DEBUG", "0");
//                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_category:
                    Log.d("DEBUG", "1");
//                    Toast.makeText(MainActivity.this, "Category", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_account:
                    Log.d("DEBUG", "2");
//                    Toast.makeText(MainActivity.this, "Your Account", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.action_cart:
                    Log.d("DEBUG", "3");
//                    Toast.makeText(MainActivity.this, "Fragment 4", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(3);
                    break;
            }
            ;

            return true;
        });
        setUpViewPager();
    }

    // Vuốt từ fragment này sang fragment khác
    private void setUpViewPager() {
        Log.d("DEBUG", "setUpViewPager");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_category).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_account).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}