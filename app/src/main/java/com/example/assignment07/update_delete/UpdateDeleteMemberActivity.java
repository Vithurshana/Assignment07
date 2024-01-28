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
import com.example.assignment07.activity.Member;
import com.example.assignment07.regex.Regex;

import java.util.ArrayList;

public class UpdateDeleteMemberActivity extends AppCompatActivity {

    private Button updateButton;
    private Button deleteButton;
    private static final String BOOK_LOAN_TABLE = "Book_Loan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_member);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("details");
        ArrayList<String> details = (ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        DBHandler dbHandler = new DBHandler(this);

        EditText editCardNo = findViewById(R.id.idEditCard_No);
        EditText editName = findViewById(R.id.idMemberName);
        EditText editAddress = findViewById(R.id.idEdtMemberAddress);
        EditText editPhone = findViewById(R.id.idEdtMemberPhone);
        EditText editUnpaidDues = findViewById(R.id.idEdtUnpaid_dues);
        editCardNo.setText(details.get(0));
        editName.setText(details.get(1));
        editAddress.setText(details.get(2));
        editPhone.setText(details.get(3));
        editUnpaidDues.setText(details.get(4));
        updateButton = findViewById(R.id.btnUpdateMember);
        deleteButton = findViewById(R.id.btnDeleteMember);

        updateButton.setOnClickListener(v -> {
            String newName = editName.getText().toString();
            String newCardNo = editCardNo.getText().toString();
            String newAddress = editAddress.getText().toString();
            String newPhone = editPhone.getText().toString();
            String txtUnpaidDues = editUnpaidDues.getText().toString();
            Double newUnpaidDues = 0.0;

            if (newName.isEmpty() && newCardNo.isEmpty() && newAddress.isEmpty() && newPhone.isEmpty()
                    && newUnpaidDues == null) {
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter all the data..",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newName.matches(Regex.TEXT_REGEX)) {
                editName.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter valid name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!newAddress.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                editAddress.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter valid address",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!newPhone.matches(Regex.PHONE_REGEX)) {
                editPhone.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter valid phone number",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (!txtUnpaidDues.matches(Regex.NUMBER_REGEX)) {
                editUnpaidDues.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter valid unpaid dues",
                        Toast.LENGTH_LONG).show();
                return;
            }
            newUnpaidDues = Double.parseDouble(txtUnpaidDues);
            if (txtUnpaidDues.length()>6) {
                editUnpaidDues.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Please enter valid range unpaid dues",
                        Toast.LENGTH_LONG).show();
                return;
            }

            boolean isCardNoAlreadyExists = false;

            if (!details.get(0).equals(newCardNo)){
                isCardNoAlreadyExists = dbHandler.isCardNoExists(newCardNo);
            }
            if (!isCardNoAlreadyExists) {
                dbHandler.updateMember(details.get(0), newCardNo, newName, newAddress, newPhone, newUnpaidDues);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Member has been updated.",
                        Toast.LENGTH_SHORT).show();
                Intent  newIntent = new Intent(UpdateDeleteMemberActivity.this, Member.class);
                startActivity(newIntent);

            } else {
                editCardNo.setTextColor(Color.RED);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Card no already exists, Please try again", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            String cardNo = details.get(0);
            boolean isCardNoIsExistsInBookLoan = dbHandler.isCardNoExistsInBookLoan(cardNo);
            if (!isCardNoIsExistsInBookLoan) {
                dbHandler.deleteMember(cardNo);
                Toast.makeText(UpdateDeleteMemberActivity.this, "Member is deleted successfully",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteMemberActivity.this, Member.class);
                startActivity(newIntent);
            } else {
                Toast.makeText(UpdateDeleteMemberActivity.this, "Can't delete the Member. It's used in " + BOOK_LOAN_TABLE + " table",
                        Toast.LENGTH_LONG).show();
                Intent  newIntent = new Intent(UpdateDeleteMemberActivity.this, Member.class);
                startActivity(newIntent);
            }

        });
    }
}