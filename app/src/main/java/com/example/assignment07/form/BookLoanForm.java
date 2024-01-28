package com.example.assignment07.form;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookLoan;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class BookLoanForm extends AppCompatActivity {
    private String formAction;
    private EditText editAccessNumber;
    private EditText editBranchId;
    private EditText editCardNumber;
    private TextView txtBookOutDate;
    private TextView txtBookDueDate;
    private TextView txtBookReturnDate;

    private String accessNumber = null;
    private String branchId = null;
    private String cardNumber = null;
    private Date bookOutDate = null;
    private DatabaseHandler databaseHandler;
    private Intent bookLoanActivityIntent;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_loan_form);

        editAccessNumber = findViewById(R.id.editLoanAccessNumber);
        editBranchId = findViewById(R.id.editLoanBranchId);
        editCardNumber = findViewById(R.id.editLoanCardNumber);
        txtBookOutDate = findViewById(R.id.txtLoanOutDate);
        txtBookDueDate = findViewById(R.id.txtLoanDueDate);
        txtBookReturnDate = findViewById(R.id.txtLoanReturnDate);
        ImageButton btnBookOut = findViewById(R.id.btnLoanDateOut);
        ImageButton btnBookDue = findViewById(R.id.btnLoanDateDue);
        ImageButton btnBookReturn = findViewById(R.id.btnLoanDateReturn);

        Button btnFormAction = findViewById(R.id.btnBookLoanFormAction);
        Button btnDelete = findViewById(R.id.btnDeleteBookLoan);
        ImageButton backBtn = findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(this);
        databaseHandler = new DatabaseHandler(this);
        bookLoanActivityIntent = new Intent(this, BookLoan.class);
        Intent bookFormIntent = getIntent();
        formAction = bookFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDelete.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_book_form);
            accessNumber = bookFormIntent.getStringExtra(Constant.ACCESS_NUMBER);
            branchId = bookFormIntent.getStringExtra(Constant.BRANCH_ID);
            cardNumber = bookFormIntent.getStringExtra(Constant.CARD_NUMBER);
            try {
                bookOutDate = new Date(sdf.parse(bookFormIntent.getStringExtra(Constant.LOAN_DATE_OUT)).getTime());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Map<String, String> bookLoan =
                    fetchDatabaseHandler.getBookLoansByAccessNumberAndBranchIdAndCardNumberAndDateOut(accessNumber, branchId, cardNumber, bookOutDate);
            editBranchId.setText(branchId);
            editAccessNumber.setText(accessNumber);
            editCardNumber.setText(cardNumber);
            txtBookDueDate.setText(bookLoan.get(Constant.LOAN_DATE_DUE));
            txtBookReturnDate.setText(bookLoan.get(Constant.LOAN_DATE_RETURN));
            txtBookOutDate.setText(bookLoan.get(Constant.LOAN_DATE_OUT));
            btnFormAction.setText(R.string.update_book_loan);
        }
        btnFormAction.setOnClickListener(listener);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteBookLoan(accessNumber, branchId, cardNumber, bookOutDate);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(bookLoanActivityIntent);
            }
        });

        btnBookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showCalender(txtBookOutDate, null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnBookDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showCalender(txtBookDueDate, txtBookOutDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnBookReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showCalender(txtBookReturnDate, txtBookOutDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void showCalender(TextView textView, String minDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookLoanForm.this,
                (view, year1, month1, dayOfMonth) -> {
                    textView.setText(year1 + "-" + (month1 + 1) + "-" + dayOfMonth);
                }, year, month, day);
        if (minDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            datePickerDialog.getDatePicker().setMinDate(sdf.parse(minDate).getTime());
        }
        datePickerDialog.show();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String accessNumber1 = editAccessNumber.getText().toString();
            String branchId1 = editBranchId.getText().toString();
            String cardNumber1 = editCardNumber.getText().toString();
            Date dateOut1 = null;
            Date dateDue1 = null;
            Date dateReturn1 = null;
            try {
                dateOut1 = new Date(sdf.parse(txtBookOutDate.getText().toString()).getTime());
                dateDue1 = new Date(sdf.parse(txtBookOutDate.getText().toString()).getTime());
                dateReturn1 = new Date(sdf.parse(txtBookOutDate.getText().toString()).getTime());

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            boolean isExists = databaseHandler.isAccessNoAndBranchIdAndCardNoAndDateOutExists(accessNumber1, branchId1, cardNumber1, dateOut1);
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                if (isExists) {
                    Toast.makeText(BookLoanForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewBookLoan(accessNumber1, branchId1, cardNumber1, dateOut1, dateDue1, dateReturn1);
                    startActivity(bookLoanActivityIntent);

                }
            } else {
                if (!accessNumber1.equals(accessNumber) && !branchId1.equals(branchId) && !cardNumber1.equals(cardNumber)
                        && !dateOut1.equals(bookOutDate) && isExists) {
                    Toast.makeText(BookLoanForm.this, "The Book ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBookLoan(accessNumber, branchId, cardNumber, bookOutDate, accessNumber1, branchId1, cardNumber1, dateOut1, dateDue1, dateReturn1);
                    startActivity(bookLoanActivityIntent);
                }

            }


        }
    };
}