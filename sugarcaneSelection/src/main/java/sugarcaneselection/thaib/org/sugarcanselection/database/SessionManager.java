package sugarcaneselection.thaib.org.sugarcanselection.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.LoginActivity;
import sugarcaneselection.thaib.org.sugarcanselection.MenuActivity;
import sugarcaneselection.thaib.org.sugarcanselection.UserDataItem;

/**
* Created by Jitpakorn on 1/7/15 AD.
*/
public class SessionManager {

    public static final String PREF_NAME = "SugarCaneBreeding";
    //Keyname
    public static final String KEY_NAME = "name";
    public static final String KEY_Surname = "surname";
    public static final String KEY_Username = "username";
    public static final String KEY_Password = "password";
    public static final String KEY_UserID = "userid";
    public static final String KEY_LineID = "lineid";
    public static final String KEY_Email = "email";
    public static final String KEY_FacebookAccount = "facebookaccount";
    public static final String KEY_MobileNumber = "mobilenumber";
    public static final String KEY_WorkAddress = "workaddress";
    public static final String KEY_WorkTelephone = "worktelephone";
    public static final String KEY_WorkPlace = "workplace";
    public static final String KEY_PlaceCode = "placecode";
    public static final String KEY_PictureUrl = "pictureURL";
    public static final String KEY_PictureUUID = "pictureuuid";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_DataItem = "dataitem";
    private static final String ARG_POSITION = "position";
    private static final String A_TEAM = "ATEAM";
    private static final String B_TEAM = "BTEAM";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    UserDataItem item;
    Activity activity;
    int PRIVATE_MODE = 0;
    private int UserID;
    private String Username;
    private int Authority;
    private String Password;
    private String Name;
    private String Sector;
    private String Tel;
    private String Email;
    private String Address;
    private String PicUrl;
    private String UUID;
    private String WorkPlace;
    private String LineID;
    private String FacebookID;
    private String WorkPosition = null;

    public SessionManager(Context context, Activity activity) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        this.activity = activity;
    }

    public void createLoginSession(UserDataItem item) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putInt("UserID", item.getUserID());
        editor.putString("UserName", item.getUsername());
        editor.putInt("Authority", item.getAuthority());
        editor.putString("Password", item.getPassword());
        editor.putString("Name", item.getName());
        editor.putString("Sector", item.getSector());
        editor.putString("Tel", item.getTel());
        editor.putString("Email", item.getEmail());
        editor.putString("Address", item.getAddress());
        editor.putString("PicUrl", item.getPicUrl());
        editor.putString("UUID", item.getUUID());
        editor.putString("WorkPlace", item.getWorkPlace());
        editor.putString("LineID", item.getLineID());
        editor.putString("FacebookID", item.getFacebookID());
        System.out.println("Create New Session");
        editor.commit();


    }

    public HashMap<String, String> getUserDetail() {

        return null;
    }

    public void checkLogin() {
        if (this.isLoggedIn()) {

            UserDataItem ud = new UserDataItem();

            ud.setUserID(pref.getInt("UserID", 0));
            ud.setUsername(pref.getString("UserName", null));
            ud.setAuthority(pref.getInt("Authority", 0));
            ud.setSector(pref.getString("Sector", null));
            ud.setPicUrl(pref.getString("PicUrl", null));
            ud.setUUID(pref.getString("UUID", null));
            ud.setName(pref.getString("Name", null));
            ud.setWorkPlace(pref.getString("WorkPlace", null));
            ud.setTel(pref.getString("Tel", null));
            ud.setEmail(pref.getString("Email", null));
            ud.setLineID(pref.getString("LineID", null));
            ud.setFacebookID(pref.getString("FacebookID", null));

            BaseApplication mApplication = (BaseApplication) _context;
            mApplication.setUserdata(ud);
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MenuActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
            activity.finish();

        }
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void Logout(Activity activity) {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        SQLiteDatabase db = _context.openOrCreateDatabase("CloneSelection", Context.MODE_WORLD_WRITEABLE, null);

        /**
         * Drop Table
         * */
        db.execSQL(SQLiteCommand.DROP_TABLE_MYCLONE);
        db.execSQL(SQLiteCommand.DROP_TABLE_MYFAMILY);
        db.execSQL(SQLiteCommand.DROP_TABLE_MYSTANDARDCLONE);

        /**
         * Re Create Table
         * */
        db.execSQL(SQLiteCommand.CREATE_TABLE_MYCLONE);
        db.execSQL(SQLiteCommand.CREATE_TABLE_MYFAMIY);
        db.execSQL(SQLiteCommand.CREATE_TABLE_STANDARD);

        Log.d(" Database ", "Created Table!!!!");
        db.close();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        activity.finish();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(IS_LOGIN, false);
    }

    public void selectWorkPosition(int team) {
        switch (team) {
            case 1:
                WorkPosition = A_TEAM;
                editor.putString(ARG_POSITION, WorkPosition);
                break;
            case 2:
                WorkPosition = B_TEAM;
                editor.putString(ARG_POSITION, WorkPosition);
                break;
        }
    }


}
