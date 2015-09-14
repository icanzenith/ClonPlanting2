package sugarcaneselection.thaib.org.clonplanting2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;


public class RepSelectActivity extends AppCompatActivity {

    private ListView mListView;
    private int mNextAct;
    private String mLandID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_select);

        mListView = (ListView) findViewById(R.id.listView);
        MyAdapter mAdapter= new MyAdapter(CreateRepListData());
        mNextAct = getIntent().getIntExtra(AppTag.NEXT_ACTIVITY,0);
        mLandID = getIntent().getStringExtra(AppTag.LAND_ID);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String rep = (String) parent.getItemAtPosition(position);
                Intent intent = null;

                switch (mNextAct){
                    case AppTag.PlantingClone :
                        intent = new Intent(RepSelectActivity.this, PlantingCloneActivity.class);
                        break;
                    case AppTag.CountClonePlanted:
                        intent = new Intent(RepSelectActivity.this, CountClonePlantedActivity.class);
                        break;
                    case AppTag.CountCloneSurvive:
                        intent = new Intent(RepSelectActivity.this,CountSurviveActivity.class);
                        break;
                }
                intent.putExtra(AppTag.LAND_ID,mLandID);
                intent.putExtra(AppTag.REPLICATION, rep);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> CreateRepListData() {
        ArrayList<String> arrayList = new ArrayList<>();
        String rep1 = "1";
        String rep2 = "2";
        arrayList.add(rep1);
        arrayList.add(rep2);
        return arrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rep_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends BaseAdapter{
        LayoutInflater inflater = getLayoutInflater();
        ArrayList<String> mData;

        public MyAdapter(ArrayList<String> mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if (convertView == null) {
                v = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_rep_list, parent, false);
                v.rep = (TextView) convertView.findViewById(R.id.tvRep);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }
            v.rep.setText("Replication "+mData.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView rep;

        }
    }




}
