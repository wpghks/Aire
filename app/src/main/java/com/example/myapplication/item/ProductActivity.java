package com.example.myapplication.item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product); // 수정된 부분

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String productPrice = intent.getStringExtra("PRODUCT_PRICE");
        String productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION");
        String productImageUri = intent.getStringExtra("PRODUCT_IMAGE"); // URI를 String으로 받아오기
        Uri productImage = Uri.parse(productImageUri); // String을 Uri로 변환

        // UI에 데이터 설정하기
        TextView nameTextView = findViewById(R.id.product_name);
        TextView priceTextView = findViewById(R.id.product_price);
        TextView descriptionTextView = findViewById(R.id.product_description);
        ImageView productImageView = findViewById(R.id.product_image);

        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        descriptionTextView.setText(productDescription);
        productImageView.setImageURI(productImage); // Uri를 ImageView에 설정
    }
}
