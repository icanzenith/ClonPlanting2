package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class FragmentDetail extends Fragment {

    private static final String ARG_CLONECODE = "clonecode";
    TableLayout mTable;
    BaseApplication baseApplication;
    ContentResolver resolver;
    private String mCloneCode;
    private ReviewActivity reviewActivity;

    public FragmentDetail() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String CloneCode) {
        Fragment fragment = new FragmentDetail();
        Bundle args = new Bundle();
        args.putString(ARG_CLONECODE, CloneCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewActivity = (ReviewActivity) getActivity();
        reviewActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));
        resolver = getActivity().getContentResolver();

        baseApplication = (BaseApplication) getActivity().getApplication();
        if (getArguments() != null) {
            mCloneCode = getArguments().getString(ARG_CLONECODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_detail, container, false);
        mTable = (TableLayout) rootView.findViewById(R.id.mTableLayout);

        String familycode = null;
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = {};
        String sortOrder = null;
        String selection = Columns.CLONECODE + " = ?";
        String[] selectionArgs = {mCloneCode};
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            familycode = c.getString(c.getColumnIndex(Columns.FAMILYCODE));
        }

        c.close();
        SetTable(MakeCompareArrayData(prepareCloneData(), CalculateAverageStandardCloneScore(familycode)));
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

    private ArrayList<Item> MakeCompareArrayData(CloneDataItem cloneData, CloneDataItem standardData) {

        baseApplication.setCloneDataItem(cloneData);


        ArrayList<Item> data = new ArrayList<>();
        Item iH = new Item();
        iH.setRowName("");
        iH.setCloneValue("โคลน");
        iH.setStandardValue("พันธุ์เช็ค");
        data.add(iH);
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


    private CloneDataItem CalculateAverageStandardCloneScore(String FamilyCode) {


        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        int stalkperclump = 0;
        Float brix = 0f;
        int height = 0;
        Float size = 0f;
        while (c != null && c.moveToNext()) {
//            stalkperclump = stalkperclump + c.getInt(c.getColumnIndex(Columns.STALKPERCLUMP));
            brix = brix + c.getFloat(c.getColumnIndex(Columns.BRIX));
//            height = height + c.getInt(c.getColumnIndex(Columns.STALKHEIGHT));
//            size = size + c.getFloat(c.getColumnIndex(Columns.STALKSIZE));
        }

        c.close();
        CloneDataItem sd = new CloneDataItem();
        if (c.getCount() != 0) {

//            sd.setStalkperclump(stalkperclump / c.getCount());
//            sd.setBrix(brix / c.getCount());
//            sd.setHeight(height / c.getCount());
            Log.d("Standard", "Fragment detail");
            baseApplication.setStandardSpecieData(sd);
        } else {
            baseApplication.setStandardSpecieData(sd);
        }
        return sd;

    }

    private CloneDataItem prepareCloneData() {

        CloneDataItem cd = new CloneDataItem();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.CLONECODE + "= ?";
        String[] selectionArgs = {mCloneCode};
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {

            cd.setClonecode(c.getString(c.getColumnIndex(Columns.CLONECODE)));
//            cd.setObserve_score(c.getFloat(c.getColumnIndex(Columns.OVERALL)));
//            cd.setStalkperclump(c.getInt(c.getColumnIndex(Columns.STALKPERCLUMP)));
//            cd.setStalktype(c.getInt(c.getColumnIndex(Columns.STALKSHAPE)));
//            cd.setStalksize(c.getFloat(c.getColumnIndex(Columns.STALKSIZE)));
//            cd.setBrix(c.getFloat(c.getColumnIndex(Columns.BRIX)));
//            cd.setFlowering(c.getInt(c.getColumnIndex(Columns.FLOWERING)));
//            cd.setHeight(c.getInt(c.getColumnIndex(Columns.STALKHEIGHT)));
//            cd.setTexture(c.getInt(c.getColumnIndex(Columns.STALKTEXTURE)));
//            cd.setWhitefly(c.getInt(c.getColumnIndex(Columns.WHITE_FLY)));
//            cd.setBorer(c.getInt(c.getColumnIndex(Columns.BORER)));
//            cd.setYellowspot(c.getInt(c.getColumnIndex(Columns.YELLOWSPOT)));
//            cd.setOtherdisease(c.getInt(c.getColumnIndex(Columns.OTHERDISEASE)));
//            cd.setLeaffall(c.getInt(c.getColumnIndex(Columns.LEAFFALL)));
//            cd.setInterNodePerStalk(c.getInt(c.getColumnIndex(Columns.INTERNODEPERSTALK)));
//            cd.setInternode_lenght(c.getFloat(c.getColumnIndex(Columns.INTERNODELENGHT)));


        }

        c.close();

        return cd;
    }


    private void SetTable(ArrayList<Item> datas) {


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
                tableRow.setBackgroundColor(0x55228b22);
            } else {
//                tableRow.setBackgroundColor(0xFF228b22);
            }

            TextView tvRowName = new TextView(getActivity());
            tvRowName.setText(datas.get(i).getRowName());

            tvRowName.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tvRowName.setTextColor(0xFFFF9999);
            tvRowName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            tvRowName.setTypeface((Typeface.DEFAULT_BOLD));

            TextView tvCloneValue = new TextView(getActivity());
            tvCloneValue.setText(datas.get(i).getCloneValue());
            tvCloneValue.setLayoutParams(new TableRow.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tvCloneValue.setTextColor(0xFFFF9999);
            tvCloneValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            tvCloneValue.setTypeface((Typeface.DEFAULT_BOLD));

            TextView tvStandardValue = new TextView(getActivity());
            tvStandardValue.setText(datas.get(i).getStandardValue());
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
    }

    class Item {
        private String RowName;
        private String CloneValue;
        private String StandardValue;

        public String getRowName() {
            return RowName;
        }

        public void setRowName(String rowName) {
            RowName = rowName;
        }

        public String getCloneValue() {
            return CloneValue;
        }

        public void setCloneValue(String cloneValue) {
            CloneValue = cloneValue;
        }

        public String getStandardValue() {
            return StandardValue;
        }

        public void setStandardValue(String standardValue) {
            StandardValue = standardValue;
        }
    }


}
