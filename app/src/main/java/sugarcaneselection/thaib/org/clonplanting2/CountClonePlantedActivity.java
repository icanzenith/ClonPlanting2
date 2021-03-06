package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.dialog.CountClonePlantedDialog;
import sugarcaneselection.thaib.org.clonplanting2.dialog.EditPlantedCloneListDialog;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.util.onFinishUpdate;


public class CountClonePlantedActivity extends AppCompatActivity implements onFinishUpdate{

    private ListView mListView;
    private String mLandID;
    private CountClonePlantedAdapter mAdapter;
    private String mRep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_clone_planted);
        mLandID = getIntent().getStringExtra(AppTag.LAND_ID);
        mRep = getIntent().getStringExtra(AppTag.REPLICATION);

        mAdapter = new CountClonePlantedAdapter(createPlantedData());
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CloneListItem item = (CloneListItem) parent.getItemAtPosition(position);
                CountClonePlantedDialog dialog = (CountClonePlantedDialog) CountClonePlantedDialog.newInstance();
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog);
                dialog.setItem(item);
                dialog.show(getSupportFragmentManager(),"CountClone");
            }
        });
    }

    private ArrayList<Object> createPlantedData() {
        ArrayList<Object> item = new ArrayList<>();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
        String[] projection = null;
        String sortOrder = Columns.RowNumber + " ASC";
        String selection = Columns.LandID + " = ?"+
                " AND "+Columns.RowNumber+" != 0 AND "+Columns.Replication+" = ?";
        String[] selectionArgs = {mLandID,mRep};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        Log.d("String get get", c.getCount() + " ");
        if (c.getCount() != 0) {
            while (c != null && c.moveToNext()) {
                Log.d("String get get", c.getString(c.getColumnIndex(Columns.CloneCode)) + " ");
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
        getMenuInflater().inflate(R.menu.menu_count_clone_planted, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishUpdate() {
        refreshListView();

    }

    private void refreshListView() {
            mAdapter.setData(createPlantedData());
            mAdapter.notifyDataSetChanged();
    }

    class CountClonePlantedAdapter extends BaseAdapter{

        ArrayList<Object> data;
        LayoutInflater inflater;

        public ArrayList<Object> getData() {
            return data;
        }

        public void setData(ArrayList<Object> data) {
            this.data = data;
        }

        public CountClonePlantedAdapter(ArrayList<Object> data) {
            this.data = data;
            inflater = CountClonePlantedActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CloneListItem i = (CloneListItem) data.get(position);
            ViewHolder v;
            if (convertView == null){
                v= new ViewHolder();
                //TODO Initialize Widget
                convertView = inflater.inflate(R.layout.single_planted_list,parent,false);
                v.tvCloneCode = (TextView) convertView.findViewById(R.id.tvCloneCode);
                v.tvRowNumber = (TextView) convertView.findViewById(R.id.tvRowNumber);
                v.tvPlantedAmount = (TextView) convertView.findViewById(R.id.tvPlantedAmount);
                v.tagUploadStatus = convertView.findViewById(R.id.upload_status_tag);
                convertView.setTag(v);
            }else{
                v = (ViewHolder) convertView.getTag();
            }
            v.tvPlantedAmount.setVisibility(View.VISIBLE);
            v.tvCloneCode.setText(i.getCloneCode());
            v.tvRowNumber.setText("แถวที่ "+i.getRowNumber());
            v.tvPlantedAmount.setText(i.getPlantAmount()+" ต้น");

            if (i.getUploadStatus()==Upload_Status.UPLOADED){
                v.tagUploadStatus.setBackgroundColor(0xff22ee22);
            }else{
                v.tagUploadStatus.setBackgroundColor(0xffee2222);
            }
            return convertView;
        }


    }
    private class ViewHolder{
        TextView tvRowNumber;
        TextView tvCloneCode;
        TextView tvPlantedAmount;
        View tagUploadStatus;

    }
}
