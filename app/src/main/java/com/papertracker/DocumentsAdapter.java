package com.papertracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView expirationDate;

        protected ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvDocName);
            expirationDate = itemView.findViewById(R.id.tvDocExpirationDate);
        }

    }
    private List<Document> documents;

    protected DocumentsAdapter(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View docView = inflater.inflate(R.layout.document, parent, false);

        return new ViewHolder(docView);
    }

    public void onBindViewHolder(DocumentsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Document doc = documents.get(position);

        // Set item views based on your views and data model
        TextView tvName = viewHolder.name;
        tvName.setText(doc.getName());
        TextView tvExpirationDate = viewHolder.expirationDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        tvExpirationDate.setText(formatter.format(doc.getExpirationDate()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return documents.size();
    }
}
