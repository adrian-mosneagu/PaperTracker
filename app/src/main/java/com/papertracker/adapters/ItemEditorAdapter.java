package com.papertracker.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.papertracker.R;
import com.papertracker.models.Document;

import java.util.ArrayList;
import java.util.List;


public class ItemEditorAdapter extends RecyclerView.Adapter<ItemEditorAdapter.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText name;
        private EditText expirationDate;

        protected ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.newDocName);
            expirationDate = itemView.findViewById(R.id.expirationDate);

            expirationDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    DatePickerDialog picker = new DatePickerDialog(itemView.getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    expirationDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });


            ImageButton deleteButton = itemView.findViewById(R.id.btnNewDocDelete);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    documents.remove(getAdapterPosition());
                    notifyDataSetChanged();}
            });
        }
    }

    private List<Document> documents;


    public ItemEditorAdapter(List<Document> documents) {
        this.documents = new ArrayList<>(documents);
    }

    @Override
    public ItemEditorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View docView = inflater.inflate(R.layout.new_doc, parent, false);

        return new ViewHolder(docView);
    }

    public void onBindViewHolder(ItemEditorAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Document doc = documents.get(position);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return documents.size();
    }

    public List<Document> getDocuments() {
        return documents;
    }
}
