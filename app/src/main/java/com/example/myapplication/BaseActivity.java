package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    public void updateProfile(String username, String name, String password, String address, String phone) {
        SharedPreferences prefs = getSharedPreferences("loginPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // SharedPreferences에 수정된 정보 저장
        editor.putString("username", username);
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putString("address", address);
        editor.putString("phone", phone);
        editor.apply();

        // 현재 Activity의 사용자 정보를 업데이트
        this.username = username;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;

        // 수정 완료 후 UI 업데이트 또는 확인 메시지 추가
        Toast.makeText(this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
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
