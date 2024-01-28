package com.example.assignment07.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment07.R;
import com.example.assignment07.activity.BookActivity;
import com.example.assignment07.activity.BranchActivity;
import com.example.assignment07.db_handler.DatabaseHandler;
import com.example.assignment07.db_handler.FetchDatabaseHandler;
import com.example.assignment07.utills.Constant;

import java.util.Map;

public class BranchForm extends AppCompatActivity {
    private String formAction;
    private EditText edtBranchId;
    private EditText edtBranchName;
    private EditText edtAddress;

    private String branchId = null;

    private DatabaseHandler databaseHandler;
    private Intent branchActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_form);

        edtBranchId = findViewById(R.id.editBranchId);
        edtBranchName = findViewById(R.id.editBranchName);
        edtAddress = findViewById(R.id.editBranchAddress);
        Button btnBranchFormAction = findViewById(R.id.btnBranchFormAction);
        Button btnDeleteBranch = findViewById(R.id.btnDeleteBranch);
        ImageButton backBtn =  findViewById(R.id.backBtn);
        FetchDatabaseHandler fetchDatabaseHandler = new FetchDatabaseHandler(BranchForm.this);
        databaseHandler = new DatabaseHandler(BranchForm.this);
        branchActivityIntent = new Intent(BranchForm.this, BranchActivity.class);
        Intent branchFormIntent = getIntent();
        formAction = branchFormIntent.getStringExtra(Constant.FORM_ACTION);
        if (formAction.equals(Constant.UPDATE_FORM_ACTION)) {
            btnDeleteBranch.setVisibility(View.VISIBLE);
            TextView toolBarText = findViewById(R.id.branchFormToolBarText);
            toolBarText.setText(R.string.update_branch_form);
            branchId = branchFormIntent.getStringExtra(Constant.BRANCH_ID);
            Map<String, String> branchData = fetchDatabaseHandler.getBranchById(branchId);
            edtBranchId.setText(branchData.get(Constant.BRANCH_ID));
            edtBranchName.setText(branchData.get(Constant.BRANCH_NAME));
            edtAddress.setText(branchData.get(Constant.BRANCH_ADDRESS));
            btnBranchFormAction.setText(R.string.update_branch);
        }
        btnBranchFormAction.setOnClickListener(listener);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(branchActivityIntent);
            }
        });
        btnDeleteBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteBranch(branchId);
                startActivity(branchActivityIntent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (formAction.equals(Constant.CREATE_FORM_ACTION)) {
                boolean isBranchIdExists = databaseHandler.isBranchIdExistsInParticular(Constant.BRANCH_TABLE_NAME, edtBranchId.getText().toString());
                if (isBranchIdExists) {
                    Toast.makeText(BranchForm.this, "The Branch ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.addNewBranch(edtBranchId.getText().toString(), edtBranchName.getText().toString(),
                            edtAddress.getText().toString());
                    startActivity(branchActivityIntent);
                }

            } else {
                boolean isBranchIdExists = databaseHandler.isBranchIdExistsInParticular(Constant.BRANCH_TABLE_NAME, edtBranchId.getText().toString());
                if (!branchId.equals(edtBranchId.getText().toString()) && isBranchIdExists) {
                    Toast.makeText(BranchForm.this, "The Branch ID is already exists", Toast.LENGTH_LONG).show();
                } else {
                    databaseHandler.updateBranch(branchId, edtBranchId.getText().toString(), edtBranchName.getText().toString(),
                            edtAddress.getText().toString());
                    startActivity(branchActivityIntent);
                }

            }
        }
    };
}