package sugarcaneselection.thaib.org.cloneplanting2.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import adapter.LandListAdapter;
import item.LandListData;
import sugarcaneselection.thaib.org.cloneplanting2.R;

public class LandListActivity extends ActionBarActivity {

    private ListView mListView;
    private LandListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_list);
        mListView = (ListView) findViewById(R.id.listView);

        //TODO Change Parameter in LandListAdapter form null to Real DataSet;
        mAdapter = new LandListAdapter(this,null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LandListData landListData = (LandListData) parent.getItemAtPosition(position);
                Intent intent = new Intent(LandListActivity.this,MenuListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_land_list, menu);
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
}
