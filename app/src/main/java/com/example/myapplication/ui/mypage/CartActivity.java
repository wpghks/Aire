package com.example.myapplication.ui.mypage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CartActivity extends AppCompatActivity {

    private Cart cart;  // 장바구니 객체
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private Button buttonClearCart;
    private Button buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);  // 장바구니 레이아웃

        // 싱글톤으로 장바구니 객체 가져오기
        cart = Cart.getInstance();

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recycler_cart_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 설정
        adapter = new CartItemAdapter(cart.getCartItems(), this);
        recyclerView.setAdapter(adapter);

        // 장바구니 비우기 버튼 설정
        buttonClearCart = findViewById(R.id.button_clear_cart);
        buttonClearCart.setOnClickListener(v -> {
            cart.clearCart();
            adapter.notifyDataSetChanged();
            updateTotalPrice(); // 총합 업데이트
            Toast.makeText(this, "장바구니가 비워졌습니다.", Toast.LENGTH_SHORT).show();
        });

        // 결제 버튼 설정
        buttonCheckout = findViewById(R.id.button_checkout);
        buttonCheckout.setOnClickListener(v -> {
            // 결제 로직 추가 (예: 결제 화면으로 이동)
            Toast.makeText(this, "결제 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
        });

        // 총합 표시
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int totalPrice = cart.getTotalPrice();
        TextView totalPriceTextView = findViewById(R.id.text_total_price);
        totalPriceTextView.setText("총합: ₩" + totalPrice);
    }
}
