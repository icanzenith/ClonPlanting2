package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import item.LandListData;
import sugarcaneselection.thaib.org.cloneplanting2.R;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */
public class LandListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<LandListData> data;

    public LandListAdapter(Context context,ArrayList<LandListData> LandListData) {

        this.context  = context;
//        data = LandListData;
        data = createLandListData();

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
            convertView = inflater.inflate(R.layout.single_landlist,parent,false);
            v = new ViewHolder();
            v.tvLandNumber = (TextView) convertView.findViewById(R.id.tvLandNumber);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }
        v.tvLandNumber.setText("แปลงที่ "+data.get(position).getLandNumber());

        return convertView;
    }

    private ArrayList<LandListData> createLandListData(){

        ArrayList<LandListData> landListDatas = new ArrayList<>();
        LandListData l1 = new LandListData();
        l1.setLandID(200);
        l1.setLandName("แปลง 1 ไร่ตะวันสีทอง");
        l1.setLandNumber(1);
        LandListData l2 = new LandListData();
        l2.setLandID(201);
        l2.setLandName("แปลง 2 ไร่ตะวันแดง");
        l2.setLandNumber(1);

        landListDatas.add(l1);
        landListDatas.add(l2);

        return landListDatas;

    }

    private class ViewHolder{
        TextView tvLandNumber;
    }
}
