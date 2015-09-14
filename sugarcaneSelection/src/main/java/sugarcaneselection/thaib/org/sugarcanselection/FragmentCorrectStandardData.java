package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.freetest.TestFragment;


public class FragmentCorrectStandardData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager mPager;
    private TabPageIndicator mIndicator;
    private PagerAdapter adapter;
    private BaseApplication baseApplication;
    private PagerTabStrip mStrip;
    // TODO: Rename and change types of parameters
    private String mFragmentNumber;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public FragmentCorrectStandardData() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentCorrectStandardData newInstance(String FragmentNumber, String param2) {
        FragmentCorrectStandardData fragment = new FragmentCorrectStandardData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, FragmentNumber);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication = (BaseApplication) getActivity().getApplication();

        if (getArguments() != null) {
            mFragmentNumber = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment_correct_standard_data, container, false);
        mPager = (ViewPager) rootView.findViewById(R.id.Viewpager);
        mStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_title_strip);

        mStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);


        ArrayList<TestFragment> s = new ArrayList<>();
        s.add(TestFragment.newInstance("1", null));
        s.add(TestFragment.newInstance("2", null));
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), s);

        mPager.setAdapter(adapter);


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<TestFragment> arrayList;

        MyPagerAdapter(FragmentManager fm, ArrayList<TestFragment> arrayList) {
            super(fm);
            this.arrayList = arrayList;
        }

        @Override
        public Fragment getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "ต้นที่ " + (position + 1);
        }

    }
}
