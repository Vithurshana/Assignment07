package com.example.assignment07.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.adapter.BookCopyListViewAdapter;
import com.example.assignment07.insert_data.AddBookCopy;
import com.example.assignment07.update_delete.UpdateDeleteBookCopyActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class BookCopy extends AppCompatActivity {
    private ArrayList<ArrayList> list;
    private FloatingActionButton addBookCopyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy);
        addBookCopyButton = findViewById(R.id.bookCopyAddButton);
        list = getAllBookCopyDetails();
        ListView listView = findViewById(R.id.bookCopyListView);
        BookCopyListViewAdapter bookCopyListViewAdapter = new BookCopyListViewAdapter(this, list);
        listView.setAdapter(bookCopyListViewAdapter);

        addBookCopyButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookCopy.this, AddBookCopy.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> particularBookCopyDetails = list.get(position);
            Intent intent = new Intent(BookCopy.this, UpdateDeleteBookCopyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ARRAYLIST",(Serializable) particularBookCopyDetails);
            intent.putExtra("details", bundle);
            startActivity(intent);
        });
    }
    private ArrayList<ArrayList> getAllBookCopyDetails() {
        DBHandler dbHandler = new DBHandler(this);
        return dbHandler.getAllBookCopy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = getAllBookCopyDetails();
        ListView listView = findViewById(R.id.bookCopyListView);
        BookCopyListViewAdapter bookCopyListViewAdapter = new BookCopyListViewAdapter(this, list);
        listView.setAdapter(bookCopyListViewAdapter);
    }
}