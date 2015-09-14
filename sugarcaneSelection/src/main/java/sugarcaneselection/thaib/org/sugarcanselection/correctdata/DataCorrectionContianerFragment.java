package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 2/23/15 AD.
 */
public class DataCorrectionContianerFragment extends Fragment{

    HashMap<String,ScoreItem> scoreItem;

    public DataCorrectionContianerFragment() {
    }

    public static Fragment newInstance(){

        Fragment fragment = new DataCorrectionContianerFragment();

        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){

        }
        scoreItem = new HashMap<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_correct,container,false);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
