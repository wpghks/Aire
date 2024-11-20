package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentShirtsBinding;

public class ShirtsFragment extends Fragment {

    private FragmentShirtsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShirtsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        Button sh = root.findViewById(R.id.button);
//        sh.setOnClickListener(v -> {
//            // 다른 프래그먼트로 이동
//            NavHostFragment.findNavController(ShirtsFragment.this)
//                    .navigate(R.id.action_navigation_shirts_to_navigation_shirts); // 상의로 이동
//        });


        Button buttonNavigate = root.findViewById(R.id.button2);
        buttonNavigate.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(ShirtsFragment.this)
                    .navigate(R.id.action_navigation_shirts_to_navigation_pants); // 바지로 이동
        });
        
        Button pa = root.findViewById(R.id.button3);
        pa.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(ShirtsFragment.this)
                    .navigate(R.id.action_navigation_shirts_to_navigation_sports); // 스포츠로 이동
        });
        Button sp = root.findViewById(R.id.button4);
        sp.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(ShirtsFragment.this)
                    .navigate(R.id.action_navigation_shirts_to_navigation_shoes); // 신발로 이동
        });
        Button ou = root.findViewById(R.id.button5);
        ou.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(ShirtsFragment.this)
                    .navigate(R.id.action_navigation_shirts_to_navigation_outer); // 아우터로 이동
        });
        Button ac = root.findViewById(R.id.button6);
        ac.setOnClickListener(v -> {
            // 다른 프래그먼트로 이동
            NavHostFragment.findNavController(ShirtsFragment.this)
                    .navigate(R.id.action_navigation_shirts_to_navigation_accessory); // 악세서리 이동
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}