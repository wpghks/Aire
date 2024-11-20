package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private ImageView bannerImageView;
    private int[] bannerImages = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private int currentBannerIndex = 0;
    private Handler handler;
    private Runnable runnable;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false); //배너
        bannerImageView = view.findViewById(R.id.bannerImageView); //배너

        binding = FragmentHomeBinding.inflate(inflater, container, false);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // 현재 배너 인덱스 증가
                currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length;
                // 다음 배너로 변경
                bannerImageView.setImageResource(bannerImages[currentBannerIndex]);
                // 3초 후에 다시 실행
                handler.postDelayed(this, 3000);
            }
        };


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Fragment가 화면에 나타날 때 runnable을 시작합니다.
        handler.postDelayed(runnable, 3000);
    }

    public void onPause() {
        super.onPause();
        // Fragment가 화면에 보이지 않으면 runnable을 중지합니다.
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}