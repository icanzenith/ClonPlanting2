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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sugarcaneselection.thaib.org.clonplanting2.adapter.SentCloneListAdapter;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.dialog.SentCloneEditDialog;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.OperationType;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.scanner.SentCloneScannerActivity;
import sugarcaneselection.thaib.org.clonplanting2.util.EditSentDialogInterface;
import sugarcaneselection.thaib.org.clonplanting2.util.GsonTransformer;
import sugarcaneselection.thaib.org.clonplanting2.util.UpdateAllDatabases;
import sugarcaneselection.thaib.org.clonplanting2.util.onUploadData;
import sugarcaneselection.thaib.org.clonplanting2.util.uploadClonebyClone;

public class SentCloneListActivity extends AppCompatActivity implements EditSentDialogInterface,onUploadData,uploadClonebyClone {

    public static final int TAG_REQUEST_QR = 100;

    private String ToPlaceCode;
    //TODO แก้ไข หลังจาก Login
    private String FromPlaceCode;

    private TextView tvToPlaceCode;
    private TextView tvFromPlaceCode;

    private ListView listView;
    private SentCloneListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication b = (BaseApplication) getApplication();
        FromPlaceCode = b.getUserDataitem().getSector();
        setContentView(R.layout.activity_select_place_to_sent_clone);

        p = (ProgressBar) findViewById(R.id.progressBar);

        tvFromPlaceCode = (TextView) findViewById(R.id.tvFromPlaceCode);
        tvToPlaceCode = (TextView) findViewById(R.id.tvToPlaceCode);
        ToPlaceCode = getIntent().getStringExtra(AppTag.PLACECODE);
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new SentCloneListAdapter(SentCloneListActivity.this,CreateSentAmountDataSet());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CloneListItem item = (CloneListItem) parent.getItemAtPosition(position);
                SentCloneEditDialog d = (SentCloneEditDialog) SentCloneEditDialog.newInstance(item.getCloneCode(),item.getSentAmount(),ToPlaceCode,item.getUploadStatus(),item.getObjectID());
                d.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog);
                d.show(getSupportFragmentManager(),"EditSentCloneDialog");


            }
        });



        tvFromPlaceCode.setText(FromPlaceCode);
        tvToPlaceCode.setText(ToPlaceCode);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_sent_clone_list, menu);
        return true;
    }


    private void OpenQrCode() {
        Intent intent = new Intent(SentCloneListActivity.this,SentCloneScannerActivity.class);
        startActivityForResult(intent,TAG_REQUEST_QR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==TAG_REQUEST_QR && resultCode == RESULT_OK){
            if (data!=null){
                final String CloneCode = data.getStringExtra(AppTag.CLONECODE);
                Integer SentAmount = data.getIntExtra(AppTag.SENTAMOUNT, 0);
                final CloneListItem i = new CloneListItem();
                i.setCloneCode(CloneCode);
                i.setSentAmount(SentAmount);
                i.setSentTo(ToPlaceCode);


                //ถ้าไม่ซ้ำ ให้ Insert ใหม่
                if (!CheckReplete(i)) {
                    p.setVisibility(View.VISIBLE);
                    final ParseObject object = new ParseObject(ParseClass.Clone);
                    object.put("PlaceTest",FromPlaceCode);
                    object.put(Columns.CloneCode,CloneCode);
                    object.put(Columns.SentAmount,SentAmount);
                    object.put(Columns.SentTo,ToPlaceCode);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e==null){
                                i.setUploadStatus(Upload_Status.UPLOADED);
                                Toast.makeText(SentCloneListActivity.this,"Upload สำเร็จ",Toast.LENGTH_LONG).show();

                                ContentResolver resolver = getContentResolver();
                                Uri uri = Uri.parse(getResources().getString(R.string.uri_clonetosent));
                                ContentValues v  = new ContentValues();
                                v.put(Columns.CloneCode,i.getCloneCode());
                                v.put(Columns.SentTo,ToPlaceCode);
                                v.put(Columns.SentAmount,i.getSentAmount());
                                v.put(Columns.UploadStatus,Upload_Status.UPLOADED);
                                v.put(Columns.ObjectID,object.getObjectId());
                                v.put(Columns.FromPlace,FromPlaceCode);

                                resolver.insert(uri,v);

                                mAdapter.getDataSet().add(i);
                                mAdapter.notifyDataSetChanged();
                                listView.smoothScrollToPosition((mAdapter.getCount() - 1));
                                p.setVisibility(View.INVISIBLE);

                            }else {

                                //Wait for ObjectId next time
                                p.setVisibility(View.INVISIBLE);
                                i.setUploadStatus(Upload_Status.NOT_UPLOADED);
                                ContentResolver resolver = getContentResolver();
                                Uri uri = Uri.parse(getResources().getString(R.string.uri_clonetosent));
                                ContentValues v  = new ContentValues();
                                v.put(Columns.CloneCode,i.getCloneCode());
                                v.put(Columns.SentTo,ToPlaceCode);
                                v.put(Columns.SentAmount,i.getSentAmount());
                                v.put(Columns.UploadStatus,Upload_Status.NOT_UPLOADED);
                                v.put(Columns.ObjectID,object.getObjectId());
                                v.put(Columns.FromPlace,ToPlaceCode);
                                resolver.insert(uri, v);

                                mAdapter.getDataSet().add(i);
                                mAdapter.notifyDataSetChanged();
                                listView.smoothScrollToPosition((mAdapter.getCount() - 1));
                            }


                        }
                    });



                }else{
                    Toast.makeText(SentCloneListActivity.this,"เบอร์ซ้ำ",Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    //CheckReplete
    private boolean CheckReplete(CloneListItem item){
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clonetosent));
        String[] projection =null;
        String sortOrder = null;
        String selection = Columns.CloneCode+"= ? AND "+Columns.SentTo+" = ?";
        String[] selectionArgs = {item.getCloneCode(),item.getSentTo()};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c.getCount()!=0){
            return true;
        }

        c.close();

        return false;
    }
    ProgressBar p;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_openQR) {
            OpenQrCode();
            return true;
        }
        if (id == R.id.action_upload){
            p.setVisibility(View.VISIBLE);
            UpdateAllDatabases d = new UpdateAllDatabases(SentCloneListActivity.this,ToPlaceCode,mAdapter);
            d.execute();

        }

