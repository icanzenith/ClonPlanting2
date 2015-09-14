package sugarcaneselection.thaib.org.clonplanting2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v4.app.DialogFragment;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParsePush;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.LandListItem;
import sugarcaneselection.thaib.org.clonplanting2.test.TestChatActivity;


public class HomeMenu2Activity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu2);
        mListView = (ListView) findViewById(R.id.listView);
        CreateMenuList();
        Adapter mAdapter = new Adapter(HomeMenu2Activity.this, menuItems);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(HomeMenu2Activity.this,SelectPlaceListActivity.class);
                        break;
                    case 1:
                        intent = new Intent(HomeMenu2Activity.this,ReceiveCloneListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(HomeMenu2Activity.this,LandListActivity.class);
                        intent.putExtra(AppTag.NEXT_ACTIVITY,AppTag.PlantingClone);
                        break;
                    case 3:
                        intent = new Intent(HomeMenu2Activity.this,LandListActivity.class);
                        intent.putExtra(AppTag.NEXT_ACTIVITY, AppTag.CountClonePlanted);
                        break;
                    case 4:
                        intent = new Intent(HomeMenu2Activity.this,LandListActivity.class);
                        intent.putExtra(AppTag.NEXT_ACTIVITY, AppTag.CountCloneSurvive);
                        break;
                    case 5:
                        intent = new Intent(HomeMenu2Activity.this, SelectPlaceToConclusionActivity.class);
                        intent.putExtra("WhereToGo", SelectPlaceToConclusionActivity.SentCloneConclusion);
                        break;
                    case 6:
                        intent = new Intent(HomeMenu2Activity.this, SelectPlaceToConclusionActivity.class);
                        intent.putExtra("WhereToGo",SelectPlaceToConclusionActivity.ReceivedCloneConclusion);
                        break;
                    case 7:
//                        intent = new Intent(HomeMenu2Activity.this,TestChatActivity.class);
//                        startActivity(intent);
                        createDialog();
//                        BaseApplication baseApplication = (BaseApplication) getApplication();
//                        baseApplication.getSessionManager().Logout(HomeMenu2Activity.this);
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                if (position!=7) {
                    startActivity(intent);
                }
            }
        });
    }

    private void CreateMenuList() {
        String[] mMenuList = new String[]{
                "ส่งโคลนอ้อย", "รับโคลนอ้อย", "ปลูกโคลน", "นับจำนวนที่ปลูก", "จำนวนต้นที่รอด",
                "สรุปข้อมูลการส่ง", "สรุปข้อมูลการรับ", "Logout"
        };
        Integer[] mIconId = new Integer[]{
                R.mipmap.ic_sent_clone, R.mipmap.ic_recieved_clone, R.mipmap.ic_plantclone, R.mipmap.ic_count_planted, R.mipmap.ic_count_servive,
                R.mipmap.ic_sent_conclusion, R.mipmap.ic_recieved_conclusion, R.mipmap.ic_logout
        };

        for (int i = 0; i < mMenuList.length; i++) {
            MenuItem d = new MenuItem();
            d.setMenu(mMenuList[i]);
            d.setIcon(mIconId[i]);
            menuItems.add(d);
        }
    }





    class MenuItem {
        String Menu;
        Integer Icon;

        public String getMenu() {
            return Menu;
        }

        public void setMenu(String menu) {
            Menu = menu;
        }

        public Integer getIcon() {
            return Icon;
        }

        public void setIcon(Integer icon) {
            Icon = icon;
        }
    }

    class Adapter extends BaseAdapter {
        private ArrayList<MenuItem> dataSet;
        LayoutInflater inflater;

        public Adapter(Context context, ArrayList<MenuItem> dataSet) {
//        this.dataSet = dataSet;
            this.dataSet = dataSet;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSet.get(position);
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
                convertView = inflater.inflate(R.layout.singlelist_menu, parent, false);
                v.tvMenu = (TextView) convertView.findViewById(R.id.tvName);
                v.icon = (ImageView) convertView.findViewById(R.id.ic_menu);
                convertView.setTag(v);
            } else {
                v = (ViewHolder) convertView.getTag();
            }
            v.tvMenu.setText("" + dataSet.get(position).getMenu());
            v.icon.setImageResource(dataSet.get(position).getIcon());


            return convertView;
        }

        private class ViewHolder {
            TextView tvMenu;
            ImageView icon;
        }


    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeMenu2Activity.this);
        builder.setMessage("คุณต้องการ Logout ?")
                .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BaseApplication baseApplication = (BaseApplication) getApplication();

                        //TODO Subscript Parse Channel
                        ParsePush.unsubscribeInBackground("ALL");
                        ParsePush.unsubscribeInBackground(baseApplication.getUserDataitem().getSector());


                        baseApplication.getSessionManager().Logout(HomeMenu2Activity.this);
                    }
                })
                .setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).create().show();
        // Create the AlertDialog object and return it
    }


}
