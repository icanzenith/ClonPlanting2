package sugarcaneselection.thaib.org.clonplanting2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

/**
 * Created by Jitpakorn on 4/24/15 AD.
 */
public class SentCloneListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CloneListItem> dataSet;

    public SentCloneListAdapter(Context context,ArrayList<CloneListItem> dataSet) {
        this.dataSet = dataSet;
        this.context = context;
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
        CloneListItem t = dataSet.get(position);
        ViewHolder v;
        if(convertView == null){
            v  = new ViewHolder();
            convertView = View.inflate(context, R.layout.single_sentclone_list,null);
            v.CloneCode = (TextView) convertView.findViewById(R.id.tvCloneCode);
            v.SentAmount = (TextView) convertView.findViewById(R.id.tvSentAmount);
            v.statusTag = convertView.findViewById(R.id.upload_status_tag);
            convertView.setTag(v);
        }else
        {
            v = (ViewHolder) convertView.getTag();
        }

        v.CloneCode .setText(t.getCloneCode());
        v.SentAmount.setText(t.getSentAmount() + " ต้น");
        if (t.getUploadStatus()== Upload_Status.UPLOADED){
            v.statusTag.setBackgroundColor(0xff22ee22);
        }else{
            v.statusTag.setBackgroundColor(0xffee2222);

        }

        return convertView;
    }

    public ArrayList<CloneListItem> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<CloneListItem> dataSet) {
        this.dataSet = dataSet;
    }


    private class ViewHolder{
        TextView CloneCode;
        TextView SentAmount;
        View statusTag;
    }

}
