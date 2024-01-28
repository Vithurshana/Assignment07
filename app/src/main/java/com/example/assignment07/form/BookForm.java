package com.example.assignment07.form;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookActivity;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.util.Map;

public class BookForm extends AppCompatActivity {
    private String formAction;
    private EditText edtBookId;
    private EditText edtBootTitle;
    private EditText edtPublisher;

    private String bookId = null;

    private DatabaseHandler databaseHandler;
    private Intent bookActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        edtBookId = findViewById(R.id.editBookId);
        edtBootTitle = findViewById(R.id.editBookTitle);
        edtPublisher = findViewById(R.id.editBookPublisher);
        Button btnBookFormAction = findViewById(R.id.btnBookFormAction);
        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);
        ImageButton backBtn =  findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(BookForm.this);
        databaseHandler = new DatabaseHandler(BookForm.this);
        bookActivityIntent = new Intent(BookForm.this, BookActivity.class);
        Intent bookFormIntent = getIntent();
        formAction = bookFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDeleteBook.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_book_form);
            bookId = bookFormIntent.getStringExtra(Constant.BOOK_ID);
            Map<String, String> bookData = fetchDatabaseHandler.getBookById(bookId);
            edtBookId.setText(bookData.get(Constant.BOOK_ID));
            edtBootTitle.setText(bookData.get(Constant.BOOK_TITLE));
            edtPublisher.setText(bookData.get(Constant.PUBLISHER_NAME));
            btnBookFormAction.setText(R.string.update_book);
        }
        btnBookFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(bookActivityIntent);
            }
        });
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteBook(bookId);
                startActivity(bookActivityIntent);
            }
        });
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isBookIdExists = databaseHandler.isBookIdExistsInParticularTable(Constant.BOOK_TABLE_NAME, edtBookId.getText().toString());
                if (isBookIdExists) {
                    Toast.makeText(BookForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addBook(edtBookId.getText().toString(), edtBootTitle.getText().toString(),
                            edtPublisher.getText().toString());
                    startActivity(bookActivityIntent);
                }

            } else {
                boolean isBookIdExists = databaseHandler.isBookIdExistsInParticularTable(Constant.BOOK_TABLE_NAME, edtBookId.getText().toString());
                if (!bookId.equals(edtBookId.getText().toString()) && isBookIdExists) {
                    Toast.makeText(BookForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBook(bookId, edtBookId.getText().toString(), edtBootTitle.getText().toString(),
                            edtPublisher.getText().toString());
                    startActivity(bookActivityIntent);
                }

            }
        }
    };
}