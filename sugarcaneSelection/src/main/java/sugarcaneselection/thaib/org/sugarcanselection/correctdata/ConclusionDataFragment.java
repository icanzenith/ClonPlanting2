package sugarcaneselection.thaib.org.sugarcanselection.correctdata;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.Code;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ProgressScoreView;
import sugarcaneselection.thaib.org.sugarcanselection.Item.ScoreItem;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;


public class ConclusionDataFragment extends Fragment {
    private HashMap<String,ScoreItem> dataItem;
    private MainActivity mainActivity;
    private BaseApplication baseApplication;
    private ActionBar actionBar;
    public static ConclusionDataFragment newInstance() {
        ConclusionDataFragment fragment = new ConclusionDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ConclusionDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }



        baseApplication = (BaseApplication) getActivity().getApplication();
        dataItem =  baseApplication.getCloneDataItem().getDataScore();

        mainActivity = (MainActivity) getActivity();
        actionBar = mainActivity.getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayOptions(ActionBar.NAVIGATION_MODE_STANDARD);
        hasOptionsMenu();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conclusion_data, container, false);
        LinearLayout v = (LinearLayout) view.findViewById(R.id.container);
        for (String i : dataItem.keySet()){
                v.addView(ProgressScoreView.ProgreesContainerView(getActivity(),R.layout.single_list_progressview,dataItem.get(i)));
        }

        return view;
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

            getCollectingTime();

        }


        return super.onOptionsItemSelected(item);
    }
    private void onSavetoDataBase(boolean selectstatus){
        CloneDataItem cloneDataItem = baseApplication.getCloneDataItem();
        HashMap<String,ScoreItem> data = cloneDataItem.getDataScore();

        String Clonecode = cloneDataItem.getClonecode();
        ContentResolver cr = getActivity().getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        ContentValues v = new ContentValues();
        String where = "clonecode = ?";
        String[] selection = {Clonecode};

        if (!selectstatus) {
            v.put(Columns.SELECT_STATUS, 0);

        } else {
            v.put(Columns.SELECT_STATUS, 1);
        }

        v.put(Columns.CLONECODE,cloneDataItem.getClonecode());

        v.put(Columns.COLLECTINGTIME, getCollectingTime());

        v.put(Columns.STALK_AMOUNT, (int)cloneDataItem.getDataScore().get(Code.StalkAmount).getData());
        v.put(Columns.STALK_AMOUNT_SCORE,cloneDataItem.getDataScore().get(Code.StalkAmount).getScore());

        v.put(Columns.CLUMP_SHAPE, (String)cloneDataItem.getDataScore().get(Code.ClumpShape).getData());
        v.put(Columns.CLUMP_SHAPE_SCORE, cloneDataItem.getDataScore().get(Code.ClumpShape).getScore());

        //White Fly
        v.put(Columns.WHITE_FLY, (String) cloneDataItem.getDataScore().get(Code.WhiteFly).getData());
        v.put(Columns.WHITE_FLY_SCORE, cloneDataItem.getDataScore().get(Code.WhiteFly).getScore());

        v.put(Columns.BORER, (String) cloneDataItem.getDataScore().get(Code.Borer).getData());
        v.put(Columns.BORER_SCORE, cloneDataItem.getDataScore().get(Code.Borer).getScore());

        v.put(Columns.APHID, (String) cloneDataItem.getDataScore().get(Code.Aphid).getData());
        v.put(Columns.APHID_SCORE, cloneDataItem.getDataScore().get(Code.Aphid).getScore());

        v.put(Columns.ICERYA_MEAL_BUG, (String) cloneDataItem.getDataScore().get(Code.IceryaMealbug).getData());
        v.put(Columns.ICERYA_MEAL_BUG_SCORE, cloneDataItem.getDataScore().get(Code.IceryaMealbug).getScore());

        v.put(Columns.SCALE, (String) cloneDataItem.getDataScore().get(Code.Scale).getData());
        v.put(Columns.SCALE_SCORE, cloneDataItem.getDataScore().get(Code.Scale).getScore());

        v.put(Columns.POKKAH_BOENG, (String) cloneDataItem.getDataScore().get(Code.PokkahBoeng).getData());
        v.put(Columns.POKKAH_BOENG_SCORE, cloneDataItem.getDataScore().get(Code.PokkahBoeng).getScore());

        v.put(Columns.YELLOW_SPOT, (String) cloneDataItem.getDataScore().get(Code.YellowSpot).getData());
        v.put(Columns.YELLOW_SPOT_SCORE, cloneDataItem.getDataScore().get(Code.YellowSpot).getScore());

        v.put(Columns.BROWN_SPOT, (String) cloneDataItem.getDataScore().get(Code.BrownSpot).getData());
        v.put(Columns.BROWN_SPOT_SCORE, cloneDataItem.getDataScore().get(Code.BrownSpot).getScore());

        v.put(Columns.RING_SPOT, (String) cloneDataItem.getDataScore().get(Code.RingSpot).getData());
        v.put(Columns.RING_SPOT_SCORE, cloneDataItem.getDataScore().get(Code.RingSpot).getScore());

        v.put(Columns.RUST, (String) cloneDataItem.getDataScore().get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE, cloneDataItem.getDataScore().get(Code.Rust).getScore());

        //////
        v.put(Columns.DOWNY_MILDEW, (String) cloneDataItem.getDataScore().get(Code.DownyMildew).getData());
        v.put(Columns.DOWNY_MILDEW_SCORE, cloneDataItem.getDataScore().get(Code.DownyMildew).getScore());

        v.put(Columns.OTHER_DISEASE, (String) cloneDataItem.getDataScore().get(Code.OtherDisease).getData());
        //TODO เพิ่มชื่อ
        v.put(Columns.OTHER_DISEASE_NAME, cloneDataItem.getDataScore().get(Code.OtherDisease).getTagName());
        v.put(Columns.OTHER_DISEASE_SCORE, cloneDataItem.getDataScore().get(Code.OtherDisease).getScore());


        v.put(Columns.RUST, (String) cloneDataItem.getDataScore().get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE, cloneDataItem.getDataScore().get(Code.Rust).getScore());

        v.put(Columns.RUST, (String) cloneDataItem.getDataScore().get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE, cloneDataItem.getDataScore().get(Code.Rust).getScore());

        v.put(Columns.RUST, (String) cloneDataItem.getDataScore().get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE, cloneDataItem.getDataScore().get(Code.Rust).getScore());

        v.put(Columns.RUST, (String) cloneDataItem.getDataScore().get(Code.Rust).getData());
        v.put(Columns.RUST_SCORE, cloneDataItem.getDataScore().get(Code.Rust).getScore());



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
        v.put(Columns.SAVEDSTATUS, 1);


    }

    private String getCollectingTime() {
        String CollectingTime;
        Time now = new Time();
        now.setToNow();
        CollectingTime = String.format(now.format("%Y-%m-%d"));
        Log.d("Tag",CollectingTime);
        return CollectingTime;
    }
}
