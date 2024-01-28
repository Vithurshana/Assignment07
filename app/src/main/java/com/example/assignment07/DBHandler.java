package com.example.assignment07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = DBHandler.class.getSimpleName();

    private static final String COMA = ",";

    private static final String DB_NAME = "library_db.db";

    private static final int DB_VERSION = 2;

    private static final String BOOK_TABLE = "books";

    private static final String BOOK_ID = "BOOK_ID";

    private static final String TITLE = "TITLE";

    private static final String PUBLISHER_NAME = "PUBLISHER_NAME ";

    private static final String PUBLISHER_TABLE = "publishers";

    private static final String NAME = "NAME";

    private static final String ADDRESS = "ADDRESS";

    private static final String PHONE = "PHONE";

    private static final String BRANCH_TABLE = "branches";

    private static final String BRANCH_ID = "BRANCHID";

    private static final String BRANCH_NAME = "BRANCHNAME";

    private static final String BRANCH_ADDRESS = "BRANCHADDRESS";

    private static final String MEMBER_TABLE = "members";

    private static final String CARD_NO = "CARD_NO";

    private static final String MEMBER_NAME = "MEMBERNAME";

    private static final String MEMBER_ADDRESS = "MEMBERADDRESS";

    private static final String MEMBER_PHONE = "MEMBERPHONE";

    private static final String UNPAID_DUES = "UNPAID_DUES";

    private static final String BOOK_AUTHOR_TABLE = "Book_Author";

    private static final String AUTHOR_NAME = "AUTHOR_NAME";

    private static final String BOOK_COPY_TABLE = "Book_Copy";

    private static final String ACCESS_NO = "ACCESS_NO";

    private static final String BOOK_LOAN_TABLE = "Book_Loan";

    private static final String DATE_OUT = "DATE_OUT";

    private static final String DATE_DUE = "DATE_DUE";

    private static final String DATE_RETURNED = "DATE_RETURNED";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String bookQuery = "CREATE TABLE " + BOOK_TABLE + " ("
                + BOOK_ID + " VARCHAR(13) PRIMARY KEY, "
                + TITLE + " VARCHAR(30),"
                + PUBLISHER_NAME + " VARCHAR(20))";

        String publisherQuery = "CREATE TABLE " + PUBLISHER_TABLE + " ("
                + PUBLISHER_NAME + " VARCHAR(20) PRIMARY KEY, "
                + ADDRESS + " VARCHAR(30),"
                + PHONE + " VARCHAR(10))";

        String branchQuery = "CREATE TABLE " + BRANCH_TABLE + " ("
                + BRANCH_ID + " VARCHAR(5) PRIMARY KEY, "
                + BRANCH_NAME + " VARCHAR(20),"
                + BRANCH_ADDRESS + " VARCHAR(30))";

        String memberQuery = "CREATE TABLE " + MEMBER_TABLE + " ("+ CARD_NO + " VARCHAR(10) PRIMARY KEY, " + MEMBER_NAME + " VARCHAR(20),"
                + MEMBER_ADDRESS + " VARCHAR(30)," + MEMBER_PHONE + " VARCHAR(10)," + UNPAID_DUES + " DOUBLE(5,2))";

        String bookAuthorQuery = "CREATE TABLE " + BOOK_AUTHOR_TABLE + " ("
                + BOOK_ID + " VARCHAR(13), "
                + AUTHOR_NAME + " VARCHAR(20),"
                + "PRIMARY KEY (" + BOOK_ID + COMA + AUTHOR_NAME + "),"
                + "FOREIGN KEY (" + BOOK_ID + ") REFERENCES "
                + BOOK_TABLE + "(" + BOOK_ID + "))";

        String bookCopyQuery = "CREATE TABLE " + BOOK_COPY_TABLE + " ("
                + BOOK_ID + " VARCHAR(13), "
                + BRANCH_ID + " VARCHAR(5),"
                + ACCESS_NO + " VARCHAR(5),"
                + "PRIMARY KEY (" + ACCESS_NO + COMA + BRANCH_ID + "), "
                + "FOREIGN KEY (" + BOOK_ID + ") REFERENCES "
                + BOOK_TABLE + "(" + BOOK_ID + "), "
                + "FOREIGN KEY (" + BRANCH_ID + ") REFERENCES "
                + BRANCH_TABLE + "(" + BRANCH_ID + "))";

        String bookLoanQuery = "CREATE TABLE " + BOOK_LOAN_TABLE + " ("
                + ACCESS_NO + " VARCHAR(5),"
                + BRANCH_ID + " VARCHAR(5),"
                + CARD_NO + " VARCHAR(5),"
                + DATE_OUT + " DATE,"
                + DATE_DUE + " DATE,"
                + DATE_RETURNED + " DATE,"
                + "PRIMARY KEY (" + ACCESS_NO + COMA + BRANCH_ID + COMA
                + CARD_NO + COMA + DATE_OUT + "), "
                + "FOREIGN KEY (" + ACCESS_NO + COMA + BRANCH_ID + ") REFERENCES "
                + BOOK_COPY_TABLE + "(" + ACCESS_NO + COMA + BRANCH_ID + "), "
                + "FOREIGN KEY (" + CARD_NO + ") REFERENCES "
                + MEMBER_TABLE + "(" + CARD_NO + "), "
                + "FOREIGN KEY (" + BRANCH_ID + ") REFERENCES "
                + BRANCH_TABLE + "(" + BRANCH_ID + "))";



        db.execSQL(bookQuery);
        db.execSQL(publisherQuery);
        db.execSQL(branchQuery);
        db.execSQL(memberQuery);
        db.execSQL(bookAuthorQuery);
        db.execSQL(bookCopyQuery);
        db.execSQL(bookLoanQuery);
    }


    public void addNewBook(String bookID, String bookTitle, String PublisherName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BOOK_ID, bookID);
        values.put(TITLE, bookTitle);
        values.put(PUBLISHER_NAME, PublisherName);

        db.insert(BOOK_TABLE, null, values);
        db.close();
    }

    public boolean isBookIdExists(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + BOOK_ID + ") FROM " + BOOK_TABLE + " b WHERE b." + BOOK_ID + " = '" + bookId + "'";
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

    public boolean isBookIdExistsInParticularTable(String tableName, String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + BOOK_ID + ") FROM " + tableName + " b WHERE b." + BOOK_ID + " = '" + bookId + "'";
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

    public ArrayList<ArrayList> getAllBook() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOK_TABLE, null);
        ArrayList<ArrayList> booksList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> bookDetails = new ArrayList<>();
                bookDetails.add(cursor.getString(0));
                bookDetails.add(cursor.getString(1));
                bookDetails.add(cursor.getString(2));
                booksList.add(bookDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booksList;
    }

    public void updateBook(String originalBookId, String bookID, String bookTitle, String PublisherName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BOOK_ID, bookID);
        values.put(TITLE, bookTitle);
        values.put(PUBLISHER_NAME, PublisherName);

        db.update(BOOK_TABLE, values, BOOK_ID + "=?", new String[]{originalBookId});
        db.close();
    }

    public void deleteBook(String originalBookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOK_TABLE, BOOK_ID + "=?", new String[]{originalBookId});
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    public void addNewPublisher(String Name, String Address, String Phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PUBLISHER_NAME, Name);
        values.put(ADDRESS, Address);
        values.put(PHONE, Phone);

        db.insert(PUBLISHER_TABLE, null, values);
        db.close();
    }

    public ArrayList<ArrayList> getAllPublishers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PUBLISHER_TABLE, null);
        ArrayList<ArrayList> publishersList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> publisherDetails = new ArrayList<>();
                publisherDetails.add(cursor.getString(0));
                publisherDetails.add(cursor.getString(1));
                publisherDetails.add(cursor.getString(2));

                publishersList.add(publisherDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return publishersList;
    }

    public boolean isPublisherExists(String publisherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(p." + PUBLISHER_NAME + ") FROM " + PUBLISHER_TABLE
                + " p WHERE p." + PUBLISHER_NAME + " = '" + publisherName + "'";
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

        values.put(PUBLISHER_NAME, Name);
        values.put(ADDRESS, Address);
        values.put(PHONE, Phone);

        db.update(PUBLISHER_TABLE, values, PUBLISHER_NAME + "=?", new String[]{originalName});
        db.close();
    }

    public void deletePublisher(String originalName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PUBLISHER_TABLE, PUBLISHER_NAME + "=?", new String[]{originalName});
        db.close();
    }
