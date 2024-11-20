package com.example.myapplication.ui.notifications;

import android.content.ContentValues;
import android.content.Intent;
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

import com.example.myapplication.R;
import com.example.myapplication.helper;
import com.example.myapplication.search;
import com.example.myapplication.zzim;

public class join extends AppCompatActivity {
    private helper dbHelper;
    private SQLiteDatabase db;
    Button jo;
    View.OnClickListener cl;
    EditText inphone,inname,add,id,pw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        dbHelper = new helper(this);
        db = dbHelper.getWritableDatabase();

        inname = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.password);
        add = (EditText) findViewById(R.id.address);
        inphone = (EditText) findViewById(R.id.phone);
        jo = (Button) findViewById(R.id.join);

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = id.getText().toString();
                String password = pw.getText().toString();
                String name = inname.getText().toString();
                String address = add.getText().toString();
                String phone = inphone.getText().toString();

                insertUser(username, password, name, address, phone);
                Toast.makeText(join.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                // 로그인 액티비티로 전환
                Intent intent = new Intent(join.this, login.class);
                startActivity(intent);
            }
        };
        jo.setOnClickListener(cl);
    }
    public void insertUser(String username, String password, String name, String address, String phone) {
        ContentValues values = new ContentValues();
        values.put(helper.COLUMN_USERNAME, username);
        values.put(helper.COLUMN_PASSWORD, password);
        values.put(helper.COLUMN_NAME, name);
        values.put(helper.COLUMN_ADDRESS, address);
        values.put(helper.COLUMN_PHONE, phone);

        db.insert(helper.TABLE_USERS, null, values);
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
