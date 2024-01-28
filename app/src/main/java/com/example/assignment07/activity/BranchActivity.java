package com.example.assignment07.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.assignment07.MainActivity;
import com.example.assignment07.R;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.form.BookForm;
import com.example.assignment07.form.BranchForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class BranchActivity extends AppCompatActivity {
    private FloatingActionButton BranchFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        DatabaseHandler handler = new DatabaseHandler(this);
        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        BranchFab = findViewById(R.id.BranchFab);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> branchList = fetchHandler.getAllBranches();

        Intent branchFormIntent = new Intent(this, BranchForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(branchList, branchFormIntent);
        BranchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branchFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(branchFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });
    }

    private void tableLayout(ArrayList<Map<String, String>> branches, Intent branchFormIntent) {
        TableLayout bookTableLayout = findViewById(R.id.branchTbContent);
        for (int i = 0; i < branches.size(); i++) {
            Map<String, String> branch = branches.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView branchId = getSampleTextView(this, branch.get(Constant.BRANCH_ID), 3);
            row.addView(branchId);
            TextView branchName = getSampleTextView(this, branch.get(Constant.BRANCH_NAME), 4);
            row.addView(branchName);
            TextView branchAddress = getSampleTextView(this, branch.get(Constant.BRANCH_ADDRESS), 4);
            row.addView(branchAddress);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", branch.get(Constant.BRANCH_ID));
                    branchFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    branchFormIntent.putExtra(Constant.BRANCH_ID, branch.get(Constant.BRANCH_ID));
                    startActivity(branchFormIntent);
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