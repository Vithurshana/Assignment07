package com.example.assignment07.update_delete;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.activity.BookLoan;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateDeleteBookLoanActivity extends AppCompatActivity {


    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_book_loan);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");

        EditText editAccessNo = findViewById(R.id.idEditLoanAccessNo);
        EditText editBranchId = findViewById(R.id.idEditLoanBranchId);
        EditText editCardNo = findViewById(R.id.idEditLoanCardNo);
        ImageButton dateOut = findViewById(R.id.btnLoanDateOut);
        ImageButton dateDue = findViewById(R.id.btnLoanDateDue);
        ImageButton dateReturned = findViewById(R.id.btnLoanDateReturn);
        TextView txtDateOut = findViewById(R.id.idTxtLoanDateOut);
        TextView txtDateDue = findViewById(R.id.idTxtLoanDateDue);
        TextView txtDateReturned = findViewById(R.id.idTxtLoanDateReturn);
        DBHandler dbHandler = new DBHandler(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        editAccessNo.setText(details.get(0));
        editBranchId.setText(details.get(1));
        editCardNo.setText(details.get(2));
        txtDateOut.setText(details.get(3));
        txtDateDue.setText(details.get(4));
        txtDateReturned.setText(details.get(5));
        updateButton = findViewById(R.id.btnUpdateBookLoan);
        deleteButton = findViewById(R.id.btnDeleteBookLoan);
        dateOut.setOnClickListener(v -> {
            try {

                pickTheDate(txtDateOut, null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        dateDue.setOnClickListener(v -> {
            try {
                pickTheDate(txtDateDue, txtDateOut.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        dateReturned.setOnClickListener(v -> {
            try {
                pickTheDate(txtDateReturned, txtDateOut.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        updateButton.setOnClickListener(v -> {
            String newAccessNo = editAccessNo.getText().toString();
            String newBranchId = editBranchId.getText().toString();
            String newCardNo = editCardNo.getText().toString();

            Date oldDateOut = null;
            Date newDateOut = null;
            Date newDateDue = null;
            Date newDateReturned = null;
            try {
                oldDateOut = new Date(sdf.parse(details.get(3)).getTime());
                newDateOut = new Date(sdf.parse(txtDateOut.getText().toString()).getTime());
                newDateDue = new Date(sdf.parse(txtDateDue.getText().toString()).getTime());
                newDateReturned = new Date(sdf.parse(txtDateReturned.getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (newAccessNo.isEmpty() && newBranchId.isEmpty() && newCardNo.isEmpty() &&
                    newDateOut.toString().isEmpty() && newDateDue.toString().isEmpty() && newDateReturned.toString().isEmpty()) {
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            boolean isBranchIdAlreadyExistsInBranchTable = dbHandler.isBranchIdExists(newBranchId);
            boolean isAccessNoAlreadyExistsInBookCopyTable = dbHandler.isAccessNoExists(newAccessNo);
            boolean isCardNoAlreadyExistsInMemberTable = dbHandler.isCardNoExists(newCardNo);
            boolean isAccessNoAndBranchIdAndCardNoAndDateOutExists = false;
            if (!details.get(0).equals(newAccessNo) && !details.get(0).equals(newBranchId)
                    && !details.get(0).equals(newCardNo) && !details.get(0).equals(newDateOut)) {
                isAccessNoAndBranchIdAndCardNoAndDateOutExists = dbHandler
                        .isAccessNoAndBranchIdAndCardNoAndDateOutExists(newAccessNo, newBranchId,newCardNo, newDateOut);
            }
            if (isAccessNoAlreadyExistsInBookCopyTable && isCardNoAlreadyExistsInMemberTable
                    && isBranchIdAlreadyExistsInBranchTable && !isAccessNoAndBranchIdAndCardNoAndDateOutExists) {

                dbHandler.updateBookLoan(details.get(0), details.get(1), details.get(2), oldDateOut,
                        newAccessNo, newBranchId, newCardNo, newDateOut, newDateDue, newDateReturned);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Book loan has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteBookLoanActivity.this, BookLoan.class);
                startActivity(newIntent);

            } else if (!isAccessNoAlreadyExistsInBookCopyTable) {
                editAccessNo.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Access no is not exists", Toast.LENGTH_LONG).show();
            } else if (!isBranchIdAlreadyExistsInBranchTable) {
                editBranchId.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Branch ID is not exists", Toast.LENGTH_LONG).show();
            } else if (!isCardNoAlreadyExistsInMemberTable){
                editCardNo.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Card no is not exists", Toast.LENGTH_LONG).show();
            } else {
                editAccessNo.setTextColor(Color.RED);
                editBranchId.setTextColor(Color.RED);
                editCardNo.setTextColor(Color.RED);
                txtDateOut.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "The data already exists", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            try {
                Date oldDateOut = new Date(sdf.parse(details.get(3)).getTime());
                dbHandler.deleteBookLoan(details.get(0),  details.get(1), details.get(2), oldDateOut);
                Toast.makeText(UpdateDeleteBookLoanActivity.this, "Book Loan is deleted successfully",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteBookLoanActivity.this, BookLoan.class);
                startActivity(newIntent);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    private void pickTheDate(TextView textViewToDisplayOut, String minDate) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateDeleteBookLoanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewToDisplayOut.setText(year + "-" + (month+1) + "-" +dayOfMonth);
                    }
                }, year, month, day);
        if (minDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            datePickerDialog.getDatePicker().setMinDate(sdf.parse(minDate).getTime());
        }
        datePickerDialog.show();
    }
}