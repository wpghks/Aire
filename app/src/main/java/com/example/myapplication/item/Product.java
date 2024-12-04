package com.example.myapplication.item;

import android.net.Uri;

public class Product {
    private String name;
    private String price;
    private String description;
    private Uri imageUri;
    private String category;

    public Product(String name, String price, String description, Uri imageUri, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUri = imageUri;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getCategory() {
        return category;
    }
}