//-----------------------------------------------------------------------------------------------------------------

    public void addNewBranch(String BranchID, String BranchName, String BranchAddress) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BRANCH_ID, BranchID);
        values.put(BRANCH_NAME, BranchName);
        values.put(BRANCH_ADDRESS, BranchAddress);

        db.insert(BRANCH_TABLE, null, values);
        db.close();
    }

    public ArrayList<ArrayList> getAllBranches() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BRANCH_TABLE, null);
        ArrayList<ArrayList> branchesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> branchDetails = new ArrayList<>();
                branchDetails.add(cursor.getString(0));
                branchDetails.add(cursor.getString(1));
                branchDetails.add(cursor.getString(2));

                branchesList.add(branchDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return branchesList;
    }


    public boolean isBranchIdExists(String branchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + BRANCH_ID + ") FROM " + BRANCH_TABLE + " b WHERE b." + BRANCH_ID + " = '" + branchId + "'";
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

    public boolean isBranchIdExistsInParticular(String tableName, String branchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + BRANCH_ID + ") FROM " + tableName + " b WHERE b." + BRANCH_ID + " = '" + branchId + "'";
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

    public void updateBranch(String originalBranchId, String BranchID, String BranchName, String BranchAddress) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BRANCH_ID, BranchID);
        values.put(BRANCH_NAME, BranchName);
        values.put(BRANCH_ADDRESS, BranchAddress);

        db.update(BRANCH_TABLE, values, BRANCH_ID + "=?", new String[]{originalBranchId});
        db.close();
    }

    public void deleteBranch(String originalBranchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BRANCH_TABLE, BRANCH_ID + "=?", new String[]{originalBranchId});
        db.close();
    }

