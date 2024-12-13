package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_PHONE + " TEXT);";

    public helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void updateUserInfo(String username, String newName, String newPassword, String newAddress, String newPhone) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 수정할 데이터 업데이트
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_PASSWORD, newPassword);
        values.put(COLUMN_ADDRESS, newAddress);
        values.put(COLUMN_PHONE, newPhone);

        // WHERE 절을 사용하여 특정 사용자의 정보를 업데이트
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        db.update(TABLE_USERS, values, selection, selectionArgs);
        db.close();
    }





    // 중복 체크 메서드
    public boolean isUsernameAvailable(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        boolean available = (cursor != null && cursor.getCount() == 0);

        if (cursor != null) {
            cursor.close();
        }
        return available;
    }
}
