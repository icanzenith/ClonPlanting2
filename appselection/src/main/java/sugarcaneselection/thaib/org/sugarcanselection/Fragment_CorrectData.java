//package sugarcaneselection.thaib.org.sugarcanselection;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.SeekBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class Fragment_CorrectData extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
//
//    MainActivity mainActivity;
//    BaseApplication baseApplication;
//    SeekBar SB_stalkperclump, SB_brix, SB_height, SB_size, SB_observe, SB_internode, sb_internodeLenght;
//    TextView tv_stalkperclump, tv_brix, tv_height, tv_observe, tv_internode, tv_internodeLenght, tv_conclusion_height;
//    int stalkperclump = 0;
//    int brix = 0;
//    int height = 0;
//    int size = 0;
//    Float observe = 0.2f;
//    int leaffall = 0;
//    int internodeLenght = 0;
//    Float f_size = 0.2f;
//    Float f_brix = 0.2f;
//    int height_score = 0;
//    int brix_score = 0;
//    int stalkperclump_score = 0;
//    int size_score = 0;
//    int type_score = 0;
//    int texture_score = 0;
//    float observe_score = 0;
//    int scStalkShape = 0;
//    int scClumpShape = 0;
//    int scDisease = 0;
//    int scStuffDensity = 0;
//    int scLeafSheath = 0;
//    int result = 0;
//    private RadioGroup rgStalkShape, rgClumpShape, rgStuffDensity, rgDisease, rgLeafSheath;
//    private EditText StalkSize1, StalkSize2, StalkSize3, StalkSize4, StalkSize5;
//    private EditText InternodeLength1, InternodeLength2, InternodeLength3, InternodeLength4, InternodeLength5;
//    private EditText edtStalkPerClump, edtOverView, edtBrix, edtInternodeAmount, edtHeight;
//    private Button btAverage;
//    private TextView tvStalkSizeAverage, tvInternodeLengthAverage;
//    private Float vStalkSize1, vStalkSize2, vStalkSize3, vStalkSize4, vStalkSize5, vStalkSizeAverage = new Float(0);
//    private Float vInternodeLength1, vInternodeLength2, vInternodeLength3, vInternodeLength4, vInternodeLength5, vInternodeLengthAverage = new Float(0);
//    private RadioButton rbClumpShape0, rbClumpShape1, rbClumpShape2, rbClumpShape3;
//    private RadioButton rbStalkShape0, rbStalkShape1, rbStalkShape2, rbStalkShape3, rbStalkShape4;
//    private RadioButton rbDisease0, rbDisease1, rbDisease2;
//    private RadioButton rbStuffDensity0, rbStuffDensity1, rbStuffDensity2, rbStuffDensity3, rbStuffDensity4, rbStuffDensity5;
//    private RadioButton rbLeafSheath0, rbLeafSheath1, rbLeafSheath2, rbLeafSheath3, rbLeafSheath4;
//    private Button bt_increase_height;
//    private Button bt_decrease_height;
//    private Button btIncreaseStalkPerClump;
//    private Button btDecreaseStalkPerClump;
//    private Button btIncreaseBrix;
//    private Button btDecreaseBrix;
//    private Button btIncreaseInternodeAmount;
//    private Button btDecreaseInternodeAmount;
//    private Button btIncreaseObserve;
//    private Button btDecreaseObserve;
//    private CalculateScore cal;
//    private Integer Height;
//    private Integer InternodeAmount;
//    private Integer StalkPerClump;
//    private Float OverView;
//    private Float Brix;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        mainActivity = (MainActivity) getActivity();
//        baseApplication = (BaseApplication) mainActivity.getApplication();
//        if (baseApplication.getCalculateScore() != null) {
//            cal = baseApplication.getCalculateScore();
//        } else {
//            baseApplication.setCalculateScore(new CalculateScore());
//            cal = baseApplication.getCalculateScore();
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_default, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.next) {
//            CalculateAverageInternode();
//            onGetDataFromEditText();
//            mainActivity.onFinishCorrectData();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void onGetDataFromEditText() {
//
//
//        Height = Integer.parseInt(edtHeight.getText().toString());
//        InternodeAmount = Integer.parseInt(edtInternodeAmount.getText().toString());
//        StalkPerClump = Integer.parseInt(edtStalkPerClump.getText().toString());
//        Brix = Float.parseFloat(edtBrix.getText().toString());
//        OverView = Float.parseFloat(edtOverView.getText().toString());
//
//        baseApplication.getCloneDataItem().setHeight(Height);
//        baseApplication.getCloneDataItem().setInterNodePerStalk(InternodeAmount);
//        baseApplication.getCloneDataItem().setStalkperclump(StalkPerClump);
//        baseApplication.getCloneDataItem().setBrix(Brix);
//        baseApplication.getCloneDataItem().setObserve_score(OverView);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_correctdata, container, false);
//
//        rgClumpShape = (RadioGroup) rootView.findViewById(R.id.rgClumpShape);
//        rgDisease = (RadioGroup) rootView.findViewById(R.id.rgDisease);
//        rgStalkShape = (RadioGroup) rootView.findViewById(R.id.rgStalkShape);
//        rgStuffDensity = (RadioGroup) rootView.findViewById(R.id.rgStuffDensity);
//        rgLeafSheath = (RadioGroup) rootView.findViewById(R.id.rgLeafSheath);
//
//        rgClumpShape.setOnCheckedChangeListener(this);
//        rgDisease.setOnCheckedChangeListener(this);
//        rgStalkShape.setOnCheckedChangeListener(this);
//        rgStuffDensity.setOnCheckedChangeListener(this);
//        rgLeafSheath.setOnCheckedChangeListener(this);
//
//        //Set Radio Button Here
//        InitailRadioButton(rootView);
//        InitialEditText(rootView);
//
//
//        StalkSize1 = (EditText) rootView.findViewById(R.id.StalkSize1);
//        StalkSize2 = (EditText) rootView.findViewById(R.id.StalkSize2);
//        StalkSize3 = (EditText) rootView.findViewById(R.id.StalkSize3);
//        StalkSize4 = (EditText) rootView.findViewById(R.id.StalkSize4);
//        StalkSize5 = (EditText) rootView.findViewById(R.id.StalkSize5);
//
//        InternodeLength1 = (EditText) rootView.findViewById(R.id.InternodeLength1);
//        InternodeLength2 = (EditText) rootView.findViewById(R.id.InternodeLength2);
//        InternodeLength3 = (EditText) rootView.findViewById(R.id.InternodeLength3);
//        InternodeLength4 = (EditText) rootView.findViewById(R.id.InternodeLength4);
//        InternodeLength5 = (EditText) rootView.findViewById(R.id.InternodeLength5);
//
//        tvInternodeLengthAverage = (TextView) rootView.findViewById(R.id.InternodeLengthAverage);
//        tvStalkSizeAverage = (TextView) rootView.findViewById(R.id.StalkSizeAverage);
//
//        btAverage = (Button) rootView.findViewById(R.id.btAverage);
//        btAverage.setOnClickListener(this);
//
//        bt_increase_height = (Button) rootView.findViewById(R.id.bt_increse_height);
//        bt_decrease_height = (Button) rootView.findViewById(R.id.bt_decrease_height);
//        bt_increase_height.setOnClickListener(this);
//        bt_decrease_height.setOnClickListener(this);
//
//
//        btIncreaseStalkPerClump = (Button) rootView.findViewById(R.id.bt_increase_StalkPerClump);
//        btDecreaseStalkPerClump = (Button) rootView.findViewById(R.id.bt_decrease_StalkPerClump);
//        btIncreaseStalkPerClump.setOnClickListener(this);
//        btDecreaseStalkPerClump.setOnClickListener(this);
//
//        btIncreaseBrix = (Button) rootView.findViewById(R.id.bt_increase_Brix);
//        btDecreaseBrix = (Button) rootView.findViewById(R.id.bt_decrease_Brix);
//        btIncreaseBrix.setOnClickListener(this);
//        btDecreaseBrix.setOnClickListener(this);
//
//        btIncreaseObserve = (Button) rootView.findViewById(R.id.bt_increase_observe);
//        btDecreaseObserve = (Button) rootView.findViewById(R.id.bt_decrease_observe);
//        btIncreaseObserve.setOnClickListener(this);
//        btDecreaseObserve.setOnClickListener(this);
//
//        btIncreaseInternodeAmount = (Button) rootView.findViewById(R.id.bt_increase_InternodeAmount);
//        btDecreaseInternodeAmount = (Button) rootView.findViewById(R.id.bt_decrease_InternodeAmount);
//        btIncreaseInternodeAmount.setOnClickListener(this);
//        btDecreaseInternodeAmount.setOnClickListener(this);
//
//
//        tv_stalkperclump = (TextView) rootView.findViewById(R.id.tv_stalkperclump);
//        tv_brix = (TextView) rootView.findViewById(R.id.tv_brix);
//        tv_height = (TextView) rootView.findViewById(R.id.tv_height);
//        tv_conclusion_height = (TextView) rootView.findViewById(R.id.height_level);
//        tv_observe = (TextView) rootView.findViewById(R.id.tv_observe);
//        tv_internode = (TextView) rootView.findViewById(R.id.tv_internode);
//
//        SB_stalkperclump = (SeekBar) rootView.findViewById(R.id.seekBar_stalkperclump);
//        SB_brix = (SeekBar) rootView.findViewById(R.id.seekBar_brix);
//        SB_height = (SeekBar) rootView.findViewById(R.id.seekBar_height);
//
//        SB_observe = (SeekBar) rootView.findViewById(R.id.seekBar_observe);
//        SB_internode = (SeekBar) rootView.findViewById(R.id.seekBar_internode);
//
//        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                switch (seekBar.getId()) {
//
//                    case R.id.seekBar_observe:
//
//                        observe = ((float) progress / 2);
//                        tv_observe.setText(String.format("%.1f", observe) + " คะแนน");
//                        setObserve_Score(observe);
//                        Log.d("Debug", "" + observe);
//                        break;
//
//                    case R.id.seekBar_stalkperclump:
//                        stalkperclump = progress;
//                        tv_stalkperclump.setText("" + stalkperclump + " ลำ/กอ");
//                        setStalkperClump_Score(stalkperclump);
//                        break;
//
//                    case R.id.seekBar_brix:
//                        brix = progress;
//                        f_brix = ((float) brix / 10);
//                        tv_brix.setText(String.format("%.1f", f_brix));
//                        setBrix_Score(brix);
//                        break;
//
//                    case R.id.seekBar_height:
//                        height = progress;
//                        tv_height.setText("" + height + " cm");
//                        setHeight_Score(height);
//                        break;
//
//                    case R.id.seekBar_internode:
//                        InternodeAmount = progress;
//                        tv_internode.setText("" + InternodeAmount + " ปล้อง/ลำ");
//                        baseApplication.getCloneDataItem().setInterNodePerStalk(InternodeAmount);
//                        break;
//                }
//
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        };
//
//        SB_stalkperclump.setOnSeekBarChangeListener(onSeekBarChangeListener);
//        SB_brix.setOnSeekBarChangeListener(onSeekBarChangeListener);
//        SB_height.setOnSeekBarChangeListener(onSeekBarChangeListener);
//
//        SB_observe.setOnSeekBarChangeListener(onSeekBarChangeListener);
//        SB_internode.setOnSeekBarChangeListener(onSeekBarChangeListener);
//
//
//        return rootView;
//    }
//
//    private void InitialEditText(View rootView) {
//
//        edtBrix = (EditText) rootView.findViewById(R.id.edtBrix);
//        edtHeight = (EditText) rootView.findViewById(R.id.edtHeight);
//        edtInternodeAmount = (EditText) rootView.findViewById(R.id.edtInternodeAmount);
//        edtOverView = (EditText) rootView.findViewById(R.id.edtOverView);
//        edtStalkPerClump = (EditText) rootView.findViewById(R.id.edtStalkperClump);
//
//
//    }
//
//    private void InitailRadioButton(View v) {
//
//        rbClumpShape0 = (RadioButton) v.findViewById(R.id.rbClumpShape0);
//        rbClumpShape1 = (RadioButton) v.findViewById(R.id.rbClumpShape1);
//        rbClumpShape2 = (RadioButton) v.findViewById(R.id.rbClumpShape2);
//        rbClumpShape3 = (RadioButton) v.findViewById(R.id.rbClumpShape3);
//
//        rbStalkShape0 = (RadioButton) v.findViewById(R.id.rbStalkShape0);
//        rbStalkShape1 = (RadioButton) v.findViewById(R.id.rbStalkShape1);
//        rbStalkShape2 = (RadioButton) v.findViewById(R.id.rbStalkShape2);
//        rbStalkShape3 = (RadioButton) v.findViewById(R.id.rbStalkShape3);
//        rbStalkShape4 = (RadioButton) v.findViewById(R.id.rbStalkShape4);
//
//        rbDisease0 = (RadioButton) v.findViewById(R.id.rbDisease0);
//        rbDisease1 = (RadioButton) v.findViewById(R.id.rbDisease1);
//        rbDisease2 = (RadioButton) v.findViewById(R.id.rbDisease2);
//
//        rbStuffDensity0 = (RadioButton) v.findViewById(R.id.rbStuffDensity0);
//        rbStuffDensity1 = (RadioButton) v.findViewById(R.id.rbStuffDensity1);
//        rbStuffDensity2 = (RadioButton) v.findViewById(R.id.rbStuffDensity2);
//        rbStuffDensity3 = (RadioButton) v.findViewById(R.id.rbStuffDensity3);
//        rbStuffDensity4 = (RadioButton) v.findViewById(R.id.rbStuffDensity4);
//        rbStuffDensity5 = (RadioButton) v.findViewById(R.id.rbStuffDensity5);
//
//        rbLeafSheath0 = (RadioButton) v.findViewById(R.id.rbLeafSheath0);
//        rbLeafSheath1 = (RadioButton) v.findViewById(R.id.rbLeafSheath1);
//        rbLeafSheath2 = (RadioButton) v.findViewById(R.id.rbLeafSheath2);
//        rbLeafSheath3 = (RadioButton) v.findViewById(R.id.rbLeafSheath3);
//        rbLeafSheath4 = (RadioButton) v.findViewById(R.id.rbLeafSheath4);
//
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mainActivity = (MainActivity) getActivity();
//    }
//
//    private void setHeight_Score(int height) {
//
//        int standard = baseApplication.getStandardSpecieData().getHeight();
//        baseApplication.getCloneDataItem().setHeight(height);
//        result = height - standard;
//
//        Log.d("Debug", "" + result);
//
//        if (result > 3) {
//            // สูงกว่าพันธุ์ตรวจสอบมากกว่า 3
//            cal.setScore_height(5);
//            height_score = 5;
//
//            tv_conclusion_height.setText("สูงกว่าพันธุ์ตรวจสอบ");
//
//        } else if (result <= 3 && result >= -3) {
//            // บวก ลบ 3
//            cal.setScore_height(4);
//            height_score = 4;
//            tv_conclusion_height.setText("ใกล้เคียงพันธตรวจสอบ");
//        } else if (result < -3) {
//            // ต่ำกว่าพันธฺุ์มากกว่า 3 cm
//            cal.setScore_height(2);
//            height_score = 2;
//            tv_conclusion_height.setText("ต่ำกว่าพันธุ์ตรวจสอบ");
//        }
//
//    }
//
//    private void setBrix_Score(int brix) {
//
//        baseApplication.getCloneDataItem().setBrix(f_brix);
//
//        if (brix > 210) {
//            cal.setScore_Brix(5);
//            brix_score = 5;
//
//        } else if (brix > 200 && brix <= 210) {
//            cal.setScore_Brix(4);
//            brix_score = 4;
//
//        } else if (brix > 190 && brix <= 200) {
//            cal.setScore_Brix(3);
//            brix_score = 3;
//
//        } else if (brix > 180 && brix <= 190) {
//            cal.setScore_Brix(2);
//            brix_score = 2;
//
//        } else if (brix > 170 && brix <= 180) {
//            cal.setScore_Brix(1);
//            brix_score = 1;
//
//        } else if (brix <= 170) {
//            cal.setScore_Brix(0);
//            brix_score = 0;
//
//        }
//    }
//
//    private void setObserve_Score(float observe) {
//        observe_score = observe;
//        cal.setScore_observe_score(observe_score);
//        baseApplication.getCloneDataItem().setObserve_score(observe);
//    }
//
//    private void setStalkperClump_Score(int stalkperclump) {
//        baseApplication.getCloneDataItem().setStalkperclump(stalkperclump);
//        if (stalkperclump > 10) {
//            cal.setScore_stalkperclump(5);
//            stalkperclump_score = 5;
//
//        } else if (stalkperclump <= 9 && stalkperclump >= 7) {
//            cal.setScore_stalkperclump(4);
//            stalkperclump_score = 4;
//
//
//        } else if (stalkperclump >= 5 && stalkperclump <= 6) {
//            cal.setScore_stalkperclump(3);
//            stalkperclump_score = 3;
//
//        } else if (stalkperclump == 4) {
//            cal.setScore_stalkperclump(2);
//            stalkperclump_score = 2;
//
//        } else if (stalkperclump >= 1 && stalkperclump <= 3) {
//            cal.setScore_stalkperclump(1);
//            stalkperclump_score = 1;
//        }
//
//    }
//
//    private void setStalkSize_Score(float size) {
//        if (size >= 3) {
//            cal.setScore_stalksize(5);
//            size_score = 5;
//        } else if (size >= 2.7 && size <= 2.9) {
//            cal.setScore_stalksize(4);
//            size_score = 4;
//        } else if (size >= 2.5 && size <= 2.6) {
//            cal.setScore_stalksize(3);
//            size_score = 3;
//        } else {
//            cal.setScore_stalksize(0);
//            size_score = 0;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if (v.getId() == R.id.btAverage) {
//            CalculateAverageInternode();
//        }
//
//        //Brix
//        if (v.getId() == R.id.bt_decrease_Brix || v.getId() == R.id.bt_increase_Brix) {
//            if (brix < getResources().getInteger(R.integer.maxbrix_limit) && brix >= 0) {
//                if (v.getId() == R.id.bt_increase_Brix) {
//                    brix++;
//                    f_brix = ((float) brix / 10);
//                    setBrix_Score(brix);
//                    SB_brix.setProgress(brix);
//
//                } else if (v.getId() == R.id.bt_decrease_Brix && brix != 0) {
//                    brix--;
//                    f_brix = ((float) brix / 10);
//                    setBrix_Score(brix);
//                    SB_brix.setProgress(brix);
//
//                }
//            } else if (brix == getResources().getInteger(R.integer.maxbrix_limit)) {
//                if (v.getId() == R.id.bt_decrease_Brix) {
//                    brix--;
//                    f_brix = ((float) brix / 10);
//                    setBrix_Score(brix);
//                    SB_brix.setProgress(brix);
//
//                }
//            }
//        }
//        //End Brix
//
//        //InternodeAmount
//
//        if (v.getId() == R.id.bt_decrease_InternodeAmount || v.getId() == R.id.bt_increase_InternodeAmount) {
//            if (InternodeAmount < getResources().getInteger(R.integer.maxInternodeAmountLimit) && InternodeAmount >= 0) {
//                if (v.getId() == R.id.bt_increase_InternodeAmount) {
//                    InternodeAmount++;
//                    SB_internode.setProgress(InternodeAmount);
//                    tv_internode.setText("" + InternodeAmount + " ปล้อง/ลำ");
//                    baseApplication.getCloneDataItem().setInterNodePerStalk(InternodeAmount);
//
//
//                } else if (v.getId() == R.id.bt_decrease_InternodeAmount && InternodeAmount != 0) {
//                    InternodeAmount--;
//                    SB_internode.setProgress(InternodeAmount);
//                    tv_internode.setText("" + InternodeAmount + " ปล้อง/ลำ");
//                    baseApplication.getCloneDataItem().setInterNodePerStalk(InternodeAmount);
//
//                }
//            } else if (InternodeAmount == getResources().getInteger(R.integer.maxInternodeAmountLimit)) {
//                if (v.getId() == R.id.bt_decrease_InternodeAmount) {
//                    InternodeAmount--;
//                    SB_internode.setProgress(InternodeAmount);
//                    tv_internode.setText("" + InternodeAmount + " ปล้อง/ลำ");
//                    baseApplication.getCloneDataItem().setInterNodePerStalk(InternodeAmount);
//
//                }
//            }
//        }
//        // End InternodeAmount
//
//        //Observe Score
//        if (v.getId() == R.id.bt_decrease_observe || v.getId() == R.id.bt_increase_observe) {
//            if (observe < (getResources().getInteger(R.integer.maxobserve) / 2) && observe >= 0) {
//                if (v.getId() == R.id.bt_increase_observe) {
//                    observe = observe + Float.valueOf((float) 0.5);
//                    SB_observe.setProgress((int) (Float.valueOf(observe) * 2));
//                    tv_observe.setText(String.format("%.1f", observe) + " คะแนน");
//                    setObserve_Score(observe);
//
//
//                } else if (v.getId() == R.id.bt_decrease_observe && observe != 0) {
//                    observe = observe - Float.valueOf((float) 0.5);
//                    SB_observe.setProgress((int) (Float.valueOf(observe) * 2));
//                    tv_observe.setText(String.format("%.1f", observe) + " คะแนน");
//                    setObserve_Score(observe);
//                }
//            } else if (observe == (getResources().getInteger(R.integer.maxobserve) / 2)) {
//                if (v.getId() == R.id.bt_decrease_observe) {
//                    observe = observe - Float.valueOf((float) 0.5);
//                    SB_observe.setProgress((int) (Float.valueOf(observe) * 2));
//                    tv_observe.setText(String.format("%.1f", observe) + " คะแนน");
//                    setObserve_Score(observe);
//                }
//            }
//        }
//        //End Observe
//
//
//        // Stalk Per Clump
//        if (v.getId() == R.id.bt_decrease_StalkPerClump || v.getId() == R.id.bt_increase_StalkPerClump) {
//            if (stalkperclump < getResources().getInteger(R.integer.maxstalk_limit) && stalkperclump >= 0) {
//                if (v.getId() == R.id.bt_increase_StalkPerClump) {
//                    stalkperclump++;
//                    SB_stalkperclump.setProgress(stalkperclump);
//                    tv_stalkperclump.setText("" + stalkperclump + " ลำ/กอ");
//                    setStalkperClump_Score(stalkperclump);
//
//
//                } else if (v.getId() == R.id.bt_decrease_StalkPerClump && stalkperclump != 0) {
//                    stalkperclump--;
//                    SB_stalkperclump.setProgress(stalkperclump);
//                    tv_stalkperclump.setText("" + stalkperclump + " ลำ/กอ");
//                    setStalkperClump_Score(stalkperclump);
//                }
//            } else if (stalkperclump == getResources().getInteger(R.integer.maxstalk_limit)) {
//                if (v.getId() == R.id.bt_decrease_StalkPerClump) {
//                    stalkperclump--;
//                    SB_stalkperclump.setProgress(stalkperclump);
//                    tv_stalkperclump.setText("" + stalkperclump + " ลำ/กอ");
//                    setStalkperClump_Score(stalkperclump);
//                }
//            }
//        }
//        //End Stalk Per Clump
//
//        if (v.getId() == R.id.bt_decrease_height || v.getId() == R.id.bt_increse_height) {
//            if (height < getResources().getInteger(R.integer.maxheight_limit) && height >= 0) {
//                if (v.getId() == R.id.bt_increse_height) {
//                    height++;
//                    tv_height.setText("" + height + " cm");
//                    SB_height.setProgress(height);
//                    setHeight_Score(height);
//                } else if (v.getId() == R.id.bt_decrease_height && height != 0) {
//                    height--;
//                    tv_height.setText("" + height + " cm");
//                    SB_height.setProgress(height);
//                    setHeight_Score(height);
//                }
//            } else if (height == getResources().getInteger(R.integer.maxheight_limit)) {
//                if (v.getId() == R.id.bt_decrease_height) {
//                    height--;
//                    tv_height.setText("" + height + " cm");
//                    SB_height.setProgress(height);
//                    setHeight_Score(height);
//                }
//            }
//
//        }
//
//
//    }
//
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//
//        } else {
//
//        }
//        SetData();
//    }
//
//    private void SetData() {
//        //observe
//        //TODO RECHECK WHAT THEY GOT
//
//        if (baseApplication.getCloneDataItem().getStalkSize1() != 0) {
//            StalkSize1.setText(baseApplication.getCloneDataItem().getStalkSize1() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getStalkSize2() != 0) {
//            StalkSize2.setText(baseApplication.getCloneDataItem().getStalkSize2() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getStalkSize3() != 0) {
//            StalkSize3.setText(baseApplication.getCloneDataItem().getStalkSize3() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getStalkSize4() != 0) {
//            StalkSize4.setText(baseApplication.getCloneDataItem().getStalkSize4() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getStalkSize5() != 0) {
//            StalkSize5.setText(baseApplication.getCloneDataItem().getStalkSize5() + "");
//        } else {
//        }
//        ;
//
//
//
//
//
//        if (baseApplication.getCloneDataItem().getInternodeLength1() != 0) {
//            InternodeLength1.setText(baseApplication.getCloneDataItem().getInternodeLength1() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getInternodeLength2() != 0) {
//            InternodeLength2.setText(baseApplication.getCloneDataItem().getInternodeLength2() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getInternodeLength3() != 0) {
//            InternodeLength3.setText(baseApplication.getCloneDataItem().getInternodeLength3() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getInternodeLength4() != 0) {
//            InternodeLength4.setText(baseApplication.getCloneDataItem().getInternodeLength4() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getInternodeLength5() != 0) {
//            InternodeLength5.setText(baseApplication.getCloneDataItem().getInternodeLength5() + "");
//        } else {
//        }
//        ;
//
//
//        if (baseApplication.getCloneDataItem().getStalkSizeAverage() != 0) {
//            tvStalkSizeAverage.setText(baseApplication.getCloneDataItem().getStalkSizeAverage() + "");
//        } else {
//        }
//        ;
//        if (baseApplication.getCloneDataItem().getInternodeLengthAverage() != 0) {
//            tvInternodeLengthAverage.setText(baseApplication.getCloneDataItem().getInternodeLengthAverage() + "");
//        } else {
//        }
//        ;
//
//
//        if (baseApplication.getCloneDataItem().getObserve_score()!=null) {
//            observe_score = baseApplication.getCloneDataItem().getObserve_score();
//            SB_observe.setProgress((int) (observe_score * 2));
//            tv_observe.setText(String.format("%.0f", observe_score) + " คะแนน");
//            setObserve_Score(observe);
//        }else{
//            tv_observe.setText(0+" คะแนน");
//
//        }
//
//        //Stalk per Clump
//        stalkperclump = baseApplication.getCloneDataItem().getStalkperclump();
//
//        edtStalkPerClump.setText("" + baseApplication.getCloneDataItem().getStalkperclump());
//
//        SB_stalkperclump.setProgress(stalkperclump);
//        tv_stalkperclump.setText("" + stalkperclump + " ลำ/กอ");
//        setStalkperClump_Score(stalkperclump);
//
//        //Brix
//        f_brix = baseApplication.getCloneDataItem().getBrix();
//
//        edtBrix.setText("" + baseApplication.getCloneDataItem().getBrix());
//
//        SB_brix.setProgress((int) (f_brix * 10));
//        tv_brix.setText(String.format("%.1f", f_brix));
//        setBrix_Score(brix);
//
//        //Height
//        height = baseApplication.getCloneDataItem().getHeight();
//
//        edtHeight.setText("" + baseApplication.getCloneDataItem().getHeight());
//
//        SB_height.setProgress(height);
//        setHeight_Score(height);
//
//
//        //INTERNODE
//        InternodeAmount = baseApplication.getCloneDataItem().getInterNodePerStalk();
//
//        edtInternodeAmount.setText("" + baseApplication.getCloneDataItem().getInterNodePerStalk());
//
//        SB_internode.setProgress(InternodeAmount);
//        tv_internode.setText("" + InternodeAmount + " ปล้อง/ลำ");
//
//        //StalkShape
//
//        switch (scStalkShape) {
//
//            case 4:
//                rbStalkShape4.setChecked(true);
//                break;
//            case 3:
//                rbStalkShape3.setChecked(true);
//                break;
//            case 2:
//                rbStalkShape2.setChecked(true);
//                break;
//            case 1:
//                rbStalkShape1.setChecked(true);
//                break;
//            case 0:
//                rbStalkShape0.setChecked(true);
//                break;
//        }
//
//        //CLUMP_SHAPE
//
//        switch (scClumpShape) {
//            case 3:
//                rbClumpShape3.setChecked(true);
//                break;
//            case 2:
//                rbClumpShape2.setChecked(true);
//                break;
//            case 1:
//                rbClumpShape1.setChecked(true);
//                break;
//            case 0:
//                rbClumpShape0.setChecked(true);
//                break;
//        }
//        //LeafSheath
//        switch (scLeafSheath) {
//            case 4:
//                rbLeafSheath4.setChecked(true);
//                break;
//            case 3:
//                rbLeafSheath3.setChecked(true);
//                break;
//            case 2:
//                rbLeafSheath2.setChecked(true);
//                break;
//            case 1:
//                rbLeafSheath1.setChecked(true);
//                break;
//            case 0:
//                rbLeafSheath0.setChecked(true);
//                break;
//        }
//        //Disease
//        switch (scDisease) {
//            case 2:
//                rbDisease2.setChecked(true);
//                break;
//            case 1:
//                rbDisease1.setChecked(true);
//                break;
//            case 0:
//                rbDisease0.setChecked(true);
//                break;
//        }
//
//        //StuffDensity
//        switch (scStuffDensity) {
//            case 5:
//                rbStuffDensity5.setChecked(true);
//                break;
//            case 4:
//                rbStuffDensity4.setChecked(true);
//                break;
//            case 3:
//                rbStuffDensity3.setChecked(true);
//                break;
//            case 2:
//                rbStuffDensity2.setChecked(true);
//                break;
//            case 1:
//                rbStuffDensity1.setChecked(true);
//                break;
//            case 0:
//                rbStuffDensity0.setChecked(true);
//                break;
//        }
//
//
//    }
//
//    private void CalculateAverageInternode() {
//        vStalkSize1 = 0f;
//        vStalkSize2 = 0f;
//        vStalkSize3 = 0f;
//        vStalkSize4 = 0f;
//        vStalkSize5 = 0f;
//        vInternodeLength1 = 0f;
//        vInternodeLength2 = 0f;
//        vInternodeLength3 = 0f;
//        vInternodeLength4 = 0f;
//        vInternodeLength5 = 0f;
//        int stalkcount = 0;
//        int internodecount = 0;
//
//        if (!StalkSize1.getText().toString().equals("")) {
//            vStalkSize1 = Float.valueOf(StalkSize1.getText().toString());
//            stalkcount++;
//        }
//        if (!StalkSize2.getText().toString().equals("")) {
//            vStalkSize2 = Float.valueOf(StalkSize2.getText().toString());
//            stalkcount++;
//        }
//        if (!StalkSize3.getText().toString().equals("")) {
//            vStalkSize3 = Float.valueOf(StalkSize3.getText().toString());
//            stalkcount++;
//        }
//        if (!StalkSize4.getText().toString().equals("")) {
//            vStalkSize4 = Float.valueOf(StalkSize4.getText().toString());
//            stalkcount++;
//        }
//        if (!StalkSize5.getText().toString().equals("")) {
//            vStalkSize5 = Float.valueOf(StalkSize5.getText().toString());
//            stalkcount++;
//        }
//
//
//        if (!InternodeLength1.getText().toString().equals("")) {
//            vInternodeLength1 = Float.valueOf(InternodeLength1.getText().toString());
//            internodecount++;
//        }
//        if (!InternodeLength2.getText().toString().equals("")) {
//            vInternodeLength2 = Float.valueOf(InternodeLength2.getText().toString());
//            internodecount++;
//        }
//        if (!InternodeLength3.getText().toString().equals("")) {
//            vInternodeLength3 = Float.valueOf(InternodeLength3.getText().toString());
//            internodecount++;
//        }
//        if (!InternodeLength4.getText().toString().equals("")) {
//            vInternodeLength4 = Float.valueOf(InternodeLength4.getText().toString());
//            internodecount++;
//        }
//        if (!InternodeLength5.getText().toString().equals("")) {
//            vInternodeLength5 = Float.valueOf(InternodeLength5.getText().toString());
//            internodecount++;
//        }
//
//        if (stalkcount != 0) {
//            vStalkSizeAverage = (vStalkSize1 + vStalkSize2 + vStalkSize3 + vStalkSize4 + vStalkSize5) / stalkcount;
//            baseApplication.getCloneDataItem().setStalkSize1(vStalkSize1);
//            baseApplication.getCloneDataItem().setStalkSize2(vStalkSize2);
//            baseApplication.getCloneDataItem().setStalkSize3(vStalkSize3);
//            baseApplication.getCloneDataItem().setStalkSize4(vStalkSize4);
//            baseApplication.getCloneDataItem().setStalkSize5(vStalkSize5);
//            baseApplication.getCloneDataItem().setStalkSizeAverage(vStalkSizeAverage);
//        } else {
//            baseApplication.getCloneDataItem().setStalkSize1(vStalkSize1);
//            baseApplication.getCloneDataItem().setStalkSize2(vStalkSize2);
//            baseApplication.getCloneDataItem().setStalkSize3(vStalkSize3);
//            baseApplication.getCloneDataItem().setStalkSize4(vStalkSize4);
//            baseApplication.getCloneDataItem().setStalkSize5(vStalkSize5);
//            baseApplication.getCloneDataItem().setStalkSizeAverage(0f);
//        }
//
//
//        if (internodecount != 0) {
//            vInternodeLengthAverage = (vInternodeLength1 + vInternodeLength2 + vInternodeLength3 + vInternodeLength4 + vInternodeLength5) / internodecount;
//            baseApplication.getCloneDataItem().setInternodeLength1(vInternodeLength1);
//            baseApplication.getCloneDataItem().setInternodeLength2(vInternodeLength2);
//            baseApplication.getCloneDataItem().setInternodeLength3(vInternodeLength3);
//            baseApplication.getCloneDataItem().setInternodeLength4(vInternodeLength4);
//            baseApplication.getCloneDataItem().setInternodeLength5(vInternodeLength5);
//            baseApplication.getCloneDataItem().setInternodeLengthAverage(vInternodeLengthAverage);
//        } else {
//            baseApplication.getCloneDataItem().setInternodeLength1(vInternodeLength1);
//            baseApplication.getCloneDataItem().setInternodeLength2(vInternodeLength2);
//            baseApplication.getCloneDataItem().setInternodeLength3(vInternodeLength3);
//            baseApplication.getCloneDataItem().setInternodeLength4(vInternodeLength4);
//            baseApplication.getCloneDataItem().setInternodeLength5(vInternodeLength5);
//            baseApplication.getCloneDataItem().setInternodeLengthAverage(0f);
//        }
//
//
//        tvInternodeLengthAverage.setText(String.format("%.2f", vInternodeLengthAverage));
//        tvStalkSizeAverage.setText(String.format("%.2f", vStalkSizeAverage));
//        setStalkSize_Score(vStalkSizeAverage);
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        if (group.getId() == R.id.rgClumpShape) {
//            switch (checkedId) {
//
//                case R.id.rbClumpShape0:
//                    scClumpShape = 0;
//                    break;
//
//                case R.id.rbClumpShape1:
//                    scClumpShape = 1;
//                    break;
//
//                case R.id.rbClumpShape2:
//                    scClumpShape = 2;
//                    break;
//
//                case R.id.rbClumpShape3:
//                    scClumpShape = 3;
//                    break;
//            }
//        }
//
//        if (group.getId() == R.id.rgDisease) {
//            switch (checkedId) {
//                case R.id.rbDisease0:
//                    scDisease = 0;
//                    break;
//
//                case R.id.rbDisease1:
//                    scDisease = 1;
//                    break;
//                case R.id.rbDisease2:
//                    scDisease = 2;
//                    break;
//            }
//        }
//
//        if (group.getId() == R.id.rgStalkShape) {
//            switch (checkedId) {
//
//                case R.id.rbStalkShape0:
//                    scStalkShape = 0;
//                    break;
//
//                case R.id.rbStalkShape1:
//                    scStalkShape = 1;
//                    break;
//                case R.id.rbStalkShape2:
//                    scStalkShape = 2;
//                    break;
//                case R.id.rbStalkShape3:
//                    scStalkShape = 3;
//                    break;
//                case R.id.rbStalkShape4:
//                    scStalkShape = 4;
//                    break;
//            }
//        }
//
//        if (group.getId() == R.id.rgStuffDensity) {
//            switch (checkedId) {
//                case R.id.rbStuffDensity0:
//                    scStuffDensity = 0;
//                    break;
//                case R.id.rbStuffDensity1:
//                    scStuffDensity = 1;
//                    break;
//                case R.id.rbStuffDensity2:
//                    scStuffDensity = 2;
//                    break;
//                case R.id.rbStuffDensity3:
//                    scStuffDensity = 3;
//                    break;
//                case R.id.rbStuffDensity4:
//                    scStuffDensity = 4;
//                    break;
//                case R.id.rbStuffDensity5:
//                    scStuffDensity = 5;
//                    break;
//
//            }
//            if (group.getId() == R.id.rgLeafSheath) {
//                switch (checkedId) {
//                    case R.id.rbLeafSheath0:
//                        scLeafSheath = 0;
//                        break;
//                    case R.id.rbLeafSheath1:
//                        scLeafSheath = 1;
//                        break;
//                    case R.id.rbLeafSheath2:
//                        scLeafSheath = 2;
//                        break;
//                    case R.id.rbLeafSheath3:
//                        scLeafSheath = 3;
//                        break;
//                    case R.id.rbLeafSheath4:
//                        scLeafSheath = 4;
//                        break;
//                }
//            }
//        }
//    }
//}
