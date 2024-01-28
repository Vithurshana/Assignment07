package com.example.assignment07.activity;

import android.annotation.SuppressLint;
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
import com.example.assignment07.form.PublisherForm;
import com.example.assignment07.utills.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class PublisherActivity extends AppCompatActivity {
    private FloatingActionButton publisherFab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        FetchDatabaseHandler fetchHandler = new FetchDatabaseHandler(this);
        publisherFab = findViewById(R.id.publisherFab);
        ImageView backBtn = findViewById(R.id.backBtn);

        ArrayList<Map<String, String>> publisherList = fetchHandler.getAllPublishers();

        Intent publisherFormIntent = new Intent(this, PublisherForm.class);
        Intent mainActivityIntent = new Intent(this, MainActivity. class);
        tableLayout(publisherList, publisherFormIntent);
        publisherFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publisherFormIntent.putExtra(Constant.FORM_ACTION, Constant.CREATE_FORM_ACTION);
                startActivity(publisherFormIntent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivityIntent);
            }
        });
    }

    private void tableLayout(ArrayList<Map<String, String>> publisherList, Intent publisherFormIntent) {
        TableLayout publisherTbContent = findViewById(R.id.publisherTbContent);
        for (int i = 0; i < publisherList.size(); i++) {
            Map<String, String> publisher = publisherList.get(i);
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView name = getSampleTextView(this, publisher.get(Constant.PUBLISHER_NAME), 4);
            row.addView(name);
            TextView address = getSampleTextView(this, publisher.get(Constant.ADDRESS), 4);
            row.addView(address);
            TextView phone = getSampleTextView(this, publisher.get(Constant.PHONE), 4);
            row.addView(phone);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Clicked row values", publisher.get(Constant.PUBLISHER_NAME));
                    publisherFormIntent.putExtra(Constant.FORM_ACTION, Constant.UPDATE_FORM_ACTION);
                    publisherFormIntent.putExtra(Constant.PUBLISHER_NAME, publisher.get(Constant.PUBLISHER_NAME));
                    startActivity(publisherFormIntent);
                }
            });
            publisherTbContent.addView(row);
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