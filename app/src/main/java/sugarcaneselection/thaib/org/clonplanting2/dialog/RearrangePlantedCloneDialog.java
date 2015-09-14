package sugarcaneselection.thaib.org.clonplanting2.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.test.Cheeses;
import sugarcaneselection.thaib.org.clonplanting2.test.DynamicListView;
import sugarcaneselection.thaib.org.clonplanting2.test.StableArrayAdapter;

/**
 * Created by Jitpakorn on 5/9/15 AD.
 */
public class RearrangePlantedCloneDialog extends DialogFragment {

    public RearrangePlantedCloneDialog() {

    }

    public static DialogFragment newInstance(){
        DialogFragment d = new RearrangePlantedCloneDialog();

        return d;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_rearrage_planted_clone, container, false);
        DynamicListView listView = (DynamicListView) v.findViewById(R.id.listView);
        ArrayList<String> mCheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            mCheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(getActivity(), R.layout.text_view, mCheeseList);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return v;
    }
}
