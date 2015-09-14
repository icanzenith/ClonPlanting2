package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.InternodeLength;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.Item.StalkSize;
import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightValue;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class FragmentDetailTable extends Fragment {

    private TableLayout mTable;
    private BaseApplication baseApplication;



    public FragmentDetailTable() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment fragment = new FragmentDetailTable();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication = (BaseApplication) getActivity().getApplication();
        if (getArguments() != null) {

        }
    }

    private    ActionBar actionBar;
    private TextView tvTotalScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity m = (MainActivity) getActivity();


        actionBar = m.getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setTitle("บันทึกข้อมูล");
        actionBar.setDisplayOptions(ActionBar.NAVIGATION_MODE_STANDARD | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_fragment_detail, container, false);
        mTable = (TableLayout) rootView.findViewById(R.id.mTableLayout);
        tvTotalScore = (TextView) rootView.findViewById(R.id.tvTotalScore);
        tvTotalScore.setText(getTotalScore()+"คะแนน");
        SetTable(CreateScoreItemsArrayData());

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            SaveDatatoDatabase();
        }
        return true;
    }

    private void SaveDatatoDatabase() {
        Log.d("FAMCODE 2",baseApplication.getCloneDataItem().getFamilycode());
        ContentValues v = new ContentValues();
        BaseApplication b = (BaseApplication) getActivity().getApplication();
        CloneDataItem d = b.getCloneDataItem();
        HashMap<String, ScoreItem> a = d.getDataScore();

        String Clonecode = baseApplication.getCloneDataItem().getClonecode();
        String FamilyCode = baseApplication.getCloneDataItem().getFamilycode();

        ContentResolver cr = getActivity().getContentResolver();
        String where;
        String[] selection;
        Uri uri;
        if (baseApplication.getCloneDataItem().getDataType()==CloneDataItem.DATATYPE_STANDARD){
            uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
            v.put(Columns.SPECIENAME,baseApplication.getCloneDataItem().getSpecieName());
            v.put(Columns.ROWNUMBER,baseApplication.getCloneDataItem().getSTANDARD_ROW());

            where = Columns.FAMILYCODE+" = ? AND "+Columns.PLANTORDER+" = "+b.getCloneDataItem().getSTANDARD_ROW();
            selection = new String[]{b.getCloneDataItem().getFamilycode()};



        }else{
            uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));

            where = Columns.CLONECODE+" = ?";
            selection = new String[]{Clonecode};
        }

        //Disease
        v.put(Columns.WHITE_FLY, (String) a.get(Code.WhiteFly).getData());
        v.put(Columns.WHITE_FLY_SCORE,a.get(Code.WhiteFly).getScore());

        v.put(Columns.BORER, (String) a.get(Code.Borer).getData());
        v.put(Columns.BORER_SCORE,a.get(Code.Borer).getScore());

        v.put(Columns.APHID, (String) a.get(Code.Aphid).getData());
        v.put(Columns.APHID_SCORE,a.get(Code.Aphid).getScore());

        v.put(Columns.ICERYA_MEAL_BUG, (String) a.get(Code.IceryaMealbug).getData());
        v.put(Columns.ICERYA_MEAL_BUG_SCORE,a.get(Code.IceryaMealbug).getScore());

        v.put(Columns.SCALE, (String) a.get(Code.Scale).getData());
        v.put(Columns.SCALE_SCORE,a.get(Code.Scale).getScore());

        v.put(Columns.POKKAH_BOENG, (String) a.get(Code.PokkahBoeng).getData());
        v.put(Columns.POKKAH_BOENG_SCORE,a.get(Code.PokkahBoeng).getScore());

        v.put(Columns.YELLOW_SPOT, (String) a.get(Code.YellowSpot).getData());
        v.put(Columns.YELLOW_SPOT_SCORE,a.get(Code.YellowSpot).getScore());

        v.put(Columns.BROWN_SPOT, (String) a.get(Code.BrownSpot).getData());
        v.put(Columns.BROWN_SPOT_SCORE,a.get(Code.BrownSpot).getScore());

        v.put(Columns.RING_SPOT, (String) a.get(Code.RingSpot).getData());
        v.put(Columns.RING_SPOT_SCORE,a.get(Code.RingSpot).getScore());

        v.put(Columns.RUST, (String) a.get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE,a.get(Code.Rust).getScore());

        v.put(Columns.DOWNY_MILDEW, (String) a.get(Code.DownyMildew).getData());
