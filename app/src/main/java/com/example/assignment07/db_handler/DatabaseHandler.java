package com.example.assignment07.db_handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment07.utills.Constant;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String bookQuery = "CREATE TABLE " + Constant.BOOK_TABLE_NAME + " ("
                + Constant.BOOK_ID + " VARCHAR(13) PRIMARY KEY, "
                + Constant.BOOK_TITLE + " VARCHAR(30)"
                + Constant.PUBLISHER_NAME + " VARCHAR(30))";

        db.execSQL(bookQuery);
    }

    public void createTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DROP TABLE "+ Constant.BOOK_TABLE_NAME;
        String bookQuery = "CREATE TABLE " + Constant.BOOK_TABLE_NAME + " ("
                + Constant.BOOK_ID + " VARCHAR(13) PRIMARY KEY, "
                + Constant.BOOK_TITLE + " VARCHAR(30),"
                + Constant.PUBLISHER_NAME + " VARCHAR(30))";

//        db.execSQL(deleteQuery);
        db.execSQL(bookQuery);
    }

    public void addBook(String bookId, String bookTitle, String publisherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.BOOK_ID, bookId);
        values.put(Constant.BOOK_TITLE, bookTitle);
        values.put(Constant.PUBLISHER_NAME, publisherName);

        db.insert(Constant.BOOK_TABLE_NAME, null, values);
        db.close();
    }

    public boolean isBookIdExistsInParticularTable(String tableName, String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." +Constant.BOOK_ID  + ") FROM " + tableName + " b WHERE b." + Constant.BOOK_ID + " = '" + bookId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String value = cursor.getString(0);
            if (Integer.parseInt(value) > 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void updateBook(String originalBookId, String bookId, String bookTitle, String publisherName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.BOOK_ID, bookId);
        values.put(Constant.BOOK_TITLE, bookTitle);
        values.put(Constant.PUBLISHER_NAME, publisherName);

        db.update(Constant.BOOK_TABLE_NAME, values, Constant.BOOK_ID + "=?", new String[]{originalBookId});
        db.close();
    }

    public void deleteBook(String originalBookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.BOOK_TABLE_NAME, Constant.BOOK_ID + "=?", new String[]{originalBookId});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
