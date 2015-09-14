package sugarcaneselection.thaib.org.cloneplanting2.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import adapter.MenuListAdapter;
import item.MenuListItem;
import sugarcaneselection.thaib.org.cloneplanting2.R;

public class MenuListActivity extends ActionBarActivity {

    private ListView mListView;
    private MenuListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        mAdapter = new MenuListAdapter(this,null);
        mListView = (ListView) findViewById(R.id.listView);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuListItem m = (MenuListItem) parent.getItemAtPosition(position);
                Intent intent = null;
               switch (m.getMenuID()){

                   case 1:
                       //Receive
                       intent = new Intent(MenuListActivity.this,ReceiveCloneListActivity.class);
                       break;
                   case 2:
                       //PlantActivity
                       intent = new Intent(MenuListActivity.this,PlantedCloneListActivity.class);

                       break;
                   case 3:
                       //CountPlantedActivity
                       intent = new Intent(MenuListActivity.this,CountClonePlantedListActivity.class);

                       break;
                   case 4:
                       //Count Survive
                       intent = new Intent(MenuListActivity.this,CountCloneSurvivedActivity.class);

                       break;
                   case 5:
                       //Land Information
                       intent = new Intent(MenuListActivity.this,LandInformationActivity.class);

                       break;
                   case 6:
                       //ConclusionData
                       intent = new Intent(MenuListActivity.this,ConclusionListActivity.class);

                       break;

               }
                if (intent!=null){
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_list, menu);
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

    private void CreateListMenu(){


    }


}
