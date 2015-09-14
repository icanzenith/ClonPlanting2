package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DataGroupItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreListData;
import sugarcaneselection.thaib.org.sugarcanselection.R;



public class DataEntryStuffFragment extends Fragment {

    private LinearLayout rootContainer;
    private BaseApplication baseApplication;


    public static DataEntryStuffFragment newInstance() {
        DataEntryStuffFragment fragment = new DataEntryStuffFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public DataEntryStuffFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        baseApplication = (BaseApplication) getActivity().getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_entry3, container, false);
        rootContainer = (LinearLayout) rootView.findViewById(R.id.container);
        ScoreListData sc = new ScoreListData();
        for (int i = 0 ; i< sc.getStuffFragmentDataList().size() ;i++){
            rootContainer.addView(CreateDataGroup(sc.getStuffFragmentDataList().get(i)));
        }
        putDatatoView();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    //TODO WRITE COMMENT FOR UNDERSTANDING THIS METTHOD!!!!
    /**
     *
     * CreateDataGroup use DataGroupItem class to Create
     *
     *
     * */
    private RelativeLayout CreateDataGroup(final DataGroupItem item) {

        final RelativeLayout container = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        container.setLayoutParams(param);

        TextView Title = new TextView(getActivity());

        Title.setLayoutParams(new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Title.setText(item.getGroupTitle());

        RadioGroup radioGroup = new RadioGroup(getActivity());
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        radioGroup.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setTag(item);
        radioGroup.setContentDescription(item.getGroupCode().toString());

        radioGroup.addView(Title);

        for (int i = 0; i < item.getScoreItem().size(); i++) {

            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setText(item.getScoreItem().get(i).getData().toString());
            radioButton.setTag(item.getScoreItem().get(i));

            radioButton.setId(View.generateViewId());
            radioGroup.addView(radioButton);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        buttonView.setTextSize(32f);
                        buttonView.setTextColor(0xFFAA0000);
                        buttonView.setBackgroundColor(0x3300AA00);
                    }else{
                        buttonView.setTextSize(20f);
                        buttonView.setTextColor(0xFF000000);
                        buttonView.setBackgroundColor(0x00000000);
                    }
                }
            });
            if (i == 0){
                radioGroup.check(radioButton.getId());
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                DataGroupItem d = (DataGroupItem) group.getTag();
                RadioButton radioButton = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
                ScoreItem item = (ScoreItem) radioButton.getTag();
                baseApplication.getCloneDataItem().getDataScore().put(d.getGroupCode(),item);
            }
        });

        container.addView(radioGroup);

        container.setTag(item);



        return container;

    }

    private void putDatatoView() {

        ScoreListData sc = new ScoreListData();
        for (int i = 0; i < sc.getStuffFragmentDataList().size(); i++) {
            final ArrayList<View> outViews = new ArrayList<View>();
            RadioGroup rd;
            rootContainer.findViewsWithText(outViews, sc.getStuffFragmentDataList().get(i).getGroupCode(), View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            if (outViews != null) {
                rd = (RadioGroup) outViews.get(0);
            } else {
                rd = null;
            }
            for (int j = 0; j < sc.getStuffFragmentDataList().get(i).getScoreItem().size(); j++) {

                ScoreItem tag = sc.getStuffFragmentDataList().get(i).getScoreItem().get(j);
                if (baseApplication.getCloneDataItem().getDataScore().get(tag.getTagName()).getData()!=null){
                    if (tag.getData().toString().equals(baseApplication.getCloneDataItem().getDataScore().get(tag.getTagName()).getData().toString())) {
                        for (int x = 0; x < rd.getChildCount(); x++) {
                            RadioButton rb = null;
                            try {
                                rb = (RadioButton) rd.getChildAt(x);
                            } catch (Exception e) {

                            }
                            if (rb != null) {
                                if (rb.getText().toString().equals(baseApplication.getCloneDataItem().getDataScore().get(tag.getTagName()).getData().toString())) {
                                    rb.setChecked(true);
                                }
                            }
                        }

                    } else {

                    }
                }

            }
        }


    }
    @Override
    public void onDetach() {
        super.onDetach();
    }




}
