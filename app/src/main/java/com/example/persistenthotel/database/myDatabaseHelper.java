package com.example.persistenthotel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.persistenthotel.model.Guest;

public class myDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "hotel.db";
    public static final String TABLE_NAME= "guests";
    public static final String COLUMN_GUEST_ID = "guest_id";
    public static final String COLUMN_GUEST_PREFIX = "guest_prefix";
    public static final String COLUMN_GUEST_NAME = "guest_name";
    public static final String COLUMN_DATE_MADE ="date_made";
    public static final String COLUMN_HOTEL_NUMBER ="hotel_number";
    public static final String COLUMN_PASSWORD ="password";

    public static final int DB_VERSION = 1;

    public myDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("TAG_H: ", "hi-----");

        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_GUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GUEST_PREFIX + " TEXT, " +
                COLUMN_GUEST_NAME + " TEXT, " +
                COLUMN_DATE_MADE + " TEXT, " +
                COLUMN_HOTEL_NUMBER + " TEXT, "+
                COLUMN_PASSWORD+" TEXT)";

        Log.d("TAG_H: ", createTable);
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG_H: ", "Database making");

        String updateTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(updateTable);
        onCreate(db);
    }

    public void addNewGuest(Guest guest){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GUEST_PREFIX, guest.getPrefix());
        contentValues.put(COLUMN_GUEST_NAME, guest.getActualName());
        contentValues.put(COLUMN_DATE_MADE, guest.getDateMade());
        contentValues.put(COLUMN_HOTEL_NUMBER, guest.getRoomNumber());
        contentValues.put(COLUMN_PASSWORD, guest.getPassword());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db = null;
    }

    public Cursor readAllGuests() {

        Cursor allGuests = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null, null);
        return allGuests;
    }

    public void deleteGuest(Guest deleteGuest) {
        String delete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_GUEST_ID + " = " + deleteGuest.getGuestID();
        getWritableDatabase().execSQL(delete);
    }

}
