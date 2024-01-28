package com.example.assignment07.db_handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment07.utills.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchDatabaseHandler extends SQLiteOpenHelper {
    public FetchDatabaseHandler(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public ArrayList<Map<String,String>> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.BOOK_TABLE_NAME, null);
        ArrayList<Map<String,String>> bookList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookMap = new HashMap<>();
                bookMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookMap.put(Constant.BOOK_TITLE, cursor.getString(1));
                bookMap.put(Constant.PUBLISHER_NAME, cursor.getString(2));

                bookList.add(bookMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    public Map<String,String> getBookById(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.BOOK_TABLE_NAME + " WHERE " + Constant.BOOK_ID + "='" + bookId + "';";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Map<String,String>> bookList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookMap = new HashMap<>();
                bookMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookMap.put(Constant.BOOK_TITLE, cursor.getString(1));
                bookMap.put(Constant.PUBLISHER_NAME, cursor.getString(2));

                bookList.add(bookMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList.get(0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
