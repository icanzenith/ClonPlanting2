package database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import item.UserDataItem;
import sugarcaneselection.thaib.org.cloneplanting2.main.BaseApplication;
import sugarcaneselection.thaib.org.cloneplanting2.main.HomeActivity;

/**
 * Created by Jitpakorn on 4/22/15 AD.
 */
public class SessionManager {

    public static final String PREF_NAME = "ClonePlanting";
    public static final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    UserDataItem item;
    Activity activity;

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
    private static final String KEY_isLOGIN = "IsLoggedIn";
    private static final String KEY_POSITION = "position";

    public SessionManager(Context context, Activity activity) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        this.activity = activity;
    }

    public void createLoginSession(UserDataItem item) {

        editor.putBoolean(KEY_isLOGIN, true);
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
        editor.commit();

        Log.d("Debug SessionManager","Create Login Session");
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
            mApplication.setUserDataitem(ud);
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, HomeActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
            activity.finish();

        }
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEY_isLOGIN, false);
    }


}
