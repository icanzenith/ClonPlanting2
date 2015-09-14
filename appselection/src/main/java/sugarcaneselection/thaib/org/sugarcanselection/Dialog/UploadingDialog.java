package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightValue;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.R;

public class UploadingDialog extends DialogFragment {

    BaseApplication baseApplication;
    TextView Clonename;
    ImageView image;
    ProgressBar progressBar;
    AQuery aq;
    GsonTransformer gsonTransformer = new GsonTransformer();
    HashMap<String, Object> param = new HashMap<String, Object>();
    String url;
    int current = 1;


    public UploadingDialog() {

    }

    public UploadingDialog newInstance(Context context) {
        UploadingDialog d = new UploadingDialog();
        Bundle bundle = new Bundle();
        d.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        d.setArguments(bundle);
        return d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_upload, null);
        Clonename = (TextView) v.findViewById(R.id.clone_name);
        image = (ImageView) v.findViewById(R.id.image);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_FAMILY));
        String[] projection = null;
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getActivity().getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            String FamilyCode = c.getString(c.getColumnIndex(Columns.FAMILYCODE));
            ReCalculateScore(FamilyCode);
        }

        c.close();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                baseApplication = (BaseApplication) getActivity().getApplicationContext();
                aq = new AQuery(getActivity().getApplicationContext());

                //CloneData
                url = getResources().getString(R.string.url_uploadimage);
                String uri = getResources().getString(R.string.URI_MYCLONE);
                String[] projection = null;
                String selection = Columns.CHANGESTATUS+" = 1";
                String[] selectionArgs = null;
                String sortOrder = null;

                Cursor c = getActivity().getContentResolver().query(Uri.parse(uri), projection, selection, selectionArgs, sortOrder);

                progressBar.setMax(c.getCount());



                if (c.getCount() != 0) {

                    while (c.moveToNext()) {

                        //
                        HashMap<String, Object> param = new HashMap<String, Object>();

                        // Edit Here Detail
                        param.put(Params.CLONECODE, c.getString(c.getColumnIndex(Columns.CLONECODE)));
                        param.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        param.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        param.put(Params.COLLECTINGTIME, c.getString(c.getColumnIndex(Columns.COLLECTINGTIME)));
//
                        //ลำ
                        param.put(Params.STALK_SIZE_1, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_1)));
                        param.put(Params.STALK_SIZE_2, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_2)));
                        param.put(Params.STALK_SIZE_3, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_3)));
                        param.put(Params.STALK_SIZE_4, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_4)));
                        param.put(Params.STALK_SIZE_5, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_5)));
                        param.put(Params.STALK_SIZE_AVERAGE, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));

                        //ความยาวข้อปล้อง
                        param.put(Params.INTERNODE_LENGTH1, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH1)));
                        param.put(Params.INTERNODE_LENGTH2, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH2)));
                        param.put(Params.INTERNODE_LENGTH3, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH3)));
                        param.put(Params.INTERNODE_LENGTH4, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH4)));
                        param.put(Params.INTERNODE_LENGTH5, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH5)));
                        param.put(Params.INTERNODE_LENGTH_AVERAGE, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH_AVERAGE)));


