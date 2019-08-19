package com.papertracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Item> itemList = new ArrayList<>();

        // TODO: This info should be retrieved from DB
        itemList.add(new Item("Vigneta", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("RCA", "some details"));
        itemList.add(new Item("Vigneta", "some details"));

        FloatingActionButton fab = findViewById(R.id.addButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open add activity
            }
        });

        RecyclerView rvItems = findViewById(R.id.rvItems);

        ItemAdapter adapter = new ItemAdapter(itemList);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Playground", "OnStart Called!");
    }
}
