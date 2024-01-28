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
import com.example.assignment07.form.BookForm;
import com.example.assignment07.form.BookLoanForm;
import com.example.assignment07.form.MemberForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class MemberActivity extends AppCompatActivity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        DatabaseHandler handler = new DatabaseHandler(this);
        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        fab = findViewById(R.id.memberFab);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> members = fetchHandler.getAllMembers();

        Intent memberFormIntent = new Intent(this, MemberForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(members, memberFormIntent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(memberFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });
    }

    private void tableLayout(ArrayList<Map<String, String>> members, Intent memberFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.bookTbContent);
        for (int i = 0; i < members.size(); i++) {
            Map<String, String> member = members.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView accessNumber = getSampleTextView(this, member.get(Constant.CARD_NUMBER), 130);
            row.addView(accessNumber);
            TextView branchId = getSampleTextView(this, member.get(Constant.MEMBER_NAME),100);
            row.addView(branchId);
            TextView cardNumber = getSampleTextView(this, member.get(Constant.MEMBER_ADDRESS),100);
            row.addView(cardNumber);
            TextView outDate = getSampleTextView(this, member.get(Constant.MEMBER_PHONE),100);
            row.addView(outDate);
            TextView dueDate = getSampleTextView(this, member.get(Constant.UNPAID_DUES),100);
            row.addView(dueDate);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", member.get(Constant.BOOK_ID));
                    memberFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    memberFormIntent.putExtra(Constant.BOOK_ID, member.get(Constant.BOOK_ID));
                    startActivity(memberFormIntent);
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