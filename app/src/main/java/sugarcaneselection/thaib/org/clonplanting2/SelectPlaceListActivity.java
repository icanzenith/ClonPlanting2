package sugarcaneselection.thaib.org.clonplanting2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.PlaceDataItem;


public class SelectPlaceListActivity extends AppCompatActivity {

    private ListView mListView;
    private PlaceListAdapter mAdapter;
    private String sentFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place_list);
        BaseApplication b = (BaseApplication) getApplication();
        sentFrom  = b.getUserDataitem().getSector();
        ActionBar ac = getSupportActionBar();
        ac.setTitle("ส่งอ้อยจาก "+sentFrom+" ไปไหน ?");
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new PlaceListAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlaceDataItem p = (PlaceDataItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(SelectPlaceListActivity.this,SentCloneListActivity.class);
                intent.putExtra(AppTag.PLACECODE,p.getPlaceCode());
                intent.putExtra(AppTag.PLACENAME,p.getPlaceName());
                startActivity(intent);



            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_select_place_to_sent, menu);
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

    private class PlaceListAdapter extends BaseAdapter{

        ArrayList<PlaceDataItem> a;

        private PlaceListAdapter() {

            //TODO Update Work PlaceName and Code Online

            a = new ArrayList<>();

            String[] PlaceCode = getResources().getStringArray(R.array.PlaceCode);
            String[] PlaceName = getResources().getStringArray(R.array.PlaceName);

            for (int i = 0 ;i < PlaceCode.length;i++){
                PlaceDataItem item = new PlaceDataItem();
                item.setPlaceCode(PlaceCode[i]);
                item.setPlaceName(PlaceName[i]);
                a.add(item);
            }


        }

        @Override
        public int getCount() {
            return a.size();
        }

        @Override
        public Object getItem(int position) {
            return a.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder v;

            if(convertView == null){

                v  = new ViewHolder();

                convertView = View.inflate(SelectPlaceListActivity.this, R.layout.single_place_list,null);

                v.PlaceCode = (TextView) convertView.findViewById(R.id.tvPlaceCode);
                v.PlaceName = (TextView) convertView.findViewById(R.id.tvPlaceName);

                convertView.setTag(v);

            }else{
                v = (ViewHolder) convertView.getTag();
            }

            v.PlaceCode.setText(a.get(position).getPlaceCode());
            v.PlaceName.setText(a.get(position).getPlaceName());

            return convertView;
        }
    }

    private class ViewHolder{
        TextView PlaceName;
        TextView PlaceCode;
    }
}
