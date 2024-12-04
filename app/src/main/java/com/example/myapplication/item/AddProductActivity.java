package com.example.myapplication.item;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.search;
import com.example.myapplication.zzim;

import org.jetbrains.annotations.Nullable;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etProductDescription;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnSaveProduct;
    private Uri selectedImageUri;
    private Spinner categorySpinner;

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_GALLERY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Spinner 초기화
        categorySpinner = findViewById(R.id.spinner_category);
        String[] categories = {"상의", "하의", "스포츠", "신발", "아우터", "악세서리"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // UI 요소 초기화
        etProductName = findViewById(R.id.et_product_name);
        etProductPrice = findViewById(R.id.et_product_price);
        etProductDescription = findViewById(R.id.et_product_description);
        ivProductImage = findViewById(R.id.iv_product_image);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSaveProduct = findViewById(R.id.btn_save_product);

        // 이미지 선택 버튼 클릭 리스너
        btnSelectImage.setOnClickListener(v -> {
            if (checkPermission()) {
                openGallery();
            } else {
                requestPermission();
            }
        });

        // 저장 버튼 클릭 리스너
        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    private boolean checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        Log.d("Permission", "권한 요청을 보냅니다.");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // 이미지 타입으로 설정
        startActivityForResult(intent, REQUEST_CODE_GALLERY); // 100은 요청 코드
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // 선택한 이미지의 URI를 가져옵니다.
            ivProductImage.setImageURI(selectedImageUri); // 이미지 뷰에 URI를 설정합니다.

            // 이미지 URI를 스캔하여 미디어 저장소에 추가합니다.
            scanMediaFile(selectedImageUri);
        }
    }

    private void scanMediaFile(Uri fileUri) {
        String path = null;
        if (fileUri.getScheme().equals("content")) {
            // content URI인 경우
            Cursor cursor = getContentResolver().query(fileUri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex != -1) {
                    path = cursor.getString(columnIndex); // 실제 경로 추출
                }
                cursor.close();
            }
        } else if (fileUri.getScheme().equals("file")) {
            // file URI인 경우
            path = fileUri.getPath();
        }

        if (path != null) {
            MediaScannerConnection.scanFile(this, new String[]{path}, null, (path1, uri) -> {
                Log.d("MediaScanner", "Media scan completed for: " + path1);
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery(); // 권한이 승인되면 갤러리 열기
            } else {
                Toast.makeText(this, "권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String price = etProductPrice.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 이미지 URI 로그 확인
        Log.d("AddProductActivity", "Selected Image URI: " + selectedImageUri);

        // ProductDatabaseHelper 사용
        ProductDatabaseHelper dbHelper = new ProductDatabaseHelper(this);

        // Product 객체 생성
        Product product = new Product(name, price, description, selectedImageUri, category);

        // 데이터베이스에 저장
        if (dbHelper.addProduct(product)) {
            Toast.makeText(this, "상품이 저장되었습니다!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "상품 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_zzim) {
            startActivity(new Intent(this, zzim.class));
            return true;
        } else if (id == R.id.menu_search) {
            startActivity(new Intent(this, search.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
