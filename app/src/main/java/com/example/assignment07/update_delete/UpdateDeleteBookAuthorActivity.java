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
import com.example.assignment07.activity.BookAuthor;
import com.example.assignment07.activity.BookCopy;
import com.example.assignment07.regex.Regex;

import java.util.ArrayList;

public class UpdateDeleteBookAuthorActivity extends AppCompatActivity {

    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_book_author);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        DBHandler dbHandler = new DBHandler(this);

        EditText editBookId = findViewById(R.id.idEditAuthorBookID);
        EditText editAuthorName = findViewById(R.id.idEditBookAuthorName);
        editBookId.setText(details.get(0));
        editAuthorName.setText(details.get(1));

        updateButton = findViewById(R.id.btnUpdateBookAuthor);
        deleteButton = findViewById(R.id.btnDeleteBookAuthor);

        updateButton.setOnClickListener(v -> {
            String newBookId = editBookId.getText().toString();
            String newAuthorName = editAuthorName.getText().toString();

            if (newBookId.isEmpty() && newAuthorName.isEmpty()) {
                Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newAuthorName.matches(Regex.TEXT_REGEX)) {
                editAuthorName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Please enter valid name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            boolean isBookIdAndAuthorNameExists = false;
            boolean isBookIdAlreadyExistsInBookTable = dbHandler.isBookIdExists(newBookId);
            if (!details.get(0).equals(newBookId) && !details.get(1).equals(newAuthorName)) {
                isBookIdAndAuthorNameExists = dbHandler.isBookIdAndAuthorNameExists(newBookId, newAuthorName);
            }
            if (isBookIdAlreadyExistsInBookTable && !isBookIdAndAuthorNameExists) {
                dbHandler.updateBookAuthor(details.get(0), details.get(1), newBookId, newAuthorName);
                Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Book Author has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteBookAuthorActivity.this, BookAuthor.class);
                startActivity(newIntent);
            } else if (!isBookIdAlreadyExistsInBookTable){
                editBookId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Book ID is not exists", Toast.LENGTH_LONG).show();
            } else {
                editBookId.setTextColor(Color.RED);
                editAuthorName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Records already exists", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            dbHandler.deleteBookAuthor(details.get(0), details.get(1));
            Toast.makeText(UpdateDeleteBookAuthorActivity.this, "Book Author is deleted successfully",
                    Toast.LENGTH_LONG).show();
            Intent  newIntent = new Intent(UpdateDeleteBookAuthorActivity.this, BookCopy.class);
            startActivity(newIntent);
        });
    }
}