//                        //Disease
                        param.put(Params.WHITE_FLY,c.getString(c.getColumnIndex(Columns.WHITE_FLY)));
                        param.put(Params.WHITE_FLY_SCORE,c.getString(c.getColumnIndex(Columns.WHITE_FLY_SCORE)));
                        param.put(Params.BORER,c.getString(c.getColumnIndex(Columns.BORER)));
                        param.put(Params.BORER_SCORE,c.getString(c.getColumnIndex(Columns.BORER_SCORE)));
                        param.put(Params.APHID,c.getString(c.getColumnIndex(Columns.APHID)));
                        param.put(Params.APHID_SCORE,c.getString(c.getColumnIndex(Columns.APHID_SCORE)));
                        param.put(Params.ICERYA_MEAL_BUG,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG)));
                        param.put(Params.ICERYA_MEAL_BUG_SCORE,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG_SCORE)));
                        param.put(Params.SCALE,c.getString(c.getColumnIndex(Columns.SCALE)));
                        param.put(Params.SCALE_SCORE,c.getString(c.getColumnIndex(Columns.SCALE_SCORE)));
                        param.put(Params.POKKAH_BOENG,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG)));
                        param.put(Params.POKKAH_BOENG_SCORE,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG_SCORE)));
                        param.put(Params.YELLOW_SPOT,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT)));
                        param.put(Params.YELLOW_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT_SCORE)));
                        param.put(Params.BROWN_SPOT,c.getString(c.getColumnIndex(Columns.BROWN_SPOT)));
                        param.put(Params.BROWN_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.BROWN_SPOT_SCORE)));
                        param.put(Params.RING_SPOT,c.getString(c.getColumnIndex(Columns.RING_SPOT)));
                        param.put(Params.RING_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.RING_SPOT_SCORE)));
                        param.put(Params.RUST,c.getString(c.getColumnIndex(Columns.RUST)));
                        param.put(Params.RUST_SCORE,c.getString(c.getColumnIndex(Columns.RUST_SCORE)));
                        param.put(Params.DOWNY_MILDEW,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW)));
                        param.put(Params.DOWNY_MILDEW_SCORE,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW_SCORE)));

                        param.put(Params.OTHER_DISEASE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE)));
                        param.put(Params.OTHER_DISEASE_NAME,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)));
                        param.put(Params.OTHER_DISEASE_SCORE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_SCORE)));

                        param.put(Params.FLOWERING,c.getString(c.getColumnIndex(Columns.FLOWERING)));
                        param.put(Params.FLOWERING_SCORE,c.getString(c.getColumnIndex(Columns.FLOWERING_SCORE)));
                        param.put(Params.BRIX,c.getString(c.getColumnIndex(Columns.BRIX)));
                        param.put(Params.BRIX_SCORE,c.getString(c.getColumnIndex(Columns.BRIX_SCORE)));
                        param.put(Params.HEIGHT,c.getString(c.getColumnIndex(Columns.HEIGHT)));
                        param.put(Params.HEIGHT_SCORE,c.getString(c.getColumnIndex(Columns.HEIGHT_SCORE)));
                        param.put(Params.OVERALL,c.getString(c.getColumnIndex(Columns.OVERALL)));
                        param.put(Params.OVERALL_SCORE,c.getString(c.getColumnIndex(Columns.OVERALL_SCORE)));
                        param.put(Params.LEAF_SHEATH,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH)));
                        param.put(Params.LEAF_SHEATH_SCORE,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH_SCORE)));
                        param.put(Params.STALK_AMOUNT,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT)));
                        param.put(Params.STALK_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT_SCORE)));
                        param.put(Params.INTERNODE_AMOUNT,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT)));
                        param.put(Params.INTERNODE_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT_SCORE)));
                        param.put(Params.STALK_SIZE_AVERAGE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));
                        param.put(Params.STALK_SIZE_AVERAGE_SCORE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE_SCORE)));
                        param.put(Params.CLUMP_SHAPE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE)));
                        param.put(Params.CLUMP_SHAPE_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE_SCORE)));
                        param.put(Params.CLUMP_CHARACTERISTIC,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC)));
                        param.put(Params.CLUMP_CHARACTERISTIC_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC_SCORE)));
                        param.put(Params.INTERNAL_SYSTOM,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM)));
                        param.put(Params.INTERNAL_SYSTOM_SCORE,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM_SCORE)));



                        param.put(Params.INTERNAL_FIRMNESS,c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS)));
                        param.put(Params.INTERNAL_FIRMNESS_SCORE,c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS_SCORE)));
                        param.put(Params.STUFF,c.getString(c.getColumnIndex(Columns.STUFF)));
                        param.put(Params.STUFF_SCORE,c.getString(c.getColumnIndex(Columns.STUFF_SCORE)));
                        param.put(Params.TOTAL_SCORE,c.getFloat(c.getColumnIndex(Columns.TOTAL_SCORE)));


//
                        Log.d(Params.TOTAL_SCORE,param.get(Params.TOTAL_SCORE).toString());
                        //สถานะการเลือก
                        param.put(Params.SELECT_STATUS, c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)));
                        //TODO Check Why Select
