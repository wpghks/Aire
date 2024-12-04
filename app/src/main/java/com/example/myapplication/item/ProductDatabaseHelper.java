package com.example.myapplication.item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 3;  // 버전 3으로 변경
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";  // 이미지를 BLOB 형식으로 저장
    private static final String COLUMN_CATEGORY = "category";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;  // context를 받아서 멤버 변수로 저장
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " BLOB, " +  // 이미지 컬럼을 BLOB 형식으로 변경
                COLUMN_CATEGORY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_CATEGORY + " TEXT;");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_IMAGE + " BLOB;");
        }
    }

    // 이미지 저장 메소드
    public boolean addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_CATEGORY, product.getCategory());

        if (product.getImageUri() != null) {
            // 비트맵을 BLOB 형식으로 변환
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), product.getImageUri());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                values.put(COLUMN_IMAGE, byteArrayOutputStream.toByteArray());  // BLOB 데이터로 저장
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        values.put(COLUMN_CATEGORY, product.getCategory());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // 이미지 가져오기
    public Bitmap getProductImage(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_IMAGE}, COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
            cursor.close();
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);  // BLOB을 Bitmap으로 변환
        }
        return null;
    }

    // 모든 상품 목록 읽기
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String imageUriString = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                Uri imageUri = imageUriString != null && !imageUriString.isEmpty() ? Uri.parse(imageUriString) : null;
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));

                Product product = new Product(name, price, description, imageUri, category);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    // 특정 카테고리의 상품을 가져오는 메소드
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_CATEGORY + " = ?",
                new String[]{category}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String imageUriString = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                Uri imageUri = imageUriString != null && !imageUriString.isEmpty() ? Uri.parse(imageUriString) : null;
                String categoryFromDb = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));

                Product product = new Product(id, name, price, description, imageUri, categoryFromDb);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
