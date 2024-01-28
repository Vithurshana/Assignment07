package com.example.assignment07.form;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookActivity;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.util.Map;

public class BookCopyForm extends AppCompatActivity {
    private String formAction;
    private EditText edtBookId;
    private EditText edtBranchId;
    private EditText editAccessNumber;

    private String accessNumber = null;
    private String branchId = null;

    private DatabaseHandler databaseHandler;
    private Intent bookCopyActivityIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy_form);

        edtBookId = findViewById(R.id.editBookId);
        edtBranchId = findViewById(R.id.editBranchId);
        editAccessNumber = findViewById(R.id.editAccessNumber);
        Button btnBookFormAction = findViewById(R.id.btnBookFormAction);
        Button btnDelete = findViewById(R.id.btnDelete);
        ImageButton backBtn =  findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(BookCopyForm.this);
        databaseHandler = new DatabaseHandler(BookCopyForm.this);
        bookCopyActivityIntent = new Intent(BookCopyForm.this, BookActivity.class);
        Intent bookFormIntent = getIntent();
        formAction = bookFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDelete.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_book_copy_form);
            accessNumber = bookFormIntent.getStringExtra(Constant.ACCESS_NUMBER);
            Map<String, String> bookCopyData = fetchDatabaseHandler.getBookCopyByAccessNumberAndBranchId(accessNumber, branchId);
            edtBookId.setText(bookCopyData.get(Constant.BOOK_ID));
            edtBranchId.setText(bookCopyData.get(Constant.BRANCH_ID));
            editAccessNumber.setText(bookCopyData.get(Constant.ACCESS_NUMBER));
            btnBookFormAction.setText(R.string.update_book_copy);
        }
        btnBookFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(bookCopyActivityIntent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteBookCopy(branchId, accessNumber);
                startActivity(bookCopyActivityIntent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isExists = databaseHandler.isAccessNoAndBranchIdExists(Constant.BOOK_TABLE_NAME, edtBookId.getText().toString());
                if (isExists) {
                    Toast.makeText(BookCopyForm.this, "The Book copy is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewBookCopy(edtBookId.getText().toString(), edtBranchId.getText().toString(),
                            editAccessNumber.getText().toString());
                    startActivity(bookCopyActivityIntent);
                }

            } else {
                boolean isExists = databaseHandler.isAccessNoAndBranchIdExists(accessNumber, branchId);
                if (!branchId.equals(edtBranchId.getText().toString()) && !accessNumber.equals(editAccessNumber.getText().toString()) && isExists) {
                    Toast.makeText(BookCopyForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBookCopy(branchId, accessNumber, edtBookId.getText().toString(), edtBranchId.getText().toString(),
                            editAccessNumber.getText().toString());
                    startActivity(bookCopyActivityIntent);
                }

            }
        }
    };
}