//        Log.d(Columns.DOWNY_MILDEW,(String) a.get(Code.DownyMildew).getData());
        v.put(Columns.DOWNY_MILDEW_SCORE,a.get(Code.DownyMildew).getScore());

        v.put(Columns.OTHER_DISEASE, (String) a.get(Code.OtherDisease).getData());
        v.put(Columns.OTHER_DISEASE_SCORE,a.get(Code.OtherDisease).getScore());
        v.put(Columns.OTHER_DISEASE_NAME,b.getCloneDataItem().getOtherDiseaseName());


        // Other Data
        v.put(Columns.FLOWERING, (String) a.get(Code.Flowering).getData());
        v.put(Columns.FLOWERING_SCORE, a.get(Code.Flowering).getScore());

        v.put(Columns.BRIX, (Float) a.get(Code.Brix).getData());
        v.put(Columns.BRIX_SCORE, a.get(Code.Brix).getScore());

        v.put(Columns.HEIGHT, (Integer) a.get(Code.Height).getData());
        v.put(Columns.HEIGHT_SCORE, a.get(Code.Height).getScore());

        v.put(Columns.OVERALL, (Float) a.get(Code.OverAll).getData());
        v.put(Columns.OVERALL_SCORE, a.get(Code.OverAll).getScore());

        v.put(Columns.LEAF_SHEATH, (String) a.get(Code.LeafSheath).getData());
        v.put(Columns.LEAF_SHEATH_SCORE, a.get(Code.LeafSheath).getScore());

        v.put(Columns.STALK_AMOUNT, (Integer) a.get(Code.StalkAmount).getData());
        v.put(Columns.STALK_AMOUNT_SCORE, a.get(Code.StalkAmount).getScore());

        v.put(Columns.INTERNODE_AMOUNT, (Integer) a.get(Code.InternodeAmount).getData());
        v.put(Columns.INTERNODE_AMOUNT_SCORE, a.get(Code.InternodeAmount).getScore());

        v.put(Columns.CLUMP_SHAPE, (String) a.get(Code.ClumpShape).getData());
        v.put(Columns.CLUMP_SHAPE_SCORE, a.get(Code.ClumpShape).getScore());

        v.put(Columns.CLUMP_CHARACTERISTIC, (String) a.get(Code.ClumpCharacteristic).getData());
        v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, a.get(Code.ClumpCharacteristic).getScore());

        v.put(Columns.INTERNAL_SYSTOM, (String) a.get(Code.InternalSymtom).getData());
        v.put(Columns.INTERNAL_SYSTOM_SCORE, a.get(Code.InternalSymtom).getScore());

        v.put(Columns.INTERNAL_FIRMNESS, (String) a.get(Code.InternalFirmness).getData());
        v.put(Columns.INTERNAL_FIRMNESS_SCORE, a.get(Code.InternalFirmness).getScore());

        v.put(Columns.STUFF, (String) a.get(Code.Stuff).getData());
        v.put(Columns.STUFF_SCORE, a.get(Code.Stuff).getScore());

        v.put(Columns.STALK_SIZE_1, d.getStalkSize().get(StalkSize.StalkSize1.name()));
        v.put(Columns.STALK_SIZE_2, d.getStalkSize().get(StalkSize.StalkSize2.name()));
        v.put(Columns.STALK_SIZE_3, d.getStalkSize().get(StalkSize.StalkSize3.name()));
        v.put(Columns.STALK_SIZE_4, d.getStalkSize().get(StalkSize.StalkSize4.name()));
        v.put(Columns.STALK_SIZE_5, d.getStalkSize().get(StalkSize.StalkSize5.name()));
        v.put(Columns.STALK_SIZE_AVERAGE, (Float) a.get(Code.StalkSize).getData());
        v.put(Columns.STALK_SIZE_AVERAGE_SCORE, a.get(Code.StalkSize).getScore());

        v.put(Columns.INTERNODE_LENGTH1, d.getInternodeLength().get(InternodeLength.InternodeLength1.name()));
        v.put(Columns.INTERNODE_LENGTH2, d.getInternodeLength().get(InternodeLength.InternodeLength2.name()));
        v.put(Columns.INTERNODE_LENGTH3, d.getInternodeLength().get(InternodeLength.InternodeLength3.name()));
        v.put(Columns.INTERNODE_LENGTH4, d.getInternodeLength().get(InternodeLength.InternodeLength4.name()));
        v.put(Columns.INTERNODE_LENGTH5, d.getInternodeLength().get(InternodeLength.InternodeLength5.name()));
        v.put(Columns.INTERNODE_LENGTH_AVERAGE, (Float) a.get(Code.InternodeLength).getData());
        v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE, a.get(Code.InternodeLength).getScore());


        v.put(Columns.COLLECTINGTIME, getCollectingTime());

        v.put(Columns.CHANGESTATUS, 1);
        v.put(Columns.UPLOADSTATUS, 0);
        v.put(Columns.SAVEDSTATUS, 1);

        if (baseApplication.getCloneDataItem().getDataType() == CloneDataItem.DATATYPE_CLONE) {
            v.put(Columns.WHY_SELECT, d.getWhySelect());
            if (d.getSelectStatus()==null){
                v.put(Columns.SELECT_STATUS,SelectStatus.SAVED);
            }else{
                if (Integer.parseInt(d.getSelectStatus())!=SelectStatus.NOTHING){
                    v.put(Columns.SELECT_STATUS, d.getSelectStatus());
                }else{
                    v.put(Columns.SELECT_STATUS, SelectStatus.SAVED);
                }

            }

            v.put(Columns.TOTAL_SCORE, getTotalScore());
        }

        int update = cr.update(uri, v, where, selection);

