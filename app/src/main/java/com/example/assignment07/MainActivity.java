package com.example.assignment07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.activity.*;
import com.example.assignment07.db_handler.DatabaseHandler;


public class MainActivity extends AppCompatActivity {

    //    private String fileName = "list.txt";
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler handler = new DatabaseHandler(this);
//        handler.createTables();
        Button btnBook = findViewById(R.id.BtnBook);
        Button btnBookLoan = findViewById(R.id.BtnBookLoan);
        Button btnBranch = findViewById(R.id.BtnBranch);
        Button btnMember = findViewById(R.id.BtnMember);
        Button btnBookAuthor = findViewById(R.id.BtnBookAuthor);
        Button btnBookCopy = findViewById(R.id.BtnBookCopy);
        Button btnPublisher = findViewById(R.id.BtnPublisher);

        Intent bookActivityIntent = new Intent(this, BookActivity.class);
        Intent bookLoanActivityIntent = new Intent(this, BookLoan.class);
        Intent branchActivityIntent = new Intent(this, BranchActivity.class);
        Intent memberActivityIntent = new Intent(this, MemberActivity.class);
        Intent publisherActivityIntent = new Intent(this, PublisherActivity.class);
        Intent bookAuthorActivityIntent = new Intent(this, BookAuthorActivity.class);
        Intent bookCopyActivityIntent = new Intent(this, BookCopyActivity.class);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookActivityIntent);
            }
        });


        btnBookLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookLoanActivityIntent);
            }
        });


        btnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(branchActivityIntent);
            }
        });

        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(memberActivityIntent);
            }
        });

        btnPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(publisherActivityIntent);
            }
        });

        btnBookCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookCopyActivityIntent);
            }
        });

        btnBookAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookAuthorActivityIntent);
            }
        });
    }
}
