package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class FragmentScoreDetail extends Fragment implements View.OnClickListener {

    private static final String ARG_CloneCode = "clonecode";

    private static final int TYPE_GOOD = 1001;
    private static final int TYPE_BAD = 1002;


    private BaseApplication baseApplication;
    private MainActivity mainActivity;
    //TextView;
    private TextView tvSelectStatus;
    private TextView tv_clonecode;
    private TextView tv_stalkperclump;
    private TextView tv_stalkshape;
    private TextView tv_stalksize;
    private TextView tv_Brix;
    private TextView tv_flowering;
    private TextView tv_height;
    private TextView tv_stuff;
    private TextView tv_whitefly;
    private TextView tv_borer;
    private TextView tv_yellowspot;
    private TextView tv_otherdisease;
    private TextView tv_leaffall;
    private TextView tv_internode;
    private TextView tv_observe;
    private TextView tv_final_score;
    private RoundCornerProgressBar pb_observer;
    private RoundCornerProgressBar pb_stalkperclump;
    private RoundCornerProgressBar pb_stalkshape;
    private RoundCornerProgressBar pb_stalksize;
    private RoundCornerProgressBar pb_Brix;
    private RoundCornerProgressBar pb_flowering;
    private RoundCornerProgressBar pb_height;
    private RoundCornerProgressBar pb_stuff;
    private RoundCornerProgressBar pb__whitefly;
    private RoundCornerProgressBar pb_borer;
    private RoundCornerProgressBar pb_yellowspot;
    private RoundCornerProgressBar pb_otherdisease;
    private RoundCornerProgressBar pb_leaffall;

    private String mCloneCode;


    private OnFragmentInteractionListener mListener;
    private Button btSelect, btUnselect;


    public FragmentScoreDetail() {
        // Required empty public constructor
    }

    public static FragmentScoreDetail newInstance(String CloneCode) {
        FragmentScoreDetail fragment = new FragmentScoreDetail();
        if (CloneCode == null) {

        } else {
            Bundle args = new Bundle();
            //TODO Add CloneCode here!!
            args.putString(ARG_CloneCode, CloneCode);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication = (BaseApplication) getActivity().getApplication();

        setHasOptionsMenu(true);
        if (getArguments() != null) {

            setHasOptionsMenu(false);

            CloneDataItem cd = new CloneDataItem();
            mCloneCode = getArguments().getString(ARG_CloneCode);
            Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
            String[] projection = null;
            String sortOrder = null;
            String selection = Columns.CLONECODE + " = ?";
            String[] selectionArgs = {mCloneCode};
            ContentResolver r = getActivity().getContentResolver();
            Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
            while (c != null && c.moveToNext()) {

                cd.setClonecode(c.getString(c.getColumnIndex(Columns.CLONECODE)));
//                cd.setObserve_score(c.getFloat(c.getColumnIndex(Columns.OVERALL)));
//                cd.setStalkperclump(c.getInt(c.getColumnIndex(Columns.STALKPERCLUMP)));
//                cd.setStalktype(c.getInt(c.getColumnIndex(Columns.STALKSHAPE)));
//                cd.setStalksize(c.getFloat(c.getColumnIndex(Columns.STALKSIZE)));
//                cd.setBrix(c.getFloat(c.getColumnIndex(Columns.BRIX)));
//                cd.setFlowering(c.getInt(c.getColumnIndex(Columns.FLOWERING)));
//                cd.setHeight(c.getInt(c.getColumnIndex(Columns.STALKHEIGHT)));
//                cd.setTexture(c.getInt(c.getColumnIndex(Columns.STALKTEXTURE)));
//                cd.setWhitefly(c.getInt(c.getColumnIndex(Columns.WHITE_FLY)));
//                cd.setBorer(c.getInt(c.getColumnIndex(Columns.BORER)));
//                cd.setYellowspot(c.getInt(c.getColumnIndex(Columns.YELLOWSPOT)));
//                cd.setOtherdisease(c.getInt(c.getColumnIndex(Columns.OTHERDISEASE)));
//                cd.setLeaffall(c.getInt(c.getColumnIndex(Columns.LEAFFALL)));
//                cd.setInterNodePerStalk(c.getInt(c.getColumnIndex(Columns.INTERNODEPERSTALK)));
//                cd.setInternode_lenght(c.getFloat(c.getColumnIndex(Columns.INTERNODELENGHT)));

            }
            c.close();

            baseApplication.setStandardSpecieData(CalculateAverageStandardCloneScore(getFamilyCode()));
            OnCalculateScore oCal = new OnCalculateScore(cd, baseApplication);
//            baseApplication.setCalculateScore(oCal.getCal());
//            baseApplication.getCalculateScore().getTotalScore(cd);


        } else {
            mainActivity = (MainActivity) getActivity();
        }
    }

    private String getFamilyCode() {
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
        return familycode;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_newdetail, container, false);

        btSelect = (Button) rootView.findViewById(R.id.btSelect);
        btUnselect = (Button) rootView.findViewById(R.id.btUnselect);

        btSelect.setOnClickListener(this);
        btUnselect.setOnClickListener(this);

        tvSelectStatus = (TextView) rootView.findViewById(R.id.tvSelectStatus);
        if (getArguments() != null) {
            btUnselect.setVisibility(View.INVISIBLE);
            btSelect.setVisibility(View.INVISIBLE);
            tvSelectStatus.setVisibility(View.INVISIBLE);
        }
        tv_final_score = (TextView) rootView.findViewById(R.id.tv_final_score);

        tv_clonecode = (TextView) rootView.findViewById(R.id.tv_clonecode);
        tv_stalkperclump = (TextView) rootView.findViewById(R.id.tv_stalkperclump);
        tv_Brix = (TextView) rootView.findViewById(R.id.tv_brix);
        tv_height = (TextView) rootView.findViewById(R.id.tv_height);

        tv_stalksize = (TextView) rootView.findViewById(R.id.tv_size);
        tv_stalkshape = (TextView) rootView.findViewById(R.id.tv_stalkshape);
        tv_stuff = (TextView) rootView.findViewById(R.id.tv_stuff);
        tv_leaffall = (TextView) rootView.findViewById(R.id.tv_leaffall);
        tv_flowering = (TextView) rootView.findViewById(R.id.tv_flowering);
        tv_observe = (TextView) rootView.findViewById(R.id.tv_observe);
        tv_whitefly = (TextView) rootView.findViewById(R.id.tv_whitefly);
        tv_borer = (TextView) rootView.findViewById(R.id.tv_borer);
        tv_yellowspot = (TextView) rootView.findViewById(R.id.tv_yellowspot);
        tv_otherdisease = (TextView) rootView.findViewById(R.id.tv_otherdisease);


        pb_observer = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_observe);
        pb_stalkperclump = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_stalkperclump);
        pb_stalkshape = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_stalkshape);
        pb_stalksize = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_stalksize);
        pb_Brix = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_brix);
        pb_flowering = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_flowering);
        pb_height = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_height);
        pb_stuff = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_stuff);
        pb__whitefly = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_whitefly);
        pb_borer = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_borer);
        pb_yellowspot = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_yellowspot);
        pb_otherdisease = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_otherdisease);
        pb_leaffall = (RoundCornerProgressBar) rootView.findViewById(R.id.progressBar_leaffall);

