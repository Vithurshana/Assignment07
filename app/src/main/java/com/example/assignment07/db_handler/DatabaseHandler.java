package com.example.assignment07.db_handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.assignment07.utills.Constant;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();
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

        String publisherQuery = "CREATE TABLE " + Constant.PUBLISHER_TABLE_NAME + " ("
                + Constant.PUBLISHER_NAME + " VARCHAR(20) PRIMARY KEY, "
                + Constant.ADDRESS + " VARCHAR(30),"
                + Constant.PHONE + " VARCHAR(10))";

        String bookAuthorQuery = "CREATE TABLE " + Constant.BOOK_AUTHOR_TABLE_NAME  + " ("
                + Constant.BOOK_ID  + " VARCHAR(13), "
                + Constant.AUTHOR_NAME  + " VARCHAR(20),"
                + "PRIMARY KEY (" + Constant.BOOK_ID + Constant.COMA + Constant.AUTHOR_NAME + "))";
//                + "FOREIGN KEY (" + BOOK_ID + ") REFERENCES "
//                + BOOK_TABLE + "(" + BOOK_ID + "))";

        String bookCopyQuery = "CREATE TABLE " + Constant.BOOK_COPY_TABLE_NAME + " ("
                + Constant.BOOK_ID + " VARCHAR(13), "
                + Constant.BRANCH_ID + " VARCHAR(5),"
                + Constant.ACCESS_NUMBER + " VARCHAR(5),"
                + "PRIMARY KEY (" + Constant.ACCESS_NUMBER + Constant.COMA + Constant.BRANCH_ID + "))";
//                + "FOREIGN KEY (" + Constant.BOOK_ID + ") REFERENCES "
//                + Constant.BOOK_TABLE_NAME  + "(" + Constant.BOOK_ID + "), "
//                + "FOREIGN KEY (" + Constant.BRANCH_ID + ") REFERENCES "
//                + Constant.BRANCH_TABLE_NAME  + "(" + Constant.BRANCH_ID + "))";

        String bookLoanQuery = "CREATE TABLE " + Constant.BOOK_LOAN_TABLE_NAME + " ("
                + Constant.ACCESS_NUMBER + " VARCHAR(5),"
                + Constant.BRANCH_ID + " VARCHAR(5),"
                + Constant.CARD_NUMBER + " VARCHAR(5),"
                + Constant.LOAN_DATE_OUT + " DATE,"
                + Constant.LOAN_DATE_DUE + " DATE,"
                + Constant.LOAN_DATE_RETURN + " DATE,"
                + "PRIMARY KEY (" + Constant.ACCESS_NUMBER + Constant.COMA + Constant.BRANCH_ID + Constant.COMA
                + Constant.CARD_NUMBER + Constant.COMA + Constant.LOAN_DATE_OUT + ")) ";
//                + "FOREIGN KEY (" + Constant.ACCESS_NUMBER + Constant.COMA + Constant.BRANCH_ID + ") REFERENCES "
//                + Constant.BOOK_COPY_TABLE_NAME + "(" + Constant.ACCESS_NUMBER + Constant.COMA + Constant.BRANCH_ID + "), "
//                + "FOREIGN KEY (" + Constant.CARD_NUMBER + ") REFERENCES "
//                + Constant.MEMBER_tABLE_NAME + "(" + Constant.CARD_NUMBER + "), "
//                + "FOREIGN KEY (" + Constant.BRANCH_ID + ") REFERENCES "
//                + Constant.BRANCH_tABLE_NAME + "(" + Constant.BRANCH_ID + "))";

//        db.execSQL(deleteQuery);
        db.execSQL(bookQuery);
        db.execSQL(publisherQuery);
