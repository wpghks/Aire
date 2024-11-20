package com.example.myapplication.ui.dashboard;

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

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 이미지 버튼 클릭 이벤트 설정
        ImageButton imageButton1 = root.findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_shirts); // action ID를 변경
        });

        ImageButton imageButton2 = root.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_pants); // action ID를 변경
        });

        ImageButton imageButton3 = root.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_sports); // action ID를 변경
        });

        ImageButton imageButton4 = root.findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_shoes); // action ID를 변경
        });

        ImageButton imageButton5 = root.findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_outer); // action ID를 변경
        });
        ImageButton imageButton6 = root.findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_accessory); // action ID를 변경
        });

        // ... 다른 이미지 버튼들도 같은 방식으로 클릭 이벤트 설정

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