//                        param.put(Params.WHY_SELECT,"อยากได้อ่ะ");
                        param.put(Params.WHY_SELECT, c.getString(c.getColumnIndex(Columns.WHY_SELECT)));




                        aq.sync(getCallback(c.getString(c.getColumnIndex(Columns.CLONECODE))).type(JSONObject.class).transformer(gsonTransformer).url(url).params(param));
                    }
                    if (c.isAfterLast()){
                        Clonename.setText("Finish");
                    }
                }
            }


        }).start();

    }

    public int getStandardAverageHeight(String FamilyCode) {
        int standardAverageHeight = 0;

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

    private void ReCalculateScore(String FamilyCode) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        int AverageStandardHeight = getStandardAverageHeight(FamilyCode);
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

                        String where2 = Columns.CLONECODE+" = ?";
                        String[] selectionArgs = {c.getString(c.getColumnIndex(Columns.CLONECODE))};
                        contentResolver.update(uri,v,where2,selectionArgs);


                    }
                }
            }
        }

    }

    private int UploadState = 0;

    public void UploadStandardSpecieData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                baseApplication = (BaseApplication) getActivity().getApplicationContext();
                aq = new AQuery(getActivity().getApplicationContext());

                //TODO CHANGE to STANDARD DATA URL
                url = getResources().getString(R.string.url_uploadimage);
                String uri = getResources().getString(R.string.URI_MY_STANDARDCLONE);
                String[] projection = null;
                String selection = Columns.CHANGESTATUS+" = 1";
                String[] selectionArgs = null;
                String sortOrder = null;

                Cursor c = getActivity().getContentResolver().query(Uri.parse(uri), projection, selection, selectionArgs, sortOrder);

                progressBar.setMax(c.getCount());



                if (c.getCount() != 0) {

                    while (c.moveToNext()) {

                        //
                        HashMap<String, Object> param = new HashMap<String, Object>();

                        // Edit Here Detail
                        final String CloneCode = c.getString(1);
                        param.put(Params.SpecieName, c.getString(c.getColumnIndex(Columns.SPECIENAME)));
                        param.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                        param.put(Params.USERID, baseApplication.getUserdata().getUserID());
                        param.put(Params.COLLECTINGTIME, c.getString(c.getColumnIndex(Columns.COLLECTINGTIME)));
//
                        //ลำ
                        param.put(Params.STALK_SIZE_1, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_1)));
                        param.put(Params.STALK_SIZE_2, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_2)));
                        param.put(Params.STALK_SIZE_3, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_3)));
                        param.put(Params.STALK_SIZE_4, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_4)));
                        param.put(Params.STALK_SIZE_5, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_5)));
                        param.put(Params.STALK_SIZE_AVERAGE, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));


                        //ความยาวข้อปล้อง
                        param.put(Params.INTERNODE_LENGTH1, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH1)));
                        param.put(Params.INTERNODE_LENGTH2, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH2)));
                        param.put(Params.INTERNODE_LENGTH3, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH3)));
                        param.put(Params.INTERNODE_LENGTH4, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH4)));
                        param.put(Params.INTERNODE_LENGTH5, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH5)));
                        param.put(Params.INTERNODE_LENGTH_AVERAGE, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH_AVERAGE)));


                        //Disease
                        param.put(Params.WHITE_FLY,c.getString(c.getColumnIndex(Columns.WHITE_FLY)));
                        param.put(Params.WHITE_FLY_SCORE,c.getString(c.getColumnIndex(Columns.WHITE_FLY_SCORE)));
                        param.put(Params.BORER,c.getString(c.getColumnIndex(Columns.BORER)));
                        param.put(Params.BORER_SCORE,c.getString(c.getColumnIndex(Columns.BORER_SCORE)));
                        param.put(Params.APHID,c.getString(c.getColumnIndex(Columns.APHID)));
                        param.put(Params.APHID_SCORE,c.getString(c.getColumnIndex(Columns.APHID_SCORE)));
                        param.put(Params.ICERYA_MEAL_BUG,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG)));
                        param.put(Params.ICERYA_MEAL_BUG_SCORE,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG_SCORE)));
                        param.put(Params.SCALE,c.getString(c.getColumnIndex(Columns.SCALE)));
                        param.put(Params.SCALE_SCORE,c.getString(c.getColumnIndex(Columns.SCALE_SCORE)));
                        param.put(Params.POKKAH_BOENG,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG)));
                        param.put(Params.POKKAH_BOENG_SCORE,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG_SCORE)));
                        param.put(Params.YELLOW_SPOT,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT)));
                        param.put(Params.YELLOW_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT_SCORE)));
                        param.put(Params.BROWN_SPOT,c.getString(c.getColumnIndex(Columns.BROWN_SPOT)));
                        param.put(Params.BROWN_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.BROWN_SPOT_SCORE)));
                        param.put(Params.RING_SPOT,c.getString(c.getColumnIndex(Columns.RING_SPOT)));
                        param.put(Params.RING_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.RING_SPOT_SCORE)));
                        param.put(Params.RUST,c.getString(c.getColumnIndex(Columns.RUST)));
                        param.put(Params.RUST_SCORE,c.getString(c.getColumnIndex(Columns.RUST_SCORE)));
                        param.put(Params.DOWNY_MILDEW,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW)));
                        param.put(Params.DOWNY_MILDEW_SCORE,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW_SCORE)));

                        param.put(Params.OTHER_DISEASE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE)));
                        param.put(Params.OTHER_DISEASE_NAME,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)));
                        param.put(Params.OTHER_DISEASE_SCORE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_SCORE)));

                        param.put(Params.FLOWERING,c.getString(c.getColumnIndex(Columns.FLOWERING)));
                        param.put(Params.FLOWERING_SCORE,c.getString(c.getColumnIndex(Columns.FLOWERING_SCORE)));
                        param.put(Params.BRIX,c.getString(c.getColumnIndex(Columns.BRIX)));
                        param.put(Params.BRIX_SCORE,c.getString(c.getColumnIndex(Columns.BRIX_SCORE)));
                        param.put(Params.HEIGHT,c.getString(c.getColumnIndex(Columns.HEIGHT)));
                        param.put(Params.HEIGHT_SCORE,c.getString(c.getColumnIndex(Columns.HEIGHT_SCORE)));
                        param.put(Params.OVERALL,c.getString(c.getColumnIndex(Columns.OVERALL)));
                        param.put(Params.OVERALL_SCORE,c.getString(c.getColumnIndex(Columns.OVERALL_SCORE)));
                        param.put(Params.LEAF_SHEATH,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH)));
                        param.put(Params.LEAF_SHEATH_SCORE,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH_SCORE)));
                        param.put(Params.STALK_AMOUNT,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT)));
                        param.put(Params.STALK_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT_SCORE)));
                        param.put(Params.INTERNODE_AMOUNT,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT)));
                        param.put(Params.INTERNODE_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT_SCORE)));
                        param.put(Params.STALK_SIZE_AVERAGE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));
                        param.put(Params.STALK_SIZE_AVERAGE_SCORE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE_SCORE)));
                        param.put(Params.CLUMP_SHAPE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE)));
                        param.put(Params.CLUMP_SHAPE_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE_SCORE)));
                        param.put(Params.CLUMP_CHARACTERISTIC,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC)));
                        param.put(Params.CLUMP_CHARACTERISTIC_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC_SCORE)));
                        param.put(Params.INTERNAL_SYSTOM,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM)));
                        param.put(Params.INTERNAL_SYSTOM_SCORE,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM_SCORE)));


                        param.put(Params.INTERNAL_FIRMNESS, c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS)));
                        param.put(Params.INTERNAL_FIRMNESS_SCORE, c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS_SCORE)));
                        param.put(Params.STUFF, c.getString(c.getColumnIndex(Columns.STUFF)));
                        param.put(Params.STUFF_SCORE, c.getString(c.getColumnIndex(Columns.STUFF_SCORE)));
                        param.put(Params.TOTAL_SCORE, c.getFloat(c.getColumnIndex(Columns.TOTAL_SCORE)));