//        tv_final_score.setText("Total Score " + baseApplication.getCalculateScore().getTotalScore(baseApplication.getCloneDataItem()) + " คะแนน");


        setProgressBar();

        return rootView;
    }

    private void setProgressBar() {
        CloneDataItem sc = baseApplication.getCloneDataItem();
//        CalculateScore cal = baseApplication.getCalculateScore();
//        Log.d("GetCalTy", "" + cal.getScore_texture() + " " + cal.getScore_stalktype());


//        setProgressBarStyle(pb_observer, 5, cal.getScore_observe_score(), TYPE_GOOD);
//        setProgressBarStyle(pb_stalkperclump, 5, cal.getScore_stalkperclump(), TYPE_GOOD);
//        setProgressBarStyle(pb_stalkshape, 5, cal.getScore_stalktype(), TYPE_GOOD);
//        setProgressBarStyle(pb_stalksize, 5, cal.getScore_stalksize(), TYPE_GOOD);
//        setProgressBarStyle(pb_Brix, 5, cal.getScore_Brix(), TYPE_GOOD);
//        setProgressBarStyle(pb_flowering, 5, cal.getScore_flowering(), TYPE_GOOD);
//        setProgressBarStyle(pb_height, 5, cal.getScore_height(), TYPE_GOOD);
//        setProgressBarStyle(pb_stuff, 5, cal.getScore_texture(), TYPE_GOOD);
//        setProgressBarStyle(pb_leaffall, 5, sc.getLeaffall(), TYPE_GOOD);
//
//        setProgressBarStyle(pb__whitefly, 5, cal.getScore_whitefly(), TYPE_BAD);
//        setProgressBarStyle(pb_borer, 5, cal.getScore_borer(), TYPE_BAD);
//        setProgressBarStyle(pb_yellowspot, 5, cal.getScore_yellospot(), TYPE_BAD);
//        setProgressBarStyle(pb_otherdisease, 5, cal.getScore_otherdisease(), TYPE_BAD);
//
//
//        setTextView(sc, cal);

    }

    private void setTextView(CloneDataItem sc, CalculateScore cal) {

        tv_clonecode.setText("หมายเลข Clone " + sc.getClonecode());
        tv_stalkperclump.setText((cal.getScore_stalkperclump() * 20) + "/" + (5 * 20) + " คะแนน");

        tv_observe.setText((cal.getScore_observe_score() * 5) + "/" + (5 * 5) + " คะแนน");

        tv_stalkshape.setText((cal.getScore_stalktype() * 15) + "/" + (5 * 15) + " คะแนน");
        tv_stalksize.setText((cal.getScore_stalksize() * 8) + "/" + (5 * 8) + " คะแนน");
        tv_Brix.setText((cal.getScore_Brix() * 15) + "/" + (5 * 15) + " คะแนน");
        tv_flowering.setText((cal.getScore_flowering() * 10) + "/" + (5 * 10) + " คะแนน");
        tv_height.setText((cal.getScore_height() * 8) + "/" + (5 * 8) + " คะแนน");
        tv_stuff.setText((cal.getScore_texture() * 10) + "/" + (5 * 10) + " คะแนน");
        tv_whitefly.setText((cal.getScore_whitefly() * 3) + "/" + (5 * 3) + " คะแนน");
        tv_borer.setText((cal.getScore_borer() * 2) + "/" + (5 * 2) + " คะแนน");
        tv_yellowspot.setText((cal.getScore_yellospot() * 2) + "/" + (5 * 2) + " คะแนน");
        tv_otherdisease.setText((cal.getScore_otherdisease() * 2) + "/" + (5 * 2) + " คะแนน");
//        tv_leaffall.setText(sc.getLeaffall() + "/" + (5) + " คะแนน");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setProgressBarStyle(RoundCornerProgressBar p, float max, float progress, int type) {


        p.setMax(max);
        p.setProgress(progress);
        p.setBackgroundColor(0x00000000);
        if (type == TYPE_GOOD) {
            switch ((int) progress) {
                case 5:
                    p.setProgressColor(0xFF88CC00);
                    break;
                case 4:
                    p.setProgressColor(0xFF7F9900);
                    break;
                case 3:
                    p.setProgressColor(0xFFFFAA00);
                    break;
                case 2:
                    p.setProgressColor(0xFFFF8000);
                    break;
                case 1:
                    p.setProgressColor(0xFFCC2200);
                    break;
                default:

                    break;
            }

        } else if (type == TYPE_BAD) {
            switch ((int) progress) {
                case 5:
                    p.setProgressColor(0xFFCC2200);
                    break;
                case 4:
                    p.setProgressColor(0xFFFF8000);
                    break;
                case 3:
                    p.setProgressColor(0xFFFFAA00);
                    break;
                case 2:
                    p.setProgressColor(0xFF7F9900);
                    break;
                case 1:
                    p.setProgressColor(0xFF88CC00);
                    break;
                default:

                    break;
            }
        }

    }

    private void SaveDatatoDatabase(boolean selectstatus) {


        CloneDataItem cloneDataItem = baseApplication.getCloneDataItem();
        CloneDataItem standardSpecieData = baseApplication.getStandardSpecieData();

        String Clonecode = cloneDataItem.getClonecode();
        ContentResolver cr = getActivity().getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        ContentValues v = new ContentValues();
        String where = "clonecode = ?";
        String[] selection = {Clonecode};

        //ContentAdd


        if (!selectstatus) {
            v.put(Columns.SELECT_STATUS, 0);

        } else {
            v.put(Columns.SELECT_STATUS, 1);
        }
//
//        v.put("collectingtime", "");
//        v.put("stalkperclump", cloneDataItem.getStalkperclump());
//        v.put("stalkshape", cloneDataItem.getStalktype());
//        v.put("stalksize", cloneDataItem.getStalkSizeAverage());
//        v.put("brix", cloneDataItem.getBrix());
//        v.put("flowering", cloneDataItem.getFlowering());
//        v.put("stalkheight", cloneDataItem.getHeight());
//        v.put("stalktexture", cloneDataItem.getTexture());
//        v.put("whitefly", cloneDataItem.getWhitefly());
//        v.put("borer", cloneDataItem.getBorer());
//        v.put("yellowspot", cloneDataItem.getYellowspot());
//        v.put("otherdisease", cloneDataItem.getOtherdisease());
//        v.put("observe", cloneDataItem.getObserve_score());
//        v.put("changestatus", 1);
//        v.put("uploadstatus", 0);

//        v.put(Columns.CLUMPSHAPE, cloneDataItem.getClumpShape());
//        v.put(Columns.LEAFFALL, cloneDataItem.getLeafSheath());
//        v.put(Columns.DISEASE, cloneDataItem.getDisease());
//        v.put(Columns.STUFFDENSITY, cloneDataItem.getStuffDensity());
//        v.put(Columns.STALKSHAPE, cloneDataItem.getStalkShape());

//        v.put(Columns.STALK_SIZE_1, cloneDataItem.getStalkSize1());
//        v.put(Columns.STALK_SIZE_2, cloneDataItem.getStalkSize2());
//        v.put(Columns.STALK_SIZE_3, cloneDataItem.getStalkSize3());
//        v.put(Columns.STALK_SIZE_4, cloneDataItem.getStalkSize4());
//        v.put(Columns.STALK_SIZE_5, cloneDataItem.getStalkSize5());
//        v.put(Columns.AVERAGE_STALK_SIZE, cloneDataItem.getStalkSizeAverage());



//        v.put("leaffall", cloneDataItem.getLeaffall());
//        v.put("internode", cloneDataItem.getInterNodePerStalk());
//        v.put(Columns.INTERNODELENGHT, cloneDataItem.getInternode_lenght());
//        v.put(Columns.SAVEDSTATUS, 1);

        int update = cr.update(uri, v, where, selection);

        ContentValues v2 = new ContentValues();
        v2.put("SavedStatus", 1);
        String where2 = "clonecode = ?";
        String[] selection2 = {cloneDataItem.getClonecode()};
        int update2 = cr.update(uri, v2, where2, selection2);
        if (update >= 0) {
            Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
        }

        mainActivity.onFinishDetail();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btUnselect:
                SaveDatatoDatabase(false);
                break;
            case R.id.btSelect:
                SaveDatatoDatabase(true);
                break;

        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
