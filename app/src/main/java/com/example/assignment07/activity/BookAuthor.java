package com.example.assignment07.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.adapter.BookAuthorListViewAdapter;
import com.example.assignment07.insert_data.AddBookAuthor;
import com.example.assignment07.update_delete.UpdateDeleteBookAuthorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class BookAuthor extends AppCompatActivity {
    private ArrayList<ArrayList> list;
    private FloatingActionButton addBookAuthorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_author);
        addBookAuthorButton = findViewById(R.id.bookAuthorAddButton);
        list = getAllBookAuthorDetails();
        ListView listView = findViewById(R.id.bookAuthorListView);
        BookAuthorListViewAdapter bookAuthorListViewAdapter = new BookAuthorListViewAdapter(this, list);
        listView.setAdapter(bookAuthorListViewAdapter);

        addBookAuthorButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookAuthor.this, AddBookAuthor.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> particularBookAuthorDetails = list.get(position);
            Intent intent = new Intent(BookAuthor.this, UpdateDeleteBookAuthorActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ARRAYLIST",(Serializable) particularBookAuthorDetails);
            intent.putExtra("details", bundle);
            startActivity(intent);
        });
    }
    private ArrayList<ArrayList> getAllBookAuthorDetails() {
        DBHandler dbHandler = new DBHandler(this);
        return dbHandler.getAllBookAuthor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = getAllBookAuthorDetails();
        ListView listView = findViewById(R.id.bookAuthorListView);
        BookAuthorListViewAdapter bookAuthorListViewAdapter = new BookAuthorListViewAdapter(this, list);
        listView.setAdapter(bookAuthorListViewAdapter);
    }
}