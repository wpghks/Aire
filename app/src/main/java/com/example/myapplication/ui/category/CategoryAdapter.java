package com.example.myapplication.ui.category;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.item.Product;
import com.example.myapplication.item.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Product> productList; // Product 객체 리스트

    // 생성자
    public CategoryAdapter(List<Product> productList) {
        // Null 체크를 통해 안전하게 초기화
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_category 레이아웃을 사용하여 ViewHolder 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        // 데이터를 View에 바인딩
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());

        // 이미지가 Uri로 저장되었으므로, Uri를 사용하여 이미지를 설정
        Uri imageUri = product.getImageUri();
        if (imageUri != null) {
            // Glide를 사용하여 이미지 로딩
            Glide.with(holder.productImage.getContext())
                    .load(imageUri)
                    .placeholder(R.drawable.ic_default_image) // 기본 이미지 설정
                    .into(holder.productImage);
        } else {
            // 이미지 URI가 없으면 기본 이미지 설정
            holder.productImage.setImageResource(R.drawable.ic_default_image);
        }

        // 아이템 클릭 시 상세 페이지로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductActivity.class);

            // 데이터 전달
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_PRICE", product.getPrice());
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
            intent.putExtra("PRODUCT_IMAGE", product.getImageUri().toString()); // URI를 String으로 전달

            holder.itemView.getContext().startActivity(intent); // ProductActivity로 이동
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice;
        ImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
