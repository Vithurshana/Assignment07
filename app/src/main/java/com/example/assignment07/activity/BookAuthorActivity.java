package com.example.assignment07.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.assignment07.MainActivity;
import com.example.assignment07.R;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.form.BookAuthorForm;
import com.example.assignment07.form.MemberForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class BookAuthorActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_author);
        DatabaseHandler handler = new DatabaseHandler(this);
        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        fab = findViewById(R.id.bookAuthorFab);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> bookAuthors = fetchHandler.getAllBookAuthor();

        Intent authorFormIntent = new Intent(this, BookAuthorForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(bookAuthors, authorFormIntent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(authorFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });
    }

    private void tableLayout(ArrayList<Map<String, String>> authorList, Intent bookAuthorFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.bookTbContent);
        for (int i = 0; i < authorList.size(); i++) {
            Map<String, String> author = authorList.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView bookId = getSampleTextView(this, author.get(Constant.BOOK_ID), 4);
            row.addView(bookId);
            TextView name = getSampleTextView(this, author.get(Constant.AUTHOR_NAME), 4);
            row.addView(name);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", author.get(Constant.BOOK_ID));
                    bookAuthorFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    bookAuthorFormIntent.putExtra(Constant.BOOK_ID, author.get(Constant.BOOK_ID));
                    bookAuthorFormIntent.putExtra(Constant.AUTHOR_NAME, author.get(Constant.AUTHOR_NAME));
                    startActivity(bookAuthorFormIntent);
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