package com.example.assignment07.utills;

public class Constant {
    public static final String COMA = ",";
    public static final String DB_NAME = "library_db.db";
    public static final int DB_VERSION = 3;
    public static final String FORM_ACTION = "action";
    public static final String CREATE_FORM_ACTION = "create";
    public static final String UPDATE_FORM_ACTION = "update";


    //    BOOKS
    public static final String BOOK_TABLE_NAME = "books";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_TITLE = "book_title";
    public static final String PUBLISHER_NAME = "publisher_name";

//    BOOK LOAN

    public static final String BOOK_LOAN_TABLE_NAME = "book_loans";
    public static final String ACCESS_NUMBER = "access_number";
    public static final String BRANCH_ID = "branch_id";
    public static final String CARD_NUMBER = "card_number";
    public static final String LOAN_DATE_OUT = "loan_data_out";
    public static final String LOAN_DATE_DUE = "loan_data_due";
    public static final String LOAN_DATE_RETURN = "loan_data_return";

    //    PUBLISHER
    public static final String PUBLISHER_TABLE_NAME = "publishers";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";

    public static final String BOOK_AUTHOR_TABLE_NAME = "book_authors";
    public static final String AUTHOR_NAME = "book_authors";

    public static final String BOOK_COPY_TABLE_NAME = "book_copy";

    // BRANCH
    public static final String BRANCH_TABLE_NAME = "branch";
    public static final String BRANCH_NAME = "branch_name";
    public static final String BRANCH_ADDRESS = "branch_address";

    public static final String MEMBER_TABLE_NAME = "member";
    public static final String MEMBER_NAME = "member_name";
    public static final String MEMBER_ADDRESS = "member_address";
    public static final String MEMBER_PHONE = "member_phone";
    public static final String UNPAID_DUES = "unpaid_dues";
}
