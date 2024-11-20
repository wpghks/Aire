package com.example.myapplication.item;

public class Product {
    private String name;
    private String price;
    private String description;
    private int imageResourceId;
    private String category; // 카테고리 필드 추가

    // 생성자에 카테고리 추가
    public Product(String name, String price, String description, int imageResourceId, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.category = category;
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

    public int getImageResourceId() {
        return imageResourceId;
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

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
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
                ", imageResourceId=" + imageResourceId +
                ", category='" + category + '\'' +
                '}';
    }
}
