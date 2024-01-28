package com.example.assignment07.update_delete;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.activity.Publisher;
import com.example.assignment07.regex.Regex;

import java.util.ArrayList;

public class UpdateDeletePublisherActivity extends AppCompatActivity {


    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_publisher);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");

        EditText editName = findViewById(R.id.idEditPublisherName);
        EditText editAddress = findViewById(R.id.idEditPublisherAddress);
        EditText editPhoneNo = findViewById(R.id.idEdtPublisherPhone);
        editName.setText(details.get(0));
        editAddress.setText(details.get(1));
        editPhoneNo.setText(details.get(2));
        updateButton = findViewById(R.id.btnUpdatePublisher);
        deleteButton = findViewById(R.id.btnDeletePublisher);
        DBHandler dbHandler = new DBHandler(this);

        updateButton.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            String newAddress = editAddress.getText().toString();
            String newPhoneNo = editPhoneNo.getText().toString();

            if (newName.isEmpty() || newAddress.isEmpty() || newPhoneNo.isEmpty()) {
                Toast.makeText(UpdateDeletePublisherActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newName.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                editName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeletePublisherActivity.this, "Please enter valid name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!newAddress.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                editName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeletePublisherActivity.this, "Please enter valid address",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!newPhoneNo.matches(Regex.PHONE_REGEX)) {
                editPhoneNo.setTextColor(Color.RED);
                Toast.makeText(UpdateDeletePublisherActivity.this, "Please enter valid phone number",
                        Toast.LENGTH_LONG).show();
                return;
            }
            boolean isPublisherNameAlreadyExists = false;

            if (!details.get(0).equals(newName)) {
                isPublisherNameAlreadyExists = dbHandler.isPublisherExists(newName);
            }

            if (!isPublisherNameAlreadyExists) {
                dbHandler.updatePublisher(details.get(0), newName, newAddress, newPhoneNo);
                Toast.makeText(UpdateDeletePublisherActivity.this, "Publisher has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(UpdateDeletePublisherActivity.this, Publisher.class);
                startActivity(newIntent);
            } else {
                editName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeletePublisherActivity.this, "Publisher name already exists, Please try again", Toast.LENGTH_LONG).show();
            }
        });
        deleteButton.setOnClickListener(v -> {
            dbHandler.deletePublisher(details.get(0).toString());
            Toast.makeText(UpdateDeletePublisherActivity.this, "Publisher is deleted successfully",
                    Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(UpdateDeletePublisherActivity.this, Publisher.class);
            startActivity(newIntent);
        });
    }
}