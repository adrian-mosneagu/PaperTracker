package com.papertracker.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.papertracker.R;
import com.papertracker.adapters.ItemEditorAdapter;
import com.papertracker.models.Document;

import java.util.ArrayList;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {
    ArrayList<Document> docList;
    RecyclerView rvDocs;
    ItemEditorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        ImageButton cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditItemActivity.this).setTitle("Cancel?")
                        .setMessage("Item will not be saved!")
                        .setIcon(android.R.drawable.stat_sys_warning)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // TODO: Add toast on main activity for cancel
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        Button save = findViewById(R.id.saveDoc);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add item to DB, add toast on main activity for cancel
                finish();
            }
        });

        docList = new ArrayList<>();
        docList.add(new Document("test", new Date()));
        adapter = new ItemEditorAdapter(docList);
        rvDocs = findViewById(R.id.rvNewDocs);
        rvDocs.setAdapter(adapter);
        rvDocs.setLayoutManager(new LinearLayoutManager(EditItemActivity.this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.addDocumentButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Document doc = new Document("", new Date());
                adapter.getDocuments().add(doc);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
