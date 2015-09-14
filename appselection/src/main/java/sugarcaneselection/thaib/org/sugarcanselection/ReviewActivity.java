package sugarcaneselection.thaib.org.sugarcanselection;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class ReviewActivity extends ActionBarActivity implements FragmentScoreDetail.OnFragmentInteractionListener {

    public static final String ARG_ACTIVITY = "Activity";
    private BaseApplication baseApplication;
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private int whicfrom = 0;
    private String mCloneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));
        setContentView(R.layout.activity_review);
        baseApplication = (BaseApplication) getApplication();
        if (getIntent() != null) {
            whicfrom = getIntent().getIntExtra(ARG_ACTIVITY, 0);
            mCloneCode = getIntent().getStringExtra("CloneCode");


        }

        viewPager = (ViewPager) findViewById(R.id.ViewPager);

        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
        pagerTabStrip.setBackgroundColor(0xFFFFFFFF);
        pagerTabStrip.setTabIndicatorColor(0xFF000000);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        Vector<Fragment> vector = new Vector<>();
        //TODO สร้างให้เข้าใจง่ายใหม่

//        Fragment rawDataFragment = FragmentDetail.newInstance(mCloneCode);
//        Fragment scoreDataFragment = FragmentScoreDetail.newInstance(mCloneCode);

//        vector.add(rawDataFragment);
//        vector.add(scoreDataFragment);

        ArrayList<String[]> data = getPictureData(mCloneCode);
        for (int i = 0; i < data.size(); i++) {
            vector.add(ImageViewFragment.newInstance(data.get(i)[0]));

        }
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), vector);

        viewPager.setAdapter(adapter);

    }

    private ArrayList<String[]> getPictureData(String clonecode) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        ContentResolver resolver = this.getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String selection = "clonecode = ?";
        String[] selectionArgs = {clonecode};
        Cursor c = resolver.query(uri, null, selection, selectionArgs, null);
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                String[] full = new String[]{c.getString(c.getColumnIndex(Columns.FULLPICURL))};
                String[] lower = new String[]{c.getString(c.getColumnIndex(Columns.LOWERPICURL))};
                String[] upper = new String[]{c.getString(c.getColumnIndex(Columns.UPPERPICURL))};

                /** Dont Change Order of Array List Adding*/
                arrayList.add(full);
                arrayList.add(lower);
                arrayList.add(upper);


//                Log.d("Debug Lower", lower[0]);
//                Log.d("Debug Upper", upper[0]);
//                Log.d("Debug Full", full[0]);

            }
        }

        return arrayList;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class ImageViewFragment extends Fragment {

        private static final String ARG_IMAGEPATH = "imagepath";
        String path;
        ImageView image;

        public ImageViewFragment() {
        }

        public static Fragment newInstance(String ImagePath) {
            Fragment fragment = new ImageViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ARG_IMAGEPATH, ImagePath);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                path = getArguments().getString(ARG_IMAGEPATH);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.imageview_fragment, container, false);
            Log.d("Debug Path", "" + path);
            image = (ImageView) rootView.findViewById(R.id.imageView);
            if (path != null) {
                if (path.contains("http://")) {
                    Picasso.with(getActivity()).load(path).rotate(90).into(image);
                } else {
                    Picasso.with(getActivity()).load(new File(path)).into(image);
                }
            } else {


            }


            return rootView;
        }


    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        Vector<Fragment> vector;
        String[] PageTitle = {
//                "ข้อมูล", "คะแนน",
                "รูปเต็ม", "ส่วนล่าง", "ส่วนบน"
        };

        MyPagerAdapter(FragmentManager fm, Vector<Fragment> vector) {
            super(fm);
            this.vector = vector;

        }

        @Override
        public Fragment getItem(int position) {
            return vector.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PageTitle[position];
        }

        @Override
        public int getCount() {
            return vector.size();
        }
    }
}