//        db.execSQL(branchQuery);
//        db.execSQL(memberQuery);
        db.execSQL(bookAuthorQuery);
        db.execSQL(bookCopyQuery);
        db.execSQL(bookLoanQuery);
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



    public void addNewPublisher(String Name, String Address, String Phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.PUBLISHER_NAME, Name);
        values.put(Constant.ADDRESS, Address);
        values.put(Constant.PHONE, Phone);

        db.insert(Constant.PUBLISHER_TABLE_NAME, null, values);
        db.close();
    }



    public boolean isPublisherExists(String publisherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(p." + Constant.PUBLISHER_NAME + ") FROM " + Constant.PUBLISHER_NAME
                + " p WHERE p." + Constant.PUBLISHER_NAME + " = '" + publisherName + "'";
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

    public void updatePublisher(String originalName, String Name, String Address, String Phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.PUBLISHER_NAME, Name);
        values.put(Constant.ADDRESS, Address);
        values.put(Constant.PHONE, Phone);

        db.update(Constant.PUBLISHER_TABLE_NAME, values, Constant.PUBLISHER_NAME + "=?", new String[]{originalName});
        db.close();
    }

    public void deletePublisher(String originalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.PUBLISHER_TABLE_NAME, Constant.PUBLISHER_NAME + "=?", new String[]{originalName});
        db.close();
    }

