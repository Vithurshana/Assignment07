package com.example.assignment07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment07.activity.Book;
import com.example.assignment07.activity.BookAuthor;
import com.example.assignment07.activity.BookCopy;
import com.example.assignment07.activity.BookLoan;
import com.example.assignment07.activity.Branch;
import com.example.assignment07.activity.Member;
import com.example.assignment07.activity.Publisher;

public class MainActivity extends AppCompatActivity {

    //    private String fileName = "list.txt";
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bookBtn = (Button) findViewById(R.id.BtnBook);
        Button publisherBtn = (Button) findViewById(R.id.BtnPublisher);
        Button branchBtn = (Button) findViewById(R.id.BtnBranch);
        Button memberBtn = (Button) findViewById(R.id.BtnMember);
        Button bookAuthorBtn = (Button) findViewById(R.id.BtnBookAuthor);
        Button bookCopyBtn = (Button) findViewById(R.id.BtnBookCopy);
        Button bookLoanBtn = (Button) findViewById(R.id.BtnBookLoan);


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Book.class);
                startActivity(in);
            }
        });
        publisherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Publisher.class);
                startActivity(in);
            }
        });

        branchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Branch.class);
                startActivity(in);
            }
        });
        memberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Member.class);
                startActivity(in);
            }
        });

        bookAuthorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, BookAuthor.class);
                startActivity(in);
            }
        });

        bookCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, BookCopy.class);
                startActivity(in);
            }
        });

        bookLoanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, BookLoan.class);
                startActivity(in);
            }
        });
    }
}
