package com.example.myapplication.ui.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper;

public class login extends AppCompatActivity {

    Button jo, lo;
    Intent i;
    EditText inid, inpw;
    private helper dbHelper;
    private SQLiteDatabase db;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = new helper(this);
        db = dbHelper.getWritableDatabase();

        jo = findViewById(R.id.join);
        lo = findViewById(R.id.login);
        inid = findViewById(R.id.inid);
        inpw = findViewById(R.id.inpw);

        // 로그인 및 회원가입 버튼 클릭 리스너 설정
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inid.getText().toString();
                String password = inpw.getText().toString();

                // 로그인 처리
                if (getUser(username, password)) {
                    Toast.makeText(login.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    // 로그인 성공 후 사용자 정보 가져오기
                    Cursor userInfo = getUserInfo(username, password);
                    if (userInfo.moveToFirst()) {
                        String id = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_USERNAME));
                        String address = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_ADDRESS));
                        String phone = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_PHONE));
                        String name = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_NAME));
                        String pw = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_PASSWORD));
                        userInfo.close();

                        // SharedPreferences에 사용자 정보 저장
                        SharedPreferences.Editor editor = getSharedPreferences("loginPref", MODE_PRIVATE).edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("username", id);
                        editor.putString("address", address);
                        editor.putString("phone", phone);
                        editor.putString("name", name);
                        editor.putString("password", pw);
                        editor.apply();

                        // 로그인 후 MainActivity로 이동
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "아이디 또는 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // 회원가입 버튼 클릭 리스너 설정
        jo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), join.class);
                startActivity(i);
            }
        });
    }

    public boolean getUser(String username, String password) {
        String[] columns = { helper.COLUMN_USERNAME };
        String selection = helper.COLUMN_USERNAME + " = ? AND " + helper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(
                helper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    // 사용자 정보를 가져오는 메서드
    public Cursor getUserInfo(String username, String password) {
        String[] columns = {
                helper.COLUMN_USERNAME, // id
                helper.COLUMN_ADDRESS,
                helper.COLUMN_PHONE,
                helper.COLUMN_NAME,
                helper.COLUMN_PASSWORD
        };
        String selection = helper.COLUMN_USERNAME + " = ? AND " + helper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        return db.query(
                helper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );
    }
}
