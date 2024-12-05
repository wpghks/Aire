package com.example.myapplication.ui.mypage;

import android.net.Uri;

public class CartItem {
    private String productName;
    private String productPrice;  // 가격을 String으로 변경
    private String productCategory;
    private String productImageUri;

    public CartItem(String productName, String productPrice, String productCategory, String productImageUri) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productImageUri = productImageUri;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;  // String 반환
    }

    public String getProductCategory() {
        return productCategory;
    }

    // String을 Uri로 변환하여 반환
    public Uri getProductImageUri() {
        return productImageUri != null ? Uri.parse(productImageUri) : null;
    }
}
