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
import com.example.assignment07.activity.BookCopy;

import java.util.ArrayList;

public class UpdateDeleteBookCopyActivity extends AppCompatActivity {


    private Button updateButton;
    private Button deleteButton;
    private static final String BOOK_COPY_TABLE = "Book_Copy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_book_copy);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        DBHandler dbHandler = new DBHandler(this);

        EditText editBookId = findViewById(R.id.idEditCopyBookID);
        EditText editBranchId = findViewById(R.id.idEditCopyBranchId);
        EditText editAccessNo = findViewById(R.id.idEdtCopyAccessNo);

        editBookId.setText(details.get(0));
        editBranchId.setText(details.get(1));
        editAccessNo .setText(details.get(2));
        updateButton = findViewById(R.id.btnUpdateBookCopy);
        deleteButton = findViewById(R.id.btnDeleteBookCopy);

        updateButton.setOnClickListener(v -> {
            String newBookId = editBookId.getText().toString();
            String newBranchId = editBranchId.getText().toString();
            String newAccessNo = editAccessNo.getText().toString();

            if (newBookId.isEmpty() && newBranchId.isEmpty() && newAccessNo.isEmpty()) {
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isBookIdAlreadyExistsInBookTable = dbHandler.isBookIdExists(newBookId);
            boolean isBranchIdAlreadyExistsInBranchTable = dbHandler.isBranchIdExists(newBranchId);
            boolean isAccessNoAndBranchIdExists = false;
            if (!details.get(1).equals(newBranchId) && !details.get(2).equals(newAccessNo)) {
                isAccessNoAndBranchIdExists = dbHandler.isAccessNoAndBranchIdExists(newAccessNo, newBranchId);
            }
            if (isBookIdAlreadyExistsInBookTable && isBranchIdAlreadyExistsInBranchTable && !isAccessNoAndBranchIdExists) {
                dbHandler.updateBookCopy(details.get(1), details.get(2), newBookId, newBranchId, newAccessNo);
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Book  copy has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteBookCopyActivity.this, BookCopy.class);
                startActivity(newIntent);
            } else if (!isBookIdAlreadyExistsInBookTable){
                editBookId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Book ID is not exists", Toast.LENGTH_LONG).show();
            } else if (!isBranchIdAlreadyExistsInBranchTable){
                editBranchId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Branch ID is not exists", Toast.LENGTH_LONG).show();
            } else {
                editBranchId.setTextColor(Color.RED);
                editAccessNo.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Branch Id and Access no already exists", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            boolean isExists = dbHandler.isAccessNoAndBranchIdExistsInBookLoan(details.get(0), details.get(2));
            if (!isExists) {
                dbHandler.deleteBookCopy(details.get(1), details.get(2));
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Book Copy is deleted successfully",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookCopyActivity.this, BookCopy.class);
                startActivity(newIntent);
            } else {
                Toast.makeText(UpdateDeleteBookCopyActivity.this, "Can't delete the Book. It's used in " + BOOK_COPY_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookCopyActivity.this, BookCopy.class);
                startActivity(newIntent);
            }
        });
    }
}