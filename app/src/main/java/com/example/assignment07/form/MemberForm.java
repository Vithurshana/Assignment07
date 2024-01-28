package com.example.assignment07.form;

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

public class MemberForm extends AppCompatActivity {
    private String formAction;
    private EditText editCardNumber;
    private EditText editMemberName;
    private EditText editMemberAddress;
    private EditText editMemberPhone;
    private EditText editUnpaidDues;

    private String cardNumber = null;

    private DatabaseHandler databaseHandler;
    private Intent bookActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_form);


        editCardNumber = findViewById(R.id.editCardNumber);
        editMemberName = findViewById(R.id.editMemberName);
        editMemberAddress = findViewById(R.id.editMemberAddress);
        editMemberPhone = findViewById(R.id.editMemberPhone);
        editUnpaidDues = findViewById(R.id.editUnpaidDues);
        Button btnFormAction = findViewById(R.id.btnFormAction);
        Button btnDelete = findViewById(R.id.btnDelete);
        ImageButton backBtn =  findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(MemberForm.this);
        databaseHandler = new DatabaseHandler(MemberForm.this);
        bookActivityIntent = new Intent(MemberForm.this, BookActivity.class);
        Intent memberFormIntent = getIntent();
        formAction = memberFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDelete.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.bookFormToolBarText);
            toolBarText.setText(R.string.update_member_form);
            cardNumber = memberFormIntent.getStringExtra(Constant.CARD_NUMBER);
            Map<String, String> memberData = fetchDatabaseHandler.getMemberByCardNumber(cardNumber);
            editCardNumber.setText(memberData.get(Constant.CARD_NUMBER));
            editMemberName.setText(memberData.get(Constant.MEMBER_NAME));
            editMemberAddress.setText(memberData.get(Constant.MEMBER_ADDRESS));
            editMemberPhone.setText(memberData.get(Constant.MEMBER_PHONE));
            editUnpaidDues.setText(memberData.get(Constant.UNPAID_DUES));
            btnFormAction.setText(R.string.update_member);
        }
        btnFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(bookActivityIntent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteMember(cardNumber);
                startActivity(bookActivityIntent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isExists = databaseHandler.isCardNoExists( editCardNumber.getText().toString());
                if (isExists) {
                    Toast.makeText(MemberForm.this, "The member is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewMember(editCardNumber.getText().toString(), editMemberName.getText().toString(),
                            editMemberAddress.getText().toString(), editMemberPhone.getText().toString() , Double.valueOf(editUnpaidDues.getText().toString()));
                    startActivity(bookActivityIntent);
                }

            } else {
                boolean isExists = databaseHandler.isCardNoExists(editCardNumber.getText().toString());
                if (!cardNumber.equals(editCardNumber.getText().toString()) && isExists) {
                    Toast.makeText(MemberForm.this, "The member is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateMember(cardNumber, editCardNumber.getText().toString(), editMemberName.getText().toString(),
                            editMemberAddress.getText().toString(), editMemberPhone.getText().toString() , Double.valueOf(editUnpaidDues.getText().toString()));
                    startActivity(bookActivityIntent);
                }

            }
        }
    };
}