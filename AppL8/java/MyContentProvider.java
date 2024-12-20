package hua.dit.mobdev.ec.appl8;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;

import hua.dit.mobdev.ec.appl8.db.Sex;
import hua.dit.mobdev.ec.appl8.db.User;
import hua.dit.mobdev.ec.appl8.db.UserDB;

public class MyContentProvider extends ContentProvider {

    private static final String TAG = "MyContentProvider";

    /* Content Provider URI */
    private static final String MY_PROVIDER = "hua.dit.mobdev.ec.appl8.dbcontent";
    private static final String CONTENT_URI_STR = "content://" + MY_PROVIDER + "/user";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STR);

    /* Helper - UriMatcher */
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URI_CODE = 1;
    static {
        uriMatcher.addURI(MY_PROVIDER, "user", URI_CODE);
    }

    private UserDB db;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        db = Room.databaseBuilder(getContext(), UserDB.class, "user_dn.sqlite").build();
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Insert Data: uri=" + uri + " , values=" + values);
        if (uriMatcher.match(uri) == URI_CODE) {
            String userName = values.getAsString("name");
            Integer userAge = values.getAsInteger("age");
            String sexName = values.getAsString("sex");
            // Find Sex ID
            Sex sex = db.sexDao().getSexByName(sexName.trim().toLowerCase());
            if (sex == null)
                throw new RuntimeException("Sex name not found ! sexName=" + sexName);
            // Insert Data and Return Row URI
            long user_id = db.userDao().storeData(new User(userName, userAge, sex.id));
            Log.i(TAG, "Insert Data: NEW User ID: " + user_id);
            return ContentUris.withAppendedId(CONTENT_URI, user_id);
        }

        throw new RuntimeException("Insert Method - Not supported URI: " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query Data: uri=" + uri + " ...");
        // Select Fields, Where Conditions and Order Instructions are being ignored
        if (uriMatcher.match(uri) == URI_CODE)
            return db.userDao().getUserWithSexCursor();
        throw new RuntimeException("Query Method - Not supported URI: " + uri);
    }

    @Override
    public String getType(Uri uri) {
        if (uriMatcher.match(uri) == URI_CODE)
            return "vnd.android.cursor.dir/user";
        throw new RuntimeException("Get Type Method - Not supported URI: " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}