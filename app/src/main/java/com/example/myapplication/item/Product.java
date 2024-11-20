package com.example.myapplication.item;

public class Product {
    private String name;
    private String price;
    private String description;
    private int imageResourceId;

    public Product(String name, String price, String description, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
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
}
