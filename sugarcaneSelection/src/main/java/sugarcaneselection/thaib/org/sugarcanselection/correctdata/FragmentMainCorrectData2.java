package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Vector;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.InitScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.InternodeLength;
import sugarcaneselection.thaib.org.sugarcanselection.Item.MatchCodeAndColumn;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.Item.StalkSize;
import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightScore;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class FragmentMainCorrectData2 extends Fragment implements ActionBar.TabListener {

    private static final int INDEX_OVERVIEW = 0;
    private static final int INDEX_STALK = 1;
    private static final int INDEX_STUFF = 2;
    private static final int INDEX_DISEASE = 3;

    private BaseApplication b;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Vector<Fragment> vector;
    private ActionBar actionBar;
    private int CloneType;

    public static FragmentMainCorrectData2 newInstance(int CloneType) {
        FragmentMainCorrectData2 fragment = new FragmentMainCorrectData2();
        Bundle args = new Bundle();
        args.putInt("CloneType", CloneType);

        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMainCorrectData2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        boolean newDataAdd = true;
        if (getArguments() != null) {

            b = (BaseApplication) getActivity().getApplication();

            HashMap<String, ScoreItem> DataScoreItem = new HashMap<>();
            HashMap<String, Float> StalkSizeMap = new HashMap<>();
            HashMap<String, Float> InternodeLengthMap = new HashMap<>();


            //OverAll
            DataScoreItem.put(Code.OverAll, InitScoreItem.Overview());
            DataScoreItem.put(Code.Brix, InitScoreItem.Brix());
            DataScoreItem.put(Code.Flowering, InitScoreItem.Flowering());
            DataScoreItem.put(Code.LeafSheath, InitScoreItem.LeafSheath());
            DataScoreItem.put(Code.ClumpShape, InitScoreItem.ClumpShape());
            DataScoreItem.put(Code.ClumpCharacteristic, InitScoreItem.ClumpCharacteristic());


            //Stalk Amount
            DataScoreItem.put(Code.StalkSize, InitScoreItem.StalkSize());
            DataScoreItem.put(Code.InternodeLength, InitScoreItem.InternodeLength());
            DataScoreItem.put(Code.StalkAmount, InitScoreItem.StalkAmount());
            DataScoreItem.put(Code.Height, InitScoreItem.Height());
            DataScoreItem.put(Code.InternodeAmount, InitScoreItem.InternodeAmount());

            //Stuff
            DataScoreItem.put(Code.InternalSymtom, InitScoreItem.InternalSymtom());
            DataScoreItem.put(Code.InternalFirmness, InitScoreItem.InternalFirmness());
            DataScoreItem.put(Code.Stuff, InitScoreItem.Stuff());

            //Diease

            DataScoreItem.put(Code.WhiteFly, InitScoreItem.WhiteFly());
            DataScoreItem.put(Code.Borer, InitScoreItem.Borer());
            DataScoreItem.put(Code.Aphid, InitScoreItem.Aphid());
            DataScoreItem.put(Code.IceryaMealbug, InitScoreItem.IceryaMealbug());
            DataScoreItem.put(Code.Scale, InitScoreItem.Scale());
            DataScoreItem.put(Code.PokkahBoeng, InitScoreItem.PokkahBoeng());
            DataScoreItem.put(Code.YellowSpot, InitScoreItem.YellowSpot());
            DataScoreItem.put(Code.BrownSpot, InitScoreItem.BrownSpot());
            DataScoreItem.put(Code.RingSpot, InitScoreItem.RingSpot());
            DataScoreItem.put(Code.Rust, InitScoreItem.Rust());
            DataScoreItem.put(Code.DownyMildew, InitScoreItem.DownyMildew());
            DataScoreItem.put(Code.OtherDisease, InitScoreItem.OtherDisease());


            BaseApplication b = (BaseApplication) getActivity().getApplication();
            b.getCloneDataItem().setDataScore(DataScoreItem);

            b.getCloneDataItem().setStalkSize(new HashMap<String, Float>());
            b.getCloneDataItem().setInternodeLength(new HashMap<String, Float>());

            for (StalkSize s : StalkSize.values()) {
                b.getCloneDataItem().getStalkSize().put(s.name(), 0f);
            }
            for (InternodeLength s : InternodeLength.values()) {
                b.getCloneDataItem().getInternodeLength().put(s.name(), 0f);
            }
            ContentResolver contentResolver = getActivity().getContentResolver();

            Uri uri;
            String[] projection;
            String selection;
            String[] selectionArgs;
            String sortOrder;

            if (b.getCloneDataItem().getDataType() == CloneDataItem.DATATYPE_CLONE) {
                // CLONE DATA HERE
                uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
                selection = Columns.CLONECODE + " = ?";
                selectionArgs = new String[]{b.getCloneDataItem().getClonecode()};
                sortOrder = null;
                projection = null;


                Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
                if (c.getCount() != 0) {
                    while (c.moveToNext()) {
                        // GET CLONE DATA HERE

                        if (c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)) != SelectStatus.NOTHING) {
                            newDataAdd = false;
                            if (c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)) != null) {
                                if (!c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)).equals("")) {
                                    b.getCloneDataItem().setOtherDiseaseName(c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)));
                                }
                            }
                            for (String i : Code.KeySet) {
                                ScoreItem item = new ScoreItem();
                                boolean isItemNull = true;


                                int dd = c.getType(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i)));

                                switch (dd) {
                                    case Cursor.FIELD_TYPE_FLOAT:
                                        item.setData(c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                        isItemNull = false;
                                        break;
                                    case Cursor.FIELD_TYPE_INTEGER:
                                        item.setData(c.getInt(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                        isItemNull = false;
                                        break;
                                    case Cursor.FIELD_TYPE_STRING:
                                        if (c.getString(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))) != null) {

                                            item.setData(c.getString(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                            isItemNull = false;

                                        } else {
                                            isItemNull = true;
                                        }
                                        break;

                                    default:
                                        break;
                                }
                                if (!isItemNull) {
                                    item.setTagName(i);
                                    item.setScore(c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getScoreColumn().get(i))));
                                    item.setRawScore((item.getScore() / WeightScore.getWeigth().get(i)));
                                    item.setUnit(Code.getUnit().get(i));
//                                Log.d("Check Query Data","Tag "+item.getTagName()+" / Data "+item.getData()+" Score "+item.getScore());


                                    DataScoreItem.put(i, item);
                                }
                            }

                            for (StalkSize s : StalkSize.values()) {
                                b.getCloneDataItem().getStalkSize().put(s.name(), c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(s.name()))));

                            }
                            for (InternodeLength s : InternodeLength.values()) {
                                b.getCloneDataItem().getInternodeLength().put(s.name(), c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(s.name()))));
                            }

                            b.getCloneDataItem().setDataScore(DataScoreItem);
                            b.getCloneDataItem().setSelectStatus(c.getString(c.getColumnIndex(Columns.SELECT_STATUS)));
                        }
                    }
                    c.close();
                }
            } else {
                // STANDARD DATA HERE

                uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
                selection = Columns.FAMILYCODE + " = ? AND " + Columns.ROWNUMBER + " = " + b.getCloneDataItem().getSTANDARD_ROW();
                selectionArgs = new String[]{b.getCloneDataItem().getFamilycode()};
                sortOrder = null;
                projection = null;


                Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
                if (c.getCount() != 0) {
                    while (c.moveToNext()) {
                        //GET CLONE DATA HERE

                        if (c.getInt(c.getColumnIndex(Columns.SAVEDSTATUS)) == SelectStatus.SAVED) {
                            newDataAdd = false;

                            if (c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)) != null && !c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)).equals("")) {
                                b.getCloneDataItem().setOtherDiseaseName(c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)));
                            }

                            b.getCloneDataItem().setSpecieName(c.getString(c.getColumnIndex(Columns.SPECIENAME)));
                            b.getCloneDataItem().setSTANDARD_ROW(c.getInt(c.getColumnIndex(Columns.ROWNUMBER)));
                            for (String i : Code.KeySet) {
                                ScoreItem item = new ScoreItem();
                                item.setTagName(i);

                                int dd = c.getType(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i)));

                                switch (dd) {
                                    case Cursor.FIELD_TYPE_FLOAT:
                                        item.setData(c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                        break;
                                    case Cursor.FIELD_TYPE_INTEGER:
                                        item.setData(c.getInt(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                        break;
                                    case Cursor.FIELD_TYPE_STRING:
                                        item.setData(c.getString(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(i))));
                                        break;

                                    default:
                                        break;
                                }

                                item.setScore(c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getScoreColumn().get(i))));
                                item.setRawScore((item.getScore() / WeightScore.getWeigth().get(i)));
                                item.setUnit(Code.getUnit().get(i));
                                Log.d("Check Query Data", "Tag " + item.getTagName() + " / Data " + item.getData() + " Score " + item.getScore());


                                DataScoreItem.put(i, item);
                            }

                            for (StalkSize s : StalkSize.values()) {
                                b.getCloneDataItem().getStalkSize().put(s.name(), c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(s.name()))));

                            }
                            for (InternodeLength s : InternodeLength.values()) {
                                b.getCloneDataItem().getInternodeLength().put(s.name(), c.getFloat(c.getColumnIndex(MatchCodeAndColumn.getTagColumn().get(s.name()))));
                            }

                            b.getCloneDataItem().setDataScore(DataScoreItem);
                        }
                    }
                    c.close();
                }


            }


            CloneType = getArguments().getInt("CloneType");

        }
    }

    public Vector<Fragment> getVectorFragment() {
        return vector;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity m = (MainActivity) getActivity();


        actionBar = m.getSupportActionBar();
        actionBar.setTitle("บันทึกข้อมูล");
        actionBar.setCustomView(R.layout.actionbartop);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Button btNext = (Button) actionBar.getCustomView().findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                m.onFinishCorrectData();
            }
        });

        vector = new Vector<>();
        vector.add(INDEX_OVERVIEW, DataEntryOverViewFragment.newInstance(CloneType));
        vector.add(INDEX_STALK, DataEntryStalkFragment.newInstance());
        vector.add(INDEX_STUFF, DataEntryStuffFragment.newInstance());
        vector.add(INDEX_DISEASE, FragmentDiseaseCorrect2.newInstance());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), vector);

        View rootView = inflater.inflate(R.layout.fragment_correct_data2, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

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

    @Override
    public void onDestroyView() {

        mSectionsPagerAdapter = null;

        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public SectionsPagerAdapter getmSectionsPagerAdapter() {
        return mSectionsPagerAdapter;
    }

    public void setmSectionsPagerAdapter(SectionsPagerAdapter mSectionsPagerAdapter) {
        this.mSectionsPagerAdapter = mSectionsPagerAdapter;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        Vector<Fragment> vector;

        public SectionsPagerAdapter(FragmentManager fm, Vector<android.support.v4.app.Fragment> vector) {
            super(fm);

            this.vector = vector;
        }

        @Override
        public Fragment getItem(int position) {
            return vector.get(position);
        }

        @Override
        public int getCount() {

            return vector.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case INDEX_OVERVIEW:
                    return "ภาพรวม";
                case INDEX_STALK:
                    return "ลำและข้อปล้อง";
                case INDEX_STUFF:
                    return "เนื้ออ้อย";
                case INDEX_DISEASE:
                    return "โรคและแมลง";
            }

            return null;
        }
    }


}
