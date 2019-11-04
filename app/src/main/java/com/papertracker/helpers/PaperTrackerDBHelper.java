package com.papertracker.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.papertracker.models.Document;
import com.papertracker.models.Item;

import java.util.ArrayList;
import java.util.Date;

public class PaperTrackerDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PaperTracker DB";
    private static final String ITEMS_TABLE_NAME = "Items";
    private static final String DOCUMENTS_TABLE_NAME = "Documents";

    private static final String ID_COLUMN = "_id";
    private static final String NAME_COLUMN = "Name";
    private static final String DESCRIPTION_COLUMN = "Description";
    private static final String EXPIRATION_DATE_COLUMN = "ExpirationDate";

    public PaperTrackerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createItemsTable = "CREATE TABLE " +
                ITEMS_TABLE_NAME + " (" +
                ID_COLUMN + " blob PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN + " text, " +
                DESCRIPTION_COLUMN + " text)";

        String createDocumentsTable = "CREATE TABLE " +
                DOCUMENTS_TABLE_NAME + " (" +
                ID_COLUMN + " blob PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN + " text, " +
                EXPIRATION_DATE_COLUMN + " datetime)";

        sqLiteDatabase.execSQL(createItemsTable);
        sqLiteDatabase.execSQL(createDocumentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DOCUMENTS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ITEMS_TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            String details = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN));
            items.add(new Item(name, details));
        }

        cursor.close();
        return items;
    }

    public Item getItem(int itemID) {
        return null;
    }

    public ArrayList<Document> getDocuments(int itemID) {
        return null;
    }

    public void addItem(String name, String details) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, name);
        values.put(DESCRIPTION_COLUMN, details);
        db.insert(ITEMS_TABLE_NAME, null, values);
        db.close();

        // TODO: Return created item ID
    }

    public void deleteItem(int itemID) {}

    public void addDocument(int itemID, String name, Date expirationDate) {}

    public void deleteDocument(int documentID) {}

}
