package com.example.assignment07.insert_data;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.regex.Regex;

public class AddPublisher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publisher);

        EditText editName = findViewById(R.id.idEditPublisherName);
        EditText editAddress = findViewById(R.id.idEditPublisherAddress);
        EditText editPhoneNo = findViewById(R.id.idEdtPublisherPhone);
        Button btnAddPublisher = findViewById(R.id.idBtnAddPublisher);
        DBHandler dbHandler = new DBHandler(AddPublisher.this);

        btnAddPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String address = editAddress.getText().toString();
                String phoneNo = editPhoneNo.getText().toString();

                if (name.isEmpty() || address.isEmpty() || phoneNo.isEmpty()) {
                    Toast.makeText(AddPublisher.this, "Please enter all the data..",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    editName.setTextColor(Color.RED);
                    Toast.makeText(AddPublisher.this, "Please enter valid name",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!address.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    editName.setTextColor(Color.RED);
                    Toast.makeText(AddPublisher.this, "Please enter valid address",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!phoneNo.matches(Regex.PHONE_REGEX)) {
                    editPhoneNo.setTextColor(Color.RED);
                    Toast.makeText(AddPublisher.this, "Please enter valid phone number",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isPublisherNameAlreadyExists = dbHandler.isPublisherExists(name);
                if (!isPublisherNameAlreadyExists) {
                    dbHandler.addNewPublisher(name, address, phoneNo);
                    Toast.makeText(AddPublisher.this, "Publisher has been added.",
                            Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editAddress.setText("");
                    editPhoneNo.setText("");
                } else {
                    editName.setTextColor(Color.RED);
                    Toast.makeText(AddPublisher.this, "Publisher name already exists, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
