package com.example.assignment07.insert_data;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;

public class AddBookCopy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_copy);

        EditText editBookId = findViewById(R.id.idEditCopyBookID);
        EditText editBranchId = findViewById(R.id.idEditCopyBranchId);
        EditText editAccessNo = findViewById(R.id.idEdtCopyAccessNo);
        Button addBookCopyBtn = findViewById(R.id.idBtnAddBookCopy);
        DBHandler dbHandler;

        dbHandler = new DBHandler(AddBookCopy.this);

        addBookCopyBtn.setOnClickListener(v -> {
            String bookId = editBookId.getText().toString();
            String branchId = editBranchId.getText().toString();
            String accessNo = editAccessNo.getText().toString();

            if (bookId.isEmpty() && branchId.isEmpty() && accessNo.isEmpty()) {
                Toast.makeText(AddBookCopy.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isBookIdAlreadyExistsInBookTable = dbHandler.isBookIdExists(bookId);
            boolean isBranchIdAlreadyExistsInBranchTable = dbHandler.isBranchIdExists(branchId);
            boolean isAccessNoAndBranchIdExists = dbHandler.isAccessNoAndBranchIdExists(accessNo, branchId);
            if (isBookIdAlreadyExistsInBookTable && isBranchIdAlreadyExistsInBranchTable && !isAccessNoAndBranchIdExists) {
                dbHandler.addNewBookCopy(bookId, branchId, accessNo);
                Toast.makeText(AddBookCopy.this, "Book copy has been added.",
                        Toast.LENGTH_SHORT).show();
                editBookId.setText("");
                editBranchId.setText("");
                editAccessNo.setText("");
            } else if (!isBookIdAlreadyExistsInBookTable){
                editBookId.setTextColor(Color.RED);
                Toast.makeText(AddBookCopy.this, "Book ID is not exists", Toast.LENGTH_LONG).show();
            } else if (isBranchIdAlreadyExistsInBranchTable){
                editBranchId.setTextColor(Color.RED);
                Toast.makeText(AddBookCopy.this, "Branch ID is not exists", Toast.LENGTH_LONG).show();
            } else {
                editBranchId.setTextColor(Color.RED);
                editAccessNo.setTextColor(Color.RED);
                Toast.makeText(AddBookCopy.this, "Branch Id and Access no already exists", Toast.LENGTH_LONG).show();
            }
        });
    }
}