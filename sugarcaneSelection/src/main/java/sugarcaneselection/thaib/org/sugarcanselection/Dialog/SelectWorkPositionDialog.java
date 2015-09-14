package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.SessionManager;

/**
 * Created by Jitpakorn on 1/27/15 AD.
 */
public class SelectWorkPositionDialog extends DialogFragment {

    private String mWorkPosition;
    private RadioGroup mRadioGroup;

    public SelectWorkPositionDialog() {
    }

    public DialogFragment newInstance() {
        DialogFragment d = new SelectWorkPositionDialog();
        Bundle bundle = new Bundle();
        d.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        d.setArguments(bundle);
        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_select_work_position, null);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.rgWorkPosition);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rbTeamA:

                        if (getActivity().getSharedPreferences(SessionManager.PREF_NAME, 0) != null) {
                            SessionManager sessionManager = new SessionManager(getActivity().getApplication(), getActivity());
                            sessionManager.selectWorkPosition(1);
                        }
                        break;
                    case R.id.rbTeamB:
                        if (getActivity().getSharedPreferences(SessionManager.PREF_NAME, 0) != null) {
                            SessionManager sessionManager = new SessionManager(getActivity().getApplication(), getActivity());
                            sessionManager.selectWorkPosition(2);
                        }

                        break;
                }
            }
        });


        return v;
    }
}
