package com.example.myapplication.ui.mypage;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper;

public class EditProfileActivity extends BaseActivity {

    private TextView usernameTextView;
    private EditText nameEditText, passwordEditText, emailEditText, phoneEditText;
    private Button saveButton;
    private ImageView eyeIcon;
    private helper dbHelper;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // EditText 및 Button 초기화
        usernameTextView = findViewById(R.id.usernameTextView);
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        saveButton = findViewById(R.id.saveButton);
        eyeIcon = findViewById(R.id.eyeIcon);



        // helper 객체 초기화
        dbHelper = new helper(this);



        // BaseActivity에서 정보를 받아오기
        if (isLoggedIn()) {
            String username = getUsername();  // BaseActivity에서 가져온 username
            String name = getName();          // BaseActivity에서 가져온 name
            String password = getPassword();  // BaseActivity에서 가져온 password
            String email = getAddress();      // BaseActivity에서 가져온 address (이메일로 사용)
            String phone = getPhone();        // BaseActivity에서 가져온 phone

            // EditText에 기존 정보 세팅
            usernameTextView.setText(username);
            nameEditText.setText(name);
            passwordEditText.setText(password);
            emailEditText.setText(email);
            phoneEditText.setText(phone);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력 값 가져오기
                String name = nameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                // 입력값 검증
                if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 사용자 정보를 데이터베이스에 업데이트 (아이디는 변경하지 않음)
                    dbHelper.updateUserInfo(getUsername(), name, password, email, phone);

                    // SharedPreferences에서 정보 갱신
                    updateProfile(getUsername(), name, password, email, phone);

                    Toast.makeText(EditProfileActivity.this, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        // 아이콘 클릭 리스너 추가
        eyeIcon.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // 비밀번호 숨기기
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye_off);  // 숨김 아이콘
            } else {
                // 비밀번호 보이기
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye_on);  // 보임 아이콘
            }
            // 커서 위치 재설정
            passwordEditText.setSelection(passwordEditText.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });
    }
}
