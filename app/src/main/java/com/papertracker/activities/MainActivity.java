package com.papertracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.papertracker.R;
import com.papertracker.adapters.ItemAdapter;
import com.papertracker.models.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.addButton);

        populateItems();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        populateItems();
        super.onResume();
    }

    private void populateItems() {
        List<Item> itemList = new ArrayList<>();

        RecyclerView rvItems = findViewById(R.id.rvItems);

        // TODO: This info should be retrieved from DB
        itemList.add(new Item("TM23MOS", "BMW"));
        itemList.add(new Item("TM18MOS", "Honda"));

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
