package com.example.assignment07.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookAuthorActivity;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.util.Map;

public class BookAuthorForm extends AppCompatActivity {
    private String formAction;
    private EditText edtBookId;
    private EditText editAuthorName;

    private String bookId = null;
    private String authorName = null;

    private DatabaseHandler databaseHandler;
    private Intent bookAuthorActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_author_form);

        edtBookId = findViewById(R.id.editBookId);
        editAuthorName = findViewById(R.id.editAuthorName);
        Button btnBookFormAction = findViewById(R.id.btnBookFormAction);
        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);
        ImageButton backBtn = findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(BookAuthorForm.this);
        databaseHandler = new DatabaseHandler(BookAuthorForm.this);
        bookAuthorActivityIntent = new Intent(BookAuthorForm.this, BookAuthorActivity.class);
        Intent bookFormIntent = getIntent();
        formAction = bookFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDeleteBook.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_book_form);
            bookId = bookFormIntent.getStringExtra(Constant.BOOK_ID);
            Map<String, String> authorData = fetchDatabaseHandler.getBookAuthorByBookIdAndName(bookId, authorName);
            edtBookId.setText(authorData.get(Constant.BOOK_ID));
            editAuthorName.setText(authorData.get(Constant.AUTHOR_NAME));
            btnBookFormAction.setText(R.string.update_book);
        }
        btnBookFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(bookAuthorActivityIntent);
            }
        });
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteBookAuthor(bookId, authorName);
                startActivity(bookAuthorActivityIntent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isExists = databaseHandler.isBookIdAndAuthorNameExists(edtBookId.getText().toString(), editAuthorName.getText().toString());
                if (isExists) {
                    Toast.makeText(BookAuthorForm.this, "The Book Author is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewBookAuthor(edtBookId.getText().toString(), editAuthorName.getText().toString());
                    startActivity(bookAuthorActivityIntent);
                }

            } else {
                boolean isBookIdExists = databaseHandler.isBookIdExistsInParticularTable(Constant.BOOK_TABLE_NAME, edtBookId.getText().toString());
                if (!bookId.equals(edtBookId.getText().toString()) && !authorName.equals(editAuthorName.getText().toString()) && isBookIdExists) {
                    Toast.makeText(BookAuthorForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBookAuthor(bookId, authorName, edtBookId.getText().toString(), editAuthorName.getText().toString());
                    startActivity(bookAuthorActivityIntent);
                }

            }
        }
    };
}