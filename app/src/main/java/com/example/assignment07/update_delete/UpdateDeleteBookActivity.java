package com.example.assignment07.update_delete;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.activity.Book;
import com.example.assignment07.regex.Regex;

import java.util.ArrayList;

public class UpdateDeleteBookActivity extends AppCompatActivity {
    private EditText edtBookId;
    private EditText edtBookTitle;
    private EditText edtBookPublisher;
    private Button updateButton;
    private Button deleteButton;
    private static final String BOOK_AUTHOR_TABLE = "Book_Author";
    private static final String BOOK_COPY_TABLE = "Book_Copy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_book);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");

        edtBookId = (EditText) findViewById(R.id.edtBookId);
        edtBookTitle = (EditText) findViewById(R.id.edtBookTitle);
        edtBookPublisher = (EditText) findViewById(R.id.edtBookPublisher);

        updateButton = findViewById(R.id.btnUpdateBook);
        deleteButton = findViewById(R.id.btnDeleteBook);

        edtBookId.setText(details.get(0));
        edtBookTitle.setText(details.get(1));
        edtBookPublisher.setText(details.get(2));

        updateButton.setOnClickListener(v -> {
            DBHandler dbHandler = new DBHandler(this);
            String newBookId = edtBookId.getText().toString();
            String newBookTitle = edtBookTitle.getText().toString();
            String newBookPublisher = edtBookPublisher.getText().toString();

            if (newBookId.isEmpty() && newBookTitle.isEmpty() && newBookPublisher.isEmpty()) {
                Toast.makeText(UpdateDeleteBookActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newBookTitle.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                edtBookPublisher.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookActivity.this, "Please enter valid title",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!newBookPublisher.matches(Regex.TEXT_REGEX)) {
                edtBookPublisher.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookActivity.this, "Please enter valid publisher name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            boolean isBookIdAlreadyExists = false;
            if (!details.get(0).equals(newBookId)) {
                isBookIdAlreadyExists = dbHandler.isBookIdExists(newBookId);
            }
            if (!isBookIdAlreadyExists) {
                dbHandler.updateBook(details.get(0), newBookId, newBookTitle, newBookPublisher);
                Toast.makeText(UpdateDeleteBookActivity.this, "Book has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteBookActivity.this, Book.class);
                startActivity(newIntent);
            } else {
                edtBookId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookActivity.this, "Book ID already exists, Please try again",
                        Toast.LENGTH_LONG).show();
            }

        });

        deleteButton.setOnClickListener(v -> {
            DBHandler dbHandler = new DBHandler(this);
            String bookId = details.get(0);
            boolean isBookIdIsUsedInBookAuthor = dbHandler.isBookIdExistsInParticularTable(BOOK_AUTHOR_TABLE, bookId);
            boolean isBookIdIsUsedInBookCopy = dbHandler.isBookIdExistsInParticularTable(BOOK_COPY_TABLE, bookId);
            if (!isBookIdIsUsedInBookAuthor && !isBookIdIsUsedInBookCopy){
                dbHandler.deleteBook(bookId);
                Toast.makeText(UpdateDeleteBookActivity.this, "Book is deleted successfully",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookActivity.this, Book.class);
                startActivity(newIntent);
            } else if (isBookIdIsUsedInBookAuthor) {
                Toast.makeText(UpdateDeleteBookActivity.this, "Can't delete the Book. It's used in " + BOOK_AUTHOR_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookActivity.this, Book.class);
                startActivity(newIntent);
            } else {
                Toast.makeText(UpdateDeleteBookActivity.this, "Can't delete the Book. It's used in " + BOOK_COPY_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookActivity.this, Book.class);
                startActivity(newIntent);
            }

        });
    }
}