//    ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void addNewBookAuthor(String bookId, String authorName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.BOOK_ID, bookId);
        values.put(Constant.AUTHOR_NAME, authorName);
        db.insert(Constant.BOOK_AUTHOR_TABLE_NAME, null, values);
        db.close();
    }



    public boolean isBookIdAndAuthorNameExists(String bookId, String authorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(a." + Constant.BOOK_ID + " AND a."+Constant.AUTHOR_NAME+") FROM "
                + Constant.BOOK_AUTHOR_TABLE_NAME + " a WHERE a." + Constant.BOOK_ID + " = '" + bookId + "' AND a."
                + Constant.AUTHOR_NAME + "='" + authorName + "'";
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

    public boolean isAccessNoAndBranchIdExistsInBookLoan(String accessNo, String branchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(a." + Constant.ACCESS_NUMBER + " AND a."+Constant.BRANCH_ID+") FROM "
                + Constant.BOOK_LOAN_TABLE_NAME + " a WHERE a." + Constant.ACCESS_NUMBER + " = '" + accessNo + "' AND a."
                + Constant.BRANCH_ID + "='" + branchId + "'";
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

    public void updateBookAuthor(String originalBookId, String originalBookAuthorName, String bookId,
                                 String authorName) {

        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + Constant.BOOK_AUTHOR_TABLE_NAME +" SET "+Constant.BOOK_ID+"='"+bookId+"', "
                +Constant.AUTHOR_NAME+"='"+authorName+"' WHERE "+Constant.BOOK_ID
                +"='"+originalBookId+"'AND " +Constant.AUTHOR_NAME+"='"+originalBookAuthorName+"'";
        db.execSQL(updateQuery);
        db.close();
    }

    public void deleteBookAuthor(String originalBookId, String originalAuthorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+Constant.BOOK_AUTHOR_TABLE_NAME+" WHERE "+Constant.BOOK_ID+"='"+originalBookId
                +"' AND "+Constant.AUTHOR_NAME+"='"+originalAuthorName+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    public void addNewBookCopy(String bookId, String branchId, String accessNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constant.BOOK_ID, bookId);
        values.put(Constant.BRANCH_ID, branchId);
        values.put(Constant.ACCESS_NUMBER, accessNo);
        db.insert(Constant.BOOK_COPY_TABLE_NAME, null, values);
        db.close();
    }



    public boolean isAccessNoExists(String accessNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + Constant.ACCESS_NUMBER + ") FROM " + Constant.BOOK_COPY_TABLE_NAME + " b WHERE b." + Constant.ACCESS_NUMBER + " = '" + accessNo + "'";
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

    public boolean isAccessNoAndBranchIdExists(String accessNo, String branchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + Constant.ACCESS_NUMBER + " AND b."+Constant.BRANCH_ID+") FROM "
                + Constant.BOOK_COPY_TABLE_NAME + " b WHERE b." + Constant.ACCESS_NUMBER + " = '" + accessNo + "' AND b."
                + Constant.BRANCH_ID + "='" + branchId + "'";
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

    public void updateBookCopy(String originalBranchId, String originalAccessNo, String bookId,
                               String branchId, String accessNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + Constant.BOOK_COPY_TABLE_NAME +" SET "+Constant.BOOK_ID+"='"+bookId+"', "
                +Constant.BRANCH_ID+"='"+branchId+"', "+Constant.ACCESS_NUMBER+"='"+accessNo+"' WHERE "+Constant.BOOK_ID
                +"='"+originalBranchId+"'AND " +Constant.ACCESS_NUMBER+"='"+originalAccessNo+"'";
        db.execSQL(updateQuery);
        db.close();
    }

    public void deleteBookCopy(String originalBranchId, String originalAccessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+Constant.BOOK_COPY_TABLE_NAME+" WHERE "+Constant.BRANCH_ID+"='"+originalBranchId
                +"' AND "+Constant.ACCESS_NUMBER+"='"+originalAccessNo+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    public void addNewBookLoan(String accessNo, String branchId, String cardNo, Date dateOut,
                               Date dateDue, Date dateReturned) {

        String sql = "INSERT INTO " + Constant.BOOK_LOAN_TABLE_NAME +" ("+Constant.ACCESS_NUMBER+", "+Constant.BRANCH_ID+", "+Constant.CARD_NUMBER
                +", "+Constant.LOAN_DATE_OUT+", "+Constant.LOAN_DATE_DUE+", "+Constant.LOAN_DATE_RETURN+") VALUES ('"+accessNo+"','"+branchId+"','"
                +cardNo+"', '"+dateOut+"', '"+dateDue+"', '"+dateReturned+"')";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql); // or use db.insert(), db.update(), or db.delete()
        Log.d(TAG, "Insertion successful");
        db.close();

    }



    public boolean isAccessNoAndBranchIdAndCardNoAndDateOutExists(String accessNo, String branchId,
                                                                  String cardNo, Date dateOut) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + Constant.ACCESS_NUMBER + " AND b."+Constant.BRANCH_ID+" AND b." + Constant.CARD_NUMBER
                + " AND b."+Constant.LOAN_DATE_OUT+") FROM " + Constant.BOOK_LOAN_TABLE_NAME + " b WHERE b." + Constant.ACCESS_NUMBER
                + " = '" + accessNo + "' AND b." + Constant.BRANCH_ID + "='" + branchId + "' AND b."
                + Constant.CARD_NUMBER + " = '" + cardNo + "' AND b." + Constant.LOAN_DATE_OUT + "='" + dateOut + "'";
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

    public void updateBookLoan(String originalAccessNo, String originalBranchId, String originalCardNo,
                               Date originalDateOut, String accessNo, String branchId, String cardNo,
                               Date dateOut, Date dateDue, Date dateReturned) {

        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + Constant.BOOK_LOAN_TABLE_NAME +" SET "+Constant.ACCESS_NUMBER+"='"+accessNo+"', "
                +Constant.BRANCH_ID+"='"+branchId+"', "+Constant.CARD_NUMBER+"='"+cardNo+"', "+Constant.LOAN_DATE_OUT+"='"+dateOut+"', "
                +Constant.LOAN_DATE_DUE+"='"+dateDue+"', "+Constant.LOAN_DATE_RETURN+"='"+dateReturned+"' WHERE "+Constant.ACCESS_NUMBER
                +"='"+originalAccessNo+"' AND " +Constant.BRANCH_ID+"='"+originalBranchId+"' AND "+Constant.CARD_NUMBER+"='"
                +originalCardNo+"' AND "+Constant.LOAN_DATE_OUT+"='"+originalDateOut+"'";
        db.execSQL(updateQuery);
        db.close();

    }

    public void deleteBookLoan(String accessNo, String branchId,String cardNo, Date dateOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+Constant.BOOK_LOAN_TABLE_NAME+" WHERE "+Constant.ACCESS_NUMBER+"='"+accessNo
                +"' AND "+Constant.BRANCH_ID+"='"+branchId+"' AND "+Constant.CARD_NUMBER+"='"+cardNo+"' AND "+Constant.LOAN_DATE_OUT+"='"+dateOut+"'";
        db.execSQL(deleteQuery);
        db.close();
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
