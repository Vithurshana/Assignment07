package com.example.assignment07.db_handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment07.utills.Constant;

import java.sql.Date;
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

    public ArrayList<Map<String, String>> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.BOOK_TABLE_NAME, null);
        ArrayList<Map<String, String>> bookList = new ArrayList<>();
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

    public Map<String, String> getBookById(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.BOOK_TABLE_NAME + " WHERE " + Constant.BOOK_ID + "='" + bookId + "';";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Map<String, String>> bookList = new ArrayList<>();
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


    public ArrayList<Map<String, String>> getAllPublishers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.PUBLISHER_TABLE_NAME, null);
        ArrayList<Map<String, String>> publishersList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> publisherMap = new HashMap<>();
                publisherMap.put(Constant.PUBLISHER_NAME, cursor.getString(0));
                publisherMap.put(Constant.ADDRESS, cursor.getString(1));
                publisherMap.put(Constant.PHONE, cursor.getString(2));

                publishersList.add(publisherMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return publishersList;
    }

    public Map<String, String> getPublishersByName(String publisherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.PUBLISHER_TABLE_NAME + " WHERE " + Constant.PUBLISHER_NAME + "='" + publisherName + "'", null);
        ArrayList<Map<String, String>> publishersList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> publisherMap = new HashMap<>();
                publisherMap.put(Constant.PUBLISHER_NAME, cursor.getString(0));
                publisherMap.put(Constant.ADDRESS, cursor.getString(1));
                publisherMap.put(Constant.PHONE, cursor.getString(2));

                publishersList.add(publisherMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return publishersList.get(0);
    }


    public ArrayList<Map<String, String>> getAllBookAuthor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.BOOK_AUTHOR_TABLE_NAME, null);
        ArrayList<Map<String, String>> booAuthorsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookAuthorMap = new HashMap<>();
                bookAuthorMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookAuthorMap.put(Constant.AUTHOR_NAME, cursor.getString(1));

                booAuthorsList.add(bookAuthorMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booAuthorsList;
    }

    public Map<String, String> getBookAuthorByBookIdAndName(String id, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.BOOK_AUTHOR_TABLE_NAME + " WHERE " + Constant.BOOK_ID + "='" + id +
                "' AND " + Constant.AUTHOR_NAME + "='" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Map<String, String>> booAuthorsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookAuthorMap = new HashMap<>();
                bookAuthorMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookAuthorMap.put(Constant.AUTHOR_NAME, cursor.getString(1));

                booAuthorsList.add(bookAuthorMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booAuthorsList.get(0);
    }


    public ArrayList<Map<String, String>> getAllBookCopy() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.BOOK_COPY_TABLE_NAME, null);
        ArrayList<Map<String, String>> BookCopiesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookCopyMap = new HashMap<>();
                bookCopyMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookCopyMap.put(Constant.BRANCH_ID, cursor.getString(1));
                bookCopyMap.put(Constant.ACCESS_NUMBER, cursor.getString(2));

                BookCopiesList.add(bookCopyMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return BookCopiesList;
    }

    public Map<String, String> getBookCopyByAccessNumberAndBranchId(String accessNumber, String branchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.BOOK_COPY_TABLE_NAME + " WHERE " + Constant.ACCESS_NUMBER + "='" + accessNumber +
                "' AND " + Constant.BRANCH_ID + "='" + branchId + "'";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Map<String, String>> BookCopiesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookCopyMap = new HashMap<>();
                bookCopyMap.put(Constant.BOOK_ID, cursor.getString(0));
                bookCopyMap.put(Constant.BRANCH_ID, cursor.getString(1));
                bookCopyMap.put(Constant.ACCESS_NUMBER, cursor.getString(2));

                BookCopiesList.add(bookCopyMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return BookCopiesList.get(0);
    }

    public ArrayList<Map<String, String>> getAllBookLoans() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.BOOK_LOAN_TABLE_NAME, null);
        ArrayList<Map<String, String>> bookLoansList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookLoanMap = new HashMap<>();
                bookLoanMap.put(Constant.ACCESS_NUMBER, cursor.getString(0));
                bookLoanMap.put(Constant.BRANCH_ID, cursor.getString(1));
                bookLoanMap.put(Constant.CARD_NUMBER, cursor.getString(2));
                bookLoanMap.put(Constant.LOAN_DATE_OUT, cursor.getString(3));
                bookLoanMap.put(Constant.LOAN_DATE_DUE, cursor.getString(4));
                bookLoanMap.put(Constant.LOAN_DATE_RETURN, cursor.getString(5));

                bookLoansList.add(bookLoanMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookLoansList;
    }
    public Map<String, String> getBookLoansByAccessNumberAndBranchIdAndCardNumberAndDateOut(String accessNumber, String branchId, String cardNumber, Date dateOut) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.BOOK_COPY_TABLE_NAME + " WHERE " + Constant.ACCESS_NUMBER + "='" + accessNumber +
                "' AND " + Constant.BRANCH_ID + "='" + branchId + "' AND " + Constant.CARD_NUMBER + "='" + cardNumber
                + "' AND " + Constant.LOAN_DATE_OUT + "='" + dateOut + "'";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Map<String, String>> bookLoansList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> bookLoanMap = new HashMap<>();
                bookLoanMap.put(Constant.ACCESS_NUMBER, cursor.getString(0));
                bookLoanMap.put(Constant.BRANCH_ID, cursor.getString(1));
                bookLoanMap.put(Constant.CARD_NUMBER, cursor.getString(2));
                bookLoanMap.put(Constant.LOAN_DATE_OUT, cursor.getString(3));
                bookLoanMap.put(Constant.LOAN_DATE_DUE, cursor.getString(4));
                bookLoanMap.put(Constant.LOAN_DATE_RETURN, cursor.getString(5));

                bookLoansList.add(bookLoanMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookLoansList.get(0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