//        Toast.makeText(getActivity(),""+update,Toast.LENGTH_LONG).show();

        if (update <=0){
            v.put(Columns.FAMILYCODE,FamilyCode);
            v.put(Columns.PLANTORDER,b.getCloneDataItem().getSTANDARD_ROW());
            Uri insert = cr.insert(uri,v);

        }
        if (baseApplication.getCloneDataItem().getDataType()==CloneDataItem.DATATYPE_CLONE){
            ContentValues v2 = new ContentValues();
            v2.put(Columns.SAVEDSTATUS, 1);

            String where2 = Columns.CLONECODE+" = ?";
            String[] selection2 = {baseApplication.getCloneDataItem().getClonecode()};
            int update2 = cr.update(uri, v2, where2, selection2);
            if (update >= 0 && update2 >=0) {
                Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
            }
        }else if (baseApplication.getCloneDataItem().getDataType() == CloneDataItem.DATATYPE_STANDARD){
                ReCalculateScore();
        }

        ReCheckConclusionData();

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onFinishDetail();

    }

    private void ReCheckConclusionData() {
        ContentResolver cR = getActivity().getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String selection = Columns.FAMILYCODE +" = ?";
        String[]  selectionArgs = {baseApplication.getCloneDataItem().getFamilycode()};

        Cursor c = cR.query(
                uri,projection,selection,selectionArgs,null
        );

        int selected = 0;
        int reject = 0;
        int saved = 0;
        if (c.getCount()!=0){
            while (c.moveToNext()){
                int status = c.getInt(c.getColumnIndex(Columns.SELECT_STATUS));
                if (status == SelectStatus.SAVED){
                    saved = saved+1;
                }else if(status == SelectStatus.REJECTED) {
                    reject = reject+1;
                }else if(status == SelectStatus.SELECTED){
                    selected = selected+1;
                }
            }
        }

        //นับจำนวนต้นที่มีการบันทึกข้อมูล

        //อัพเดทใน Table Family

        Uri uri2 = Uri.parse(getResources().getString(R.string.URI_MY_FAMILY));
        ContentValues v = new ContentValues();
        v.put(Columns.AMOUNT_OF_CLONE_SELECTED,selected);
        v.put(Columns.AMOUNT_OF_CLONE_REJECTED,reject);
        v.put(Columns.AMOUNT_OF_CLONE_SAVED,saved);
        String where = Columns.FAMILYCODE+" = ?";
        String[] selectionArgs2= {baseApplication.getCloneDataItem().getFamilycode()};
        cR.update(uri2,v,where,selectionArgs2);
    }

    private void ReCalculateScore() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        String FamilyCode = baseApplication.getCloneDataItem().getFamilycode();
        int AverageStandardHeight = getStandardAverageHeight();
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String where = Columns.FAMILYCODE+" = ?";
        String[] selection = {FamilyCode};
        Cursor c = contentResolver.query(uri,null,where,selection,null);

        if (c.getCount()!=0)
        {
            while (c.moveToNext()) {
                String choose = c.getString(c.getColumnIndex(Columns.SELECT_STATUS));
                if (choose != null) {
                    if (!choose.equals(SelectStatus.NOTHING)) {

                        int height = c.getInt(c.getColumnIndex(Columns.HEIGHT));
                        float result;
                        float score = 0;
                        float totalscore;

                        result = height - AverageStandardHeight;
                        if (result < -3) {
                            score = 5 * WeightValue.Height;
                        } else if (result >= -3 && result <= 3) {
                            score = 4 * WeightValue.Height;
                        } else if (result > 3) {
                            score = 2 * WeightValue.Height;
                        }

                        totalscore = c.getFloat(c.getColumnIndex(Columns.WHITE_FLY_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.BORER_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.APHID_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.ICERYA_MEAL_BUG_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.SCALE_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.POKKAH_BOENG_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.YELLOW_SPOT_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.BROWN_SPOT_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.RING_SPOT_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.RUST_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.DOWNY_MILDEW_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.OTHER_DISEASE_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.FLOWERING_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.BRIX_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.OVERALL_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.LEAF_SHEATH_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.STALK_AMOUNT_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.CLUMP_SHAPE_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.INTERNAL_SYSTOM_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.INTERNAL_FIRMNESS_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH_AVERAGE_SCORE)) +
                                c.getFloat(c.getColumnIndex(Columns.STUFF_SCORE)) +
                                score;
                        ContentValues v = new ContentValues();
                        v.put(Columns.HEIGHT_SCORE, score);
                        v.put(Columns.TOTAL_SCORE, totalscore);


                    }
                }
            }
        }

    }

    private Float getTotalScore() {
        HashMap<String, ScoreItem> object = baseApplication.getCloneDataItem().getDataScore();
        float totalscore = 0;
        for (String i : object.keySet()) {
            float score = object.get(i).getScore();
            totalscore = totalscore + score;
        }
        return totalscore;
    }

    private String getCollectingTime() {
        String CollectingTime;
        Time now = new Time();
        now.setToNow();
        CollectingTime = String.format(now.format("%Y-%m-%d"));
        Log.d("Tag", CollectingTime);
        return CollectingTime;
    }


    private ArrayList<ScoreItem> CreateScoreItemsArrayData() {

        ArrayList<ScoreItem> data = new ArrayList<>();

        ArrayList<String> m = new ArrayList<>();
        //Other
        m.add(Code.Flowering);
        m.add(Code.Brix);
        m.add(Code.Height);
        m.add(Code.OverAll);
        m.add(Code.LeafSheath);
        m.add(Code.StalkAmount);
        m.add(Code.StalkSize);
        m.add(Code.ClumpShape);
        m.add(Code.ClumpCharacteristic);
        m.add(Code.InternalSymtom);
        m.add(Code.InternalFirmness);
        m.add(Code.InternodeLength);
        m.add(Code.Stuff);

        //Disease
        m.add(Code.WhiteFly);
        m.add(Code.Borer);
        m.add(Code.Aphid);
        m.add(Code.IceryaMealbug);
        m.add(Code.Scale);
        m.add(Code.PokkahBoeng);
        m.add(Code.YellowSpot);
        m.add(Code.BrownSpot);
        m.add(Code.RingSpot);
        m.add(Code.Rust);
        m.add(Code.DownyMildew);
        m.add(Code.OtherDisease);

        for (int i = 0; i < m.size(); i++) {
            ScoreItem item = baseApplication.getCloneDataItem().getDataScore().get(m.get(i));
            data.add(item);
        }


//        for (String a : Columns.ColumnName()) {
        //TODO Column Name
//            Item i = new Item();
//            i.setRowName(TableRowName.get(a));
//            i.setCloneValue("" + cloneData.get(a));
//            i.setStandardValue("" + standardData.get(a));
//            data.add(i);

//        }

        return data;
    }


    ///**
    //
    // */


