package com.example.assignment07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.activity.BookActivity;
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

    }
}
