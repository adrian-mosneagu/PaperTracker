package com.papertracker.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.papertracker.R;
import com.papertracker.adapters.ItemEditorAdapter;
import com.papertracker.helpers.PaperTrackerDBHelper;
import com.papertracker.models.Document;
import com.papertracker.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
                                Toast.makeText(EditItemActivity.this, "Item not saved!", Toast.LENGTH_LONG).show();
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
                PaperTrackerDBHelper dbHelper = new PaperTrackerDBHelper(EditItemActivity.this);
                TextInputEditText name = findViewById(R.id.etItemName);
                TextInputEditText details = findViewById(R.id.etItemDetails);
                if (name.getText() == null || details.getText() == null) {
                    Toast.makeText(EditItemActivity.this, "Name and details are mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long itemID = dbHelper.addItem(name.getText().toString(), details.getText().toString());
                EditItemActivity.this.updateDocuments();
                ArrayList<Document> tmpDocs = new ArrayList<>();
                for (Document doc : adapter.getDocuments()) {
                    if (doc.getName() != null && !"".equals(doc.getName()) && doc.getExpirationDate() != null) {
                        tmpDocs.add(doc);
                    }
                }
                dbHelper.addDocuments(itemID, tmpDocs);
                Toast.makeText(EditItemActivity.this, "Item saved", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        int itemID = getIntent().getIntExtra("ItemID", -1);

        docList = new ArrayList<>();
        if (itemID == -1) {
            docList.add(new Document("", null));
        } else {
            PaperTrackerDBHelper dbHelper = new PaperTrackerDBHelper(this);
            TextInputEditText itemName = findViewById(R.id.etItemName);
            TextInputEditText itemDetails = findViewById(R.id.etItemDetails);
            Item item = dbHelper.getItem(itemID);
            if (item != null) {
                itemName.setText(item.getName());
                itemDetails.setText(item.getDetails());
            }
            docList = dbHelper.getDocuments(itemID);
        }
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
                EditItemActivity.this.updateDocuments();
                Document doc = new Document("", null);
                adapter.getDocuments().add(doc);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updateDocuments() {
        RecyclerView.ViewHolder holder = rvDocs.findViewHolderForAdapterPosition(adapter.getItemCount() - 1);
        TextInputEditText name = holder.itemView.findViewById(R.id.newDocName);
        EditText expirationDate = holder.itemView.findViewById(R.id.expirationDate);
        Document doc = adapter.getDocuments().get(adapter.getItemCount() - 1);
        if (name.getText() == null || "".equals(name.getText().toString()) ||
                expirationDate.getText() == null || "".equals(expirationDate.getText().toString())) {
            Toast.makeText(EditItemActivity.this, "Name and expiration date are mandatory!", Toast.LENGTH_SHORT).show();
            return;
        }
        doc.setName(name.getText().toString());
        try {
            doc.setExpirationDate(new SimpleDateFormat("dd/MM/yyyy").parse(expirationDate.getText().toString()));
        } catch (ParseException e) {
            Log.e("ERROR", "Wrong date format: " + expirationDate.getText().toString());
        }
    }
}
