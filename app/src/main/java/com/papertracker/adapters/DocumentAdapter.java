package com.papertracker.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.papertracker.R;
import com.papertracker.models.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView expirationDate;
        private ImageButton deleteButton;
        private ImageView imageViewWarning;

        protected ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvDocName);
            expirationDate = itemView.findViewById(R.id.tvDocExpirationDate);
            deleteButton = itemView.findViewById(R.id.btDocDelete);
            imageViewWarning = itemView.findViewById(R.id.ivWarning);

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

    private ArrayList<Document> documents;

    protected DocumentAdapter(ArrayList<Document> documents) {
        Date current = new Date();
        for (Document doc : documents) {
            doc.setDaysLeft((doc.getExpirationDate().getTime() - current.getTime())/ (1000 * 60 * 60 * 24));
        }
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

        if (doc.getDaysLeft() < 1) {
            viewHolder.imageViewWarning.setVisibility(View.VISIBLE);
            viewHolder.imageViewWarning.setImageResource(R.drawable.ic_warning_red_24dp);
        } else if (doc.getDaysLeft() < 7) {
            viewHolder.imageViewWarning.setVisibility(View.VISIBLE);
        }
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
