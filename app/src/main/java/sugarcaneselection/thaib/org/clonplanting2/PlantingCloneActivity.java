package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentResolver;
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
import android.widget.Toast;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.adapter.PlantingAdapter;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.dialog.EditPlantedCloneListDialog;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.scanner.PlantingCloneScannerActivity;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishClonePlanting;


public class PlantingCloneActivity extends AppCompatActivity implements onFinishClonePlanting {

    private String TAG_DEBUG = "PlantingCloneActivityDebug";

    private ListView mListView;
    private PlantingAdapter mAdapter;
    private String mLandID;

    public static final int REQUEST_ADD_NEW_ROW = 10111;

    String rep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLandID = getIntent().getStringExtra(AppTag.LAND_ID);
        rep = getIntent().getStringExtra(AppTag.REPLICATION);
        setContentView(R.layout.activity_planting_clone);

        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new PlantingAdapter(createPlantedData(), PlantingCloneActivity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CloneListItem item = (CloneListItem) parent.getItemAtPosition(position);

                Log.d("DeBig","objectID = "+item.getObjectID());

                EditPlantedCloneListDialog d = (EditPlantedCloneListDialog) EditPlantedCloneListDialog.newInstance();
                d.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog);
                d.setCloneListItem(item);
                d.show(getSupportFragmentManager(), "EditPlatedClone");


            }
        });
    }

    private ArrayList<Object> createPlantedData() {
        ArrayList<Object> item = new ArrayList<>();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
        String[] projection = null;
        String sortOrder = Columns.RowNumber + " ASC";
        String selection = Columns.LandID + " = ?"+
                " AND "+Columns.RowNumber+" != 0 AND "+
                Columns.Replication+" = "+rep;
        String[] selectionArgs = {mLandID};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);

        Log.d(TAG_DEBUG,selection.toString());
        Log.d(TAG_DEBUG, c.getCount() + " ");

        if (c.getCount() != 0) {
            while (c != null && c.moveToNext()) {
                Log.d(TAG_DEBUG, c.getString(c.getColumnIndex(Columns.CloneCode)) + " ");
                CloneListItem i = new CloneListItem();
                i.setCloneCode(c.getString(c.getColumnIndex(Columns.CloneCode)));
                i.setObjectID(c.getString(c.getColumnIndex(Columns.ObjectID)));
                i.setLandID(c.getString(c.getColumnIndex(Columns.LandID)));
                i.setRowNumber(c.getInt(c.getColumnIndex(Columns.RowNumber)));
                i.setPlantAmount(c.getInt(c.getColumnIndex(Columns.PlantedAmount)));
                i.setUploadStatus(c.getInt(c.getColumnIndex(Columns.UploadStatus)));
                item.add(i);
            }
        }
        c.close();
        return item;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planting_clone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_new_row) {
            Intent intent = new Intent(PlantingCloneActivity.this, PlantingCloneScannerActivity.class);
            intent.putExtra(AppTag.LAND_ID, mLandID);
            intent.putExtra(AppTag.REPLICATION,rep);
            startActivityForResult(intent, REQUEST_ADD_NEW_ROW);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Log.d(TAG_DEBUG,"onActivity Result");

            refreshListView();
        }
    }

    @Override
    public void onFinishUpdate(boolean isNeedRefreshListView) {
        if (isNeedRefreshListView) {

            Log.d(TAG_DEBUG,"onFinishUpdate");

            refreshListView();
        } else {

        }
    }

    private void refreshListView() {
        mAdapter.setData(createPlantedData());
        mAdapter.notifyDataSetChanged();

    }


}