//    private CloneDataItem CalculateAverageStandardCloneScore(String FamilyCode) {
//
//
//        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
//        String[] projection = null;
//        String sortOrder = null;
//        String selection = null;
//        String[] selectionArgs = null;
//        ContentResolver r = getActivity().getContentResolver();
//        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
//        int stalkperclump = 0;
//        Float brix = 0f;
//        int height = 0;
//        Float size = 0f;
//        while (c != null && c.moveToNext()) {
////            stalkperclump = stalkperclump + c.getInt(c.getColumnIndex(Columns.STALKPERCLUMP));
//            brix = brix + c.getFloat(c.getColumnIndex(Columns.BRIX));
////            height = height + c.getInt(c.getColumnIndex(Columns.STALKHEIGHT));
////            size = size + c.getFloat(c.getColumnIndex(Columns.STALKSIZE));
//        }
//
//        c.close();
//        CloneDataItem sd = new CloneDataItem();
//        if (c.getCount() != 0) {
//
//            sd.setStalkperclump(stalkperclump / c.getCount());
//            sd.setBrix(brix / c.getCount());
//            sd.setHeight(height / c.getCount());
//            Log.d("Standard", "Fragment detail");
//            baseApplication.setStandardSpecieData(sd);
//        } else {
//            baseApplication.setStandardSpecieData(sd);
//        }
//        return sd;
//
//    }

    private void CreateHeadTable() {
        TableRow tableRow = new TableRow(getActivity());
        TableLayout.LayoutParams d = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);

        tableRow.setLayoutParams(d);
        tableRow.setWeightSum(3);
        tableRow.setPadding(0, 0, 0, 0);
        tableRow.setBackgroundColor(0xFF228b22);


        //ชื่อหัวข้อ
        TextView tvRowName = new TextView(getActivity());
        tvRowName.setText("หัวข้อ");
        TableRow.LayoutParams params = new TableRow.LayoutParams
                (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        tvRowName.setLayoutParams(params);
        tvRowName.setTextColor(0xFFFF9999);
        tvRowName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tvRowName.setTypeface((Typeface.DEFAULT_BOLD));


        //ข้อมูล
        TextView tvCloneValue = new TextView(getActivity());
        tvCloneValue.setText("ข้อมูล");
        tvCloneValue.setLayoutParams(new TableRow.LayoutParams
                (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tvCloneValue.setTextColor(0xFFFF9999);
        tvCloneValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tvCloneValue.setTypeface((Typeface.DEFAULT_BOLD));

        // คะแนน
        TextView tvStandardValue = new TextView(getActivity());
        tvStandardValue.setText("คะแนน");
        tvStandardValue.setLayoutParams(new TableRow.LayoutParams
                (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tvStandardValue.setTextColor(0xFFFF9999);
        tvStandardValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        tvStandardValue.setTypeface((Typeface.DEFAULT_BOLD));


        tableRow.addView(tvRowName);
        tableRow.addView(tvCloneValue);
        tableRow.addView(tvStandardValue);
        mTable.addView(tableRow);
    }

    private void SetTable(ArrayList<ScoreItem> datas) {

        CreateHeadTable();
        for (int i = 0; i < datas.size(); i++) {

            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableLayout.LayoutParams
                    (TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);

            tableRow.setWeightSum(3);
            tableRow.setPadding(10, 10, 10, 10);
            if (i == 0) {
                tableRow.setBackgroundColor(0xFF228b22);
            } else if (i % 2 == 1) {
                tableRow.setBackgroundColor(0xAA228b22);
            } else {
                tableRow.setBackgroundColor(0xFF228b22);
            }

            //ชื่อหัวข้อ
            TextView tvRowName = new TextView(getActivity());
            tvRowName.setText(Code.getTAGName().get(datas.get(i).getTagName()));
            if (datas.get(i).getTagName() == Code.OtherDisease){
                tvRowName.setText(Code.getTAGName().get(datas.get(i).getTagName())+"\n"+
                baseApplication.getCloneDataItem().getOtherDiseaseName());
            }

            tvRowName.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tvRowName.setTextColor(0xFFFFFFFF);
            tvRowName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            tvRowName.setTypeface((Typeface.DEFAULT_BOLD));

            //ข้อมูล
            TextView tvCloneValue = new TextView(getActivity());
            if (datas.get(i).getData() != null) {
                if (datas.get(i).getScore() < 0) {
                    tvCloneValue.setText("" + datas.get(i).getData());
                    tvCloneValue.setTextColor(0xFFAA0000);
                } else {

                    try {
                        if ((Integer) datas.get(i).getData() == 0) {
                            tvCloneValue.setText("ไม่ได้บันทึก");
                            tvCloneValue.setTextColor(0xFFAA0000);
                        } else {
                            tvCloneValue.setText(""+ datas.get(i).getData());
                            tvCloneValue.setTextColor(0xFFAA0000);
                        }
                    } catch (Exception e) {
                        try {
                            if ((Float) datas.get(i).getData() == 0) {
                                tvCloneValue.setText("ไม่ได้บันทึก");
                                tvCloneValue.setTextColor(0xFFAA0000);
                            } else {
                                tvCloneValue.setText(""+ datas.get(i).getData());
                                tvCloneValue.setTextColor(0xFFAA0000);
                            }

                        }catch (Exception r){
//                            Toast.makeText(getActivity(),"Catch Exception "+datas.get(i).getTagName(),Toast.LENGTH_LONG).show();
                        }


                        tvCloneValue.setText("" + datas.get(i).getData());
                        tvCloneValue.setTextColor(0xFFFFFFFF);
                    }

                }

            } else {
                tvCloneValue.setText("-");

            }
            tvCloneValue.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            tvCloneValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            tvCloneValue.setTypeface((Typeface.DEFAULT_BOLD));

            // คะแนน
            TextView tvStandardValue = new TextView(getActivity());
            if (datas.get(i).getScore() > 0) {
                tvStandardValue.setText("" + datas.get(i).getScore());
            } else {
                tvStandardValue.setText("-");

            }
            tvStandardValue.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tvStandardValue.setTextColor(0xFFFFFFFF);
            tvStandardValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

            tvStandardValue.setTypeface((Typeface.DEFAULT_BOLD));


            tableRow.addView(tvRowName);
            tableRow.addView(tvCloneValue);
            tableRow.addView(tvStandardValue);

            mTable.addView(tableRow);


        }
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
}
