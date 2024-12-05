package com.example.myapplication.ui.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.mypage.Cart;
import com.example.myapplication.item.Product;
import com.example.myapplication.ui.mypage.CartItem;
import com.example.myapplication.item.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;

    public CategoryAdapter(List<Product> productList, Context context) {
        this.productList = productList != null ? productList : new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());

        // 가격을 String으로 설정
        final String price = product.getPrice();
        holder.productPrice.setText(price);

        // 이미지 URI 처리
        Uri imageUri = product.getImageUri();
        if (imageUri != null) {
            Glide.with(holder.productImage.getContext())
                    .load(imageUri)
                    .placeholder(R.drawable.ic_default_image)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.ic_default_image);
        }

        // 아이템 클릭 시 상세 페이지로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductActivity.class);
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_PRICE", price);  // 가격을 String으로 전달
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());

            if (imageUri != null) {
                intent.putExtra("PRODUCT_IMAGE", imageUri.toString());
            }

            holder.itemView.getContext().startActivity(intent);
        });

        // 장바구니에 추가
        holder.addToCartButton.setOnClickListener(v -> {
            // 가격은 String으로 전달
            String productPrice = product.getPrice();

            // 장바구니에 상품 추가
            Cart.getInstance().addProduct(new CartItem(product.getName(), productPrice, product.getCategory(), imageUri != null ? imageUri.toString() : null));

            // "장바구니에 추가되었습니다" 메시지 출력
            Toast.makeText(context, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show();  // 이 줄을 추가
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice;
        ImageView productImage;
        Button addToCartButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            addToCartButton = itemView.findViewById(R.id.button_add_to_cart);
        }
    }
}
