package sugarcaneselection.thaib.org.clonplanting2.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.dialog.SentCloneEditDialog;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.OperationType;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

/**
 * Created by Jitpakorn on 4/25/15 AD.
 */
public class UpdateDataBaseAsyncTask extends AsyncTask<Void, Void, Void> {
    onFinishUpdate mFinishUpdate;
    private Context context;
    private String SentFrom;
    private String CloneCode;
    private String SentTo;
    private Integer SentAmount;
    private int operationType;
    private CloneListItem item;
    SentCloneEditDialog d;

    public UpdateDataBaseAsyncTask(DialogFragment d, CloneListItem item, Context context, int operationType) {
        mFinishUpdate = (onFinishUpdate) d;
        this.d = (SentCloneEditDialog) d;

        CloneCode = item.getCloneCode();
        SentTo = item.getSentTo();
        SentAmount = item.getSentAmount();
        this.item = item;
        this.context = context;
        this.operationType = operationType;
        BaseApplication b = (BaseApplication) context.getApplicationContext();
        SentFrom = b.getUserDataitem().getSector();
    }

    boolean uploading;

    @Override
    protected Void doInBackground(final Void... params) {
        uploading = true;
        final ContentResolver resolver = context.getContentResolver();
        final Uri uri = Uri.parse(context.getString(R.string.uri_clonetosent));
        if (operationType == OperationType.TYPE_DELETE) {
            Log.d("Get ITEM ID",item.getObjectID()+0);

            if (item.getObjectID() != null) {
                final ParseQuery<ParseObject> parseQuery = new ParseQuery(ParseClass.Clone);
                parseQuery.whereEqualTo("objectId", item.getObjectID());
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                                                @Override
                                                public void done(final List<ParseObject> parseObjects, ParseException e) {
                                                    for (int i = 0; i < parseObjects.size(); i++) {
                                                        parseObjects.get(i).deleteInBackground(new DeleteCallback() {
                                                            @Override
                                                            public void done(ParseException e) {
                                                                if (e == null) {
                                                                    String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                                                                    String[] selectionArgs = {CloneCode, SentTo};
                                                                    resolver.delete(uri, where, selectionArgs);
                                                                } else {
                                                                    parseQuery.cancel();
                                                                }
                                                                uploading = false;
                                                            }
                                                        });
                                                    }
                                                }


                                            }

                );
            } else {
                String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                String[] selectionArgs = {CloneCode, SentTo};
                resolver.delete(uri, where, selectionArgs);
                uploading = false;
            }

        } else if (operationType == OperationType.TYPE_UPDATE) {
            Log.d("Check ","UPDATE");
            BaseApplication b = (BaseApplication) context.getApplicationContext();
            final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
            query.whereEqualTo(Columns.CloneCode, CloneCode);
            query.whereEqualTo(Columns.SentTo, SentTo);
            query.whereEqualTo("PlaceTest", b.getUserDataitem().getSector());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(final List<ParseObject> parseObjects, ParseException e) {

                    if (e == null) {
                        if (parseObjects.size() == 0) {
                            final ParseObject object = new ParseObject(ParseClass.Clone);
                            object.put(Columns.CloneCode, CloneCode);
                            object.put(Columns.SentTo, SentTo);
                            object.put("PlaceTest", SentFrom);
                            object.put(Columns.SentAmount, SentAmount);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        ContentValues v = new ContentValues();
                                        v.put(Columns.SentAmount, SentAmount);
                                        v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                                        v.put(Columns.ObjectID, object.getObjectId());
                                        d.setObjectID(object.getObjectId());
                                        String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                                        String[] selectionArgs = {CloneCode, SentTo};
                                        resolver.update(uri, v, where, selectionArgs);


                                    } else {
                                        //รอ Object ID ต่อไป
                                        ContentValues v = new ContentValues();
                                        v.put(Columns.SentAmount, SentAmount);
                                        v.put(Columns.UploadStatus, Upload_Status.NOT_UPLOADED);
                                        String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                                        String[] selectionArgs = {CloneCode, SentTo};
                                        resolver.update(uri, v, where, selectionArgs);
                                    }
                                        uploading = false;
                                }
                            });

                        }else {
                            //!=0
                            parseObjects.get(0).put(Columns.SentAmount,SentAmount);
                            parseObjects.get(0).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e==null){
                                        ContentValues v = new ContentValues();
                                        v.put(Columns.SentAmount, SentAmount);
                                        v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                                        String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                                        String[] selectionArgs = {CloneCode, SentTo};
                                        resolver.update(uri, v, where, selectionArgs);
                                    }else{
                                        ContentValues v = new ContentValues();
                                        v.put(Columns.SentAmount, SentAmount);
                                        v.put(Columns.UploadStatus, Upload_Status.NOT_UPLOADED);
                                        String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                                        String[] selectionArgs = {CloneCode, SentTo};
                                        resolver.update(uri, v, where, selectionArgs);
                                    }
                                    uploading = false;
                                }
                            });
                        }


                    } else {

                        ContentValues v = new ContentValues();
                        v.put(Columns.SentAmount, SentAmount);
                        v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                        String where = Columns.CloneCode + "= ? AND " + Columns.SentTo + " = ?";
                        String[] selectionArgs = {CloneCode, SentTo};
                        resolver.update(uri, v, where, selectionArgs);
                        uploading = false;


                    }


                }
            });
        }
        while (uploading) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mFinishUpdate.onFinishUpdate();


    }
}
