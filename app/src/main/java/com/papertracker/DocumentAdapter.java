package com.papertracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView expirationDate;
        private ImageButton deleteButton;

        protected ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvDocName);
            expirationDate = itemView.findViewById(R.id.tvDocExpirationDate);
            deleteButton = itemView.findViewById(R.id.btDocDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext()).setTitle("Confirm deletion")
                            .setMessage("Delete document " + name.getText() + "?")
                            .setIcon(android.R.drawable.stat_sys_warning)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    documents.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(itemView.getContext(), "Document not deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            });
        }
    }

    private List<Document> documents;

    protected DocumentAdapter(List<Document> documents) {
        this.documents = new ArrayList<>(documents);
    }

    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View docView = inflater.inflate(R.layout.document, parent, false);

        return new ViewHolder(docView);
    }

    public void onBindViewHolder(DocumentAdapter.ViewHolder viewHolder, int position) {
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
