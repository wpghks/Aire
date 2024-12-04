package com.example.myapplication.item;

import android.net.Uri;

public class Product {
    private String name;
    private String price;
    private String description;
    private String imageUri;  // URI를 String으로 변경
    private String category;

    // 생성자에 카테고리와 URI를 String으로 받음
    public Product(String name, String price, String description, Uri imageUri, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUri = imageUri.toString();  // URI를 String으로 변환하여 저장
        this.category = category;
    }

    // 이미지 URI를 String으로 반환
    public Uri getImageUri() {
        return Uri.parse(imageUri);  // String을 URI로 변환하여 반환
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri.toString();  // URI를 String으로 변환하여 저장
    }

    // Getter 메소드
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    // Setter 메소드
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // 상품 정보 출력 메소드 (디버깅용)
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", imageUri=" + imageUri +  // imageUri는 이제 String으로 저장됨
                ", category='" + category + '\'' +
                '}';
    }
}