package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected String username;
    protected String address;
    protected String phone;
    protected String name;
    protected String password;
    protected boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreferences에서 로그인 정보 불러오기
        SharedPreferences prefs = getSharedPreferences("loginPref", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            username = prefs.getString("username", "");
            address = prefs.getString("address", "");
            phone = prefs.getString("phone", "");
            name = prefs.getString("name", "");
            password = prefs.getString("password", "");
        }
    }

    // 로그인 정보가 저장되어 있는지 확인하는 메서드
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // 로그아웃 처리를 위한 메서드 추가
    public void onLogout() {
        // 로그아웃 처리 (예: UI 업데이트, 추가 작업)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            // SearchActivity로 이동
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
