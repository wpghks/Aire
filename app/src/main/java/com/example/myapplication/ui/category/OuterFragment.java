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

public class OuterFragment extends Fragment {

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
        List<Product> outerList = databaseHelper.getProductsByCategory("아우터");
        if (outerList == null) {
            outerList = new ArrayList<>(); // 빈 리스트로 초기화
        }

        // 어댑터 설정
        categoryAdapter = new CategoryAdapter(outerList, getContext());
        recyclerView.setAdapter(categoryAdapter);



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
