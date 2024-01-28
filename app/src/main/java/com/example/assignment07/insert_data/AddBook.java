package com.example.assignment07.insert_data;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.regex.Regex;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        EditText bookIdEdit = findViewById(R.id.idEditBookID);
        EditText bookTitleEdit = findViewById(R.id.idEditBookTitle);
        EditText publisherNameEdit = findViewById(R.id.idEdtPublisherName);
        Button addBookBtn = findViewById(R.id.idBtnAddBook);
        DBHandler dbHandler = new DBHandler(AddBook.this);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookID = bookIdEdit.getText().toString();
                String bookTitle = bookTitleEdit.getText().toString();
                String publisherName = publisherNameEdit.getText().toString();

                if (bookID.isEmpty() && bookTitle.isEmpty() && publisherName.isEmpty()) {
                    Toast.makeText(AddBook.this, "Please enter all the data..",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!bookTitle.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    bookTitleEdit.setTextColor(Color.RED);
                    Toast.makeText(AddBook.this, "Please enter valid title",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!publisherName.matches(Regex.TEXT_REGEX)) {
                    publisherNameEdit.setTextColor(Color.RED);
                    Toast.makeText(AddBook.this, "Please enter valid publisher name",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isBookIdAlreadyExists = dbHandler.isBookIdExists(bookID);
                if (!isBookIdAlreadyExists) {
                    dbHandler.addNewBook(bookID, bookTitle, publisherName);
                    Toast.makeText(AddBook.this, "Book has been added.",
                            Toast.LENGTH_SHORT).show();
                    bookIdEdit.setText("");
                    bookTitleEdit.setText("");
                    publisherNameEdit.setText("");
                } else {
                    bookIdEdit.setTextColor(Color.RED);
                    Toast.makeText(AddBook.this, "Book ID already exists, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}