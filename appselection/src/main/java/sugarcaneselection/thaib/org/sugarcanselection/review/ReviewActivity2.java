package sugarcaneselection.thaib.org.sugarcanselection.review;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.BaseApplication;
import sugarcaneselection.thaib.org.sugarcanselection.GsonTransformer;
import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.Params;
import sugarcaneselection.thaib.org.sugarcanselection.R;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;

public class ReviewActivity2 extends ActionBarActivity {

    private static final String ARG_CLONECODE = "CloneCode";

    private String CloneCode;

    private Button btOpenImageView;
    private Button btOpenDetail;

    private RadioGroup SelectStatusgroup;
    private RadioButton rbSaved;
    private RadioButton rbSelected;
    private RadioButton rbRejected;

    private BaseApplication baseApplication;

    private CloneDataItem cd;

    private TextView tvCloneCode,tvHeight,tvTotalScore,tvBrix,tvStalkPerClump,tvStuff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review2);
        baseApplication = (BaseApplication) getApplication();

        cd = new CloneDataItem();
        final Intent intent = getIntent();

        CloneCode = intent.getStringExtra(ARG_CLONECODE);

        tvCloneCode = (TextView) findViewById(R.id.tvCloneCode);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalScore);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvBrix = (TextView) findViewById(R.id.tvBrix);
        tvStalkPerClump = (TextView) findViewById(R.id.tvStalkPerClump);
        tvStuff = (TextView) findViewById(R.id.tvStuff);


        btOpenImageView = (Button) findViewById(R.id.btOpenImageView);
        btOpenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ReviewActivity2.this, ImageFullScreenActivity.class);
                intent1.putExtra("CloneCode", CloneCode);
                startActivity(intent1);
            }
        });

        btOpenDetail = (Button) findViewById(R.id.btOpenDetail);
        btOpenDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SelectStatusgroup = (RadioGroup) findViewById(R.id.SelectStatusGroup);
        rbSaved = (RadioButton) SelectStatusgroup.findViewById(R.id.rbSaved);
        rbSelected = (RadioButton) SelectStatusgroup.findViewById(R.id.rbSelected);
        rbRejected = (RadioButton) SelectStatusgroup.findViewById(R.id.rbRejected);

        getCloneDataItem();

        final int selectstatus = Integer.parseInt(cd.getSelectStatus());
        switch (selectstatus) {
            case SelectStatus.SAVED:
                rbSaved.setChecked(true);
                break;
            case SelectStatus.SELECTED:
                rbSelected.setChecked(true);
                break;
            case SelectStatus.REJECTED:
                rbRejected.setChecked(true);
                break;
        }
        SelectStatusgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbSaved:
                        cd.setSelectStatus(""+SelectStatus.SAVED);
                        break;
                    case R.id.rbSelected:
                        cd.setSelectStatus(""+SelectStatus.SELECTED);
                        break;
                    case R.id.rbRejected:
                        cd.setSelectStatus(""+SelectStatus.REJECTED);
                        break;

                }
                ContentResolver d = getContentResolver();
                Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
                ContentValues c = new ContentValues();
                c.put(Columns.SELECT_STATUS,cd.getSelectStatus());

                String where = Columns.CLONECODE+" = ?";

                String[] selectionArgs = {CloneCode};

                int a = d.update(uri,c,where,selectionArgs);
                if (a != 0){
                    AQuery aq = new AQuery(ReviewActivity2.this);

                    String url = getResources().getString(R.string.url_uploadimage);

                    HashMap<String,Object> param = new HashMap<>();
                    param.put(Params.CLONECODE, CloneCode);
                    param.put(Params.PLACE_TEST, baseApplication.getUserdata().getSector());
                    param.put(Params.USERID, baseApplication.getUserdata().getUserID());
                    param.put(Params.SELECT_STATUS,cd.getSelectStatus());

                    ProgressDialog f = new ProgressDialog(ReviewActivity2.this);
                    f.setMessage("Syncing");

                    aq.transformer(new GsonTransformer()).progress(f).ajax(url, param, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            super.callback(url, object, status);
                            if (status.getCode() != AjaxStatus.NETWORK_ERROR && status.getCode() != AjaxStatus.TRANSFORM_ERROR) {
                                Log.d("Check Callback", object.toString());
                            }
                        }
                    });

                }
            }
        });

    }

    private void getCloneDataItem() {
        Uri uri = Uri.parse(getResources().getString(R.string.URI_Distinc));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.CLONECODE + " = ?";
        String[] selectionArgs = {CloneCode};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            Log.d("Clone Data", c.toString());
            cd.setSelectStatus(c.getString(c.getColumnIndex(Columns.SELECT_STATUS)));
            tvCloneCode.setText(CloneCode + "");
            tvTotalScore.setText(c.getFloat(c.getColumnIndex(Columns.TOTAL_SCORE))+" คะแนน");
            tvHeight.setText(c.getInt(c.getColumnIndex(Columns.HEIGHT))+" cm");
            tvBrix.setText(c.getFloat(c.getColumnIndex(Columns.BRIX))+"");
            tvStalkPerClump.setText(c.getInt(c.getColumnIndex(Columns.STALK_AMOUNT))+"");
            tvStuff.setText(c.getString(c.getColumnIndex(Columns.STUFF)));
        }

        c.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review_activity2, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        return super.onOptionsItemSelected(item);
        return false;
    }
}
