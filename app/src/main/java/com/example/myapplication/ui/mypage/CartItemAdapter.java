package com.example.myapplication.ui.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private List<CartItem> cartItems;
    private Context context;

    public CartItemAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(cartItem.getProductPrice());
        holder.productCategory.setText(cartItem.getProductCategory());

        // 이미지 URI가 존재하면 Glide로 이미지 로딩
        if (cartItem.getProductImageUri() != null) {
            Glide.with(context)
                    .load(cartItem.getProductImageUri())
                    .placeholder(R.drawable.ic_default_image)  // 기본 이미지 설정
                    .into(holder.productImage);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_default_image)  // 기본 이미지 설정
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productCategory;
        ImageView productImage;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productCategory = itemView.findViewById(R.id.product_category);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
