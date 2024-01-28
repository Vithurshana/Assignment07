package com.example.assignment07.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.adapter.MemberListViewAdapter;
import com.example.assignment07.insert_data.AddMember;
import com.example.assignment07.update_delete.UpdateDeleteMemberActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class Member extends AppCompatActivity {
    private ArrayList<ArrayList> list;
    private FloatingActionButton addMemberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        addMemberButton = findViewById(R.id.memberAddButton);
        list = getAllMemberDetails();
        ListView listView = findViewById(R.id.memberListView);
        MemberListViewAdapter memberListViewAdapter = new MemberListViewAdapter(this, list);
        listView.setAdapter(memberListViewAdapter);

        addMemberButton.setOnClickListener(v -> {
            Intent intent = new Intent(Member.this, AddMember.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> particularMemberDetails = list.get(position);
            Intent intent = new Intent(Member.this, UpdateDeleteMemberActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ARRAYLIST",(Serializable) particularMemberDetails);
            intent.putExtra("details", bundle);
            startActivity(intent);
        });

    }
    private ArrayList<ArrayList> getAllMemberDetails() {
        DBHandler dbHandler = new DBHandler(this);
        return dbHandler.getAllMembers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = getAllMemberDetails();
        ListView listView = findViewById(R.id.memberListView);
        MemberListViewAdapter memberListViewAdapter = new MemberListViewAdapter(this, list);
        listView.setAdapter(memberListViewAdapter);
    }
}