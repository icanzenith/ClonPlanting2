package sugarcaneselection.thaib.org.clonplanting2.scanner;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.CollationElementIterator;
import java.util.List;
import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

/**
 * Created by Jitpakorn on 5/3/15 AD.
 */
public class PlantingCloneScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {

    private String TAG_DEBUG = "PlantingCloneScanner";

    private Button btOk;
    private String mCloneCode;
    private Integer mCurrentRow;
    private ZXingScannerView scannerView;
    private String mLandID;
    private String mObjectID;
    private String mReceiveCloneID;
    private TextView tvCloneCode;
    private TextView tvRowNumber;
    private BaseApplication b;
    private ImageButton camSwitch;
    private int currentCamera;
    private String mRep;


    private EditText edtPlantAmount;
    private Integer mPlantedAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = (BaseApplication) getApplication();
        setContentView(R.layout.activity_planting_clone_scanner);

        //Initialize mCurrentRow = Last Row

        mLandID = getIntent().getStringExtra(AppTag.LAND_ID);
        mRep = getIntent().getStringExtra(AppTag.REPLICATION);

        Log.d("Debug", mRep + " " + mLandID);

        checkCurrentRow();
        scannerView = (ZXingScannerView) findViewById(R.id.camera_preview);

        btOk = (Button) findViewById(R.id.btOK);
        tvCloneCode = (TextView) findViewById(R.id.tvCloneCode);
        tvRowNumber = (TextView) findViewById(R.id.tvRowNumber);

