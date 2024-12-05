package com.example.myapplication.ui.mypage;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart instance;
    private List<CartItem> cartItems;

    // 싱글톤 패턴 적용
    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // 장바구니에 아이템 추가
    public void addProduct(CartItem item) {
        cartItems.add(item);
    }

    // 장바구니 아이템 비우기
    public void clearCart() {
        cartItems.clear();
    }

    // 장바구니 아이템 목록 반환
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            try {
                totalPrice += Integer.parseInt(item.getProductPrice());  // String을 int로 변환
            } catch (NumberFormatException e) {
                e.printStackTrace(); // 변환 실패 시 예외 처리
            }
        }
        return totalPrice;
    }
}

