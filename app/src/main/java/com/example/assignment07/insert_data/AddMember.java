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

public class AddMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        EditText editCardNo = findViewById(R.id.idEditCard_No);
        EditText editName = findViewById(R.id.idMemberName);
        EditText editAddress = findViewById(R.id.idEdtMemberAddress);
        EditText editPhone = findViewById(R.id.idEdtMemberPhone);
        EditText editUnpaidDues = findViewById(R.id.idEdtUnpaid_dues);
        Button btnAddNewMember = findViewById(R.id.idBtnAddMember);

        DBHandler dbHandler = new DBHandler(AddMember.this);

        btnAddNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String cardNo = editCardNo.getText().toString();
                String address = editAddress.getText().toString();
                String phone = editPhone.getText().toString();
                String txtUnpaidDues = editUnpaidDues.getText().toString();
                Double unpaidDues = 0.0;

                if (name.isEmpty() && cardNo.isEmpty() && address.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(AddMember.this, "Please enter all the data..",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.matches(Regex.TEXT_REGEX)) {
                    editName.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Please enter valid name",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!address.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    editAddress.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Please enter valid address",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!phone.matches(Regex.PHONE_REGEX)) {
                    editPhone.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Please enter valid phone number",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!txtUnpaidDues.matches(Regex.NUMBER_REGEX)) {
                    editUnpaidDues.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Please enter valid unpaid dues",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                unpaidDues = Double.parseDouble(txtUnpaidDues);
                if (txtUnpaidDues.length()>6) {
                    editUnpaidDues.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Please enter valid range unpaid dues",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isCardNoAlreadyExists = dbHandler.isCardNoExists(cardNo);
                if (!isCardNoAlreadyExists) {
                    dbHandler.addNewMember(cardNo, name, address, phone, unpaidDues);
                    Toast.makeText(AddMember.this, "Member has been added.",
                            Toast.LENGTH_SHORT).show();
                    editCardNo.setText("");
                    editName.setText("");
                    editAddress.setText("");
                    editPhone.setText("");
                    editUnpaidDues.setText("");
                } else {
                    editCardNo.setTextColor(Color.RED);
                    Toast.makeText(AddMember.this, "Card no already exists, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}