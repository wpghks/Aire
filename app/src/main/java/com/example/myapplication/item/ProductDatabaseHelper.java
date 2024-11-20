package com.example.myapplication.item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_CATEGORY = "category"; // 카테고리 컬럼 추가

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " INTEGER, " +
                COLUMN_CATEGORY + " TEXT)"; // 카테고리 컬럼 추가
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 상품 추가 메소드
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_IMAGE, product.getImageResourceId()); // imageResourceId로 저장
        values.put(COLUMN_CATEGORY, product.getCategory()); // 카테고리 저장

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 상품 목록 읽기 메소드
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int imageResourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)); // 카테고리 가져오기

                Product product = new Product(name, price, description, imageResourceId, category);
                productList.add(product);
            }
            cursor.close();
        }
        db.close();
        return productList;
    }

    // 특정 카테고리의 상품을 가져오는 메소드
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_CATEGORY + " = ?", new String[]{category}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                int imageResourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE));
                String categoryFromDb = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));

                Product product = new Product(name, price, description, imageResourceId, categoryFromDb);
                productList.add(product);
            }
            cursor.close();
        }
        db.close();
        return productList;
    }
}