//    ---------------------------------------------------------------------------------------------------------------------

    public void addNewMember(String cardNo, String name, String address, String phone, Double unpaidDues) {

//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();

        String sql = "INSERT INTO " + MEMBER_TABLE +" ("+CARD_NO+", "+MEMBER_NAME+", "+MEMBER_ADDRESS
                +", "+MEMBER_PHONE+", "+UNPAID_DUES+") VALUES ('"+cardNo+"','"+name+"','"
                +address+"','"+phone+"', "+unpaidDues+")";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql); // or use db.insert(), db.update(), or db.delete()
            Log.d(TAG, "Insertion successful");
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting row", e);
        }
    }

    public ArrayList<ArrayList> getAllMembers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MEMBER_TABLE, null);
        ArrayList<ArrayList> membersList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> memberDetails = new ArrayList<>();
                memberDetails.add(cursor.getString(0));
                memberDetails.add(cursor.getString(1));
                memberDetails.add(cursor.getString(2));
                memberDetails.add(cursor.getString(3));
                memberDetails.add(cursor.getString(4));

                membersList.add(memberDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return membersList;
    }

    public boolean isCardNoExists(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(m." + CARD_NO + ") FROM " + MEMBER_TABLE + " m WHERE m." + CARD_NO + " = '" + cardNo + "'";
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

    public boolean isCardNoExistsInBookLoan(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(m." + CARD_NO + ") FROM " + BOOK_LOAN_TABLE + " m WHERE m." + CARD_NO + " = '" + cardNo + "'";
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

    public void updateMember(String originalCardNo, String cardNo, String name, String address, String phone, Double unpaidDues) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CARD_NO, cardNo);
        values.put(MEMBER_NAME, name);
        values.put(MEMBER_ADDRESS,address);
        values.put(MEMBER_PHONE,phone);
        values.put(UNPAID_DUES,unpaidDues);

        db.update(MEMBER_TABLE, values, CARD_NO + "=?", new String[]{originalCardNo});
        db.close();
    }

    public void deleteMember(String originalCardNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEMBER_TABLE, CARD_NO + "=?", new String[]{originalCardNo});
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    public void addNewBookAuthor(String bookId, String authorName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BOOK_ID, bookId);
        values.put(AUTHOR_NAME, authorName);
        db.insert(BOOK_AUTHOR_TABLE, null, values);
        db.close();
    }

    public ArrayList<ArrayList> getAllBookAuthor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOK_AUTHOR_TABLE, null);
        ArrayList<ArrayList> booAuthorsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> bookAuthorDetails = new ArrayList<>();
                bookAuthorDetails.add(cursor.getString(0));
                bookAuthorDetails.add(cursor.getString(1));

                booAuthorsList.add(bookAuthorDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booAuthorsList;
    }

    public boolean isBookIdAndAuthorNameExists(String bookId, String authorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(a." + BOOK_ID + " AND a."+AUTHOR_NAME+") FROM "
                + BOOK_AUTHOR_TABLE + " a WHERE a." + BOOK_ID + " = '" + bookId + "' AND a."
                + AUTHOR_NAME + "='" + authorName + "'";
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
        String query = "SELECT COUNT(a." + ACCESS_NO + " AND a."+BRANCH_ID+") FROM "
                + BOOK_LOAN_TABLE + " a WHERE a." + ACCESS_NO + " = '" + accessNo + "' AND a."
                + BRANCH_ID + "='" + branchId + "'";
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
        String updateQuery = "UPDATE " + BOOK_AUTHOR_TABLE +" SET "+BOOK_ID+"='"+bookId+"', "
                +AUTHOR_NAME+"='"+authorName+"' WHERE "+BOOK_ID
                +"='"+originalBookId+"'AND " +AUTHOR_NAME+"='"+originalBookAuthorName+"'";
        db.execSQL(updateQuery);
        db.close();
    }

    public void deleteBookAuthor(String originalBookId, String originalAuthorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+BOOK_AUTHOR_TABLE+" WHERE "+BOOK_ID+"='"+originalBookId
                +"' AND "+AUTHOR_NAME+"='"+originalAuthorName+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    public void addNewBookCopy(String bookId, String branchId, String accessNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BOOK_ID, bookId);
        values.put(BRANCH_ID, branchId);
        values.put(ACCESS_NO, accessNo);
        db.insert(BOOK_COPY_TABLE, null, values);
        db.close();
    }

    public ArrayList<ArrayList> getAllBookCopy() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOK_COPY_TABLE, null);
        ArrayList<ArrayList> BookCopiesList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> bookCopyDetails = new ArrayList<>();
                bookCopyDetails.add(cursor.getString(0));
                bookCopyDetails.add(cursor.getString(1));
                bookCopyDetails.add(cursor.getString(2));

                BookCopiesList.add(bookCopyDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return BookCopiesList;
    }

    public boolean isAccessNoExists(String accessNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + ACCESS_NO + ") FROM " + BOOK_COPY_TABLE + " b WHERE b." + ACCESS_NO + " = '" + accessNo + "'";
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
        String query = "SELECT COUNT(b." + ACCESS_NO + " AND b."+BRANCH_ID+") FROM "
                + BOOK_COPY_TABLE + " b WHERE b." + ACCESS_NO + " = '" + accessNo + "' AND b."
                + BRANCH_ID + "='" + branchId + "'";
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
        String updateQuery = "UPDATE " + BOOK_COPY_TABLE +" SET "+BOOK_ID+"='"+bookId+"', "
                +BRANCH_ID+"='"+branchId+"', "+ACCESS_NO+"='"+accessNo+"' WHERE "+BOOK_ID
                +"='"+originalBranchId+"'AND " +ACCESS_NO+"='"+originalAccessNo+"'";
        db.execSQL(updateQuery);
        db.close();
    }

    public void deleteBookCopy(String originalBranchId, String originalAccessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+BOOK_COPY_TABLE+" WHERE "+BRANCH_ID+"='"+originalBranchId
                +"' AND "+ACCESS_NO+"='"+originalAccessNo+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    public void addNewBookLoan(String accessNo, String branchId, String cardNo, Date dateOut,
                               Date dateDue, Date dateReturned) {

        String sql = "INSERT INTO " + BOOK_LOAN_TABLE +" ("+ACCESS_NO+", "+BRANCH_ID+", "+CARD_NO
                +", "+DATE_OUT+", "+DATE_DUE+", "+DATE_RETURNED+") VALUES ('"+accessNo+"','"+branchId+"','"
                +cardNo+"', '"+dateOut+"', '"+dateDue+"', '"+dateReturned+"')";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql); // or use db.insert(), db.update(), or db.delete()
            Log.d(TAG, "Insertion successful");
            db.close();
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting row", e);
        }

    }

    public ArrayList<ArrayList> getAllBookLoans() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOK_LOAN_TABLE, null);
        ArrayList<ArrayList> bookLoansList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> bookLoanDetails = new ArrayList<>();
                bookLoanDetails.add(cursor.getString(0));
                bookLoanDetails.add(cursor.getString(1));
                bookLoanDetails.add(cursor.getString(2));
                bookLoanDetails.add(cursor.getString(3));
                bookLoanDetails.add(cursor.getString(4));
                bookLoanDetails.add(cursor.getString(5));

                bookLoansList.add(bookLoanDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookLoansList;
    }

    public boolean isAccessNoAndBranchIdAndCardNoAndDateOutExists(String accessNo, String branchId,
                                                                  String cardNo, Date dateOut) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(b." + ACCESS_NO + " AND b."+BRANCH_ID+" AND b." + CARD_NO
                + " AND b."+DATE_OUT+") FROM " + BOOK_LOAN_TABLE + " b WHERE b." + ACCESS_NO
                + " = '" + accessNo + "' AND b." + BRANCH_ID + "='" + branchId + "' AND b."
                + CARD_NO + " = '" + cardNo + "' AND b." + DATE_OUT + "='" + dateOut + "'";
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
        String updateQuery = "UPDATE " + BOOK_LOAN_TABLE +" SET "+ACCESS_NO+"='"+accessNo+"', "
                +BRANCH_ID+"='"+branchId+"', "+CARD_NO+"='"+cardNo+"', "+DATE_OUT+"='"+dateOut+"', "
                +DATE_DUE+"='"+dateDue+"', "+DATE_RETURNED+"='"+dateReturned+"' WHERE "+ACCESS_NO
                +"='"+originalAccessNo+"' AND " +BRANCH_ID+"='"+originalBranchId+"' AND "+CARD_NO+"='"
                +originalCardNo+"' AND "+DATE_OUT+"='"+originalDateOut+"'";
        db.execSQL(updateQuery);
        db.close();

    }

    public void deleteBookLoan(String accessNo, String branchId,String cardNo, Date dateOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+BOOK_LOAN_TABLE+" WHERE "+ACCESS_NO+"='"+accessNo
                +"' AND "+BRANCH_ID+"='"+branchId+"' AND "+CARD_NO+"='"+cardNo+"' AND "+DATE_OUT+"='"+dateOut+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

    //    ---------------------------------------------------------------------------------------------------------------------

    // we have created a new method for reading all the courses.
    public ArrayList<MemberModel> readMember() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorMembers = db.rawQuery("SELECT * FROM " + MEMBER_TABLE, null);

        // on below line we are creating a new array list.
        ArrayList<MemberModel> MemberModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorMembers.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                MemberModalArrayList.add(new MemberModel(cursorMembers.getString(1),
                        cursorMembers.getString(4),
                        cursorMembers.getString(2),
                        cursorMembers.getString(3)));
            } while (cursorMembers.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorMembers.close();
        return MemberModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BRANCH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_AUTHOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_COPY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_LOAN_TABLE);
        onCreate(db);
    }

}

