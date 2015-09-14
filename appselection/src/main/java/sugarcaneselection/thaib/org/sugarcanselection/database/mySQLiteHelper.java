package sugarcaneselection.thaib.org.sugarcanselection.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class mySQLiteHelper extends SQLiteOpenHelper {

    public static final String TAG = "MyDatabase";

    public mySQLiteHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public mySQLiteHelper(Context context, String name, CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLiteCommand.CREATE_TABLE_MYCLONE);
        db.execSQL(SQLiteCommand.CREATE_TABLE_MYFAMIY);
        db.execSQL(SQLiteCommand.CREATE_TABLE_STANDARD);
        Log.d(TAG, "Created Table!!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQLiteCommand.DROP_TABLE_MYCLONE);
        db.execSQL(SQLiteCommand.DROP_TABLE_MYFAMILY);
        db.execSQL(SQLiteCommand.DROP_TABLE_MYSTANDARDCLONE);
        Log.d(TAG, "Upgrade Table");
        onCreate(db);
    }

}
