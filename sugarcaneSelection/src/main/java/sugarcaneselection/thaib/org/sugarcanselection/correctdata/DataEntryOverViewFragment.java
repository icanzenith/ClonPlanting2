package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DataGroupItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreListData;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.ScoreOption;


public class DataEntryOverViewFragment extends Fragment {

    private BaseApplication baseApplication;

    private ScoreItem OverAllScore;

    private MainActivity main;
    private RatingBar ratingBarOverView;
    private TextView tvOverViewScore;
    private SeekBar seekBarOverall;

    private EditText edtBrix;

    private LinearLayout rootContainer;
    private HashMap<String, ScoreItem> OverViewScoreMap;
    private ActionBar a;
    private int CloneType;
    HashMap<String, ScoreItem> scoreItem;


    public DataEntryOverViewFragment() {

    }

    public static Fragment newInstance(int CloneType) {
        Fragment fragment = new DataEntryOverViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CloneType", CloneType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CloneType = getArguments().getInt("CloneType");
        }

        baseApplication = (BaseApplication) getActivity().getApplication();
        scoreItem = new HashMap<>();
        main = (MainActivity) getActivity();
        a = main.getSupportActionBar();
    }


    RadioGroup GroupSpecieName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overview_data, container, false);
        GroupSpecieName = (RadioGroup) rootView.findViewById(R.id.groupSpecieName);
        if (CloneType == 2) {
            GroupSpecieName.setVisibility(View.VISIBLE);
            GroupSpecieName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton d;
                    switch (checkedId)
                    {
                        case R.id.rb_kk3:
                            d = (RadioButton) group.findViewById(R.id.rb_kk3);
                            baseApplication.getCloneDataItem().setSpecieName(d.getContentDescription().toString());
                            break;
                        case R.id.rb_lk92_11:
                            d = (RadioButton) group.findViewById(R.id.rb_lk92_11);
                            baseApplication.getCloneDataItem().setSpecieName(d.getContentDescription().toString());
                            break;
                    }
                }
            });
            if (baseApplication.getCloneDataItem().getSpecieName()!=null){
                View kk = GroupSpecieName.findViewById(R.id.rb_kk3);
                View lk = GroupSpecieName.findViewById(R.id.rb_lk92_11);

                if (baseApplication.getCloneDataItem().getSpecieName().toString().equals(kk.getContentDescription().toString())){
                    GroupSpecieName.check(R.id.rb_kk3);
                }else if(baseApplication.getCloneDataItem().getSpecieName().toString().equals(lk.getContentDescription().toString())) {
                    GroupSpecieName.check(R.id.rb_lk92_11);
                }

            }else{
                GroupSpecieName.check(R.id.rb_kk3);

            }
        } else {
            GroupSpecieName.setVisibility(View.GONE);
        }


        rootContainer = (LinearLayout) rootView.findViewById(R.id.container);

        // OverAll
        ratingBarOverView = (RatingBar) rootView.findViewById(R.id.ratingBarOverView);
        tvOverViewScore = (TextView) rootView.findViewById(R.id.tvOverView);
        seekBarOverall = (SeekBar) rootView.findViewById(R.id.seekBar_overall);

        seekBarOverall.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Float overallscore = Float.valueOf((float)progress/2);
                tvOverViewScore.setText( overallscore+ " คะแนน");
                ratingBarOverView.setRating(overallscore);
                OverAllScore = ScoreOption.OverViewScore(overallscore);
                baseApplication.getCloneDataItem().getDataScore().put(Code.OverAll, OverAllScore);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        ratingBarOverView.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        tvOverViewScore.setText(rating + " คะแนน");
                        OverAllScore = ScoreOption.OverViewScore(rating);

                        baseApplication.getCloneDataItem().getDataScore().put(Code.OverAll, OverAllScore);

                    }
                });

        //Brix

        edtBrix = (EditText) rootView.findViewById(R.id.edtBrix);
        TextWatcher t = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtBrix.getText().toString().equals("")) {
                    baseApplication.getCloneDataItem().getDataScore().put(Code.Brix, ScoreOption.BrixScore(Float.parseFloat(edtBrix.getText().toString())));
                }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edtBrix.addTextChangedListener(t);


        ScoreListData sc = new ScoreListData();
        for (int i = 0; i < sc.getDataList().size(); i++) {
            rootContainer.addView(CreateDataGroup(sc.getDataList().get(i)));
        }

        putDatatoView();


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Rating Bar Overall
        if (OverAllScore != null) {
            ratingBarOverView.setRating((Float) OverAllScore.getData());
            tvOverViewScore.setText(OverAllScore.getData() + "คะแนน");
        }
    }

    private void putDatatoView() {

        HashMap<String, ScoreItem> d = baseApplication.getCloneDataItem().getDataScore();

        Float overallscore = (Float) d.get(Code.OverAll).getData()*2;
        ratingBarOverView.setRating((Float) d.get(Code.OverAll).getData());
        seekBarOverall.setProgress(overallscore.intValue());

        if (d.get(Code.Brix).getData().getClass() == Float.class){
            if (d.get(Code.Brix).getData().equals(0f))
            {
                edtBrix.setText("");

            }else{
                edtBrix.setText(""+d.get(Code.Brix).getData());
            }
        }else{
            edtBrix.setText("" + d.get(Code.Brix).getData());

        }

        ScoreListData sc = new ScoreListData();
        for (int i = 0; i < sc.getDataList().size(); i++) {
            final ArrayList<View> outViews = new ArrayList<View>();
            RadioGroup rd;
            rootContainer.findViewsWithText(outViews, sc.getDataList().get(i).getGroupCode(), View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            if (outViews != null) {
                rd = (RadioGroup) outViews.get(0);
            } else {
                rd = null;
            }
            for (int j = 0; j < sc.getDataList().get(i).getScoreItem().size(); j++) {

                ScoreItem tag = sc.getDataList().get(i).getScoreItem().get(j);

            if (tag.getData()!=null && baseApplication.getCloneDataItem().getDataScore().get(tag.getTagName()).getData() != null){
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


    private RelativeLayout CreateDataGroup(final DataGroupItem item) {

        final RelativeLayout container = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        container.setLayoutParams(param);

        TextView Title = new TextView(getActivity());

        Title.setLayoutParams(new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Title.setText(item.getGroupTitle());

        final RadioGroup radioGroup = new RadioGroup(getActivity());
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
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setText(item.getScoreItem().get(i).getData().toString());
            radioButton.setTag(item.getScoreItem().get(i));

            radioButton.setId(View.generateViewId());

            radioGroup.addView(radioButton);

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setTextSize(32f);
                        buttonView.setTextColor(0xFFAA0000);
                        buttonView.setBackgroundColor(0x3300AA00);
                    } else {
                        buttonView.setTextSize(20f);
                        buttonView.setTextColor(0xFF000000);
                        buttonView.setBackgroundColor(0x00000000);
                    }
                }
            });
            if (i == 0) {
                radioGroup.check(radioButton.getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                DataGroupItem dataGroupItem = (DataGroupItem) group.getTag();
                RadioButton b = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
                ScoreItem item = (ScoreItem) b.getTag();
                baseApplication.getCloneDataItem().getDataScore().put(dataGroupItem.getGroupCode(), item);

            }
        });

        container.addView(radioGroup);

        container.setTag(item);


        return container;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }
}
