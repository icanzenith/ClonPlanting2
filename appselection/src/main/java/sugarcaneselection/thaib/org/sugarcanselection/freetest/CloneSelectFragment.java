package sugarcaneselection.thaib.org.sugarcanselection.freetest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Interface.OnFragmentInteractionListener;
import sugarcaneselection.thaib.org.sugarcanselection.R;


public class CloneSelectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_FAMILYCODE = "FAMILYCODE";


    // TODO: Rename and change types of parameters
    private String mFamilyCode;

    private OnFragmentInteractionListener mListener;

    private BaseApplication b;


    public CloneSelectFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static CloneSelectFragment newInstance(String familycode) {
        CloneSelectFragment fragment = new CloneSelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FAMILYCODE, familycode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = (BaseApplication) getActivity().getApplication();
        if (getArguments() != null) {
            mFamilyCode = getArguments().getString(ARG_FAMILYCODE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        return rootView;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
