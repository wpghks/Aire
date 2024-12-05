package com.example.myapplication.ui.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper;
import com.example.myapplication.search;
import com.example.myapplication.zzim;

public class login extends AppCompatActivity {


    Button jo,lo;

    View.OnClickListener cl;
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


        jo = (Button) findViewById(R.id.join);
        lo = (Button) findViewById(R.id.login);
        inid = (EditText) findViewById(R.id.inid);
        inpw = (EditText) findViewById(R.id.inpw);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inid.getText().toString();
                String password = inpw.getText().toString();
                int vId = v.getId();
                if ( vId == R.id.login) {
                    if (getUser(username, password)) {
                        Toast.makeText(login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        isLoggedIn = true; // 로그인 성공 시 isLoggedIn을 true로 설정

                        //로그인 성공 후 저장
                        SharedPreferences.Editor editor = getSharedPreferences("loginPref", MODE_PRIVATE).edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        Cursor userInfo = getUserInfo(username, password);
                        if (userInfo.moveToFirst()) {
                            String id = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_USERNAME));
                            String address = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_ADDRESS));
                            String phone = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_PHONE));
                            String pw = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_PASSWORD));
                            String name = userInfo.getString(userInfo.getColumnIndex(helper.COLUMN_NAME));

                            // MainActivity로 사용자 정보를 전달
                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.putExtra("isLoggedIn", isLoggedIn);
                            intent.putExtra("USER_USERNAME", id);
                            intent.putExtra("USER_ADDRESS", address);
                            intent.putExtra("USER_PHONE", phone);
                            intent.putExtra("USER_NAME", name);
                            intent.putExtra("USER_PASSWORD", pw);
                            startActivity(intent);
                            finish(); // 로그인 화면 종료
                        }
                        userInfo.close();
                    } else {
                        Toast.makeText(login.this, "아이디 또는 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }
                } else if (vId == R.id.join) {
                    i = new Intent(getApplicationContext(), join.class);
                    startActivity(i);
                }

            }
        };
        lo.setOnClickListener(cl);
        jo.setOnClickListener(cl);

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