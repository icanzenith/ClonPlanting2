package sugarcaneselection.thaib.org.clonplanting2.databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DatabasesProvider extends ContentProvider {

    static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Create Table
    static {
        matcher.addURI(Database.AUTHORITY, null, 0);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_CLONE, 1);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_LAND, 2);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_CLONE_TO_SENT, 3);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_CLONE_PLANTING, 4);

    }

    private mySQLiteHelper myHelper;

    public DatabasesProvider() {
        super();
    }

    @Override
    public boolean onCreate() {
        myHelper = new mySQLiteHelper(getContext(), Database.AUTHORITY, null, Version.DATABASE_Version);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int x = matcher.match(uri);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor c = null;
        switch (x) {
            case 0:
                break;
            case 1:
                qb.setTables(Database.TABLE_CLONE);
                c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 2:
                qb.setTables(Database.TABLE_LAND);
                c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 3:
                qb.setTables(Database.TABLE_CLONE_TO_SENT);
                c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 4:
                qb.setTables(Database.TABLE_CLONE_PLANTING);
                c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }


        return c;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        long rowid = 1;
        Uri newuri = null;
        int x = matcher.match(uri);
        switch (x) {
            case 0:
                break;
            case 1:
                rowid = db.insert(Database.TABLE_CLONE, null, values);
                break;
            case 2:
                rowid = db.insert(Database.TABLE_LAND, null, values);
                break;
            case 3:
                rowid = db.insert(Database.TABLE_CLONE_TO_SENT, null, values);
                break;
            case 4:
                rowid = db.insert(Database.TABLE_CLONE_PLANTING, null, values);
                break;
        }
        newuri = ContentUris.withAppendedId(uri, rowid);
        return newuri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        int result = 0;
        int x = matcher.match(uri);
        switch (x) {
            case 0:
                break;
            case 1:
                result = db.delete(Database.TABLE_CLONE, selection,
                        selectionArgs);
                break;
            case 2:
                result = db.delete(Database.TABLE_LAND, selection,
                        selectionArgs);
                break;
            case 3:
                result = db.delete(Database.TABLE_CLONE_TO_SENT, selection,
                        selectionArgs);
                break;
            case 4:
                result = db.delete(Database.TABLE_CLONE_PLANTING, selection,
                        selectionArgs);
                break;
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        int rowid = 1;
        int x = matcher.match(uri);
        switch (x) {
            case 0:
                break;
            case 1:
                rowid = db.update(Database.TABLE_CLONE, values, selection,
                        selectionArgs);
                break;
            case 2:
                rowid = db.update(Database.TABLE_LAND, values, selection,
                        selectionArgs);
                break;
            case 3:
                rowid = db.update(Database.TABLE_CLONE_TO_SENT, values, selection,
                        selectionArgs);
                break;
            case 4:
                rowid = db.update(Database.TABLE_CLONE_PLANTING, values, selection,
                        selectionArgs);
                break;
        }
        return rowid;
    }

}
