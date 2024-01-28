package com.example.assignment07.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment07.DBHandler;
import com.example.assignment07.R;
import com.example.assignment07.adapter.PublisherListViewAdapter;
import com.example.assignment07.insert_data.AddPublisher;
import com.example.assignment07.update_delete.UpdateDeletePublisherActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class Publisher extends AppCompatActivity {
    private ArrayList<ArrayList> list;
    private FloatingActionButton addPublisherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        addPublisherButton = findViewById(R.id.publisherAddButton);
        list = getAllPublishersDetails();
        ListView listView = findViewById(R.id.publisherListView);
        PublisherListViewAdapter publisherListViewAdapter = new PublisherListViewAdapter(this, list);
        listView.setAdapter(publisherListViewAdapter);

        addPublisherButton.setOnClickListener(v -> {
            Intent intent = new Intent(Publisher.this, AddPublisher.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> particularPublisherDetails = list.get(position);
            Intent intent = new Intent(Publisher.this, UpdateDeletePublisherActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ARRAYLIST",(Serializable) particularPublisherDetails);
            intent.putExtra("details", bundle);
            startActivity(intent);
        });
    }

    private ArrayList<ArrayList> getAllPublishersDetails() {
        DBHandler dbHandler = new DBHandler(this);
        return dbHandler.getAllPublishers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = getAllPublishersDetails();
        ListView listView = findViewById(R.id.publisherListView);
        PublisherListViewAdapter publisherListViewAdapter = new PublisherListViewAdapter(this, list);
        listView.setAdapter(publisherListViewAdapter);
    }
}