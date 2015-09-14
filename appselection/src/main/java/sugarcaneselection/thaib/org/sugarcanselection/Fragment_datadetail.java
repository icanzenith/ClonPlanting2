package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;


public class Fragment_datadetail extends Fragment {

    private static final String ARG_CLONECODE = "clonecode";
    private static final String ARG_PARAM2 = "param2";
    private IconRoundCornerProgressBar progressBar;

    private String Clonecode;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_datadetail() {
        // Required empty public constructor
    }

    public static Fragment_datadetail newInstance(String clonecode, String param2) {
        Fragment_datadetail fragment = new Fragment_datadetail();
        Bundle args = new Bundle();
        args.putString(ARG_CLONECODE, clonecode);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Clonecode = getArguments().getString(ARG_CLONECODE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_datadetail, container, false);
        progressBar = (IconRoundCornerProgressBar) rootView.findViewById(R.id.pBar_observe);
        progressBar.setMax(100);
        progressBar.setBackgroundColor(0xFF000000);
        progressBar.setProgressColor(0xFFFFFFFF);
        progressBar.setProgress(50);
        return rootView;
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
        public void onFragmentInteraction(Uri uri);
    }

}
