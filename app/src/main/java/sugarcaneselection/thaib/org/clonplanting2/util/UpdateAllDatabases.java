package sugarcaneselection.thaib.org.clonplanting2.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.adapter.SentCloneListAdapter;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

/**
 * Created by Jitpakorn on 4/26/15 AD.
 */
public class UpdateAllDatabases  extends AsyncTask<Void,Void,Void>{

    private Activity context;
    private SentCloneListAdapter baseAdapter;
    private String SentTo;
    private uploadClonebyClone onUpdate;
    private BaseApplication b;

    public UpdateAllDatabases(Activity context,String sentTo,BaseAdapter adapter) {
        this.context = context;
        onUpdate = (uploadClonebyClone) context;
        b = (BaseApplication) context.getApplication();
        if (adapter != null){
            baseAdapter = (SentCloneListAdapter) adapter;
            SentTo= sentTo;
        }
    }
        private Cursor c;
        private boolean uploading = true;
        private ContentResolver r;
    @Override
    protected Void doInBackground(Void... params) {

        final Uri uri = Uri.parse(context.getResources().getString(R.string.uri_clonetosent));
        String[] projection = null;
        String sortOrder = null;
        String selection =Columns.SentTo+" = ?";
        String[] selectionArgs ={SentTo};
        r = context.getContentResolver();
        c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c.moveToNext()) {
            uploading = true;
            if (c.getInt(c.getColumnIndex(Columns.UploadStatus))==Upload_Status.NOT_UPLOADED){

                if (c.getString(c.getColumnIndex(Columns.ObjectID)) != null){
                    final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
                    query.whereEqualTo("objectId",c.getString(c.getColumnIndex(Columns.ObjectID)));
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            if (e == null){
                                final ParseObject o= parseObjects.get(0);
                                o.put(Columns.CloneCode, c.getString(c.getColumnIndex(Columns.CloneCode)));
                                o.put(Columns.SentAmount, c.getInt(c.getColumnIndex(Columns.SentAmount)));
                                o.put(Columns.SentTo, c.getString(c.getColumnIndex(Columns.SentTo)));
                                o.put("PlaceTest",b.getUserDataitem().getSector());
                                o.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        for (int i = 0; i < baseAdapter.getDataSet().size(); i++) {
                                            if (baseAdapter.getDataSet().get(i).getCloneCode().equals(c.getString(c.getColumnIndex(Columns.CloneCode)))) {
                                                baseAdapter.getDataSet().get(i).setUploadStatus(Upload_Status.UPLOADED);
                                                baseAdapter.getDataSet().get(i).setObjectID(o.getObjectId());
                                                context.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        onUpdate.UpdateListView();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }else{
                                query.cancel();
                            }


                        }
                    });
                }else{
                    final ParseObject object = new ParseObject(ParseClass.Clone);
                    object.put(Columns.CloneCode, c.getString(c.getColumnIndex(Columns.CloneCode)));
                    object.put(Columns.SentAmount, c.getInt(c.getColumnIndex(Columns.SentAmount)));
                    object.put(Columns.SentTo,c.getString(c.getColumnIndex(Columns.SentTo)));
                    object.put("PlaceTest",b.getUserDataitem().getSector());

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                ContentValues v = new ContentValues();
                                v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                                v.put(Columns.ObjectID, object.getObjectId());
                                String where = "_id = " + c.getInt(c.getColumnIndex("_id"));
                                r.update(uri, v, where, null);
                                for (int i = 0; i < baseAdapter.getDataSet().size(); i++) {
                                    if (baseAdapter.getDataSet().get(i).getCloneCode().equals(c.getString(c.getColumnIndex(Columns.CloneCode)))) {
                                        baseAdapter.getDataSet().get(i).setUploadStatus(Upload_Status.UPLOADED);
                                        baseAdapter.getDataSet().get(i).setObjectID(object.getObjectId());
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                onUpdate.UpdateListView();
                                            }
                                        });
                                    }
                                }
                            } else {

                                ContentValues v = new ContentValues();
                                v.put(Columns.UploadStatus, Upload_Status.NOT_UPLOADED);
                                String where = "_id = " + c.getInt(c.getColumnIndex("_id"));
                                r.update(uri, v, where, null);
                                for (int i = 0; i < baseAdapter.getDataSet().size(); i++) {
                                    if (baseAdapter.getDataSet().get(i).getCloneCode().equals(c.getString(c.getColumnIndex(Columns.CloneCode)))) {
                                        baseAdapter.getDataSet().get(i).setUploadStatus(Upload_Status.NOT_UPLOADED);
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                onUpdate.UpdateListView();
                                            }
                                        });
                                    }
                                }
                                Toast.makeText(context,"เช็ค เน็ต ดิ๊",Toast.LENGTH_LONG).show();
                                uploading = false;
                                return ;

                            }
                            uploading = false;

                        }
                    });
                }
                while (uploading){

                }


            }

        }

        c.close();

        return null;


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onUpdate.UpdateAllFinished();
        Toast.makeText(context,"Update สำเร็จ",Toast.LENGTH_LONG).show();
    }
}