//
                        Log.d(Params.TOTAL_SCORE,param.get(Params.TOTAL_SCORE).toString());
                        //สถานะการเลือก
                        param.put(Params.SELECT_STATUS, c.getInt(c.getColumnIndex(Columns.SELECT_STATUS)));
                        //TODO Check Why Select
//                        param.put(Params.WHY_SELECT,"อยากได้อ่ะ");
                        param.put(Params.WHY_SELECT, c.getString(c.getColumnIndex(Columns.WHY_SELECT)));



                        aq.sync(getCallback(CloneCode).type(JSONObject.class).transformer(gsonTransformer).url(url).params(param));
                    }
                }
            }
        }).start();
    }
    public AjaxCallback<JSONObject> getCallback(final String CloneCode) {
        AjaxCallback<JSONObject> callback = new AjaxCallback<JSONObject>() {

            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Clonename.setText("Uploading " + CloneCode);
//                                progressBar.setProgress(current++);
                            }
                        });
                    }
                }).start();
                super.run();
            }

            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);

                System.out.println("Message " + status.getMessage());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
//                                    Clonename.setText("Uploading " + CloneCode);
                                progressBar.setProgress(current++);
                                if (progressBar.getMax() == current) {

                                    Clonename.setText("Finish");

                                }

                            }
                        });
                    }
                }).start();
            }
        };

        return callback;
    }


}
