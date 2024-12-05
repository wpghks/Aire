package com.example.myapplication.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.myapplication.R;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentDashboardBinding;

public class CategoryFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoryViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 각 카테고리로 이동
        ImageButton imageButton1 = root.findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_shirts);
        });

        ImageButton imageButton2 = root.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_pants);
        });

        ImageButton imageButton3 = root.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_sports);
        });

        ImageButton imageButton4 = root.findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_shoes);
        });

        ImageButton imageButton5 = root.findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_outer);
        });
        ImageButton imageButton6 = root.findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(v -> {
            NavHostFragment.findNavController(CategoryFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_accessory);
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
