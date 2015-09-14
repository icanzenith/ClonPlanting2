package sugarcaneselection.thaib.org.sugarcanselection.Dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

/**
 * Created by Jitpakorn on 2/2/15 AD.
 */
public class UploadCheckSpecie extends DialogFragment {

    private TextView tvMessage;
    private ProgressBar progressBar;
    private BaseApplication b;
    private AQuery aq;
    private String url;
    private GsonTransformer gsonTransformer = new GsonTransformer();

    public UploadCheckSpecie() {
    }

    public UploadCheckSpecie newInstance(Context context){
        UploadCheckSpecie d = new UploadCheckSpecie();
        Bundle b = new Bundle();
        d.setStyle(android.app.DialogFragment.STYLE_NO_TITLE, 0);
        d.setArguments(b);
        return d;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        b = (BaseApplication) getActivity().getApplication();
        View v  = inflater.inflate(R.layout.dialog_upload,null);
        tvMessage = (TextView) v.findViewById(R.id.clone_name);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);


//        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_FAMILY));
//        String[] projection = null;
//        String sortOrder = null;
//        String selection = null;
//        String[] selectionArgs = null;
//        ContentResolver r = getActivity().getContentResolver();
//        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
//        while (c != null && c.moveToNext()) {
//            String FamilyCode = c.getString(c.getColumnIndex(Columns.FAMILYCODE));
//
//        }
//
//        c.close();
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        url = getResources().getString(R.string.url_uploadcheckdata);

        Log.d("Debug get Url",url);
        aq = new AQuery(b);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_STANDARDCLONE));
                String[] projection = null;
                String sortOrder = null;
                String selection = Columns.CHANGESTATUS +"= 1";
                String[] selectionArgs = null;

                ContentResolver r = getActivity().getContentResolver();

                Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
//                Log.d("Debug get Cusor Count",c.getCount()+" ");
                if (c.getCount()!=0){
                    while (c.moveToNext()){
                        HashMap<String,Object> p = new HashMap<>();

                        p.put(Params.CLONECODE,c.getString(c.getColumnIndex(Columns.SPECIENAME)));
                        p.put(Params.FAMILYCODE,c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                        p.put(Params.ROWNUMBER,c.getInt(c.getColumnIndex(Columns.PLANTORDER)));
                        p.put(Params.PLACE_TEST, b.getUserdata().getSector());
                        p.put(Params.USERID, b.getUserdata().getUserID());

                        p.put(Params.COLLECTINGTIME, c.getString(c.getColumnIndex(Columns.COLLECTINGTIME)));
////
                        //ลำ
                        p.put(Params.STALK_SIZE_1, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_1)));
                        p.put(Params.STALK_SIZE_2, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_2)));
                        p.put(Params.STALK_SIZE_3, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_3)));
                        p.put(Params.STALK_SIZE_4, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_4)));
                        p.put(Params.STALK_SIZE_5, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_5)));
                        p.put(Params.STALK_SIZE_AVERAGE, c.getFloat(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));

                        //ความยาวข้อปล้อง
                        p.put(Params.INTERNODE_LENGTH1, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH1)));
                        p.put(Params.INTERNODE_LENGTH2, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH2)));
                        p.put(Params.INTERNODE_LENGTH3, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH3)));
                        p.put(Params.INTERNODE_LENGTH4, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH4)));
                        p.put(Params.INTERNODE_LENGTH5, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH5)));
                        p.put(Params.INTERNODE_LENGTH_AVERAGE, c.getFloat(c.getColumnIndex(Columns.INTERNODE_LENGTH_AVERAGE)));


