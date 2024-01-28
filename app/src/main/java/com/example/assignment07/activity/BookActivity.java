package com.example.assignment07.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.MainActivity;
import com.example.assignment07.R;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.form.BookForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class BookActivity extends AppCompatActivity {
    private FloatingActionButton bookFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        DatabaseHandler handler = new DatabaseHandler(this);
        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        bookFab = findViewById(R.id.bookFab);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> bookList = fetchHandler.getAllBooks();

        Intent bookFormIntent = new Intent(this, BookForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(bookList, bookFormIntent);
        bookFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(bookFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });
    }

    private void tableLayout(ArrayList<Map<String, String>> books, Intent bookFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.bookTbContent);
        for (int i = 0; i < books.size(); i++) {
            Map<String, String> book = books.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView bookId = getSampleTextView(this, book.get(Constant.BOOK_ID), 3);
            row.addView(bookId);
            TextView bookTitle = getSampleTextView(this, book.get(Constant.BOOK_TITLE), 4);
            row.addView(bookTitle);
            TextView publisher = getSampleTextView(this, book.get(Constant.PUBLISHER_NAME), 4);
            row.addView(publisher);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", book.get(Constant.BOOK_ID));
                    bookFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    bookFormIntent.putExtra(Constant.BOOK_ID, book.get(Constant.BOOK_ID));
                    startActivity(bookFormIntent);
                }
            });
            bookTableLayout.addView(row);
        }

    }

    private TextView getSampleTextView(Context context, String text, int weight) {
        TextView textView = new TextView(context);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight
        );
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextColor(Color.WHITE); // Replace with your color resource
        textView.setPadding(10, 10, 10, 10);
        textView.setTextSize(14);
        textView.setGravity(Gravity.START);
        return textView;
    }
}