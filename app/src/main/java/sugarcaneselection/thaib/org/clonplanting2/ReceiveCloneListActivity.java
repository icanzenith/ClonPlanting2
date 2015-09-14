package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.adapter.ReceiveCloneListAdapter;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.dialog.ReceiveCloneEditDialog;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneStatus;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.scanner.ReceiveCloneScannerActivity;
import sugarcaneselection.thaib.org.clonplanting2.util.onUpdateData;


public class ReceiveCloneListActivity extends AppCompatActivity implements onUpdateData {

    public static final int TAG_REQUEST_QR = 100;
    private CloneListItem receiveItem;
    private BaseApplication b;
    private ListView mListView;
    private ReceiveCloneListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_clone_list);
        b = (BaseApplication) getApplication();
        p = (SmoothProgressBar) findViewById(R.id.progressBar);

        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ReceiveCloneListAdapter(CreateReceiveListData(),ReceiveCloneListActivity.this);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).getClass() == CloneListItem.class){
                        CloneListItem item = (CloneListItem) parent.getItemAtPosition(position);
                        ReceiveCloneEditDialog dialog = (ReceiveCloneEditDialog) ReceiveCloneEditDialog.newInstance();
                        dialog.setItem((CloneListItem) parent.getItemAtPosition(position));
                        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog);
                        dialog.show(getSupportFragmentManager(), "EditSentDialog");
                        Log.d("Debug recieve Clone","ObjectID "+item.getObjectID());
                        Log.d("Debug recieve Clone","CloneCode "+item.getCloneCode());
                        Log.d("Debug recieve Clone","Sent To"+item.getSentTo());
                        Log.d("Debug recieve Clone","Upload Status "+item.getUploadStatus());

                    }else{

                    }
            }
        });


    }

    private ArrayList<Object> CreateReceiveListData() {

        ArrayList<Object> data = new ArrayList<>();
        String[] PlaceList = getResources().getStringArray(R.array.PlaceCode);

        for (String i: PlaceList) {
            Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
            String[] projection = null;

            String sortOrder = Columns.FromPlace+" ASC";
            String selection = Columns.FromPlace+" = ?";

            String[] selectionArgs = {i};

            ContentResolver r = getContentResolver();
            Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);

            if (c.getCount() != 0){
                data.add(i);
            }

            while ( c != null && c.moveToNext() ) {

                CloneListItem item = new CloneListItem();
                item.setCloneCode(c.getString(c.getColumnIndex((Columns.CloneCode))));
                item.setObjectID(c.getString(c.getColumnIndex(Columns.ObjectID)));
                item.setReceiveAmount(c.getInt(c.getColumnIndex(Columns.ReceiveAmount)));
                item.setUploadStatus(c.getInt(c.getColumnIndex(Columns.UploadStatus)));
                item.setSentFrom(c.getString(c.getColumnIndex(Columns.FromPlace)));
                item.setSentTo(c.getString(c.getColumnIndex(Columns.SentTo)));
                data.add(item);
                if (item.getObjectID() !=null){
                }else{

                }


            }

            c.close();
        }
        //TODO
        return data;
    }

    SmoothProgressBar p;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAG_REQUEST_QR && resultCode == RESULT_OK) {
            if (data != null) {
                p.setVisibility(View.VISIBLE);
                b = (BaseApplication) getApplication();
                //Sent CloneCode Here
                receiveItem = new CloneListItem();
                receiveItem.setCloneCode(data.getStringExtra(AppTag.CLONECODE));
                receiveItem.setReceiveAmount(data.getIntExtra(AppTag.RECEIVE_AMOUNT, 0));
                receiveItem.setSentFrom(data.getStringExtra(AppTag.SENT_FROM));

                final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
                query.whereEqualTo(Columns.CloneCode, receiveItem.getCloneCode());
                query.whereEqualTo(Columns.SentTo, b.getUserDataitem().getSector());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (e == null) {
                            if (parseObjects.size() != 0) {
                                // Update ข้อมูล เพราะมีของเก่า
                                ParseObject object = parseObjects.get(0);
                                PutParseData(object);
                                Log.d("String", "Finish parseObject !=null");

                            } else {
                                //insert ข้อมูลใหม่ เพราะ ยังไม่มีของเก่า
                                PutParseData();
                                Log.d("String", "Finish parse = 0");
                            }

                        } else {
                            query.cancel();
                            Log.d("String", "Finish");
                        }
                    }

                });

            }
        }


    }

    /**
     *
     * เอาของที่มี อยู่ใน server อยู่แล้วมาอัพเดท
     *
     */

    private void PutParseData(final ParseObject object) {
        object.put(Columns.CloneStatus, CloneStatus.Received);
        object.put(Columns.CloneCode, receiveItem.getCloneCode());
        object.put(Columns.SentTo, b.getUserDataitem().getSector());
        object.put(Columns.ReceiveAmount, receiveItem.getReceiveAmount());
        object.put("PlaceTest", receiveItem.getSentFrom());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    receiveItem.setObjectID(object.getObjectId());
                    receiveItem.setUploadStatus(Upload_Status.UPLOADED);
                    onUpdateToDatabase();
                }else{
                    receiveItem.setUploadStatus(Upload_Status.NOT_UPLOADED);
                    onUpdateToDatabase();
                }


            }
        });
    }

    private void PutParseData() {
        final ParseObject object = new ParseObject(ParseClass.Clone);
        object.put(Columns.CloneCode, receiveItem.getCloneCode());
        object.put(Columns.SentTo, b.getUserDataitem().getSector());
        object.put(Columns.ReceiveAmount, receiveItem.getReceiveAmount());
        object.put(Columns.CloneStatus,CloneStatus.Received);
        object.put("PlaceTest", receiveItem.getSentFrom());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    receiveItem.setObjectID(object.getObjectId());
                    receiveItem.setUploadStatus(Upload_Status.UPLOADED);
                    onUpdateToDatabase();
                }else {
                    onUpdateToDatabase();
                }


            }
        });

    }

    private void onUpdateToDatabase() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
        String where = Columns.CloneCode + " = ? AND " + Columns.FromPlace + " = ?";
        String[] args = {receiveItem.getCloneCode(), receiveItem.getSentFrom()};

        ContentValues v = new ContentValues();
        v.put(Columns.CloneCode, receiveItem.getCloneCode());
        v.put(Columns.ReceiveAmount, receiveItem.getReceiveAmount());
        v.put(Columns.ObjectID, receiveItem.getObjectID());
        v.put(Columns.FromPlace, receiveItem.getSentFrom());
        v.put(Columns.UploadStatus, receiveItem.getUploadStatus());

        int update = resolver.update(uri, v, where, args);
        if (update <= 0) {
            resolver.insert(uri, v);
            Log.d("String", "insert Finish" + update);
        } else {
            Log.d("String", "update Finish +" + update);
        }
        Log.d("SampleData",v.toString());
        p.setVisibility(View.GONE);
        refreshListView();
    }

    private void refreshListView(){
        mAdapter.getData().clear();
        mAdapter.setData(CreateReceiveListData());
        mAdapter.notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recieve_clone_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_openQR) {
            OpenQrCode();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void OpenQrCode() {
        Intent intent = new Intent(ReceiveCloneListActivity.this, ReceiveCloneScannerActivity.class);
        startActivityForResult(intent, TAG_REQUEST_QR);
    }

    @Override
    public void updateData() {
        refreshListView();
    }
}