//                        //Disease
                        p.put(Params.WHITE_FLY,c.getString(c.getColumnIndex(Columns.WHITE_FLY)));
                        p.put(Params.WHITE_FLY_SCORE,c.getString(c.getColumnIndex(Columns.WHITE_FLY_SCORE)));
                        p.put(Params.BORER,c.getString(c.getColumnIndex(Columns.BORER)));
                        p.put(Params.BORER_SCORE,c.getString(c.getColumnIndex(Columns.BORER_SCORE)));
                        p.put(Params.APHID,c.getString(c.getColumnIndex(Columns.APHID)));
                        p.put(Params.APHID_SCORE,c.getString(c.getColumnIndex(Columns.APHID_SCORE)));
                        p.put(Params.ICERYA_MEAL_BUG,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG)));
                        p.put(Params.ICERYA_MEAL_BUG_SCORE,c.getString(c.getColumnIndex(Columns.ICERYA_MEAL_BUG_SCORE)));
                        p.put(Params.SCALE,c.getString(c.getColumnIndex(Columns.SCALE)));
                        p.put(Params.SCALE_SCORE,c.getString(c.getColumnIndex(Columns.SCALE_SCORE)));
                        p.put(Params.POKKAH_BOENG,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG)));
                        p.put(Params.POKKAH_BOENG_SCORE,c.getString(c.getColumnIndex(Columns.POKKAH_BOENG_SCORE)));
                        p.put(Params.YELLOW_SPOT,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT)));
                        p.put(Params.YELLOW_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.YELLOW_SPOT_SCORE)));
                        p.put(Params.BROWN_SPOT,c.getString(c.getColumnIndex(Columns.BROWN_SPOT)));
                        p.put(Params.BROWN_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.BROWN_SPOT_SCORE)));
                        p.put(Params.RING_SPOT,c.getString(c.getColumnIndex(Columns.RING_SPOT)));
                        p.put(Params.RING_SPOT_SCORE,c.getString(c.getColumnIndex(Columns.RING_SPOT_SCORE)));
                        p.put(Params.RUST,c.getString(c.getColumnIndex(Columns.RUST)));
                        p.put(Params.RUST_SCORE,c.getString(c.getColumnIndex(Columns.RUST_SCORE)));
                        p.put(Params.DOWNY_MILDEW,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW)));
                        p.put(Params.DOWNY_MILDEW_SCORE,c.getString(c.getColumnIndex(Columns.DOWNY_MILDEW_SCORE)));

                        p.put(Params.OTHER_DISEASE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE)));
                        p.put(Params.OTHER_DISEASE_NAME,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_NAME)));
                        p.put(Params.OTHER_DISEASE_SCORE,c.getString(c.getColumnIndex(Columns.OTHER_DISEASE_SCORE)));

                        p.put(Params.FLOWERING,c.getString(c.getColumnIndex(Columns.FLOWERING)));
                        p.put(Params.FLOWERING_SCORE,c.getString(c.getColumnIndex(Columns.FLOWERING_SCORE)));
                        p.put(Params.BRIX,c.getString(c.getColumnIndex(Columns.BRIX)));
                        p.put(Params.BRIX_SCORE,c.getString(c.getColumnIndex(Columns.BRIX_SCORE)));
                        p.put(Params.HEIGHT,c.getString(c.getColumnIndex(Columns.HEIGHT)));
                        p.put(Params.HEIGHT_SCORE,c.getString(c.getColumnIndex(Columns.HEIGHT_SCORE)));
                        p.put(Params.OVERALL,c.getString(c.getColumnIndex(Columns.OVERALL)));
                        p.put(Params.OVERALL_SCORE,c.getString(c.getColumnIndex(Columns.OVERALL_SCORE)));
                        p.put(Params.LEAF_SHEATH,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH)));
                        p.put(Params.LEAF_SHEATH_SCORE,c.getString(c.getColumnIndex(Columns.LEAF_SHEATH_SCORE)));
                        p.put(Params.STALK_AMOUNT,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT)));
                        p.put(Params.STALK_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.STALK_AMOUNT_SCORE)));
                        p.put(Params.INTERNODE_AMOUNT,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT)));
                        p.put(Params.INTERNODE_AMOUNT_SCORE,c.getString(c.getColumnIndex(Columns.INTERNODE_AMOUNT_SCORE)));
                        p.put(Params.STALK_SIZE_AVERAGE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE)));
                        p.put(Params.STALK_SIZE_AVERAGE_SCORE,c.getString(c.getColumnIndex(Columns.STALK_SIZE_AVERAGE_SCORE)));
                        p.put(Params.CLUMP_SHAPE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE)));
                        p.put(Params.CLUMP_SHAPE_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_SHAPE_SCORE)));
                        p.put(Params.CLUMP_CHARACTERISTIC,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC)));
                        p.put(Params.CLUMP_CHARACTERISTIC_SCORE,c.getString(c.getColumnIndex(Columns.CLUMP_CHARACTERISTIC_SCORE)));
                        p.put(Params.INTERNAL_SYSTOM,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM)));
                        p.put(Params.INTERNAL_SYSTOM_SCORE,c.getString(c.getColumnIndex(Columns.INTERNAL_SYSTOM_SCORE)));



                        p.put(Params.INTERNAL_FIRMNESS,c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS)));
                        p.put(Params.INTERNAL_FIRMNESS_SCORE,c.getString(c.getColumnIndex(Columns.INTERNAL_FIRMNESS_SCORE)));
                        p.put(Params.STUFF,c.getString(c.getColumnIndex(Columns.STUFF)));
                        p.put(Params.STUFF_SCORE,c.getString(c.getColumnIndex(Columns.STUFF_SCORE)));


                        Log.d("Debug",p.toString());
                        aq.sync(getCallback(c.getPosition(),c.getCount()).type(JSONObject.class).transformer(gsonTransformer).url(url.trim()).params(p));
                    }
                }



                c.close();
            }
        }).start();
    }

    private void onFinish(){
     new Thread(new Runnable() {
         @Override
         public void run() {
             getActivity().runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                                   tvMessage.setText("Finish");
                                    getDialog().setCancelable(true);

                 }
             });
         }
     }).start();
    }

    public AjaxCallback<JSONObject> getCallback(final int Check, final int all){
        AjaxCallback<JSONObject> callback = new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);

                    Log.d("Get Callback Message",status.getMessage());
                    Log.d("Get Callback Message",object.toString());


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText((Check+1)+" อัพโหลดแล้ว");
                                if (all==(Check+1)){
                                    onFinish();
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
