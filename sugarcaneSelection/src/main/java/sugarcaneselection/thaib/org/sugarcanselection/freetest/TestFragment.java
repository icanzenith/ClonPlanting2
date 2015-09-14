package sugarcaneselection.thaib.org.sugarcanselection.freetest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
//import sugarcaneselection.thaib.org.sugarcanselection.FragmentCorrectStandardData;
//import sugarcaneselection.thaib.org.sugarcanselection.Fragment_CorrectStandardData;
import sugarcaneselection.thaib.org.sugarcanselection.R;


public class TestFragment extends Fragment {

    public static final String TAG_DEBUG = "Debug : ";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager mPager;
    PagerTabStrip mStrip;
    MyViewPagerAdapter mAdapter;
    BaseApplication b;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " onCreate ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " onDestroy ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " onCreateView ");
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        TextView tv_fragmentNumber = (TextView) rootView.findViewById(R.id.tv_fragmentNumber);
        tv_fragmentNumber.setText(mParam1);

        mPager = (ViewPager) rootView.findViewById(R.id.ViewPager);
        mStrip = (PagerTabStrip) rootView.findViewById(R.id.pager_title_strip);

        Vector<Fragment> vector = new Vector<>();

        mAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager(), vector);
        mPager.setAdapter(mAdapter);


        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " OnViewStateRestored ");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " onAttach ");
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG_DEBUG, "Fragment :" + mParam1 + " onDetach ");
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        Vector<Fragment> vector;

        MyViewPagerAdapter(FragmentManager fm, Vector<Fragment> vector) {
            super(fm);
            this.vector = vector;
        }

        @Override
        public Fragment getItem(int position) {
            return vector.get(position);
        }

        @Override
        public int getCount() {
            return vector.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Fragement : " + (position + 1);
        }
    }
}
