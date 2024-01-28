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

public class AddBranch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        EditText editBranchId = findViewById(R.id.idEditBranchID);
        EditText editBranchName = findViewById(R.id.idEditBranchName);
        EditText editBranchAddress = findViewById(R.id.idEdtBranchAddress);
        Button btnAddBranch=findViewById(R.id.idBtnAddBranch);

        DBHandler dbHandler = new DBHandler(AddBranch.this);

        btnAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String branchId = editBranchId.getText().toString();
                String branchName = editBranchName.getText().toString();
                String branchAddress = editBranchAddress.getText().toString();

                if (branchId.isEmpty() && branchName.isEmpty() && branchAddress.isEmpty()) {
                    Toast.makeText(AddBranch.this, "Please enter all the data..",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!branchName.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    editBranchName.setTextColor(Color.RED);
                    Toast.makeText(AddBranch.this, "Please enter valid title",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!branchAddress.matches(Regex.TEXT_AND_NUMBER_REGEX)) {
                    editBranchAddress.setTextColor(Color.RED);
                    Toast.makeText(AddBranch.this, "Please enter valid address",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isBranchIdAlreadyExists = dbHandler.isBranchIdExists(branchId);
                if (!isBranchIdAlreadyExists) {
                    dbHandler.addNewBranch(branchId, branchName, branchAddress);
                    Toast.makeText(AddBranch.this, "Branch has been added.",
                            Toast.LENGTH_SHORT).show();
                    editBranchId.setText("");
                    editBranchName.setText("");
                    editBranchAddress.setText("");
                } else {
                    editBranchId.setTextColor(Color.RED);
                    Toast.makeText(AddBranch.this, "Branch ID already exists, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}