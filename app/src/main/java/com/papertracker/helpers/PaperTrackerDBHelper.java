package com.papertracker.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.papertracker.models.Document;
import com.papertracker.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaperTrackerDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "PaperTracker DB";
    private static final String ITEMS_TABLE_NAME = "Items";
    private static final String DOCUMENTS_TABLE_NAME = "Documents";

    private static final String ID_COLUMN = "_id";
    private static final String NAME_COLUMN = "Name";
    private static final String DESCRIPTION_COLUMN = "Description";
    private static final String EXPIRATION_DATE_COLUMN = "ExpirationDate";

    private static final String ITEM_ID_COLUMN = "ItemID";

    public PaperTrackerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createItemsTable = "CREATE TABLE " +
                ITEMS_TABLE_NAME + " (" +
                ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN + " text, " +
                DESCRIPTION_COLUMN + " text)";

        String createDocumentsTable = "CREATE TABLE " +
                DOCUMENTS_TABLE_NAME + " (" +
                ITEM_ID_COLUMN + " INTEGER, " +
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
            int id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
            items.add(new Item(name, details, id));
        }

        cursor.close();
        return items;
    }

    public Item getItem(int itemID) {
        return null;
    }

    public ArrayList<Document> getDocuments(int itemID) {
        ArrayList<Document> documents = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DOCUMENTS_TABLE_NAME, null, ITEM_ID_COLUMN + " = ?", new String[] {String.valueOf(itemID)}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            Date expirationDate;
            try {
                expirationDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").parse(cursor.getString(cursor.getColumnIndex(EXPIRATION_DATE_COLUMN)));
            } catch (ParseException e) {
                Log.e("ERROR", "Invalid expiration date entry in the DB for " + name);
                return documents;
            }
            documents.add(new Document(name, expirationDate));
        }

        cursor.close();
        return documents;
    }

    public long addItem(String name, String details) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, name);
        values.put(DESCRIPTION_COLUMN, details);
        long itemID = db.insert(ITEMS_TABLE_NAME, null, values);
        db.close();

        return itemID;
    }

    public void deleteItem(int itemID) {}

    public void addDocument(int itemID, String name, Date expirationDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_ID_COLUMN, itemID);
        values.put(NAME_COLUMN, name);
        values.put(EXPIRATION_DATE_COLUMN, expirationDate.toString());
        db.insert(DOCUMENTS_TABLE_NAME, null, values);
        db.close();
    }

    public void addDocuments(long itemID, ArrayList<Document> documents) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Document doc : documents) {
            values.put(ITEM_ID_COLUMN, itemID);
            values.put(NAME_COLUMN, doc.getName());
            values.put(EXPIRATION_DATE_COLUMN, doc.getExpirationDate().toString());
            db.insert(DOCUMENTS_TABLE_NAME, null, values);
            values.clear();
        }
        db.close();
    }

    public void deleteDocument(int documentID) {}

}
