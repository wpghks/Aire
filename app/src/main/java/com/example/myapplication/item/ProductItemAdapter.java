package com.example.myapplication.item;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductItemAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(product.getPrice());
        holder.tvProductCategory.setText(product.getCategory());

        // 이미지 URI가 null이 아니면 Glide로 이미지 로딩, 아니면 기본 이미지 설정
        if (product.getImageUri() != null) {
            Glide.with(context)
                    .load(product.getImageUri())  // URI를 Glide로 로드
                    .placeholder(R.drawable.ic_default_image)  // 기본 이미지 설정
                    .into(holder.ivProductImage);  // ImageView에 설정
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_default_image)  // 기본 이미지 설정
                    .into(holder.ivProductImage);  // ImageView에 설정
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductCategory;
        ImageView ivProductImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.product_name);
            tvProductPrice = itemView.findViewById(R.id.product_price);
            ivProductImage = itemView.findViewById(R.id.product_image);
            tvProductCategory = itemView.findViewById(R.id.product_category);
        }
    }
}
