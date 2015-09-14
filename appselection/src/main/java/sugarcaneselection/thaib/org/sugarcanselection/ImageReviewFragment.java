package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

public class ImageReviewFragment extends Fragment {

    private static final String ARG_CLONCODE = "clonecode";

    ViewPager viewPager;
    String mCloneCode;
    ActivityListCorrected activityListCorrected;

    public ImageReviewFragment() {
        // Required empty public constructor

    }

    public static ImageReviewFragment newInstance(String CloneCode) {
        ImageReviewFragment fragment = new ImageReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLONCODE, CloneCode);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCloneCode = getArguments().getString(ARG_CLONCODE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_review, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.ViewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), getPictureData(mCloneCode));
        viewPager.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<String[]> getPictureData(String clonecode) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        ContentResolver resolver = getActivity().getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String selection = "clonecode = ?";
        String[] selectionArgs = {clonecode};
        Cursor c = resolver.query(uri, null, selection, selectionArgs, null);
        if (c.getCount() != 0) {
            while (c.moveToNext()) {

                String[] lower = new String[]{c.getString(c.getColumnIndex(Columns.LOWERPICURL))};
                String[] upper = new String[]{c.getString(c.getColumnIndex(Columns.UPPERPICURL))};
                String[] full = new String[]{c.getString(c.getColumnIndex(Columns.FULLPICURL))};

                arrayList.add(lower);
                arrayList.add(upper);
                arrayList.add(full);

                Log.d("Debug Lower", lower[0]);
                Log.d("Debug Upper", upper[0]);
                Log.d("Debug Full", full[0]);

            }
        }

        return arrayList;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onDetach() {
        super.onDetach();

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
            Picasso.with(getActivity()).setLoggingEnabled(true);
            Picasso.with(getActivity()).load(new File(path)).into(image);


            return rootView;
        }


    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<String[]> picData;

        MyPagerAdapter(FragmentManager fm, ArrayList<String[]> picData) {
            super(fm);
            this.picData = picData;
            Log.d("Debug : ", "" + picData.size());
        }

        @Override
        public Fragment getItem(int position) {
            return ImageViewFragment.newInstance(picData.get(position)[0]);
        }

        @Override
        public int getCount() {
            return picData.size();
        }
    }
}
