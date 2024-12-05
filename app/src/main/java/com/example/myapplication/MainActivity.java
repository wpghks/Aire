package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.ui.mypage.login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isLoggedIn = false;

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

        Intent intent = getIntent();
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false);
        String id = intent.getStringExtra("USER_USERNAME");
        String pw = intent.getStringExtra("USER_PASSWORD");
        String name = intent.getStringExtra("USER_NAME");
        String phone = intent.getStringExtra("USER_PHONE");
        String address = intent.getStringExtra("USER_ADDRESS");

        navView.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
                return true;

            } else if (itemId == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_dashboard);
                return true;

            } else if (itemId == R.id.navigation_notifications) {

                // 마이페이지 항목 클릭 처리
                if (!isLoggedIn) { // 로그인되어 있지 않은 경우 로그인화면으로 이동
                    startActivity(new Intent(MainActivity.this, login.class));
                } else {
                    // 데이터 담기
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_USERNAME", id);
                    bundle.putString("USER_PASSWORD", pw);
                    bundle.putString("USER_NAME", name);
                    bundle.putString("USER_PHONE", phone);
                    bundle.putString("USER_ADDRESS", address);

                    // 마이페이지 프래그먼트로 데이터 전달
                    navController.navigate(R.id.navigation_notifications, bundle);
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 액션바에 메뉴를 인플레이트
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_zzim) {
            startActivity(new Intent(this, zzim.class));
            return true;
        } else if (id == R.id.menu_search) {
            startActivity(new Intent(this, search.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
