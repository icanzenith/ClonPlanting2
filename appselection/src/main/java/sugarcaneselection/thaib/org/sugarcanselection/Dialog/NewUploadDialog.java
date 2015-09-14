package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 3/25/15 AD.
 */
public class NewUploadDialog extends DialogFragment {
    private static final String PICTYPE_UPPER = "Upper";
    private static final String PICTYPE_LOWER = "Lower";
    private static final String PICTYPE_FULL = "Full";
    private ContentResolver contentResolver;
    private BaseApplication baseApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentResolver = getActivity().getContentResolver();
        baseApplication = (BaseApplication) getActivity().getApplication();
    }


    private void UploadCloneData() {
        //TODO Query Clone First
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.CHANGESTATUS +" = 1";
        String[] selectionArgs = null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);

        while (c != null && c.moveToNext()) {

            //TODO Create Param Here And AjaxCallback Here


        }

        c.close();

    }

    private void UploadCloneStandardSpecieData() {
        //TODO Query Specie Data First
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.CHANGESTATUS +"= 1";
        String[] selectionArgs = null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
        }

        c.close();

    }

    private void UploadClonePicture() {
        //TODO CHECK BY Picture Status
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String selection = Columns.CHANGESTATUS + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c != null) {
            if (c.getCount() != 0) {
                while (c.moveToNext()) {

                    ArrayList<HashMap<String,Object>> listData = new ArrayList<>();

                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICFULLSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.CLONECODE)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.FULLPICURL))));
                        ob.put(Params.PicType, PICTYPE_FULL);
                        listData.add(ob);
                    }
                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICLOWSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.CLONECODE)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.LOWERPICURL))));
                        ob.put(Params.PicType, PICTYPE_LOWER);
                        listData.add(ob);
                    }
                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICUPSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.CLONECODE)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.UPPERPICURL))));
                        ob.put(Params.PicType, PICTYPE_UPPER);
                        listData.add(ob);
                    }
                }
            }
        }


    }

    private void UploadStandardPicture() {
        //TODO CHECK BY Picture Status
        //TODO ใช้ Family กับ Rownumber
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
        String[] projection = null;
        String selection = Columns.CHANGESTATUS + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c != null) {
            if (c.getCount() != 0) {
                while (c.moveToNext()) {

                    ArrayList<HashMap<String,Object>> listData = new ArrayList<>();

                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICFULLSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                        ob.put(Params.ROWNUMBER,c.getInt(c.getColumnIndex(Columns.PLANTORDER)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.FULLPICURL))));
                        ob.put(Params.PicType, PICTYPE_FULL);
                        listData.add(ob);
                    }
                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICLOWSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                        ob.put(Params.ROWNUMBER,c.getInt(c.getColumnIndex(Columns.PLANTORDER)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.LOWERPICURL))));
                        ob.put(Params.PicType, PICTYPE_LOWER);
                        listData.add(ob);
                    }
                    if (c.getInt(c.getColumnIndex(Columns.UPLOADPICUPSTATUS)) == 1) {
                        HashMap<String, Object> ob = new HashMap<String, Object>();
                        ob.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                        ob.put(Params.ROWNUMBER,c.getInt(c.getColumnIndex(Columns.PLANTORDER)));
                        ob.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        ob.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        ob.put(Params.UPLOAD, new File(c.getString(c.getColumnIndex(Columns.UPPERPICURL))));
                        ob.put(Params.PicType, PICTYPE_UPPER);
                        listData.add(ob);
                    }
                }
            }
        }

    }
}
