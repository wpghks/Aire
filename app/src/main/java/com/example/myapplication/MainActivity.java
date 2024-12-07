package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import com.example.myapplication.ui.mypage.login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";  // 태그를 정의합니다.
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        getSupportActionBar().setTitle(R.string.app_name);

        // SharedPreferences에서 로그인 상태 확인
        SharedPreferences prefs = getSharedPreferences("loginPref", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String id = prefs.getString("username", "");
        String pw = prefs.getString("password", "");
        String name = prefs.getString("name", "");
        String phone = prefs.getString("phone", "");
        String address = prefs.getString("address", "");

        navView.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
                return true;

            } else if (itemId == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_dashboard);
                return true;

            } else if (itemId == R.id.navigation_notifications) {
                // 로그인 여부 확인
                if (!isLoggedIn) { // 로그인되지 않은 경우
                    Log.d(TAG, "User is not logged in, redirecting to login screen");
                    startActivity(new Intent(MainActivity.this, login.class)); // 로그인 화면으로 이동
                    return true;
                } else {
                    Log.d(TAG, "User is logged in, navigating to MyPage with data");
                    // 로그인된 경우, 사용자 정보를 Bundle로 담아 마이페이지 프래그먼트로 이동
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_USERNAME", id);
                    bundle.putString("USER_PASSWORD", pw);
                    bundle.putString("USER_NAME", name);
                    bundle.putString("USER_PHONE", phone);
                    bundle.putString("USER_ADDRESS", address);

                    // 마이페이지 프래그먼트로 데이터 전달
                    navController.navigate(R.id.navigation_notifications, bundle);
                    return true;
                }
            }
            return false;
        });
    }
}