package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import item.LandListData;
import item.MenuListItem;
import sugarcaneselection.thaib.org.cloneplanting2.R;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */
public class MenuListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<MenuListItem> data;

    public MenuListAdapter(Context context,ArrayList<MenuListItem> Data) {
        //TODO CREATE LANDLIST DATA

        this.context  = context;
//        data =Data;
        data = createMenuListData();

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
        ViewHolder v;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.single_menulist,parent,false);
            v = new ViewHolder();
            v.Menu = (TextView) convertView.findViewById(R.id.tvMenu);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.Menu.setText(" "+data.get(position).getMenu());

        return convertView;
    }

    private ArrayList<MenuListItem> createMenuListData(){

        ArrayList<MenuListItem> landListDatas = new ArrayList<>();
        //Receive
        MenuListItem[] m = new MenuListItem[6];
        m[0].setMenu("รับ");
        m[0].setMenuID(1);
        landListDatas.add(m[0]);

        //Plant
        m[1].setMenu("ปลูก");
        m[1].setMenuID(2);
        landListDatas.add(m[1]);

        //PlantedAmount
        m[2].setMenu("นับจำนวนต้นที่ปลูก");
        m[2].setMenuID(3);
        landListDatas.add(m[2]);

        //SurviveAmount
        m[3].setMenu("นับจำนวนต้นที่รอด");
        m[3].setMenuID(4);
        landListDatas.add(m[3]);

        //LandInformation
        m[4].setMenu("ข้อมูลแปลง");
        m[4].setMenuID(5);
        landListDatas.add(m[4]);

        //Conclusion
        m[5].setMenu("สรุป");
        m[5].setMenuID(6);
        landListDatas.add(m[5]);

        return landListDatas;

    }

    private class ViewHolder{
        TextView Menu;
    }
}
