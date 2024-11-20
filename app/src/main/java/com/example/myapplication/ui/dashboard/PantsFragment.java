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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPantsBinding;
import com.example.myapplication.item.Product;

import java.util.ArrayList;
import java.util.List;

public class PantsFragment extends Fragment {

    private List<Product> pantsList; // Product 객체 리스트
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    private FragmentPantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2열로 설정


        // 팬츠 카테고리 데이터 설정
        pantsList = new ArrayList<>();
        pantsList.add(new Product("청바지", "편안하고 스타일리시한 청바지", "₩30,000", R.drawable.banner1, "바지"));
        pantsList.add(new Product("면바지", "여름에 좋은 가벼운 면바지", "₩25,000", R.drawable.banner2, "바지"));

        categoryAdapter = new CategoryAdapter(pantsList);
        recyclerView.setAdapter(categoryAdapter);

        Button sh = root.findViewById(R.id.button);
        sh.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(PantsFragment.this)
                    .navigate(R.id.action_navigation_pants_to_navigation_shirts); // 상의로 이동
        });

        Button pa = root.findViewById(R.id.button3);
        pa.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(PantsFragment.this)
                    .navigate(R.id.action_navigation_pants_to_navigation_sports); // 스포츠로 이동
        });
        Button sp = root.findViewById(R.id.button4);
        sp.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(PantsFragment.this)
                    .navigate(R.id.action_navigation_pants_to_navigation_shoes); // 신발로 이동
        });

        Button ou = root.findViewById(R.id.button5);
        ou.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(PantsFragment.this)
                    .navigate(R.id.action_navigation_pants_to_navigation_outer); // 아우터로 이동
        });
        Button ac = root.findViewById(R.id.button6);
        ac.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(PantsFragment.this)
                    .navigate(R.id.action_navigation_pants_to_navigation_accessory); // 악세서리 이동
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
