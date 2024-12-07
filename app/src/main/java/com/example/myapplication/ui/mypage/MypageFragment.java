package com.example.myapplication.ui.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentMypageBinding;
import com.example.myapplication.item.AddProductActivity;
import com.example.myapplication.item.ProductListActivity;
import com.example.myapplication.BaseActivity;
import com.example.myapplication.ui.mypage.login;

public class MypageFragment extends Fragment {

    private TextView inid;
    private Button initem, productlist, cart, logout;
    private FragmentMypageBinding binding;
    private String correctId = "yjh11080";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // FragmentMypageBinding을 사용하여 레이아웃과 연결
        binding = FragmentMypageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // TextView 및 Button 초기화
        inid = binding.inid;
        initem = binding.initem;
        logout = binding.logout;
        productlist = binding.productlist;
        cart = binding.cart;  // 카트 버튼 추가

        // 전달된 데이터로 UI 업데이트
        if (getArguments() != null) {
            String id = getArguments().getString("USER_USERNAME", "");
            String name = getArguments().getString("USER_NAME", "");
            String pw = getArguments().getString("USER_PASSWORD", "");
            String phone = getArguments().getString("USER_PHONE", "");
            String add = getArguments().getString("USER_ADDRESS", "");

            // inid에 name 설정
            inid.setText(name + "님 환영합니다");

            // 아이디 비교 및 버튼 가시성 설정
            if (correctId.equals(id)) {
                initem.setVisibility(View.VISIBLE);
                productlist.setVisibility(View.VISIBLE); // 숨겨진 버튼 보이기
                cart.setVisibility(View.VISIBLE); // 카트 버튼 보이기

                // 상품 리스트 버튼 클릭
                productlist.setOnClickListener(v -> {
                    // 상품리스트로 이동
                    Intent intent = new Intent(getActivity(), ProductListActivity.class);
                    startActivity(intent);
                });

                // 상품 추가 버튼 클릭
                initem.setOnClickListener(v -> {
                    // AddProductActivity로 이동
                    Intent intent = new Intent(getActivity(), AddProductActivity.class);
                    startActivity(intent);
                });

                // 카트 버튼 클릭
                cart.setOnClickListener(v -> {
                    // CartActivity로 이동
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    startActivity(intent);
                });
            } else {
                initem.setVisibility(View.INVISIBLE); // 버튼 숨기기
                productlist.setVisibility(View.INVISIBLE);
            }
        }

        // 로그아웃 버튼 클릭 리스너 추가
        logout.setOnClickListener(v -> {
            // SharedPreferences에서 로그인 정보 삭제
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("loginPref", getActivity().MODE_PRIVATE).edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("username");
            editor.remove("password");
            editor.remove("name");
            editor.remove("phone");
            editor.remove("address");
            editor.apply();

            // BaseActivity에서 로그아웃 처리
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).onLogout();
            }

            // 로그인 화면으로 이동
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
            getActivity().finish();  // 현재 액티비티 종료
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
