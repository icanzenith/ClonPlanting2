package sugarcaneselection.thaib.org.clonplanting2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.CloneListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

/**
 * Created by Jitpakorn on 5/3/15 AD.
 */
public class PlantingAdapter extends BaseAdapter{

    private ArrayList<Object> data;
    private Context context;
    private LayoutInflater mInflater;

    public PlantingAdapter(ArrayList<Object> data, Context context) {
        this.data = data;
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ViewHolder v = null;
        CloneListItem c= (CloneListItem) data.get(position);
        if (convertView == null){
            v = new ViewHolder();
            convertView = mInflater.inflate(R.layout.single_planted_list,null);
            v.CloneCode = (TextView) convertView.findViewById(R.id.tvCloneCode);
            v.RowNumber = (TextView) convertView.findViewById(R.id.tvRowNumber);
            v.UploadStatus = convertView.findViewById(R.id.upload_status_tag);
            v.PlantedAmount = (TextView) convertView.findViewById(R.id.tvPlantedAmount);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();

        }
        v.CloneCode.setText(c.getCloneCode().toString()+"");
        v.RowNumber.setText("แถวที่ "+c.getRowNumber());
        if (c.getUploadStatus() == Upload_Status.UPLOADED){
            v.UploadStatus.setBackgroundColor(0xff22ee22);
        }else{
            v.UploadStatus.setBackgroundColor(0xffee2222);
        }
        v.PlantedAmount.setText(c.getPlantAmount()+"");

        //use viewHolder here

        return convertView;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    private class ViewHolder{
        TextView RowNumber;
        TextView CloneCode;
        TextView PlantedAmount;
        View UploadStatus;
        ImageView DragButton;
    }

}
