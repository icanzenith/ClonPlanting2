package sugarcaneselection.thaib.org.clonplanting2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */
public class TemplateListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<?> dataSet;
    public TemplateListAdapter(Context context,ArrayList<?> dataSet) {
       this.dataSet = dataSet;
       this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if(convertView == null){
            v  = new ViewHolder();
            convertView = View.inflate(context, R.layout.single_landlist,null);
            v.landName = (TextView) convertView.findViewById(R.id.tvLandName);
        }else
        {
            v = (ViewHolder) convertView.getTag();
        }



        return null;
    }

    private class ViewHolder{
        TextView landName;
    }
}
