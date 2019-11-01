package com.papertracker.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.papertracker.R;
import com.papertracker.adapters.ItemEditorAdapter;
import com.papertracker.models.Document;

import java.util.ArrayList;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);


        ArrayList<Document> docList = new ArrayList<>();
        docList.add(new Document("test", new Date()));
        ItemEditorAdapter adapter = new ItemEditorAdapter(docList);
        RecyclerView rvDocs = findViewById(R.id.rvNewDocs);
        rvDocs.setAdapter(adapter);
        rvDocs.setLayoutManager(new LinearLayoutManager(EditItemActivity.this));

        FloatingActionButton fab = findViewById(R.id.addDocumentButton);

        // TODO: This behaviour should be done when clicking on save/cancel buttons
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
