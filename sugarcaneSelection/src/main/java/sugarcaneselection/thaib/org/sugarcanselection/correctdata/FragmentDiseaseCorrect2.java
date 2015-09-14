package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Dialog.DialogDiseaseList;
import sugarcaneselection.thaib.org.sugarcanselection.Interface.OnClickItemListDialogListener;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DataGroupItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.DiseaseListData;
import sugarcaneselection.thaib.org.sugarcanselection.Item.InitScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;


public class FragmentDiseaseCorrect2 extends Fragment implements View.OnClickListener, OnClickItemListDialogListener {
    private BaseApplication bApp;
    Switch Switch_Edit;
    LinearLayout mContainer;

    HashMap<String,String> DiseaseAdded;
    ArrayList<String> DiseaseNotAdd;
    HashMap<String, Integer> ViewIndex;
    ArrayList<Integer> IndividualDeleteButtonID = new ArrayList<>();
    EditText edtOtherDisesaseName;
    FloatingActionsMenu fab;
    FloatingActionButton fab_Add, fabDelete;
    private Button btAdd,btDelete;

    public static FragmentDiseaseCorrect2 newInstance() {
        FragmentDiseaseCorrect2 fragment = new FragmentDiseaseCorrect2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentDiseaseCorrect2() {

    }

    private void putDatatoView() {
        DiseaseListData d = new DiseaseListData();
        for (int i = 0; i < d.getDiseaseList().size(); i++) {
            ScoreItem item = bApp.getCloneDataItem().getDataScore().get(d.getDiseaseList().get(i).getGroupCode());
            if (item.getScore() < 0) {
                mContainer.addView(CreateRadioBox(d.getDiseaseList().get(i)));
            }
        }
        if (edtOtherDisesaseName != null){

            if (bApp.getCloneDataItem().getOtherDiseaseName()!=null ){
                if (!bApp.getCloneDataItem().getOtherDiseaseName().equals("")) {
                    edtOtherDisesaseName.setText(bApp.getCloneDataItem().getOtherDiseaseName());
                }
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("ตรวจสอบโรค");
        bApp = (BaseApplication) getActivity().getApplication();
        DiseaseAdded = new HashMap<>();
        DiseaseNotAdd = new ArrayList<>();
        ViewIndex = new HashMap<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_disease_correct2, container, false);
        btAdd = (Button) rootView.findViewById(R.id.btAdds);
        btDelete = (Button) rootView.findViewById(R.id.btDeletes);


//        fab = (FloatingActionsMenu) rootView.findViewById(R.id.fab_container);
//        fab_Add = (FloatingActionButton) rootView.findViewById(R.id.btAdd);
//        fab_Add.setOnClickListener(this);
//        fabDelete = (FloatingActionButton) rootView.findViewById(R.id.btDelete);
//        fabDelete.setOnClickListener(this);

        btAdd.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        Switch_Edit = (Switch) rootView.findViewById(R.id.switchEdit);
        mContainer = (LinearLayout) rootView.findViewById(R.id.container);
//        if (!Switch_Edit.isChecked()) {
//            fab.setVisibility(View.INVISIBLE);
//        }
//
//        Switch_Edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    fab.setVisibility(View.VISIBLE);
//                } else {
//                    fab.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

        putDatatoView();

        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        Toast.makeText(getActivity(),DiseaseAdded.toString(),Toast.LENGTH_LONG).show();
        Log.d("Debug Fragment index","View State Restore");
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("Debug ", "Attach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Debug ","Detach");
    }


    private RelativeLayout CreateRadioBox(final DataGroupItem diseaseItem) {

        final RelativeLayout container = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        container.setLayoutParams(param);

        ImageView icon = new ImageView(getActivity());

        int ic_width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
        int ic_height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams param_icon = new RelativeLayout.LayoutParams(ic_width, ic_height);
        param_icon.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        param_icon.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param_icon.setMargins(0, 10, 20, 0);

        icon.setLayoutParams(param_icon);
        icon.setMaxHeight(ic_height);
        icon.setMaxWidth(ic_width);
        icon.setImageResource(R.drawable.ic_delete);
        icon.setVisibility(View.INVISIBLE);
        container.addView(icon);


        TextView Title = new TextView(getActivity());

        Title.setLayoutParams(new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Title.setText(diseaseItem.getGroupTitle());

        final RadioGroup radioGroup = new RadioGroup(getActivity());
        param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        radioGroup.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setTag("RadioGroup");
        radioGroup.setContentDescription(diseaseItem.getGroupCode());

        radioGroup.addView(Title);

        if (diseaseItem.getOptionType() == 2) {

            final EditText edtOtherDisease = new EditText(getActivity());
            edtOtherDisease.setLayoutParams(new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            if (bApp.getCloneDataItem().getOtherDiseaseName()!=null){
                edtOtherDisease.setText(bApp.getCloneDataItem().getOtherDiseaseName());
            }else {
                edtOtherDisease.setText("โปรดระบุโรค");
            }
            edtOtherDisease.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                bApp.getCloneDataItem().setOtherDiseaseName(edtOtherDisease.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            radioGroup.addView(edtOtherDisease);
            edtOtherDisease.setHintTextColor(0xFF999999);
            edtOtherDisesaseName = edtOtherDisease;
        }
        for (int i = 0; i < diseaseItem.getScoreItem().size(); i++) {

            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setText((String) diseaseItem.getScoreItem().get(i).getData());
            radioButton.setTag(diseaseItem.getScoreItem().get(i));


            radioButton.setId(View.generateViewId());
            radioGroup.addView(radioButton);



            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        bApp.getCloneDataItem().getDataScore().put(diseaseItem.getGroupCode(), (ScoreItem) buttonView.getTag());
                    }

                }
            });
            if (i == 0) {
                radioGroup.check(radioButton.getId());
            }
        }

        container.addView(radioGroup);
        icon.setId(View.generateViewId());
        icon.setContentDescription(diseaseItem.getGroupCode());
        container.setTag(diseaseItem);
        IndividualDeleteButtonID.add(icon.getId());
        ViewIndex.put(diseaseItem.getGroupCode(), container.getId());
        DiseaseAdded.put(diseaseItem.getGroupCode(),"added");


        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                String d = (String) radioGroup.getContentDescription();
                bApp.getCloneDataItem().getDataScore().put(
                        d,
                        InitScoreItem.getDiseaseDefult(d
                        ));

                for (int j = 0 ; j < DiseaseAdded.size();j++ ){
                    if (DiseaseAdded.get(j)!=null){
                        if (DiseaseAdded.get(j).equals(d)){
                            DiseaseAdded.remove(j);
                            return;
                        }
                    }

                }



            }
        });
        return container;

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btAdds) {
            DialogFragment dialogDiseaseList = DialogDiseaseList.newInstance(FragmentDiseaseCorrect2.this);
            dialogDiseaseList.show(getActivity().getSupportFragmentManager(), "DiseaseList");
        }

        if (v.getId() == R.id.btDeletes) {
            if (IndividualDeleteButtonID.size() != 0) {
                for (int i : IndividualDeleteButtonID) {
                    if (getView().findViewById(i)!=null) {


                        if (getView().findViewById(i).getVisibility() == View.VISIBLE) {
                            getView().findViewById(i).setVisibility(View.GONE);


                        } else {
                            getView().findViewById(i).setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClickDialog(DataGroupItem groupItem) {
        int isAdded = 0;
        if (DiseaseAdded.size() != 0) {
            for (String code : DiseaseAdded.keySet()) {
                if (groupItem.getGroupCode() != code) {

                } else {
                    isAdded++;
                    Toast.makeText(getActivity(),"เพิ่มรายการนี้แล้ว", Toast.LENGTH_LONG).show();
                }
            }
          if (isAdded==0){
              mContainer.addView(CreateRadioBox(groupItem));
          }
        } else {
            mContainer.addView(CreateRadioBox(groupItem));
        }

    }

    private void getTotalScore() {
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            DataGroupItem d = (DataGroupItem) mContainer.getChildAt(i).getTag();
            if (d != null) {
                Score(d);
            }
        }
    }

    private HashMap<String, ScoreItem> ScoreMap = new HashMap<>();


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_default, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next) {
            OnClickNext();
        }

        if (item.getItemId() == R.id.CheckScore) {
            getTotalScore();
//            bApp.getCloneDataItem().getDataScore().putAll(ScoreMap);
//            Intent intent = new Intent(getActivity(), CheckScoreTestActivity.class);
//            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }

    private void OnClickNext() {


        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onFinishDiseaseCheck();


    }

    private void Score(DataGroupItem d) {
        boolean change = false;
        if (ScoreMap.size() != 0) {
            for (String tag : ScoreMap.keySet()) {
                if (d.getGroupCode() != tag) {
                    change = true;
                } else {
                    change = false;
                }
            }
        } else {
            change = true;
        }
        if (change) {
            View container = mContainer.findViewWithTag(d);
            if (container != null) {
                RadioGroup radioGroup = (RadioGroup) container.findViewWithTag("RadioGroup");
                RadioButton checked_button = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                ScoreItem tag = (ScoreItem) checked_button.getTag();
                ScoreMap.put(d.getGroupCode(), tag);
            } else {

            }

        }

    }

}
