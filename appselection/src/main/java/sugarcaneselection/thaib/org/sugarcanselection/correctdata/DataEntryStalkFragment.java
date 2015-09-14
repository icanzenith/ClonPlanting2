package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.security.CodeSigner;
import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.InternodeLength;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.StalkSize;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.ScoreOption;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class DataEntryStalkFragment extends Fragment {

    private BaseApplication baseApplication;

    private EditText edtStalkSize1;
    private EditText edtStalkSize2;
    private EditText edtStalkSize3;
    private EditText edtStalkSize4;
    private EditText edtStalkSize5;

    private EditText edtInternodeLength1;
    private EditText edtInternodeLength2;
    private EditText edtInternodeLength3;
    private EditText edtInternodeLength4;
    private EditText edtInternodeLength5;

    private TextView tvStalkSizeAverage;
    private TextView tvInternodeLengthAverage;

    private EditText edtStalkPerClump;
    private EditText edtInternodeAmount;
    private EditText edtHeight;

    private TextWatcher textWatcher, textWatcher2;


    public static Fragment newInstance() {
        DataEntryStalkFragment fragment = new DataEntryStalkFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public DataEntryStalkFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication = (BaseApplication) getActivity().getApplication();
        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_correct_data_part2, container, false);

        //TODO Initialize View
        edtStalkSize1 = (EditText) rootView.findViewById(R.id.StalkSize1);
        edtStalkSize2 = (EditText) rootView.findViewById(R.id.StalkSize2);
        edtStalkSize3 = (EditText) rootView.findViewById(R.id.StalkSize3);
        edtStalkSize4 = (EditText) rootView.findViewById(R.id.StalkSize4);
        edtStalkSize5 = (EditText) rootView.findViewById(R.id.StalkSize5);
        tvStalkSizeAverage = (TextView) rootView.findViewById(R.id.StalkSizeAverage);

        edtInternodeLength1 = (EditText) rootView.findViewById(R.id.InternodeLength1);
        edtInternodeLength2 = (EditText) rootView.findViewById(R.id.InternodeLength2);
        edtInternodeLength3 = (EditText) rootView.findViewById(R.id.InternodeLength3);
        edtInternodeLength4 = (EditText) rootView.findViewById(R.id.InternodeLength4);
        edtInternodeLength5 = (EditText) rootView.findViewById(R.id.InternodeLength5);
        tvInternodeLengthAverage = (TextView) rootView.findViewById(R.id.InternodeLengthAverage);

        edtHeight = (EditText) rootView.findViewById(R.id.edtHeight);
        edtStalkPerClump = (EditText) rootView.findViewById(R.id.edtStalkperClump);
        edtInternodeAmount = (EditText) rootView.findViewById(R.id.edtInternodeAmount);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CalculateStalkSizeScore();
                CalculateInternodeLengthScore();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Add Score Here
                if (rootView.findFocus() == edtStalkPerClump) {

                    int stalkperclump;
                    if (!edtStalkPerClump.getText().toString().equals("")) {
                        stalkperclump = Integer.parseInt(edtStalkPerClump.getText().toString());
                        ScoreItem StalkperClumpScore = ScoreOption.StalkPerClumpScore(stalkperclump);
                        baseApplication.getCloneDataItem().getDataScore().put(Code.StalkAmount, StalkperClumpScore);

                    }

                }

                if (rootView.findFocus() == edtInternodeAmount) {

                    int InternodeAmount;
                    if (!edtInternodeAmount.getText().toString().equals("")) {
                        InternodeAmount = Integer.parseInt(edtInternodeAmount.getText().toString());
                        ScoreItem InternodeAmountScore = ScoreOption.InternodeAmountScore(InternodeAmount);
                        baseApplication.getCloneDataItem().getDataScore().put(Code.InternodeAmount, InternodeAmountScore);


                    }

                }

                if (rootView.findFocus() == edtHeight) {

                    //TODO get Standard Specie Score
                    if (!edtHeight.getText().toString().equals("")) {
                        int Height = Integer.parseInt(edtHeight.getText().toString());
                        ScoreItem HeightScore = ScoreOption.Height(Height, getStandardAverageHeight());
                        baseApplication.getCloneDataItem().getDataScore().put(Code.Height, HeightScore);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        /**
         *
         * Add Text Watcher
         *
         * */

        edtStalkSize1.addTextChangedListener(textWatcher);
        edtStalkSize2.addTextChangedListener(textWatcher);
        edtStalkSize3.addTextChangedListener(textWatcher);
        edtStalkSize4.addTextChangedListener(textWatcher);
        edtStalkSize5.addTextChangedListener(textWatcher);

        edtInternodeLength1.addTextChangedListener(textWatcher);
        edtInternodeLength2.addTextChangedListener(textWatcher);
        edtInternodeLength3.addTextChangedListener(textWatcher);
        edtInternodeLength4.addTextChangedListener(textWatcher);
        edtInternodeLength5.addTextChangedListener(textWatcher);

        edtHeight.addTextChangedListener(textWatcher2);
        edtStalkPerClump.addTextChangedListener(textWatcher2);
        edtInternodeAmount.addTextChangedListener(textWatcher2);

        putDatatoView();
        return rootView;
    }

    public int getStandardAverageHeight() {
        int standardAverageHeight = 0;
        String FamilyCode = baseApplication.getCloneDataItem().getFamilycode();
        ContentResolver cr = getActivity().getContentResolver();
        String where = Columns.FAMILYCODE+" = ?";
        String[] selection = {FamilyCode};
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
        Cursor c = cr.query(uri,null,where,selection,null);
        if(c.getCount()!=0){
            while (c.moveToNext()){
                standardAverageHeight = standardAverageHeight+c.getInt(c.getColumnIndex(Columns.HEIGHT));
            }
            standardAverageHeight = (standardAverageHeight/c.getCount());
        }
        return standardAverageHeight;
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


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void CalculateStalkSizeScore() {

        Float StalkSizeAverage;

        ArrayList<Float> StalkSizeArray = new ArrayList<>();
        if (!edtStalkSize1.getText().toString().equals("")) {
            StalkSizeArray.add(Float.parseFloat(edtStalkSize1.getText().toString()));
            baseApplication.getCloneDataItem().getStalkSize().put(StalkSize.StalkSize1.name(), Float.parseFloat(edtStalkSize1.getText().toString()));

        }

        if (!edtStalkSize2.getText().toString().equals("")) {
            StalkSizeArray.add(Float.parseFloat(edtStalkSize2.getText().toString()));
            baseApplication.getCloneDataItem().getStalkSize().put(StalkSize.StalkSize2.name(), Float.parseFloat(edtStalkSize2.getText().toString()));
        }

        if (!edtStalkSize3.getText().toString().equals("")) {
            StalkSizeArray.add(Float.parseFloat(edtStalkSize3.getText().toString()));
            baseApplication.getCloneDataItem().getStalkSize().put(StalkSize.StalkSize3.name(), Float.parseFloat(edtStalkSize3.getText().toString()));
        }

        if (!edtStalkSize4.getText().toString().equals("")) {
            StalkSizeArray.add(Float.parseFloat(edtStalkSize4.getText().toString()));
            baseApplication.getCloneDataItem().getStalkSize().put(StalkSize.StalkSize4.name(), Float.parseFloat(edtStalkSize4.getText().toString()));
        }

        if (!edtStalkSize5.getText().toString().equals("")) {
            StalkSizeArray.add(Float.parseFloat(edtStalkSize5.getText().toString()));
            baseApplication.getCloneDataItem().getStalkSize().put(StalkSize.StalkSize5.name(), Float.parseFloat(edtStalkSize5.getText().toString()));
        }

        float totalstalksize = 0f;

        for (int i = 0; i < StalkSizeArray.size(); i++) {
            totalstalksize = totalstalksize + StalkSizeArray.get(i);

        }
        if (StalkSizeArray.size() > 0) {
            StalkSizeAverage = totalstalksize / StalkSizeArray.size();

            tvStalkSizeAverage.setText(String.format("%.2f", StalkSizeAverage));
            ScoreItem item = ScoreOption.StalkSize(StalkSizeAverage);
            baseApplication.getCloneDataItem().getDataScore().put(Code.StalkSize, item);
        }
    }

    private void CalculateInternodeLengthScore() {

        Float InternodeLengthAverage;

        ArrayList<Float> InternodeLengthArray = new ArrayList<>();
        if (!edtInternodeLength1.getText().toString().equals("")) {
            InternodeLengthArray.add(Float.parseFloat(edtInternodeLength1.getText().toString()));
            baseApplication.getCloneDataItem().getInternodeLength().put(InternodeLength.InternodeLength1.name(), Float.parseFloat(edtInternodeLength1.getText().toString()));
        }

        if (!edtInternodeLength2.getText().toString().equals("")) {
            InternodeLengthArray.add(Float.parseFloat(edtInternodeLength2.getText().toString()));
            baseApplication.getCloneDataItem().getInternodeLength().put(InternodeLength.InternodeLength2.name(), Float.parseFloat(edtInternodeLength2.getText().toString()));


        }

        if (!edtInternodeLength3.getText().toString().equals("")) {
            InternodeLengthArray.add(Float.parseFloat(edtInternodeLength3.getText().toString()));
            baseApplication.getCloneDataItem().getInternodeLength().put(InternodeLength.InternodeLength3.name(), Float.parseFloat(edtInternodeLength3.getText().toString()));


        }

        if (!edtInternodeLength4.getText().toString().equals("")) {
            InternodeLengthArray.add(Float.parseFloat(edtInternodeLength4.getText().toString()));
            baseApplication.getCloneDataItem().getInternodeLength().put(InternodeLength.InternodeLength4.name(), Float.parseFloat(edtInternodeLength4.getText().toString()));

        }

        if (!edtInternodeLength5.getText().toString().equals("")) {
            InternodeLengthArray.add(Float.parseFloat(edtInternodeLength5.getText().toString()));
            baseApplication.getCloneDataItem().getInternodeLength().put(InternodeLength.InternodeLength5.name(), Float.parseFloat(edtInternodeLength5.getText().toString()));

        }

        float totalintenodelentgth = 0f;

        for (int i = 0; i < InternodeLengthArray.size(); i++) {
            totalintenodelentgth = totalintenodelentgth + InternodeLengthArray.get(i);
        }

        if (InternodeLengthArray.size() > 0) {
            InternodeLengthAverage = totalintenodelentgth / InternodeLengthArray.size();
            tvInternodeLengthAverage.setText(String.format("%.2f", InternodeLengthAverage));
            ScoreItem item = ScoreOption.InternodeLengthScore(InternodeLengthAverage);
            baseApplication.getCloneDataItem().getDataScore().put(Code.InternodeLength, item);


        }

    }

    private void putDatatoView() {
        HashMap<String, Float> stalkSizeMap = baseApplication.getCloneDataItem().getStalkSize();
        HashMap<String, Float> InternodeLengthMap = baseApplication.getCloneDataItem().getInternodeLength();
        if (stalkSizeMap.get(StalkSize.StalkSize1.name()) != 0) {
            edtStalkSize1.setText("" + stalkSizeMap.get(StalkSize.StalkSize1.name()));
        } else {
            edtStalkSize1.setText("");
        }
        if (stalkSizeMap.get(StalkSize.StalkSize2.name()) != 0) {
            edtStalkSize2.setText("" + stalkSizeMap.get(StalkSize.StalkSize2.name()));
        } else {
            edtStalkSize2.setText("");
        }
        if (stalkSizeMap.get(StalkSize.StalkSize3.name()) != 0) {
            edtStalkSize3.setText("" + stalkSizeMap.get(StalkSize.StalkSize3.name()));
        } else {
            edtStalkSize3.setText("");
        }
        if (stalkSizeMap.get(StalkSize.StalkSize4.name()) != 0) {
            edtStalkSize4.setText("" + stalkSizeMap.get(StalkSize.StalkSize4.name()));
        } else {
            edtStalkSize4.setText("");
        }
        if (stalkSizeMap.get(StalkSize.StalkSize5.name()) != 0) {
            edtStalkSize5.setText("" + stalkSizeMap.get(StalkSize.StalkSize5.name()));
        } else {
            edtStalkSize5.setText("");
        }


        if (InternodeLengthMap.get(InternodeLength.InternodeLength1.name()) != 0) {
            edtInternodeLength1.setText("" + InternodeLengthMap.get(InternodeLength.InternodeLength1.name()));
        } else {
            edtInternodeLength1.setText("");
        }
        if (InternodeLengthMap.get(InternodeLength.InternodeLength2.name()) != 0) {
            edtInternodeLength2.setText("" + InternodeLengthMap.get(InternodeLength.InternodeLength2.name()));
        } else {
            edtInternodeLength2.setText("");
        }
        if (InternodeLengthMap.get(InternodeLength.InternodeLength3.name()) != 0) {
            edtInternodeLength3.setText("" + InternodeLengthMap.get(InternodeLength.InternodeLength3.name()));
        } else {
            edtInternodeLength3.setText("");
        }
        if (InternodeLengthMap.get(InternodeLength.InternodeLength4.name()) != 0) {
            edtInternodeLength4.setText("" + InternodeLengthMap.get(InternodeLength.InternodeLength4.name()));
        } else {
            edtInternodeLength4.setText("");
        }
        if (InternodeLengthMap.get(InternodeLength.InternodeLength5.name()) != 0) {
            edtInternodeLength5.setText("" + InternodeLengthMap.get(InternodeLength.InternodeLength5.name()));
        } else {
            edtInternodeLength5.setText("");
        }


        if ((Integer) baseApplication.getCloneDataItem().getDataScore().get(Code.Height).getData() != 0) {
            edtHeight.setText("" + baseApplication.getCloneDataItem().getDataScore().get(Code.Height).getData());
        } else {
            edtHeight.setText("");
        }
        if (baseApplication.getCloneDataItem().getDataScore().get(Code.StalkAmount) != null) {
            if (baseApplication.getCloneDataItem().getDataScore().get(Code.StalkAmount).getData() != null) {
                Log.d("Stalk Amount", baseApplication.getCloneDataItem().getDataScore().get(Code.StalkAmount).getData().toString());
                if ((Integer) baseApplication.getCloneDataItem().getDataScore().get(Code.StalkAmount).getData() != 0) {
                    edtStalkPerClump.setText("" + baseApplication.getCloneDataItem().getDataScore().get(Code.StalkAmount).getData());
                } else {
                    edtStalkPerClump.setText("");
                }
            }
        }


        if ((Integer) baseApplication.getCloneDataItem().getDataScore().get(Code.InternodeAmount).getData() != 0) {
            edtInternodeAmount.setText("" + baseApplication.getCloneDataItem().getDataScore().get(Code.InternodeAmount).getData());
        } else {
            edtInternodeAmount.setText("");
        }


    }

}
