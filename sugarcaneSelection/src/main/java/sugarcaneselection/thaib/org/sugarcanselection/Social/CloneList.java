package sugarcaneselection.thaib.org.sugarcanselection.Social;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 1/28/15 AD.
 */
public class CloneList extends Fragment {

    private static final String ARG_PLACETEST = "Placetest";

    private String mPlaceTest;


    public CloneList() {
    }

    public static Fragment newInstance(String PlaceTest) {
        Fragment fragment = new CloneList();
        Bundle bundle = new Bundle();
        bundle.putString("PlaceTest", PlaceTest);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clone_list, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
