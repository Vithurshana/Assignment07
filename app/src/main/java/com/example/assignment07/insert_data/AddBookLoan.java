package com.example.assignment07.insert_data;

import android.app.DatePickerDialog;
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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBookLoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_loan);

        EditText editAccessNo = findViewById(R.id.idEditLoanAccessNo);
        EditText editBranchId = findViewById(R.id.idEditLoanBranchId);
        EditText editCardNo = findViewById(R.id.idEditLoanCardNo);
        ImageButton dateOutBtn = findViewById(R.id.btnLoanDateOut);
        ImageButton dateDueBtn = findViewById(R.id.btnLoanDateDue);
        ImageButton dateReturnedBtn = findViewById(R.id.btnLoanDateReturn);
        TextView txtDateOut = findViewById(R.id.idTxtLoanDateOut);
        TextView txtDateDue = findViewById(R.id.idTxtLoanDateDue);
        TextView txtDateReturned = findViewById(R.id.idTxtLoanDateReturn);
        Button addBookLoanBtn = findViewById(R.id.idBtnAddBookLoan);
        DBHandler dbHandler = new DBHandler(AddBookLoan.this);
        dateDueBtn.setClickable(false);
        dateDueBtn.setEnabled(false);
        dateReturnedBtn.setClickable(false);
        dateReturnedBtn.setEnabled(false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateOutBtn.setOnClickListener(v -> {
            try {

                pickTheDate(txtDateOut, null);
                dateDueBtn.setClickable(true);
                dateDueBtn.setEnabled(true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        dateDueBtn.setOnClickListener(v -> {
            try {
                pickTheDate(txtDateDue, txtDateOut.getText().toString());
                dateReturnedBtn.setClickable(true);
                dateReturnedBtn.setEnabled(true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        dateReturnedBtn.setOnClickListener(v -> {
            try {
                pickTheDate(txtDateReturned, txtDateOut.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        addBookLoanBtn.setOnClickListener(v -> {
            String accessNo = editAccessNo.getText().toString();
            String branchId = editBranchId.getText().toString();
            String cardNo = editCardNo.getText().toString();

            Date dateOut = null;
            Date dateDue = null;
            Date dateReturned = null;
            try {
                dateOut = new Date(sdf.parse(txtDateOut.getText().toString()).getTime());
                dateDue = new Date(sdf.parse(txtDateDue.getText().toString()).getTime());
                dateReturned = new Date(sdf.parse(txtDateReturned.getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (accessNo.isEmpty() && branchId.isEmpty() && cardNo.isEmpty() &&
                    txtDateOut.getText().toString().isEmpty() && txtDateDue.getText().toString().isEmpty()) {
                Toast.makeText(AddBookLoan.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            boolean isBranchIdAlreadyExistsInBranchTable = dbHandler.isBranchIdExists(branchId);
            boolean isAccessNoAlreadyExistsInBookCopyTable = dbHandler.isAccessNoExists(accessNo);
            boolean isCardNoAlreadyExistsInMemberTable = dbHandler.isCardNoExists(cardNo);
            boolean isAccessNoAndBranchIdAndCardNoAndDateOutExists = dbHandler.isAccessNoAndBranchIdAndCardNoAndDateOutExists(accessNo, branchId,cardNo, dateOut);
            if (isAccessNoAlreadyExistsInBookCopyTable && isCardNoAlreadyExistsInMemberTable
                    && isBranchIdAlreadyExistsInBranchTable && !isAccessNoAndBranchIdAndCardNoAndDateOutExists) {

                    dbHandler.addNewBookLoan(accessNo, branchId, cardNo, dateOut, dateDue, dateReturned);
                    Toast.makeText(AddBookLoan.this, "Book loan has been added.",
                            Toast.LENGTH_SHORT).show();
                    editAccessNo.setText("");
                    editBranchId.setText("");
                    editCardNo.setText("");
                    txtDateOut.setText("");
                    txtDateDue.setText("");
                    txtDateReturned.setText("");

            } else if (!isAccessNoAlreadyExistsInBookCopyTable) {
                editAccessNo.setTextColor(Color.RED);
                Toast.makeText(AddBookLoan.this, "Access no is not exists", Toast.LENGTH_LONG).show();
            } else if (!isBranchIdAlreadyExistsInBranchTable) {
                editBranchId.setTextColor(Color.RED);
                Toast.makeText(AddBookLoan.this, "Branch ID is not exists", Toast.LENGTH_LONG).show();
            } else if (!isCardNoAlreadyExistsInMemberTable){
                editCardNo.setTextColor(Color.RED);
                Toast.makeText(AddBookLoan.this, "Card no is not exists", Toast.LENGTH_LONG).show();
            } else {
                editAccessNo.setTextColor(Color.RED);
                editBranchId.setTextColor(Color.RED);
                editCardNo.setTextColor(Color.RED);
                txtDateOut.setTextColor(Color.RED);
                Toast.makeText(AddBookLoan.this, "The data already exists", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pickTheDate(TextView textViewToDisplayOut, String minDate) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBookLoan.this,
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