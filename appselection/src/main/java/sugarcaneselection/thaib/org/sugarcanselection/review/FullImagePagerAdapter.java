package sugarcaneselection.thaib.org.sugarcanselection.review;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.Item.ImageFullScreenViewItem;
import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 4/6/15 AD.
 */
public class FullImagePagerAdapter extends PagerAdapter {

    private TextView tvImagePart;
    private ArrayList<ImageFullScreenViewItem> data;
    LayoutInflater inflater;
    Activity activity;

    public FullImagePagerAdapter(Activity activity,int textViewId,ArrayList<ImageFullScreenViewItem> data) {
       tvImagePart = (TextView) activity.findViewById(textViewId);
        this.data=data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.imageview,container,false);
        if (data.get(position).getPath()!=null){
            if (data.get(position).getPath().contains("http:")){
                Picasso.with(activity).load(data.get(position).getPath()).rotate(90).into(imageView);
            }else{
                Picasso.with(activity).load(new File(data.get(position).getPath())).fit().into(imageView);
            }

        }else{

            Picasso.with(activity).load(R.drawable.ic_noimage).fit().centerInside().into(imageView);

        }

        container.addView(imageView);

        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
