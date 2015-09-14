package sugarcaneselection.thaib.org.cloneplanting2.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import adapter.CloneListAdapter;
import sugarcaneselection.thaib.org.cloneplanting2.R;

public class ReceiveCloneListActivity extends ActionBarActivity {

    private ListView mListView;
    private CloneListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_clone_list);

        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new CloneListAdapter();
        mListView.setAdapter(mAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recieve_clone_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Test to Open QR Code Scanner
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
