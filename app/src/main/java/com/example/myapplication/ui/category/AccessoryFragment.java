package com.example.myapplication.ui.category;

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
import com.example.myapplication.databinding.FragmentPantsBinding;
import com.example.myapplication.item.Product;
import com.example.myapplication.item.ProductDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AccessoryFragment extends Fragment {

    private FragmentPantsBinding binding;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductDatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // RecyclerView 초기화
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2열로 설정

        // 데이터베이스 초기화
        databaseHelper = new ProductDatabaseHelper(getContext());

        // 데이터베이스에서 팬츠 데이터 가져오기
        List<Product> acceList = databaseHelper.getProductsByCategory("악세서리");
        if (acceList == null) {
            acceList = new ArrayList<>(); // 빈 리스트로 초기화
        }

        // 어댑터 설정 (Context 추가)
        categoryAdapter = new CategoryAdapter(acceList, getContext());
        recyclerView.setAdapter(categoryAdapter);

        // 버튼 클릭 리스너
        Button sh = root.findViewById(R.id.button);
        sh.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(AccessoryFragment.this)
                    .navigate(R.id.action_navigation_accessory_to_navigation_shirts); // 상의로 이동
        });

        Button buttonNavigate = root.findViewById(R.id.button2);
        buttonNavigate.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(AccessoryFragment.this)
                    .navigate(R.id.action_navigation_accessory_to_navigation_pants); // 바지로 이동
        });

        Button pa = root.findViewById(R.id.button3);
        pa.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(AccessoryFragment.this)
                    .navigate(R.id.action_navigation_accessory_to_navigation_sports); // 스포츠로 이동
        });

        Button sp = root.findViewById(R.id.button4);
        sp.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(AccessoryFragment.this)
                    .navigate(R.id.action_navigation_accessory_to_navigation_shoes); // 신발로 이동
        });

        Button ou = root.findViewById(R.id.button5);
        ou.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(AccessoryFragment.this)
                    .navigate(R.id.action_navigation_accessory_to_navigation_outer); // 아우터로 이동
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
