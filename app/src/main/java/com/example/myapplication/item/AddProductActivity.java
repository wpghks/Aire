package com.example.myapplication.item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.search;
import com.example.myapplication.zzim;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etProductDescription;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnSaveProduct;
    private Uri selectedImageUri;
    private Spinner categorySpinner; // 카테고리 Spinner 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Spinner 초기화
        categorySpinner = findViewById(R.id.spinner_category);

        // 카테고리 목록 생성
        String[] categories = {"상의", "하의", "스포츠", "신발", "아우터", "악세서리"};

        // ArrayAdapter 생성하여 Spinner에 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // UI 요소 초기화
        etProductName = findViewById(R.id.et_product_name);
        etProductPrice = findViewById(R.id.et_product_price);
        etProductDescription = findViewById(R.id.et_product_description);
        ivProductImage = findViewById(R.id.iv_product_image);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        // 이미지 선택 버튼 클릭 리스너
        btnSelectImage.setOnClickListener(v -> openGallery());

        // 저장 버튼 클릭 리스너
        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    // 1. 이미지 선택하기 - 갤러리 열기
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 100); // Request code: 100
    }

    // 2. 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivProductImage.setImageURI(selectedImageUri); // 이미지 미리보기 업데이트
        }
    }

    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String price = etProductPrice.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString(); // 선택된 카테고리 가져오기

        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // ProductDatabaseHelper 사용
        ProductDatabaseHelper dbHelper = new ProductDatabaseHelper(this);

        // 이미지 리소스를 설정하기 위한 기본 이미지 ID
        int imageResourceId = R.drawable.ic_default_image; // 기본 이미지 리소스 ID로 변경

        // Product 객체 생성 (카테고리 추가)
        Product product = new Product(name, price, description, imageResourceId, category); // 카테고리 추가

        dbHelper.addProduct(product); // Product 객체를 사용하여 추가

        Toast.makeText(this, "상품이 저장되었습니다!", Toast.LENGTH_SHORT).show();
        finish(); // 상품 추가 후 액티비티 종료
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
