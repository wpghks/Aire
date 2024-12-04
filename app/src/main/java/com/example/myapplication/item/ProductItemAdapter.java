package com.example.myapplication.item;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.tvProductDescription.setText(product.getDescription());

        // 이미지 URI가 null이 아니면 URI로 설정, 아니면 기본 이미지 설정
        if (product.getImageUri() != null) {
            holder.ivProductImage.setImageURI(product.getImageUri()); // URI로 이미지 설정
        } else {
            holder.ivProductImage.setImageResource(R.drawable.ic_default_image);  // 기본 이미지 설정
        }

        holder.tvProductCategory.setText(product.getCategory()); // 카테고리 추가
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductDescription, tvProductCategory;
        ImageView ivProductImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.product_name); // 기존 ID 사용
            tvProductPrice = itemView.findViewById(R.id.product_price); // 기존 ID 사용
            tvProductDescription = itemView.findViewById(R.id.product_description); // 기존 ID 사용
            ivProductImage = itemView.findViewById(R.id.product_image); // 기존 ID 사용
            tvProductCategory = itemView.findViewById(R.id.product_category); // 카테고리 텍스트뷰 추가
        }
    }
}