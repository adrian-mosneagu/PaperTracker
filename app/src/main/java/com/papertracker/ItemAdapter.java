package com.papertracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private boolean expanded = false;

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemDetails;
        private Button btnItemDelete;
        private Button btnItemEdit;


        protected ViewHolder(final View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDetails = itemView.findViewById(R.id.tvItemDetails);
            btnItemDelete = itemView.findViewById(R.id.btnItemDelete);
            btnItemEdit = itemView.findViewById(R.id.btnItemEdit);

            itemView.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expanded) {
                        expanded = hideView();
                    } else {
                        expanded = expandView();
                    }
                }
            });

            btnItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext()).setTitle("Confirm deletion")
                            .setMessage("Delete item " + tvItemName.getText() + "?")
                            .setIcon(android.R.drawable.stat_sys_warning)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    items.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(itemView.getContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            });
        }

        private boolean expandView() {
            ImageView ivExpandCard = itemView.findViewById(R.id.expandImage);
            ivExpandCard.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            itemView.findViewById(R.id.expandedLayout).setVisibility(View.VISIBLE);


            List<Document> docList = new ArrayList<>();

            // TODO: This info should be retrieved from DB
            docList.add(new Document("Vigneta", new Date()));
            docList.add(new Document("RCA", new Date()));
            docList.add(new Document("RCA", new Date()));

            RecyclerView rvDocuments = itemView.findViewById(R.id.rvDocuments);
            rvDocuments.setAdapter(new DocumentAdapter(docList));
            rvDocuments.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            return true;
        }

        private boolean hideView() {
            ImageView ivExpandCard = itemView.findViewById(R.id.expandImage);
            ivExpandCard.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            itemView.findViewById(R.id.expandedLayout).setVisibility(View.GONE);

            return false;
        }
    }

    private List<Item> items;

    protected ItemAdapter(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    @NonNull
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item, parent, false);

        return new ItemAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Item item = items.get(position);

        // Set item views based on your views and data model
        TextView name = viewHolder.tvItemName;
        name.setText(item.getName());
        TextView details = viewHolder.tvItemDetails;
        details.setText(item.getDetails());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }
}
