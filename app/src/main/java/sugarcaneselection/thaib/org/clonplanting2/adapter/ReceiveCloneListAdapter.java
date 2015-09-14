package sugarcaneselection.thaib.org.clonplanting2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.item.ViewType;

/**
 * Created by Jitpakorn on 4/27/15 AD.
 */
public class ReceiveCloneListAdapter extends BaseAdapter{


    private ArrayList<Object> data;
    private Context context;
    private LayoutInflater mInflater;

    public ReceiveCloneListAdapter(ArrayList<Object> data, Context context) {
        this.data = data;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ViewHolder holder = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case ViewType.DATALIST:
                    convertView = mInflater.inflate(R.layout.single_received_clone_list, null);
                    holder.tvCloneCode = (TextView) convertView.findViewById(R.id.tvCloneCode);
                    holder.tvReceiveAmount = (TextView) convertView.findViewById(R.id.tvReceiveAmount);
                    holder.UploadStatus = convertView.findViewById(R.id.upload_status_tag);
                    break;
                case ViewType.TITLE:
                    convertView = mInflater.inflate(R.layout.single_received_seperator_list, null);
                    holder.tvFromPlaceCode = (TextView) convertView.findViewById(R.id.tvFromPlaceCode);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        if (getItemViewType(position) == ViewType.DATALIST){
            CloneListItem item = (CloneListItem) data.get(position);
            holder.tvCloneCode.setText(item.getCloneCode().toString());
            holder.tvReceiveAmount.setText(item.getReceiveAmount().toString());
            if(item.getUploadStatus()== Upload_Status.UPLOADED){
                holder.UploadStatus.setBackgroundColor(0xff22ee22);
            }else{
                holder.UploadStatus.setBackgroundColor(0xffee2222);
            }

        }else if(getItemViewType(position) == ViewType.TITLE){
            String  item = (String) data.get(position);
            holder.tvFromPlaceCode.setText(item);
        }


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return  data.get(position).getClass() == CloneListItem.class ? ViewType.DATALIST:ViewType.TITLE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }



    private class ViewHolder{

        TextView tvFromPlaceCode;
        TextView tvCloneCode;
        TextView tvReceiveAmount;
        View UploadStatus;
    }
}
