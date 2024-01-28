package com.example.assignment07.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookActivity;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.util.Map;

public class PublisherForm extends AppCompatActivity {

    private String formAction;
    private EditText editPublisherName;
    private EditText editAddress;
    private EditText editPhone;

    private String publisherName = null;

    private DatabaseHandler databaseHandler;
    private Intent publisherActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_form);


        editPublisherName = findViewById(R.id.editPublisher);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        Button btnBookFormAction = findViewById(R.id.btnFormAction);
        Button btnDeleteBook = findViewById(R.id.btnDelete);
        ImageButton backBtn = findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(this);
        databaseHandler = new DatabaseHandler(this);
        publisherActivityIntent = new Intent(this, BookActivity.class);
        Intent bookFormIntent = getIntent();
        formAction = bookFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDeleteBook.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_publisher_form);
            publisherName = bookFormIntent.getStringExtra(Constant.PUBLISHER_NAME);
            Map<String, String> publisherData = fetchDatabaseHandler.getPublishersByName(publisherName);
            editPublisherName.setText(publisherData.get(Constant.PUBLISHER_NAME));
            editAddress.setText(publisherData.get(Constant.ADDRESS));
            editPhone.setText(publisherData.get(Constant.PHONE));
            btnBookFormAction.setText(R.string.update_publisher);
        }
        btnBookFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(publisherActivityIntent);
            }
        });
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deletePublisher(publisherName);
                startActivity(publisherActivityIntent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isExists = databaseHandler.isPublisherExists(editPublisherName.getText().toString());
                if (isExists) {
                    Toast.makeText(PublisherForm.this, "The Publisher is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewPublisher(editPublisherName.getText().toString(), editAddress.getText().toString(),
                            editPhone.getText().toString());
                    startActivity(publisherActivityIntent);
                }

            } else {
                boolean isExists = databaseHandler.isPublisherExists(editPublisherName.getText().toString());
                if (!publisherName.equals(editPublisherName.getText().toString()) && isExists) {
                    Toast.makeText(PublisherForm.this, "The Publisher is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBook(publisherName, editPublisherName.getText().toString(), editAddress.getText().toString(),
                            editPhone.getText().toString());
                    startActivity(publisherActivityIntent);
                }

            }
        }
    };
}