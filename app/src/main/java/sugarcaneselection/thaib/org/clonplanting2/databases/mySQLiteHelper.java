package sugarcaneselection.thaib.org.clonplanting2.databases;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class mySQLiteHelper extends SQLiteOpenHelper {

    public static final String TAG = "MyDatabase";

    public mySQLiteHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    public mySQLiteHelper(Context context, String name, CursorFactory factory,int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Create_CloneFromMe);
        db.execSQL(Create_CloneToMe);
        db.execSQL(Create_Land);
        db.execSQL(Create_TABLE_PLANTING);
        Log.d(TAG, "Created Table!!!!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE_CloneFromMe);
        db.execSQL(DROP_TABLE_CloneToMe);
        db.execSQL(DROP_TABLE_LAND);
        db.execSQL(DROP_TABLE_PLANTING);
        Log.d(TAG, "Upgrade Table");

        onCreate(db);
    }

    public static String Create_CloneFromMe="CREATE TABLE "+Database.TABLE_CLONE_TO_SENT+"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            Columns.ObjectID+" TEXT," +
            Columns.CloneCode+" TEXT," +
            Columns.CloneType+" INTEGER," +
            Columns.SentAmount+" INTEGER," +
            Columns.FromPlace+" TEXT," +
            Columns.SentTo+" TEXT," +
            Columns.SendingTime+" TEXT," +
            Columns.UploadStatus+" INTEGER"+
            ")";

    public static String Create_CloneToMe="CREATE TABLE "+Database.TABLE_CLONE+"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            Columns.ObjectID+" TEXT," +
            Columns.CloneCode+" TEXT," +
            Columns.Replication+" TEXT," +
            Columns.CloneType+" INTEGER," +
            Columns.SentAmount+" INTEGER," +
            Columns.ReceiveAmount+" INTEGER," +
            Columns.PlantedAmount +" INTEGER," +
            Columns.SurviveAmount +" INTEGER," +
            Columns.PlanterID+" INTEGER," +
            Columns.FromPlace+" TEXT," +
            Columns.SentTo+" TEXT," +
            Columns.LandID+" TEXT," +
            Columns.RowNumber+" INTEGER," +
            Columns.UploadStatus+" INTEGER"+
            ")";

    public static String Create_Land = "CREATE TABLE "+Database.TABLE_LAND+"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            Columns.ObjectID+" TEXT," +
            Columns.LandID+" INTEGER," +
            Columns.LandName+" TEXT," +
            Columns.LandNumber+" INTEGER," +
            Columns.RowAmount+" INTEGER," +
            Columns.Latitude +" REAL," +
            Columns.Longitude +" REAL," +
            Columns.LandSize+" REAL," +
            Columns.LandWidth+" REAL," +
            Columns.LandLength+" REAL," +
            Columns.LandAddress+" TEXT," +
            Columns.Sector+" TEXT," +
            Columns.UserCreate+" INTEGER," +
            Columns.UploadStatus+" INTEGER"+
            ")";

    public static String Create_TABLE_PLANTING = "CREATE TABLE "+Database.TABLE_CLONE_PLANTING+"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            Columns.ObjectID+" TEXT," +
            Columns.CloneCode+" TEXT," +
            Columns.ReceiveCloneID+" TEXT," +
            Columns.Replication+" TEXT," +
            Columns.LandID+" INTEGER," +
            Columns.PlantedAmount +" INTEGER," +
            Columns.SurviveAmount+" INTEGER," +
            Columns.RowNumber+" INTEGER," +
            Columns.UploadStatus+" INTEGER" +

            ")";


    public static final String DROP_TABLE_CloneFromMe = "DROP TABLE IF EXISTS "+Database.TABLE_CLONE_TO_SENT;
    public static final String DROP_TABLE_CloneToMe = "DROP TABLE IF EXISTS "+Database.TABLE_CLONE;
    public static final String DROP_TABLE_LAND = "DROP TABLE IF EXISTS "+Database.TABLE_LAND;
    public static final String DROP_TABLE_PLANTING = "DROP TABLE IF EXISTS "+Database.TABLE_CLONE_PLANTING;

}
