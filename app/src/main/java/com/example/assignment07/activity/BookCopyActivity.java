package com.example.assignment07.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.assignment07.R;
import com.example.assignment07.utills.Constant;

import java.util.ArrayList;
import java.util.Map;

public class BookCopyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy);
    }

    private void tableLayout(ArrayList<Map<String, String>> bookCopies, Intent bookCopyFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.bookTbContent);
        for (int i = 0; i < bookCopies.size(); i++) {
            Map<String, String> bookCopy = bookCopies.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView bookId = getSampleTextView(this, bookCopy.get(Constant.BOOK_ID), 3);
            row.addView(bookId);
            TextView branchId = getSampleTextView(this, bookCopy.get(Constant.BRANCH_ID), 4);
            row.addView(branchId);
            TextView accessNumber = getSampleTextView(this, bookCopy.get(Constant.ACCESS_NUMBER), 4);
            row.addView(accessNumber);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", bookCopy.get(Constant.BOOK_ID));
                    bookCopyFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    bookCopyFormIntent.putExtra(Constant.BOOK_ID, bookCopy.get(Constant.BOOK_ID));
                    startActivity(bookCopyFormIntent);
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