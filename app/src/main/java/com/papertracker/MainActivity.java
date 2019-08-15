package com.papertracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean expanded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        FloatingActionButton fab = findViewById(R.id.addButton);
        ImageButton btnExpand = findViewById(R.id.btnExpandCard);

        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expanded) {
                    expanded = hideView();
                } else {
                    expanded = expandView();
                }
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Open add activity
//            }
//        });

    }

    private boolean expandView() {
        ImageView ivExpandCard = findViewById(R.id.btnExpandCard);
        ivExpandCard.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        findViewById(R.id.expandedLayout).setVisibility(View.VISIBLE);

        RecyclerView rvContacts = findViewById(R.id.rvDocuments);

        List<Document> documentList = new ArrayList<>();
        documentList.add(new Document("Vigneta", new Date()));
        documentList.add(new Document("RCA", new Date()));

        DocumentsAdapter adapter = new DocumentsAdapter(documentList);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        return true;
    }

    private boolean hideView() {
        ImageView ivExpandCard = findViewById(R.id.btnExpandCard);
        ivExpandCard.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        findViewById(R.id.expandedLayout).setVisibility(View.GONE);

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Playground", "OnStart Called!");
    }
}
