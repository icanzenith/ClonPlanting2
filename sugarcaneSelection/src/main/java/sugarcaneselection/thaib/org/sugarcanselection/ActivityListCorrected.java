package sugarcaneselection.thaib.org.sugarcanselection;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.review.ReviewActivity2;


public class ActivityListCorrected extends ActionBarActivity {

    ListView mListView;
    ArrayList<myListItem> myListItems;
    private void getList(){
        ContentResolver cr = getContentResolver();


        myListItems = new ArrayList<>();

        ArrayList<String> FamilyArray = new ArrayList<>();
        HashMap<String , Integer > RowNumber = new HashMap<>();
        Uri uriFam = Uri.parse(getResources().getString(R.string.URI_Distinc));
        String[] projectionFam = {Columns.FAMILYCODE,Columns.ROWNUMBER};
        String selectionFam = Columns.SELECT_STATUS+" != "+ SelectStatus.NOTHING;
        String[] selectionArgFam = null;
        Cursor cFam = cr.query(uriFam, projectionFam, selectionFam, selectionArgFam, null);
        if (cFam != null) {
            if (cFam.getCount() != 0) {
                while (cFam.moveToNext()) {
                    FamilyArray.add(cFam.getString(cFam.getColumnIndex(Columns.FAMILYCODE)));
                    RowNumber.put(cFam.getString(cFam.getColumnIndex(Columns.FAMILYCODE)),cFam.getInt(cFam.getColumnIndex(Columns.ROWNUMBER)));
                }
            }
        }
        cFam.close();

        for (String FamCode : FamilyArray) {
            myListItem d = new myListItem();
            d.setFamilyCode(FamCode);
            d.setRowNumber(RowNumber.get(FamCode));
            d.setCloneCode(null);
            d.setType(0);
            myListItems.add(d);
            Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
            String[] projection = null;
            String selection = Columns.SELECT_STATUS+" != "+SelectStatus.NOTHING+" AND " + Columns.FAMILYCODE + " = ?";
            String[] selectionagrs = {FamCode};
            Cursor c;
            c = cr.query(uri, projection, selection, selectionagrs, null);
            if (c != null) {
                while (c.moveToNext()) {
                    myListItem item = new myListItem();

                    item.setCloneCode(c.getString(c.getColumnIndex(Columns.CLONECODE)));
                    item.setFamilyCode(c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                    item.setType(1);
                    if (c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)) != SelectStatus.NOTHING) {
                        item.setSelect(c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)));
                    }else{
                        item.setSelect(SelectStatus.NOTHING);
                    }
                    myListItems.add(item);
                }
            }
        }
    }

    myAdapter adapter;
    boolean created;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));
        setContentView(R.layout.activity_activity_list_corrected);
        created = true;

        mListView = (ListView) findViewById(R.id.listView_corrected);



        getList();
        adapter = new myAdapter(this, myListItems);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myListItem d = (myListItem) parent.getItemAtPosition(position);

                if (d.getType() == 0) {

                } else {
                    Intent intent = new Intent(ActivityListCorrected.this, ReviewActivity2.class);
                    intent.putExtra("CloneCode", d.getCloneCode());
                    intent.putExtra("Activity", 1);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("Created",true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        created = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (created){

        }else{
            getList();
            adapter.setArray(myListItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        created = savedInstanceState.getBoolean("Created");
        if (created){

        }else{
            getList();
            adapter.setArray(myListItems);
            adapter.notifyDataSetChanged();
        }
        super.onRestoreInstanceState(savedInstanceState);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_list_corrected, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class myListItem {
        private int RowNumber;
        private String CloneCode;
        private int Type;
        private String FamilyCode;
        private int Select;

        public int getRowNumber() {
            return RowNumber;
        }

        public void setRowNumber(int rowNumber) {
            RowNumber = rowNumber;
        }

        public int getSelect() {
            return Select;
        }

        public void setSelect(int select) {
            Select = select;
        }

        public String getCloneCode() {
            return CloneCode;
        }

        public void setCloneCode(String cloneCode) {
            CloneCode = cloneCode;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }

        public String getFamilyCode() {
            return FamilyCode;
        }

        public void setFamilyCode(String familyCode) {
            FamilyCode = familyCode;
        }
    }

    class myAdapter extends BaseAdapter {
        LayoutInflater inflater;
        private ArrayList<myListItem> array;


        myAdapter(Context context, ArrayList<myListItem> data) {
            array = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return array.get(position).getType();
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            return array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            int getItemViewType = getItemViewType(position);

            if (convertView == null) {
                v = new ViewHolder();
                if (getItemViewType == 0) {
                    convertView = inflater.inflate(R.layout.single_list_familyheader, null);
                } else {
                    convertView = inflater.inflate(R.layout.single_list_cloneselected, null);

                }
                if (getItemViewType == 0) {
                    v.fam = (TextView) convertView.findViewById(R.id.tvFamilycode);

                } else {

                    v.textView = (TextView) convertView.findViewById(R.id.tvCloneSelected);
                    v.tvSelectStatus = (TextView) convertView.findViewById(R.id.tvSelectStatus);

                }
                convertView.setTag(v);

            } else {
                v = (ViewHolder) convertView.getTag();
            }
            if (getItemViewType == 0) {
                v.fam.setText("  แถวที่ "+array.get(position).getRowNumber() +" " + array.get(position).getFamilyCode());

            } else {

                v.textView.setText(array.get(position).getCloneCode());
                int selectstatus = array.get(position).getSelect();
                if (selectstatus!=SelectStatus.NOTHING) {

                    if (selectstatus==SelectStatus.SAVED){
                        v.tvSelectStatus.setText("บันทึกข้อมูลแล้ว");
                        v.tvSelectStatus.setBackgroundColor(0xFF999923);
                        v.tvSelectStatus.setTextColor(0xFF222222);
                    }

                    if (selectstatus == SelectStatus.REJECTED){
                        v.tvSelectStatus.setText("คัดทิ้ง");
                        v.tvSelectStatus.setBackgroundColor(0xFF994322);
                        v.tvSelectStatus.setTextColor(0xFFFFFFFF);
                    }

                    if (selectstatus == SelectStatus.SELECTED){
                        v.tvSelectStatus.setText("คัดเลือก 2");
                        v.tvSelectStatus.setBackgroundColor(0xFF239922);
                        v.tvSelectStatus.setTextColor(0xFFFFFFFF);
                    }



                } else {

                        v.tvSelectStatus.setText("ผิดพลาด");
                        v.tvSelectStatus.setBackgroundColor(0xFF994322);
                        v.tvSelectStatus.setTextColor(0xFFFFFFFF);

                }
            }
            return convertView;
        }

        public ArrayList<myListItem> getArray() {
            return array;
        }

        public void setArray(ArrayList<myListItem> array) {
            this.array = array;
        }

        class ViewHolder {
            TextView textView;
            TextView fam;
            TextView tvSelectStatus;
        }
    }
}
