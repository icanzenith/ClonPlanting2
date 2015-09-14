package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.Activity;
import android.app.Dialog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentCloneSelect;
import sugarcaneselection.thaib.org.sugarcanselection.MainActivity;
import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * Created by Jitpakorn on 1/27/15 AD.
 */
public class DeleteDialog extends DialogFragment {

    private static final String ARG_CLONECODE = "clonecode";
    private static final String ARG_CASE = "case";
    FragmentCloneSelect fragmentCloneSelect;
    Button btOK;
    DeleteDialogClick deleteDialogClick;
    BaseApplication baseApplication;
    private TextView tvClonCode;
    private String mCloneCode;

    public DeleteDialog() {
    }
    private FragmentCloneSelect fragment;

    public FragmentCloneSelect getFragment() {
        return fragment;
    }

    public void setFragment(FragmentCloneSelect fragment) {
        this.fragment = fragment;
    }

    public static DialogFragment newInstance(String whatCase, String clonecode,FragmentCloneSelect fragment) {

        DeleteDialog d = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CLONECODE, clonecode);
        bundle.putString(ARG_CASE, whatCase);
        d.setFragment(fragment);
        d.setArguments(bundle);
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_manage, container, false);
        if (getArguments() != null) {
            mCloneCode = getArguments().getString(ARG_CLONECODE);
        }

        btOK = (Button) rootView.findViewById(R.id.ok);

        Button btCancel = (Button) rootView.findViewById(R.id.cancel);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvClonCode = (TextView) rootView.findViewById(R.id.tv_clonecode);
        tvClonCode.setText("ยืนยันการล้างข้อมูล " + mCloneCode);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity d = (MainActivity) activity;
        deleteDialogClick = fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseApplication = (BaseApplication) getActivity().getApplication();
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
                String[] projection = null;
                String sortOrder = null;
                String selection = Columns.CLONECODE + " = ?";
                String[] selectionArgs = {mCloneCode};
                ContentResolver r = getActivity().getContentResolver();
                ContentValues c = new ContentValues();
                //Clear Data
                c.put(Columns.WHITE_FLY,"");
                c.put(Columns.WHITE_FLY_SCORE,0);
                c.put(Columns.BORER,"");
                c.put(Columns.BORER_SCORE,0);
                c.put(Columns.APHID,"");
                c.put(Columns.APHID_SCORE,0);
                c.put(Columns.ICERYA_MEAL_BUG,"");
                c.put(Columns.ICERYA_MEAL_BUG_SCORE,0);
                c.put(Columns.SCALE,"");
                c.put(Columns.SCALE_SCORE,0);
                c.put(Columns.POKKAH_BOENG,"");
                c.put(Columns.POKKAH_BOENG_SCORE,0);
                c.put(Columns.YELLOW_SPOT,"");
                c.put(Columns.YELLOW_SPOT_SCORE,0);
                c.put(Columns.BROWN_SPOT,"");
                c.put(Columns.BROWN_SPOT_SCORE,0);
                c.put(Columns.RING_SPOT,"");
                c.put(Columns.RING_SPOT_SCORE,0);
                c.put(Columns.RUST,"");
                c.put(Columns.RUST_SCORE,0);
                c.put(Columns.DOWNY_MILDEW,"");
                c.put(Columns.DOWNY_MILDEW_SCORE,0);
                c.put(Columns.OTHER_DISEASE,"");
                c.put(Columns.OTHER_DISEASE_NAME,0);


                c.put(Columns.FLOWERING,"");
                c.put(Columns.FLOWERING_SCORE,0);
                c.put(Columns.BRIX,"");
                c.put(Columns.BRIX_SCORE,0);
                c.put(Columns.HEIGHT,"");
                c.put(Columns.HEIGHT_SCORE,0);
                c.put(Columns.OVERALL,"");
                c.put(Columns.OVERALL_SCORE,0);
                c.put(Columns.LEAF_SHEATH,"");
                c.put(Columns.LEAF_SHEATH_SCORE,0);
                c.put(Columns.STALK_AMOUNT,"");
                c.put(Columns.STALK_AMOUNT_SCORE,0);
                c.put(Columns.INTERNODE_AMOUNT,"");
                c.put(Columns.INTERNODE_AMOUNT_SCORE,0);
                c.put(Columns.CLUMP_SHAPE,"");
                c.put(Columns.CLUMP_SHAPE_SCORE,0);
                c.put(Columns.CLUMP_CHARACTERISTIC,"");
                c.put(Columns.CLUMP_CHARACTERISTIC_SCORE,0);
                c.put(Columns.INTERNAL_SYSTOM,"");
                c.put(Columns.INTERNAL_SYSTOM_SCORE,0);
                c.put(Columns.INTERNAL_FIRMNESS,"");
                c.put(Columns.INTERNAL_FIRMNESS_SCORE,0);
                c.put(Columns.STUFF,"");
                c.put(Columns.STUFF_SCORE,0);
                c.put(Columns.STALK_SIZE_1,0);
                c.put(Columns.STALK_SIZE_2,0);
                c.put(Columns.STALK_SIZE_3,0);
                c.put(Columns.STALK_SIZE_4,0);
                c.put(Columns.STALK_SIZE_5,0);
                c.put(Columns.STALK_SIZE_AVERAGE,0);
                c.put(Columns.STALK_SIZE_AVERAGE_SCORE,0);
                c.put(Columns.INTERNODE_LENGTH1,0);
                c.put(Columns.INTERNODE_LENGTH2,0);
                c.put(Columns.INTERNODE_LENGTH3,0);
                c.put(Columns.INTERNODE_LENGTH4,0);
                c.put(Columns.INTERNODE_LENGTH5,0);
                c.put(Columns.INTERNODE_LENGTH_AVERAGE,0);
                c.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE,0);
                c.put(Columns.TOTAL_SCORE,0);
                c.put(Columns.FULLPICURL,"");
                c.put(Columns.UPPERPICURL,"");
                c.put(Columns.LOWERPICURL,"");
                c.put(Columns.COLLECTINGTIME,"");
                c.put(Columns.UPLOADPICFULLSTATUS,"");
                c.put(Columns.UPLOADPICLOWSTATUS,"");
                c.put(Columns.UPLOADPICUPSTATUS,"");
                c.put(Columns.WHY_SELECT,"");



                //Clear Data

                c.put(Columns.CHANGESTATUS, 1);
                c.put(Columns.SAVEDSTATUS, 0);
                c.put(Columns.SELECT_STATUS, SelectStatus.NOTHING);
                int update = r.update(uri, c, selection, selectionArgs);
                Log.d("Update", "" + update);
                ReCheckConclusionData();
                deleteDialogClick.onConfirm();
                dismiss();
            }
        });
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return super.onCreateDialog(savedInstanceState);
    }

    public interface DeleteDialogClick {
        public void onConfirm();
    }

    public void onRefreshFamilyList(){


    }
}
