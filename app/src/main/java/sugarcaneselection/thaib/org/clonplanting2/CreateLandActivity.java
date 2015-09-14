package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.item.LandListItem;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;

import static sugarcaneselection.thaib.org.clonplanting2.R.id.edtRowAmount;
import static sugarcaneselection.thaib.org.clonplanting2.R.id.tvLandNumber;


public class CreateLandActivity extends AppCompatActivity implements OnMapReadyCallback
        , GoogleMap.OnMyLocationButtonClickListener
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private BaseApplication b;

    private int mActivityMode;

    private EditText edtLandName;
    private EditText edtLandAreaSize;
    private EditText edtLandWidth;
    private EditText edtLandLength;
    private EditText edtLandAddress;
    private EditText edtRowAmount;

    private int mCurrentLandNumber;

    private LandListItem m;
    private String mAddress;
    private Double mAreaSize;
    private String mName;
    private Double mLandWidth;
    private Double mLandLength;
    private Double mLatitude;
    private Double mLongitude;
    private String mObjectID;
    private Integer mRowAmount;

    private TextView tvLat, tvLng;

    private GoogleApiClient mGoogleApiClient;

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = (BaseApplication) getApplication();

        /** เช็คโหมดเพื่อดูว่าต้องสร้างใหม่ หรือ อัพเดท **/
        mActivityMode = getIntent().getIntExtra(AppTag.ACTIVITY_MODE, AppTag.MODE_CREATE);

        /** Initialize Widget*/
        m = new LandListItem();
        mCurrentLandNumber = getLastLandNumber();
        setContentView(R.layout.activity_create_land);

        /** Map */
        MapFragment m = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        m.getMapAsync(this);

        edtLandName = (EditText) findViewById(R.id.edtLandName);
        edtLandAreaSize = (EditText) findViewById(R.id.edtLandSize);
        edtLandWidth = (EditText) findViewById(R.id.edtLandWidth);
        edtLandLength = (EditText) findViewById(R.id.edtLandLength);
        edtLandAddress = (EditText) findViewById(R.id.edtLandAddress);
        edtRowAmount = (EditText) findViewById(R.id.edtRowAmount);

        TextView LandNumber = (TextView) findViewById(tvLandNumber);
        LandNumber.setText("แปลงที่ " + mCurrentLandNumber);
        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLng = (TextView) findViewById(R.id.tvLng);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_land, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_save) {
            if (getDataFromWidget()) {
                if (mActivityMode == AppTag.MODE_CREATE) {
                    CreateLand();
                } else {
                    UpdateLand();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateLand() {
        ParseQuery<ParseObject> query = new ParseQuery(ParseClass.Land);
        query.whereEqualTo(Columns.ObjectID, mObjectID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    //Query OK
                    if (list.size() != 0) {
                        onParsePut((ParseObject) list.get(0));
                    } else {
                        //ไม่มีข้อมูล แล้วมันจะเจอได้ไง - -"
                    }
                } else {
                    //TODO การอัพดทมีปัญหา อัพเดทในดาตาเบสก่อน

                }
            }
        });

    }

    //สำหรับ จัดการ ContentValue เพราะใช้ หลายครั้ง
    private ContentValues CreateContentValue(Integer uploadedStatus) {
        ContentValues c = new ContentValues();
        c.put(Columns.LandName, mName);
        c.put(Columns.LandNumber, mCurrentLandNumber);
        c.put(Columns.LandAddress, mAddress);
        c.put(Columns.LandSize, mAreaSize);
        c.put(Columns.LandWidth, mLandWidth);
        c.put(Columns.LandLength, mLandLength);
        c.put(Columns.UploadStatus, uploadedStatus);
        c.put(Columns.Latitude, mLatitude);
        c.put(Columns.Longitude, mLongitude);
        c.put(Columns.RowAmount,mRowAmount);
        return c;
    }

    //ไม่ต้อง สร้างบ่อย
    private void onParsePut(ParseObject object) {
        object.put(Columns.LandName, mName);
        object.put(Columns.LandNumber, mCurrentLandNumber);
        object.put(Columns.LandAddress, mAddress);
        object.put(Columns.LandSize, mAreaSize);
        object.put(Columns.LandWidth, mLandWidth);
        object.put(Columns.LandLength, mLandLength);
        object.put(Columns.Latitude, mLatitude);
        object.put(Columns.Longitude, mLongitude);
        object.put(Columns.RowAmount,mRowAmount);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    updateToDatabase(CreateContentValue(Upload_Status.UPLOADED));
                } else {
                    updateToDatabase(CreateContentValue(Upload_Status.NOT_UPLOADED));
                }
            }
        });
    }

    private void CreateLand() {
        final ParseObject object = new ParseObject(ParseClass.Land);
        object.put(Columns.LandName, mName);
        object.put(Columns.LandNumber, mCurrentLandNumber);
        object.put(Columns.LandAddress, mAddress);
        object.put(Columns.LandSize, mAreaSize);
        object.put(Columns.LandWidth, mLandWidth);
        object.put(Columns.LandLength, mLandLength);
        object.put(Columns.Latitude, mLatitude);
        object.put(Columns.Longitude, mLongitude);
        object.put(Columns.Sector, b.getUserDataitem().getSector());
        object.put(Columns.UserCreate, b.getUserDataitem().getUserID());
        object.put(Columns.RowAmount,mRowAmount);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ContentValues c;
                if (e == null) {
                    //Inset to Database
                    c = CreateContentValue(Upload_Status.UPLOADED);
                    c.put(Columns.ObjectID, object.getObjectId());
                    insertDatabase(c, Uri.parse(getResources().getString(R.string.uri_land)));
                } else {
                    ///UpdateFinish and Fail
                    c = CreateContentValue(Upload_Status.NOT_UPLOADED);
                    insertDatabase(c, Uri.parse(getResources().getString(R.string.uri_land)));
                    return;
                }
            }
        });
    }

    private void insertDatabase(ContentValues v, Uri uri) {
        ContentResolver c = getContentResolver();
        c.insert(uri, v);
        onFinish(true);
    }

    private void updateToDatabase(ContentValues v) {
        ContentResolver c = getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.uri_land));
        String where = Columns.ObjectID + " = ";
        String[] selectionArgs = {mObjectID};
        int update = c.update(uri, v, where, selectionArgs);
        if (update > 0) {
            Toast.makeText(getApplication(), "อัพเดทสำเร็จ", Toast.LENGTH_LONG).show();
            onFinish(true);
        } else {
            Toast.makeText(getApplication(), "อัพเดทล้มเหลว", Toast.LENGTH_LONG).show();
            onFinish(false);
        }

    }

    public int getLastLandNumber() {
        int lastLandNumber = 0;
        Uri uri = Uri.parse(getResources().getString(R.string.uri_land));
        String[] projection = null;
        String sortOrder = Columns.LandNumber + " ASC";
        String selection = Columns.LandNumber + " != 0";
        String[] selectionArgs = null;
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c != null) {
            if (c.moveToLast()) {

                lastLandNumber = c.getInt(c.getColumnIndex(Columns.LandNumber)) + 1;
            } else {
                lastLandNumber = 1;
            }
        } else {
            lastLandNumber = 1;
        }
        c.close();
        return lastLandNumber;
    }

    private boolean getDataFromWidget() {

        EditText[] editTextList = {edtLandName, edtLandAreaSize, edtLandAddress, edtLandLength, edtLandWidth,edtRowAmount};
        for (EditText edit : editTextList) {
            if (TextUtils.isEmpty(edit.getText())) {
                // EditText was empty
                // Do something fancy
                //TODO เปลียนข้อความให้อยู๋ในรูปอื่น
                Toast.makeText(getApplication(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        mName = edtLandName.getText().toString();
        mAreaSize = Double.valueOf("" + edtLandAreaSize.getText().toString());

        mLandWidth = Double.valueOf("" + edtLandWidth.getText().toString());
        mLandLength = Double.valueOf("" + edtLandLength.getText().toString());
        mAddress = edtLandAddress.getText().toString();
        mRowAmount = Integer.valueOf(edtRowAmount.getText().toString());

        m.setLandNumber(mCurrentLandNumber);
        m.setLandName(mName);
        m.setRowAmount(mRowAmount);
        m.setLandAddress(mAddress);
        m.setLandSize(mAreaSize);
        m.setLandWidth(mLandWidth);
        m.setLandLength(mLandLength);
        m.setLatitude(mLatitude);
        m.setLongitude(mLongitude);

        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    private void onFinish(boolean isFinish) {
        getIntent().putExtra("isFinish", isFinish);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mGoogleApiClient.isConnected()) {
            String msg = "Location = "
                    + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                mLatitude = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude();
                mLongitude = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude();

                tvLat.setText("" + mLatitude);
                tvLng.setText("" + mLongitude);
            } else {
                Toast.makeText(CreateLandActivity.this, "กรุณาตรวจสอบการเชื่อมต่อ สัญญาณ GPS", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CreateLandActivity.this, "กรุณาตรวจสอบการเชื่อมต่อ สัญญาณ GPS", Toast.LENGTH_SHORT).show();
        }


        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, REQUEST, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
