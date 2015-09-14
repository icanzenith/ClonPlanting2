package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import item.CloneItem;

/**
 *
 * Created by Jitpakorn on 4/23/15 AD.
 *
 */

public class CloneListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CloneItem> data;

    public CloneListAdapter() {

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



        return convertView;
    }
}
