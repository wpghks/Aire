package com.example.myapplication.item;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductItemAdapter adapter;
    private ProductDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list); // 레이아웃 설정

        // 데이터베이스 헬퍼 초기화
        databaseHelper = new ProductDatabaseHelper(this);

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 데이터베이스에서 상품 목록 읽기
        List<Product> productList = databaseHelper.getAllProducts(); // 모든 상품 읽기

        // 어댑터 설정 (ProductItemAdapter로 변경하여 카테고리 표시)
        adapter = new ProductItemAdapter(productList, this);
        recyclerView.setAdapter(adapter);
    }
}