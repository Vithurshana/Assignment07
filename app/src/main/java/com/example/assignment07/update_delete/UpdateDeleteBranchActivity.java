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
import com.example.assignment07.activity.Branch;
import com.example.assignment07.regex.Regex;

import java.util.ArrayList;

public class UpdateDeleteBranchActivity extends AppCompatActivity {
    private Button updateButton;
    private Button deleteButton;
    private static final String BOOK_COPY_TABLE = "Book_Copy";
    private static final String BOOK_LOAN_TABLE = "Book_Loan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_branch);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        DBHandler dbHandler = new DBHandler(this);

        EditText editBranchId = findViewById(R.id.idEditBranchID);
        EditText editBranchName = findViewById(R.id.idEditBranchName);
        EditText editBranchAddress = findViewById(R.id.idEdtBranchAddress);
        editBranchId.setText(details.get(0));
        editBranchName.setText(details.get(1));
        editBranchAddress.setText(details.get(2));
        updateButton = findViewById(R.id.btnUpdateBranch);
        deleteButton = findViewById(R.id.btnDeleteBranch);

        updateButton.setOnClickListener(v -> {
            String newBranchId = editBranchId.getText().toString();
            String newBranchName = editBranchName.getText().toString();
            String newBranchAddress = editBranchAddress.getText().toString();

            if (newBranchId.isEmpty() || newBranchName.isEmpty() || newBranchAddress.isEmpty()) {
                Toast.makeText(UpdateDeleteBranchActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newBranchName.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                editBranchName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBranchActivity.this, "Please enter valid title",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (!newBranchAddress.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                editBranchAddress.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBranchActivity.this, "Please enter valid address",
                        Toast.LENGTH_LONG).show();
                return;
            }
            boolean isBranchIdAlreadyExists = false;
            if (!details.get(0).equals(newBranchId)) {
                isBranchIdAlreadyExists = dbHandler.isBranchIdExists(newBranchId);
            }
            if (!isBranchIdAlreadyExists) {
                dbHandler.updateBranch(details.get(0), newBranchId, newBranchName, newBranchAddress);
                Toast.makeText(UpdateDeleteBranchActivity.this, "Branch has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteBranchActivity.this, Branch.class);
                startActivity(newIntent);
            } else {
                editBranchId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBranchActivity.this, "Branch ID already exists, Please try again", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            String branchId = details.get(0);
            boolean isBookIdIsUsedInBookLoan = dbHandler.isBranchIdExistsInParticular(BOOK_LOAN_TABLE, branchId);
            boolean isBookIdIsUsedInBookCopy = dbHandler.isBranchIdExistsInParticular(BOOK_COPY_TABLE, branchId);
            if (!isBookIdIsUsedInBookLoan && !isBookIdIsUsedInBookCopy){
                dbHandler.deleteBranch(branchId);
                Toast.makeText(UpdateDeleteBranchActivity.this, "Branch is deleted successfully",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBranchActivity.this, Branch.class);
                startActivity(newIntent);
            } else if (isBookIdIsUsedInBookCopy)  {
                Toast.makeText(UpdateDeleteBranchActivity.this, "Can't delete the Branch. It's used in " + BOOK_COPY_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBranchActivity.this, Branch.class);
                startActivity(newIntent);
            } else {
                Toast.makeText(UpdateDeleteBranchActivity.this, "Can't delete the Branch. It's used in " + BOOK_LOAN_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBranchActivity.this, Branch.class);
                startActivity(newIntent);
            }
        });
    }
}