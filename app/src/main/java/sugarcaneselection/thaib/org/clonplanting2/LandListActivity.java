package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.adapter.LandListAdapter;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.LandListItem;


public class LandListActivity extends AppCompatActivity {

    private LandListAdapter mAdapter;
    private ListView mListView;
    private int mNextAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_list);
        mNextAct = getIntent().getIntExtra(AppTag.NEXT_ACTIVITY,0);
        /**
         * สร้าง Adapter
         * */
        mAdapter = new LandListAdapter(getApplication(),createLandListData());

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LandListItem item = (LandListItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(LandListActivity.this, RepSelectActivity.class);
                //
                intent.putExtra(AppTag.NEXT_ACTIVITY,mNextAct);
                intent.putExtra(AppTag.LAND_ID, item.getLandID());
                startActivity(intent);
            }
        });


    }

    private ArrayList<LandListItem> createLandListData() {
        ArrayList<LandListItem> landList = new ArrayList<>();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_land));
        String[] projection = null;
        String sortOrder = Columns.LandNumber+" ASC";
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c != null && c.getCount()!=0){
            while (c != null && c.moveToNext()) {
                LandListItem a = new LandListItem();
                a.setLandID(c.getString(c.getColumnIndex(Columns.ObjectID)));
                a.setLandName(c.getString(c.getColumnIndex(Columns.LandName)));
                a.setLandNumber(c.getInt(c.getColumnIndex(Columns.LandNumber)));
                a.setRowAmount(c.getInt(c.getColumnIndex(Columns.RowAmount)));
                landList.add(a);
            }
        }
        c.close();
        return landList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_land_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_land){
            Intent intent = new Intent(LandListActivity.this,CreateLandActivity.class);
            intent.putExtra(AppTag.ACTIVITY_MODE,AppTag.MODE_CREATE);
            startActivityForResult(intent,AppTag.MODE_CREATE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == AppTag.MODE_CREATE){
            if (data.getBooleanExtra("isFinish",true)){
                RefreshListView();
            }
        }
    }

    private void RefreshListView(){
        /**
         * refresh ใหม่หมด เพราะจำนวนแปลงยังนัอย
         * และเขียนง่าย ในอนาตคหากจำนวนแปลงที่คัดเลือกเยอะ
         * */
        mAdapter.setDataSet(createLandListData());
        mAdapter.notifyDataSetChanged();

    }
}
