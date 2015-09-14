package sugarcaneselection.thaib.org.clonplanting2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.LandListItem;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */
public class LandListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LandListItem> dataSet;
    LayoutInflater inflater;
    public LandListAdapter(Context context,ArrayList<LandListItem> dataSet) {
//        this.dataSet = dataSet;
        this.dataSet = dataSet;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.single_landlist, parent, false);
            v.landNumber = (TextView) convertView.findViewById(R.id.tvLandName);
            v.landName = (TextView) convertView.findViewById(R.id.tvLandNameFull);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.landNumber.setText("แปลงที่ "+dataSet.get(position).getLandNumber());
        v.landName.setText(""+dataSet.get(position).getLandName());

        return convertView;
    }

    public ArrayList<LandListItem> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<LandListItem> dataSet) {
        this.dataSet = dataSet;
    }


    private class ViewHolder{
        TextView landName;
        TextView landNumber;

    }



}
