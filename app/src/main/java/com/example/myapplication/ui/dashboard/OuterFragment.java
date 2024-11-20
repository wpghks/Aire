package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOuterBinding;
import com.example.myapplication.item.Product;
import com.example.myapplication.item.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class OuterFragment extends Fragment {

    private FragmentOuterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOuterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView 초기화
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);

        // GridLayoutManager로 변경하여 한 줄에 2개 표시
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // 예시 상품 데이터 생성
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("상의", "₩10,000", "편안한 티셔츠", R.drawable.top));
        productList.add(new Product("바지", "₩20,000", "청바지", R.drawable.pants));
        productList.add(new Product("스포츠", "₩30,000", "운동복", R.drawable.sport));

        // ProductAdapter 설정
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);

        // 버튼 클릭 리스너 설정
        Button sh = root.findViewById(R.id.button);
        sh.setOnClickListener(v -> {
            NavHostFragment.findNavController(OuterFragment.this)
                    .navigate(R.id.action_navigation_outer_to_navigation_shirts);
        });

        Button buttonNavigate = root.findViewById(R.id.button2);
        buttonNavigate.setOnClickListener(v -> {
            NavHostFragment.findNavController(OuterFragment.this)
                    .navigate(R.id.action_navigation_outer_to_navigation_pants);
        });

        Button pa = root.findViewById(R.id.button3);
        pa.setOnClickListener(v -> {
            NavHostFragment.findNavController(OuterFragment.this)
                    .navigate(R.id.action_navigation_outer_to_navigation_sports);
        });

        Button sp = root.findViewById(R.id.button4);
        sp.setOnClickListener(v -> {
            NavHostFragment.findNavController(OuterFragment.this)
                    .navigate(R.id.action_navigation_outer_to_navigation_shoes);
        });

        Button ac = root.findViewById(R.id.button6);
        ac.setOnClickListener(v -> {
            NavHostFragment.findNavController(OuterFragment.this)
                    .navigate(R.id.action_navigation_outer_to_navigation_accessory);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
