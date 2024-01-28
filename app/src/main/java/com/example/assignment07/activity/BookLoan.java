package com.example.assignment07.activity;

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
import com.example.assignment07.form.BookLoanForm;
import com.example.assignment07.form.MemberForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class BookLoan extends AppCompatActivity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_loan);

        fab = findViewById(R.id.bookLoanFab);
        DatabaseHandler handler = new DatabaseHandler(this);
        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> bookLoans = fetchHandler.getAllBookLoans();

        Intent bookLoanFormIntent = new Intent(this, BookLoanForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(bookLoans, bookLoanFormIntent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookLoanFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(bookLoanFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });

    }

    private void tableLayout(ArrayList<Map<String, String>> bookLoans, Intent bookLoanFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.bookLoanTbContent);
        for (int i = 0; i < bookLoans.size(); i++) {
            Map<String, String> bookLoan = bookLoans.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView accessNumber = getSampleTextView(this, bookLoan.get(Constant.ACCESS_NUMBER), 100);
            row.addView(accessNumber);
            TextView branchId = getSampleTextView(this, bookLoan.get(Constant.BRANCH_ID),100);
            row.addView(branchId);
            TextView cardNumber = getSampleTextView(this, bookLoan.get(Constant.CARD_NUMBER),100);
            row.addView(cardNumber);
            TextView outDate = getSampleTextView(this, bookLoan.get(Constant.LOAN_DATE_OUT),100);
            row.addView(outDate);
            TextView dueDate = getSampleTextView(this, bookLoan.get(Constant.LOAN_DATE_DUE),100);
            row.addView(dueDate);
            TextView returnDate = getSampleTextView(this, bookLoan.get(Constant.LOAN_DATE_RETURN),100);
            row.addView(returnDate);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", bookLoan.get(Constant.BOOK_ID));
                    bookLoanFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    bookLoanFormIntent.putExtra(Constant.ACCESS_NUMBER, bookLoan.get(Constant.ACCESS_NUMBER));
                    bookLoanFormIntent.putExtra(Constant.BRANCH_ID, bookLoan.get(Constant.BRANCH_ID));
                    bookLoanFormIntent.putExtra(Constant.CARD_NUMBER, bookLoan.get(Constant.CARD_NUMBER));
                    bookLoanFormIntent.putExtra(Constant.LOAN_DATE_OUT, bookLoan.get(Constant.LOAN_DATE_OUT));
                    startActivity(bookLoanFormIntent);
                }
            });
            bookTableLayout.addView(row);
        }

    }

    private TextView getSampleTextView(Context context, String text, int width) {
        TextView textView = new TextView(context);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                width,
                TableRow.LayoutParams.WRAP_CONTENT,
                0
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