package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.database.SessionManager;

public class BaseApplication extends Application {
    SessionManager sessionManager;
    private UserDataItem userdata;
    private CloneDataItem standardSpecieData;
    private CloneDataItem cloneDataItem;
    private String UpperPicPath;
    private String LowerPicPath;
    private String FullPicPath;

    private String imageUploadPath;

    private String LastFamilyDo;



    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(this, null);

    }

    public UserDataItem getUserdata() {
        return userdata;
    }

    public void setUserdata(UserDataItem userdata) {
        this.userdata = userdata;
    }

    public void getCloneData(String CloneCode) {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String selection = "clonecode = ?";
        String[] selectionArgs = {CloneCode};
        String sortOrder = null;

        Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                CloneDataItem d = new CloneDataItem();
                //TODO GET CLONE DATA HERE

                this.setCloneDataItem(d);

            }
            c.close();
        }

    }

    public String getImageUploadPath() {
        return imageUploadPath;
    }

    public void setImageUploadPath(String imageUploadPath) {
        this.imageUploadPath = imageUploadPath;
    }

    public String getLowerPicPath() {
        return LowerPicPath;
    }

    public void setLowerPicPath(String lowerPicPath) {
        LowerPicPath = lowerPicPath;
    }

    public String getUpperPicPath() {
        return UpperPicPath;
    }

    public void setUpperPicPath(String upperPicPath) {
        UpperPicPath = upperPicPath;
    }

    public CloneDataItem getCloneDataItem() {
        return cloneDataItem;
    }

    public void setCloneDataItem(CloneDataItem cloneDataItem) {
        this.cloneDataItem = cloneDataItem;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
//
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public CloneDataItem getStandardSpecieData() {
        Log.d("Standard", " Get");
        return standardSpecieData;
    }

    public void setStandardSpecieData(CloneDataItem standardSpecieData) {
        Log.d("Standard", " Set");
        this.standardSpecieData = standardSpecieData;
    }


    public String getFullPicPath() {
        return FullPicPath;
    }

    public void setFullPicPath(String fullPicPath) {
        FullPicPath = fullPicPath;
    }

    public String getLastFamilyDo() {
        return LastFamilyDo;
    }

    public void setLastFamilyDo(String lastFamilyDo) {
        LastFamilyDo = lastFamilyDo;
    }
}
