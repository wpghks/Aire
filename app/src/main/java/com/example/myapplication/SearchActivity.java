package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.item.Product;
import com.example.myapplication.item.ProductDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private SearchView searchView;  // SearchView로 변경
    private RecyclerView rvSearchResults;
    private TextView tvSearchQuery;
    private SearchAdapter searchAdapter;
    private List<Product> searchResults;
    private ProductDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);  // XML 레이아웃 설정

        searchView = findViewById(R.id.search_view);  // SearchView 찾기
        tvSearchQuery = findViewById(R.id.tv_search_query);
        rvSearchResults = findViewById(R.id.rv_search_results);

        // RecyclerView 설정
        searchResults = new ArrayList<>();
        searchAdapter = new SearchAdapter(searchResults); // 어댑터 설정
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));  // LinearLayoutManager 사용
        rvSearchResults.setAdapter(searchAdapter);

        // dbHelper 초기화
        dbHelper = new ProductDatabaseHelper(this);  // 데이터베이스 헬퍼 객체 생성

        // SearchView에 텍스트 변경 리스너 설정
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색어 제출 시 실행
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색어가 변경될 때마다 실행
                if (!TextUtils.isEmpty(newText)) {
                    performSearch(newText);
                }
                return true;
            }
        });
    }

    private void performSearch(String query) {
        // 카테고리로 검색
        List<Product> results = dbHelper.getProductsByCategory(query);

        if (results.isEmpty()) {
            // 카테고리 검색 결과가 없다면, 상품명으로 검색
            results = dbHelper.getAllProducts();
            List<Product> filteredResults = new ArrayList<>();
            for (Product product : results) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredResults.add(product);  // 상품명에 검색어가 포함되면 결과에 추가
                }
            }
            results = filteredResults;  // 필터링된 결과로 갱신
        }

        // 결과 추가 및 어댑터 갱신
        searchResults.clear();  // 기존 검색 결과 초기화
        searchResults.addAll(results);
        searchAdapter.notifyDataSetChanged();  // RecyclerView 갱신
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 액티비티가 다시 돌아올 때 입력 필드와 결과를 초기화
        searchResults.clear();  // 이전 검색 결과 초기화
        searchAdapter.notifyDataSetChanged();  // 어댑터 갱신
        tvSearchQuery.setText("");  // 검색어 텍스트 초기화
        searchView.setQuery("", false);  // SearchView 초기화
    }
}
