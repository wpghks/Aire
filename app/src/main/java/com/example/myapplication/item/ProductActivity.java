package com.example.myapplication.item;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ProductActivity extends AppCompatActivity {

    private ProductDatabaseHelper dbHelper;
    private int productId; // 제품 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product);

        // DB 헬퍼 초기화
        dbHelper = new ProductDatabaseHelper(this);

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        productId = intent.getIntExtra("PRODUCT_ID", -1); // ID로 제품을 찾을 예정

        // UI에 데이터 설정하기
        TextView nameTextView = findViewById(R.id.product_name);
        TextView priceTextView = findViewById(R.id.product_price);
        TextView descriptionTextView = findViewById(R.id.product_description);
        ImageView productImageView = findViewById(R.id.product_image);

        // 데이터베이스에서 이미지 및 제품 정보 가져오기
        Product product = dbHelper.getProductById(productId);
        if (product != null) {
            nameTextView.setText(product.getName());
            priceTextView.setText(product.getPrice());
            descriptionTextView.setText(product.getDescription());

            // 이미지를 비트맵으로 변환하여 설정
            Bitmap productImage = dbHelper.getProductImage(productId);
            if (productImage != null) {
                productImageView.setImageBitmap(productImage);  // 비트맵으로 이미지 설정
            }
        }
    }
}
