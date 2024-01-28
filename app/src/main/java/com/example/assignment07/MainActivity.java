package com.example.assignment07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.activity.BookActivity;
import com.example.assignment07.activity.BookLoan;
import com.example.assignment07.activity.BranchActivity;
import com.example.assignment07.db_handler.DatabaseHandler;


public class MainActivity extends AppCompatActivity {

    //    private String fileName = "list.txt";
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DatabaseHandler handler = new DatabaseHandler(this);
//        handler.createTables();
        Button btnBook = findViewById(R.id.BtnBook);

        Intent bookActivityIntent = new Intent(this, BookActivity.class);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookActivityIntent);
            }
        });

//        Button BtnPublisher = findViewById(R.id.BtnPublisher);
//
//        Intent publisherActivityIntent = new Intent(this, PublisherActivity.class);
//        btnBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(publisherActivityIntent);
//            }
//        });
        Button BtnBookLoan = findViewById(R.id.BtnBookLoan);

        Intent BookLoanActivityIntent = new Intent(this, BookLoan.class);
        BtnBookLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BookLoanActivityIntent);
            }
        });

        Button BtnBranch = findViewById(R.id.BtnBranch);

        Intent BranchActivityIntent = new Intent(this, BranchActivity.class);
        BtnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BranchActivityIntent);
            }
        });
    }
}
