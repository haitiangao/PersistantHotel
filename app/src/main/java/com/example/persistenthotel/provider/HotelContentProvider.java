package com.example.persistenthotel.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.persistenthotel.database.myDatabaseHelper;

public class HotelContentProvider extends ContentProvider {

    private String authority = "com.example.persistenthotel.provider.HotelContentProvider";
    private String url = "content://"+authority+"/"+ myDatabaseHelper.TABLE_NAME;

    private static final int SINGLE_GUEST = 1;
    private static final int ALL_GUEST = 2;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    private myDatabaseHelper databaseHelper = null;


    @Override
    public boolean onCreate() {


        uriMatcher.addURI(authority, "guests/#", SINGLE_GUEST);
        uriMatcher.addURI(authority, "guests", ALL_GUEST);

        try {
            databaseHelper = new myDatabaseHelper(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (databaseHelper != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor guestCursor = null;

        Log.d("LOG_H","Hello");

        switch (uriMatcher.match(uri)){
            case ALL_GUEST:
                guestCursor = databaseHelper.readAllGuests();
                break;

            case SINGLE_GUEST:
                Log.d("TAG_X", "LPSegment: " + uri.getLastPathSegment());
                guestCursor = databaseHelper.readSingleGuest(uri.getLastPathSegment());
                break;
        }
        return guestCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
