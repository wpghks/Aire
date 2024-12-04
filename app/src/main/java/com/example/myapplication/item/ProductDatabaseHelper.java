package com.example.myapplication.item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 2;  // 버전 2로 변경
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";  // 이미지 URI로 저장
    private static final String COLUMN_CATEGORY = "category"; // 카테고리 컬럼 추가

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 버전 1에 대한 테이블 생성
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " + // 이미지 URI를 TEXT로 저장
                COLUMN_CATEGORY + " TEXT)"; // 카테고리 컬럼 추가
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전이 2로 업그레이드 될 때, category 컬럼을 추가
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_CATEGORY + " TEXT;");
        }
    }

    // 상품 추가 메소드
    public boolean addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_IMAGE, product.getImageUri() != null ? product.getImageUri().toString() : null); // null 체크 후 null이면 null로 저장
        values.put(COLUMN_CATEGORY, product.getCategory()); // 카테고리 저장

        // 데이터 삽입 성공 여부 확인
        long result = db.insert(TABLE_NAME, null, values);
        Log.d("Product Added", "Name: " + product.getName() + ", Category: " + product.getCategory() + ", Image URI: " + product.getImageUri());

        db.close();
        return result != -1; // 삽입 성공 시 true 반환, 실패 시 false 반환
    }

    // 상품 목록 읽기 메소드
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // 각 컬럼 인덱스를 안전하게 가져오기
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                    int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                    int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                    int imageUriIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                    int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);

                    if (nameIndex != -1 && priceIndex != -1 && descriptionIndex != -1 && imageUriIndex != -1 && categoryIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String price = cursor.getString(priceIndex);
                        String description = cursor.getString(descriptionIndex);
                        String imageUriString = cursor.getString(imageUriIndex); // 이미지 URI를 String으로 가져오기
                        Uri imageUri = imageUriString != null && !imageUriString.isEmpty() ? Uri.parse(imageUriString) : null; // null 체크
                        String category = cursor.getString(categoryIndex); // 카테고리 가져오기

                        Product product = new Product(name, price, description, imageUri, category); // URI로 객체 생성
                        productList.add(product);
                    }
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return productList;
    }

    // 특정 카테고리의 상품을 가져오는 메소드
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_NAME, null, COLUMN_CATEGORY + " = ?", new String[]{category}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // 각 컬럼 인덱스를 안전하게 가져오기
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                    int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
                    int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                    int imageUriIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                    int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);

                    if (nameIndex != -1 && priceIndex != -1 && descriptionIndex != -1 && imageUriIndex != -1 && categoryIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String price = cursor.getString(priceIndex);
                        String description = cursor.getString(descriptionIndex);
                        String imageUriString = cursor.getString(imageUriIndex); // 이미지 URI를 String으로 가져오기
                        Uri imageUri = imageUriString != null && !imageUriString.isEmpty() ? Uri.parse(imageUriString) : null; // null 체크
                        String categoryFromDb = cursor.getString(categoryIndex);

                        Product product = new Product(name, price, description, imageUri, categoryFromDb);
                        productList.add(product);
                    }
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return productList;
    }
}