//        if (id == R.id.action_reset_status){
//            ContentValues v = new ContentValues();
//            v.put(Columns.UploadStatus,Upload_Status.NOT_UPLOADED);
//            getContentResolver().update(Uri.parse(getResources().getString(R.string.uri_clonetosent)),v,null,null);
//        }
        return super.onOptionsItemSelected(item);
    }




    private void onUploadAllClone(){


    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private ArrayList<CloneListItem> CreateSentAmountDataSet(){
        ArrayList<CloneListItem> d = new ArrayList<>();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clonetosent));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.SentTo+" = ?";
        String[] selectionArgs = {ToPlaceCode};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            CloneListItem item  = new CloneListItem();
            item.setCloneCode(c.getString(c.getColumnIndex(Columns.CloneCode)));
            item.setSentAmount(c.getInt(c.getColumnIndex(Columns.SentAmount)));
            item.setSentTo(c.getString(c.getColumnIndex(Columns.SentTo)));
            item.setUploadStatus(c.getInt(c.getColumnIndex(Columns.UploadStatus)));
            item.setObjectID(c.getString(c.getColumnIndex(Columns.ObjectID)));
            Log.d("Get ITEM ID", item.getObjectID() + 0);

            d.add(item);
        }
        c.close();
        return  d;
    }

    @Override
    public boolean UpdateCloneItem(CloneListItem item,int operationType) {
        if (operationType== OperationType.TYPE_DELETE){
            //Delete
            Toast.makeText(this,"ลบ",Toast.LENGTH_LONG).show();
            for (int i = 0 ; i<mAdapter.getDataSet().size();i++){
                if (mAdapter.getDataSet().get(i).getCloneCode().equals(item.getCloneCode())) {
                    mAdapter.getDataSet().remove(i);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }else if (operationType ==OperationType.TYPE_UPDATE)
        {
            UpdateData(item);
        }



        return false;
    }

    @Override
    public void onClickOK() {

    }

    @Override
    public void onUploadDataFinish(CloneListItem item) {
        //Update database here
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clonetosent));

        String where = Columns.CloneCode+"= ? AND "+Columns.SentTo+" = ?";
        String[] selectionArgs = {item.getCloneCode(),ToPlaceCode};
        ContentValues v  = new ContentValues();
        v.put(Columns.UploadStatus,1);
        resolver.update(uri, v, where, selectionArgs);

        //Update บน List โดยไม่ต้อง Query ใหม่
        item.setUploadStatus(1);
        for (int i = 0 ; i<mAdapter.getDataSet().size();i++){
            if (mAdapter.getDataSet().get(i).getCloneCode().equals(item.getCloneCode())) {
                mAdapter.getDataSet().remove(i);
                mAdapter.getDataSet().add(i, item);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void UpdateData(final CloneListItem item){
        AQuery aq = new AQuery(getApplication());
        GsonTransformer transformer = new GsonTransformer();
        //TODO Fix URL HERE
        String url = "xx";
        Map<String, Object> params  = new HashMap<>();

        //TEST
        onUploadData o = SentCloneListActivity.this;
        o.onUploadDataFinish(item);

}

    @Override
    public void UpdateListView() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void UpdateAllFinished() {
        p.setVisibility(View.INVISIBLE);
    }
}
