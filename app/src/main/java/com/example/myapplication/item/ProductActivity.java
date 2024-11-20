package com.example.myapplication.item;

import android.content.Intent;
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
        int productImage = intent.getIntExtra("PRODUCT_IMAGE", 0);

        // UI에 데이터 설정하기
        TextView nameTextView = findViewById(R.id.product_name);
        TextView priceTextView = findViewById(R.id.product_price);
        TextView descriptionTextView = findViewById(R.id.product_description);
        ImageView productImageView = findViewById(R.id.product_image);

        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        descriptionTextView.setText(productDescription);
        productImageView.setImageResource(productImage);
    }
}