        //
        edtPlantAmount = (EditText) findViewById(R.id.edtAmount);
        //set Scanner
        tvRowNumber.setText("แถวที่ " + mCurrentRow);
        scannerView.setResultHandler(this);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCloneToPlanted();
            }
        });

        camSwitch = (ImageButton) findViewById(R.id.btSwitch);
        camSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannerView.stopCamera();
                if (currentCamera == 0) {
                    scannerView.startCamera(1);
                    currentCamera = 1;
                } else {
                    scannerView.startCamera(0);
                    currentCamera = 0;
                }


            }
        });
    }

    private void AddCloneToPlanted() {
        //TODO สมมติ ว่ามาจาก การหา ใน Database แล้วไม่พบว่าปลูกในแปลงนี้ แล้ว หาโดยใช้ LandID และ CloneCode หา และ รับแล้ว ด้วย(ต้องหารับแล้วก่อน)
        mReceiveCloneID = getReceiveCloneID(mCloneCode);
        if (edtPlantAmount.getText() != null) {
            mPlantedAmount = Integer.valueOf(edtPlantAmount.getText().toString());
        } else {
            mPlantedAmount = 0;
        }

        Log.d("DebugPlanted", "Add to Clone");

        if (!checkClonePlanted(mCloneCode)) {
            if (mObjectID != null) {
                Log.d("DebugPlanted", "ObjectID !=null");
                ParseQuery<ParseObject> query = new ParseQuery<>(ParseClass.Planting);
                query.whereEqualTo("objectId", mObjectID);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        ParseObject object = list.get(0);
                        object.put(Columns.CloneCode, mCloneCode);
                        object.put(Columns.LandID, mLandID);
                        object.put(Columns.ReceiveCloneID, mReceiveCloneID);
                        object.put(Columns.RowNumber, mCurrentRow);
                        object.put(Columns.SentTo, b.getUserDataitem().getSector());
                        object.put(Columns.Replication, mRep);
                        object.put(Columns.PlantedAmount, mPlantedAmount);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d(TAG_DEBUG, "อัพเดทสำเร็จ mObjectID != null");
                                    UpdateOnDatabase(Upload_Status.UPLOADED, true);
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Log.d(TAG_DEBUG, e.getMessage());
                                    Log.d(TAG_DEBUG, "อัพเดทสำเร็จ mObjectID == null");
                                    UpdateOnDatabase(Upload_Status.NOT_UPLOADED, true);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
                    }
                });
            } else {
                Log.d(TAG_DEBUG, "ObjectID == null");
                ParseObject object = new ParseObject(ParseClass.Planting);
                object.put(Columns.CloneCode, mCloneCode);
                object.put(Columns.LandID, mLandID);
                object.put(Columns.ReceiveCloneID, mReceiveCloneID);
                object.put(Columns.RowNumber, mCurrentRow);
                object.put(Columns.SentTo, b.getUserDataitem().getSector());
                object.put(Columns.Replication, mRep);
                object.put(Columns.PlantedAmount, mPlantedAmount);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(TAG_DEBUG, "e == null");
                            UpdateOnDatabase(Upload_Status.UPLOADED, true);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Log.d(TAG_DEBUG, "e!=null");
                            Log.d(TAG_DEBUG, e.getMessage());
                            UpdateOnDatabase(Upload_Status.NOT_UPLOADED, true);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
            }

        }


    }

    private String getReceiveCloneID(String CloneCode) {

        String id = null;
        Uri uri = Uri.parse(getResources().getString(R.string.uri_clone));
        String[] projection = null;
        String sortOrder = null;
        String selection = Columns.CloneCode + " = ? AND " + Columns.ReceiveAmount + " != 0";
        String[] selectionArgs = {CloneCode};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c.moveToFirst()) {
            id = c.getString(c.getColumnIndex(Columns.ObjectID));
        }
        c.close();
        return id;
    }

    /**
     * หาจาก หมายเลขแปลง และ มีจำนวนต้นที่ปลูกแล้ว (ก็แสดงว่าปลูกแล้วไง)
     * โดยเรียงตามลำดับเลขที่ปลูก แล้ว
     */

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    private void checkCurrentRow() {
        Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
        String[] projection = null;
        String sortOrder = Columns.RowNumber + " ASC";
        String selection = Columns.LandID + " = ? AND " + Columns.RowNumber + " != 0 AND " + Columns.Replication + " = ?";
        String[] selectionArgs = {mLandID, mRep};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c.getCount() == 0) {
            mCurrentRow = 1;
        } else {
            if (c.moveToLast()) {
                mCurrentRow = c.getInt(c.getColumnIndex(Columns.RowNumber)) + 1;
            }
        }
        c.close();
    }

    /**
     * เช็คว่าปลูกหรือยัง
     */

    //เช็คว่าปลูก หรือ ยังห
    private boolean checkClonePlanted(String mCloneCode) {
        boolean isPlanted = true;
        if (isCheckSpecie(mCloneCode)) {
            Toast.makeText(getApplication(), "พันธุ์นี้เป็นพันธุ์เช็ค", Toast.LENGTH_LONG).show();
            isPlanted = false;
        } else {
            Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
            String[] projection = null;
            String sortOrder = null;
            String selection = Columns.CloneCode + "= ? AND " + Columns.LandID + " = ? AND " + Columns.Replication + " = ?";
            String[] selectionArgs = {mCloneCode, mLandID, mRep};
            ContentResolver r = getContentResolver();
            Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
            if (c.getCount() != 0) {
                if (c.moveToFirst()) {
                    if (c.getInt(c.getColumnIndex(Columns.RowNumber)) == 0) {
                        mObjectID = c.getString(c.getColumnIndex(Columns.ObjectID));
                        isPlanted = false;
                    } else {
                        Toast.makeText(PlantingCloneScannerActivity.this, "ปลูกพันธุ์นี้ในแปลงนี้แล้ว ในแถวที่ " + c.getInt(c.getColumnIndex(Columns.RowNumber)), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (getReceiveCloneID(mCloneCode) == null) {
                        Toast.makeText(getApplication(), "ต้องตรวจรับก่อน", Toast.LENGTH_LONG).show();
                    } else {
                        isPlanted = false;
                    }
                }
            } else {
                if (getReceiveCloneID(mCloneCode) == null) {
                    Toast.makeText(getApplication(), "ต้องตรวจรับก่อน", Toast.LENGTH_LONG).show();
                } else {
                    isPlanted = false;
                }
            }
            c.close();
            return isPlanted;
        }
        return isPlanted;
    }

    //เช็คว่าเป็นพันธุ์เช็ค
    private boolean isCheckSpecie(String mCloneCode) {
        String list[] = getResources().getStringArray(R.array.check_list);
        for (String name : list) {
            if (name.equals(mCloneCode)) {
                return true;
            }
        }
        return false;
    }


    private void UpdateOnDatabase(int UploadStatus, boolean isFinish) {

        Uri uri = Uri.parse(getResources().getString(R.string.uri_planting_clone));
        ContentResolver resolver = getContentResolver();
        String where;
        String[] selectionArgs;
        if (mObjectID != null) {
            where = Columns.ObjectID + " = ? AND " + Columns.Replication + " = ?";
            selectionArgs = new String[]{mObjectID, mRep};
        } else {
            where = Columns.CloneCode + " = ? AND " + Columns.Replication + " = ?";
            selectionArgs = new String[]{mCloneCode, mRep};
        }

        ContentValues v = new ContentValues();
        v.put(Columns.CloneCode, mCloneCode);
        v.put(Columns.LandID, mLandID);
        v.put(Columns.ReceiveCloneID, mReceiveCloneID);
        v.put(Columns.RowNumber, mCurrentRow);
        v.put(Columns.UploadStatus, UploadStatus);
        v.put(Columns.Replication, mRep);
        v.put(Columns.PlantedAmount, mPlantedAmount);
        Log.d("String", v.toString());
        int update = resolver.update(uri, v, where, selectionArgs);
        if (update == 0) {
            resolver.insert(uri, v);
        }
        if (isFinish) {

        } else {

        }

    }

    @Override
    public void handleResult(Result result) {
        mCloneCode = result.getText().toString();
        tvCloneCode.setText(mCloneCode);
    }
}
