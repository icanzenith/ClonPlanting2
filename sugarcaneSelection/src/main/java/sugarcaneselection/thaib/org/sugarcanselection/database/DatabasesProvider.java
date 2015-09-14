package sugarcaneselection.thaib.org.sugarcanselection.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;

public class DatabasesProvider extends ContentProvider {

    static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Create Table
    static {
        matcher.addURI(Database.AUTHORITY, null, 0);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_MYCLONE, 1);
        matcher.addURI(Database.AUTHORITY, Database.Distinct, 2);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_MYFAMILY, 3);
        matcher.addURI(Database.AUTHORITY, Database.TABLE_MYSTANDARDCLONE, 4);

    }

    private mySQLiteHelper myHelper;

    public DatabasesProvider() {
        super();
    }

    @Override
    public boolean onCreate() {
        myHelper = new mySQLiteHelper(getContext(), Database.AUTHORITY, null, 1);
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
                qb.setTables(Database.TABLE_MYCLONE);
                c = qb.query(db, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case 2:
                qb.setTables(Database.TABLE_MYCLONE);
                qb.setDistinct(true);
//                String groupBy = "RowNumber,SavedStatus";
//                String having = "MAX(SavedStatus)";
                String groupBy = null;
                String having = null;
                c = qb.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);

                break;
            case 3:
                qb.setTables(Database.TABLE_MYFAMILY);
                c = qb.query(db, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            case 4:
                qb.setTables(Database.TABLE_MYSTANDARDCLONE);
                c = qb.query(db, projection, selection, selectionArgs, null,
                        null, sortOrder);
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
                rowid = db.insert(Database.TABLE_MYCLONE, null, values);
                break;
            case 3:
                rowid = db.insert(Database.TABLE_MYFAMILY, null, values);
                break;
            case 4:
                rowid = db.insert(Database.TABLE_MYSTANDARDCLONE, null, values);
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
                result = db.delete(Database.TABLE_MYCLONE, selection,
                        selectionArgs);
                break;
            case 3:
                result = db.delete(Database.TABLE_MYFAMILY, selection,
                        selectionArgs);
                break;
            case 4:
                result = db.delete(Database.TABLE_MYSTANDARDCLONE, selection,
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
                rowid = db.update(Database.TABLE_MYCLONE, values, selection,
                        selectionArgs);
                break;
            case 3:
                rowid = db.update(Database.TABLE_MYFAMILY, values, selection,
                        selectionArgs);
                break;

            case 4:
                rowid = db.update(Database.TABLE_MYSTANDARDCLONE, values, selection,
                        selectionArgs);
                break;


        }
        return rowid;
    }

}
