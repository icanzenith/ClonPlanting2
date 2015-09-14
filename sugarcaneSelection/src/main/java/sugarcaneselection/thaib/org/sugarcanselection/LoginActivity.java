package sugarcaneselection.thaib.org.sugarcanselection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneListData;
import sugarcaneselection.thaib.org.sugarcanselection.Item.SelectStatus;
import sugarcaneselection.thaib.org.sugarcanselection.Item.UserCloneItem;
import sugarcaneselection.thaib.org.sugarcanselection.Item.WeightValue;
import sugarcaneselection.thaib.org.sugarcanselection.Util.ReCalScoreData;
import sugarcaneselection.thaib.org.sugarcanselection.Util.ReCalculateScore;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.database.SessionManager;


public class LoginActivity extends Activity {
    public static final String TAG_DEBUG = "My Activity Debug";
    LinearLayout container;
    EditText edt_username;
    EditText edt_password;
    Button bt_Login;
    ProgressDialog p;
    AQuery aq;
    GsonTransformer gson = new GsonTransformer();
    String url;
    HashMap<String, Object> object;
    SessionManager sessionManager;
    UserDataItem ud;
    ContentResolver c;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        p = new ProgressDialog(LoginActivity.this, 0);
        p.setCancelable(false);
        p.setMessage("Syncing");


        if (getSharedPreferences(SessionManager.PREF_NAME, 0) != null) {
            sessionManager = new SessionManager(getApplication(), LoginActivity.this);
            sessionManager.checkLogin();

        } else {
            sessionManager = new SessionManager(getApplication(), LoginActivity.this);


        }
        container = (LinearLayout) findViewById(R.id.login_form_container);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        url = getResources().getString(R.string.url_login);
        object = new HashMap<String, Object>();
        c = getContentResolver();
    }

    public void onClickLogin(View v) {

        aq = new AQuery(LoginActivity.this);
        String username;
        String password;
        username = edt_username.getText().toString();
        password = edt_password.getText().toString();


        if (!username.equals("") && !password.equals("")) {
            Login(username, password);
            container.setVisibility(View.INVISIBLE);

        }


    }

    public void Login(String username, String password) {
        ud = new UserDataItem();
        object.put("Username", username.trim());
        object.put("Password", password.trim());
//        Log.d("Check Param User Password",object.toString());
        p.setMessage("กำลังล๊อคอิน");
        p.show();
        aq.transformer(gson)
                .ajax(url,
                        object,
                        LoginCallbackJson.class,
                        new AjaxCallback<LoginCallbackJson>() {
                            @Override
                            public void callback(String url, LoginCallbackJson object, AjaxStatus status) {
                                super.callback(url, object, status);
                                Log.d("Network Status", status.getMessage());

                                if (object != null) {
                                    if (object.getTempva().get(0).getUserID() != 0){
                                        ud.setUserID(object.getTempva().get(0).getUserID());
                                        ud.setUsername(object.getTempva().get(0).getUsername());
                                        ud.setAuthority(object.getTempva().get(0).getAuthority());
                                        ud.setSector(object.getTempva().get(0).getSector());
                                        LoadPersonaldata(object.getTempva().get(0).getUserID());
                                    }else{
                                        Toast.makeText(LoginActivity.this,"Username หรือ Password ไม่ถูกต้อง",Toast.LENGTH_LONG).show();
                                        container.setVisibility(View.VISIBLE);
                                        p.dismiss();
                                    }

                                }
                            }
                        });
    }

    public void LoadPersonaldata(final int userID) {
        String url_userdata = getResources().getString(R.string.url_personaldata);
        HashMap<String, Integer> object = new HashMap<String, Integer>();

        object.put("UserID", userID);
        p.setMessage("กำลังดาว์นโหลดข้อมูลส่วนตัว");
        aq.transformer(gson)
                .ajax(url_userdata,
                        object,
                        PersonalDataCallbackJson.class,
                        new AjaxCallback<PersonalDataCallbackJson>() {
                            @Override
                            public void callback(String url, PersonalDataCallbackJson object, AjaxStatus status) {
                                super.callback(url, object, status);
                                Log.d("Net Work Status", status.getMessage());
//                                Toast.makeText(LoginActivity.this, status.getMessage() + " Load personal", Toast.LENGTH_LONG).show();
                                if (object != null) {
                                    Log.d("Debug ","Object != null");
                                    ud.setPicUrl(object.getUserdata().get(0).getPicURL());
                                    ud.setUUID(object.getUserdata().get(0).getUUID());
                                    ud.setName(object.getUserdata().get(0).getName());
                                    ud.setWorkPlace(object.getUserdata().get(0).getWorkPlace());

                                    ud.setTel(object.getUserdata().get(0).getTel());
//                                    Toast.makeText(getApplicationContext(),object.getUserdata().get(0).getTel(),Toast.LENGTH_LONG).show();
                                    ud.setEmail(object.getUserdata().get(0).getEmail());
                                    ud.setLineID(object.getUserdata().get(0).getLineID());
                                    ud.setFacebookID(object.getUserdata().get(0).getFacebookID());

                                    DownLoadCheckSpecies(ud.getUserID(),ud.getSector());


                                }else{
                                    Log.d("Debug ","Object != null");
                                }
                            }
                        });
    }

    public void OnLoadUserCloneData(int userid, String placetest) {
        String url_userclone = getResources().getString(R.string.url_dowloadlistdata);
        HashMap<String, Object> params = new HashMap<String, Object>();
        final String uri_myclone = getResources().getString(R.string.URI_MYCLONE);
        params.put("UserID", userid);
        params.put("ListType", "PlaceTest");
        params.put("PlaceTest", placetest);
//        p.setMessage("กำลังดาว์นโหลดข้อมูลโคลน");
        Log.d("debug",params.toString());

//        Log.d("CheckParam",params.toString()+"");

//        Toast.makeText(LoginActivity.this, params.toString(), Toast.LENGTH_LONG).show();

        aq.transformer(gson).progress(R.id.progressbar).ajax(url_userclone, params,UserCloneItem.class, new AjaxCallback<UserCloneItem>() {
            @Override
            public void callback(String url, final UserCloneItem object, AjaxStatus status) {
                super.callback(url, object, status);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Log.d("Debug",status.getMessage());
                if (object != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (object != null) {
                                ArrayList<CloneListData> s = object.getClonelistdata();
                                for (int i = 0; i < s.size(); i++) {
                                    count = i;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            p.show();
                                            p.setMessage("กำลังดาว์นโหลดข้อมูลโคลน"
                                                    + '\n' + count + "/" + object.getClonelistdata().size());
                                        }
                                    });
//
                                    ContentValues v = new ContentValues();
                                    v.put(Columns.CLONECODE, s.get(i).getCloneCode());
                                    v.put(Columns.PLACETEST, s.get(i).getPlaceTest());
                                    v.put(Columns.FAMILYCODE, getFamilyName(s.get(i).getCloneCode()));
                                    v.put(Columns.ROWNUMBER, s.get(i).getRowNumber());
                                    v.put(Columns.PLANTORDER,s.get(i).getCloneCode().split("-")[1]);

                                    v.put(Columns.UPPERPICURL, s.get(i).getUpperPicURL());
                                    v.put(Columns.UPPERUUID, s.get(i).getUpperUUID());

                                    v.put(Columns.LOWERPICURL, s.get(i).getLowerPicURL());
                                    v.put(Columns.LOWERUUDI, s.get(i).getLowerUUID());

                                    v.put(Columns.FULLPICURL, s.get(i).getFullPicURL());
                                    v.put(Columns.FULLPICUUID, s.get(i).getFullUUID());

                                    v.put(Columns.COLLECTINGTIME, s.get(i).getCollectingTime());

                                    v.put(Columns.STALK_AMOUNT, s.get(i).getStalkAmount());
                                    v.put(Columns.STALK_AMOUNT_SCORE, s.get(i).getStalkAmountScore());

                                    v.put(Columns.CLUMP_SHAPE, s.get(i).getClumpShape());
                                    v.put(Columns.CLUMP_SHAPE_SCORE, s.get(i).getClumpShapeScore());

                                    //Clump Charecteristic
                                    if (s.get(i).getClumpCharacteristic()!=null){
                                        if (s.get(i).getClumpCharacteristic().contains("ไม่ล้ม")){
                                            v.put(Columns.CLUMP_CHARACTERISTIC,"ไม่ล้มตั้งตรง");
                                        }else{
                                            v.put(Columns.CLUMP_CHARACTERISTIC, s.get(i).getClumpCharacteristic());
                                        }

                                        if (s.get(i).getClumpCharacteristicScore()!= 0){
                                            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, s.get(i).getClumpCharacteristicScore());
                                        }else{
                                            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, ReCalculateScore.ClumpCharecteristic(s.get(i).getClumpCharacteristic()));
                                        }
                                    }else{
                                        v.put(Columns.CLUMP_CHARACTERISTIC, s.get(i).getClumpCharacteristic());
                                        v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, s.get(i).getClumpCharacteristicScore());
                                    }




                                    v.put(Columns.INTERNAL_SYSTOM, s.get(i).getInternalSymtom());
                                    v.put(Columns.INTERNAL_SYSTOM_SCORE, s.get(i).getInternalSymtomScore());

                                    v.put(Columns.INTERNAL_FIRMNESS, s.get(i).getInternalFirmness());
                                    v.put(Columns.INTERNAL_FIRMNESS_SCORE, s.get(i).getInternalFirmnessScore());

                                    v.put(Columns.STALK_SIZE_1, s.get(i).getStalkSize1());
                                    v.put(Columns.STALK_SIZE_2, s.get(i).getStalkSize2());
                                    v.put(Columns.STALK_SIZE_3, s.get(i).getStalkSize3());
                                    v.put(Columns.STALK_SIZE_4, s.get(i).getStalkSize4());
                                    v.put(Columns.STALK_SIZE_5, s.get(i).getStalkSize5());

                                    v.put(Columns.STALK_SIZE_AVERAGE, s.get(i).getStalkSizeAverage());
                                    v.put(Columns.STALK_SIZE_AVERAGE_SCORE, s.get(i).getStalkSizeAverageScore());

                                    v.put(Columns.INTERNODE_AMOUNT, s.get(i).getInternodeAmount());
                                    v.put(Columns.INTERNODE_AMOUNT_SCORE, s.get(i).getInternodeAmountScore());

                                    v.put(Columns.INTERNODE_LENGTH1, s.get(i).getInternodeLength1());
                                    v.put(Columns.INTERNODE_LENGTH2, s.get(i).getInternodeLength2());
                                    v.put(Columns.INTERNODE_LENGTH3, s.get(i).getInternodeLength3());
                                    v.put(Columns.INTERNODE_LENGTH4, s.get(i).getInternodeLength4());
                                    v.put(Columns.INTERNODE_LENGTH5, s.get(i).getInternodeLength5());
                                    v.put(Columns.INTERNODE_LENGTH_AVERAGE, s.get(i).getInternodeLengthAverage());
                                    v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE, s.get(i).getInternodeLengthAverageScore());

                                    v.put(Columns.BRIX, s.get(i).getBrix());
                                    v.put(Columns.BRIX_SCORE, s.get(i).getBrixScore());

                                    v.put(Columns.FLOWERING, s.get(i).getFlowering());
                                    v.put(Columns.FLOWERING_SCORE, s.get(i).getFloweringScore());

                                    v.put(Columns.HEIGHT, s.get(i).getHeight());
                                    v.put(Columns.HEIGHT_SCORE, s.get(i).getHeightScore());

                                    v.put(Columns.STUFF, s.get(i).getStuff());
                                    v.put(Columns.STUFF_SCORE, s.get(i).getStuffScore());

                                    v.put(Columns.WHITE_FLY, s.get(i).getWhiteFly());
                                    v.put(Columns.WHITE_FLY_SCORE, s.get(i).getWhiteFlyScore());

                                    v.put(Columns.BORER, s.get(i).getBorer());
                                    v.put(Columns.BORER_SCORE, s.get(i).getBorerScore());

                                    v.put(Columns.APHID, s.get(i).getAphid());
                                    v.put(Columns.APHID_SCORE, s.get(i).getAphidScore());

                                    v.put(Columns.ICERYA_MEAL_BUG, s.get(i).getIceryaMealbug());
                                    v.put(Columns.ICERYA_MEAL_BUG_SCORE, s.get(i).getIceryaMealbugScore());

                                    v.put(Columns.SCALE, s.get(i).getScale());
                                    v.put(Columns.SCALE_SCORE, s.get(i).getScaleScore());

                                    v.put(Columns.YELLOW_SPOT, s.get(i).getYellowSpot());
                                    v.put(Columns.YELLOW_SPOT_SCORE, s.get(i).getYellowSpotScore());

                                    v.put(Columns.POKKAH_BOENG, s.get(i).getPokkahBoeng());
                                    v.put(Columns.POKKAH_BOENG_SCORE, s.get(i).getPokkahBoengScore());

                                    v.put(Columns.BROWN_SPOT, s.get(i).getBrownSpot());
                                    v.put(Columns.BROWN_SPOT_SCORE, s.get(i).getBrownSpotScore());

                                    v.put(Columns.RING_SPOT, s.get(i).getRingSpot());
                                    v.put(Columns.RING_SPOT_SCORE, s.get(i).getRingSpotScore());

                                    v.put(Columns.RUST, s.get(i).getRust());
                                    v.put(Columns.RUST_SCORE, s.get(i).getRustScore());

                                    v.put(Columns.DOWNY_MILDEW, s.get(i).getDownyMildew());
                                    v.put(Columns.DOWNY_MILDEW_SCORE, s.get(i).getDownyMildewScore());

                                    v.put(Columns.OTHER_DISEASE, s.get(i).getOtherDisease());
                                    v.put(Columns.OTHER_DISEASE_NAME, s.get(i).getOtherDiseaseName());
                                    v.put(Columns.OTHER_DISEASE_SCORE, s.get(i).getOtherDiseaseScore());

                                    v.put(Columns.OVERALL, s.get(i).getOverAll());
                                    v.put(Columns.OVERALL_SCORE, s.get(i).getOverAllScore());

                                    v.put(Columns.LEAF_SHEATH, s.get(i).getLeafSheath());
                                    v.put(Columns.LEAF_SHEATH_SCORE, s.get(i).getLeafSheathScore());

                                    v.put(Columns.TOTAL_SCORE, s.get(i).getTotalScore());
                                    v.put(Columns.SELECT_STATUS, s.get(i).getSelectStatus());
                                    v.put(Columns.WHY_SELECT, s.get(i).getWhySelect());

                                    c.insert(Uri.parse(uri_myclone), v);

                                    v.clear();

                                }

                                p.dismiss();
                                sessionManager.createLoginSession(ud);
                                sessionManager.checkLogin();

                            }
                            InsertDataToFamilyTable();

                        }
                    }).start();

                }else{
                    switch (status.getCode()){
                        case AjaxStatus.NETWORK_ERROR:

                            break;
                        case AjaxStatus.NETWORK:
                            break;
                        case AjaxStatus.TRANSFORM_ERROR:
                            break;
                        case AjaxStatus.FILE:
                            break;
                        case AjaxStatus.AUTH_ERROR:
                            break;
                        case AjaxStatus.DATASTORE:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private String getFamilyName(String clonecode) {
        String familyname;


        familyname = clonecode.split("")[1] + clonecode.split("")[3] + clonecode.split("")[4] + clonecode.split("")[5] + clonecode.split("")[6] + clonecode.split("")[7];

//        Log.d("Family",familyname);

        return familyname;
    }



    public int getStandardAverageHeight(String FamilyCode) {
        int standardAverageHeight = 0;

        ContentResolver cr = getContentResolver();
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

    private void ReCalculate(){
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MY_FAMILY));
        String[] projection = null;
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            String FamilyCode = c.getString(c.getColumnIndex(Columns.FAMILYCODE));
            ReCalculateScore(FamilyCode);
        }
    }
    private void ReCalculateScore(String FamilyCode) {
        ContentResolver contentResolver = getContentResolver();
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
                        if (c.getFloat(c.getColumnIndex(Columns.TOTAL_SCORE))!=0){
                            ContentValues v = new ContentValues();
                            v.put(Columns.HEIGHT_SCORE, score);

                            String where2 = Columns.CLONECODE+" = ?";
                            String[] selectionArgs = {c.getString(c.getColumnIndex(Columns.CLONECODE))};
                            contentResolver.update(uri,v,where2,selectionArgs);
                        }else{
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
                            v.put(Columns.CHANGESTATUS,1);
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

    }
    private void CalculateConClusionData(){
        Uri uri = Uri.parse(getResources().getString(R.string.URI_Distinc));
        String[] projection = null;
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {
            String FamilyCode = c.getString(c.getColumnIndex(Columns.FAMILYCODE));
            ReCheckConclusionData(FamilyCode);
        }

        c.close();
    }
    private void ReCheckConclusionData(String FamilyCode) {
        ContentResolver cR = getContentResolver();

        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String selection = Columns.FAMILYCODE + " = ?";
        String[]  selectionArgs = {FamilyCode};

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
        String[] selectionArgs2= {FamilyCode};
        cR.update(uri2,v,where,selectionArgs2);

    }
    private void InsertDataToFamilyTable() {


        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse(getResources().getString(R.string.URI_Distinc));
        String[] projection = {Columns.FAMILYCODE,Columns.ROWNUMBER};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                ContentValues v = new ContentValues();
                /**
                 * SelectStatus = 1 หมายถึงเก็บข้อมูลแล้ว
                 * SelectStatus = 2 หมายถึงคัดไว้
                 * SelectStatus = 3 หมายถึงคัดทิ้ง
                 * */
                String selected = Columns.FAMILYCODE+" = '"+c.getString(0)+"' AND "+Columns.SAVEDSTATUS+" = "+ SelectStatus.SELECTED;
                String rejected = Columns.FAMILYCODE+" = '"+c.getString(0)+"' AND "+Columns.SAVEDSTATUS+" = "+ SelectStatus.REJECTED;
                String saved    = Columns.FAMILYCODE+" = '"+c.getString(0)+"' AND "+Columns.SAVEDSTATUS+" = "+ SelectStatus.SAVED;

                int savedAmount;
                int rejectedAmount;
                int selectedAmount;

                Cursor cf = contentResolver.query(Uri.parse(getResources().getString(R.string.URI_MYCLONE)),null,saved,null,null);

                savedAmount = cf.getCount();
                cf.close();

                cf = contentResolver.query(Uri.parse(getResources().getString(R.string.URI_MYCLONE)),null,selected,null,null);
                selectedAmount = cf.getCount();
                cf.close();

                cf = contentResolver.query(Uri.parse(getResources().getString(R.string.URI_MYCLONE)),null,rejected,null,null);
                rejectedAmount = cf.getCount();
                cf.close();

                savedAmount = savedAmount+selectedAmount+rejectedAmount;

                v.put(Columns.FAMILYCODE,c.getString(c.getColumnIndex(Columns.FAMILYCODE)));
                v.put(Columns.ROWNUMBER,c.getInt(c.getColumnIndex(Columns.ROWNUMBER)));
                v.put(Columns.AMOUNT_OF_CLONE_SAVED,savedAmount);
                v.put(Columns.AMOUNT_OF_CLONE_SELECTED,selectedAmount);
                v.put(Columns.AMOUNT_OF_CLONE_REJECTED,rejectedAmount);

                Uri insert = contentResolver.insert(Uri.parse(getResources().getString(R.string.URI_MY_FAMILY)), v);
                Log.d(TAG_DEBUG, insert.toString());


            }
        }
        ReCalculate();
        CalculateConClusionData();

        ReCalScoreData d = new ReCalScoreData(this,getContentResolver());
        d.onCaribrate();
    }

    private void DownLoadCheckSpecies(int userid,String placetest){
        String url_userclone = getResources().getString(R.string.url_downloadcheckdata);
        HashMap<String, Object> params = new HashMap<String, Object>();
        final String uri_mystandardclone = getResources().getString(R.string.URI_MY_STANDARDCLONE);
        params.put("UserID", userid);
        params.put("ListType", "PlaceTest");
        params.put("PlaceTest", placetest);
        p.setMessage("กำลังดาว์นโหลดข้อมูลโคลน");
        Log.d("debug",params.toString());

//        Log.d("CheckParam",params.toString()+"");

//        Toast.makeText(LoginActivity.this, params.toString(), Toast.LENGTH_LONG).show();

        aq.transformer(gson).progress(R.id.progressbar).ajax(url_userclone, params,UserCloneItem.class, new AjaxCallback<UserCloneItem>() {
            @Override
            public void callback(String url, final UserCloneItem object, AjaxStatus status) {
                super.callback(url, object, status);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Log.d("Debug",status.getMessage());
                if (object != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (object != null) {
                                ArrayList<CloneListData> s = object.getClonelistdata();
                                for (int i = 0; i < s.size(); i++) {
                                    count = i;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            p.show();
                                            p.setMessage("กำลังดาว์นโหลดข้อมูลพันธุ์เช็ค"
                                                    + '\n' + count + "/" + object.getClonelistdata().size());
                                        }
                                    });
//
                                    ContentValues v = new ContentValues();
                                    v.put(Columns.SPECIENAME, s.get(i).getCloneCode());
                                    v.put(Columns.PLACETEST, s.get(i).getPlaceTest());
                                    v.put(Columns.FAMILYCODE,s.get(i).getFamilyCode());
                                    v.put(Columns.PLANTORDER, s.get(i).getRowNumber());
                                    v.put(Columns.SAVEDSTATUS,1);

                                    v.put(Columns.UPPERPICURL, s.get(i).getUpperPicURL());
                                    v.put(Columns.UPPERUUID, s.get(i).getUpperUUID());

                                    v.put(Columns.LOWERPICURL, s.get(i).getLowerPicURL());
                                    v.put(Columns.LOWERUUDI, s.get(i).getLowerUUID());

                                    v.put(Columns.FULLPICURL, s.get(i).getFullPicURL());
                                    v.put(Columns.FULLPICUUID, s.get(i).getFullUUID());

                                    v.put(Columns.COLLECTINGTIME, s.get(i).getCollectingTime());

                                    v.put(Columns.STALK_AMOUNT, s.get(i).getStalkAmount());
                                    v.put(Columns.STALK_AMOUNT_SCORE, s.get(i).getStalkAmountScore());

                                    v.put(Columns.CLUMP_SHAPE, s.get(i).getClumpShape());
                                    v.put(Columns.CLUMP_SHAPE_SCORE, s.get(i).getClumpShapeScore());

                                    //Clump Charecteristic
                                    if (s.get(i).getClumpCharacteristic()!=null){
                                        if (s.get(i).getClumpCharacteristic().contains("ไม่ล้ม")){
                                            v.put(Columns.CLUMP_CHARACTERISTIC,"ไม่ล้มตั้งตรง");
                                        }else{
                                            v.put(Columns.CLUMP_CHARACTERISTIC, s.get(i).getClumpCharacteristic());
                                        }

                                        if (s.get(i).getClumpCharacteristicScore()!= 0){
                                            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, s.get(i).getClumpCharacteristicScore());
                                        }else{
                                            v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, ReCalculateScore.ClumpCharecteristic(s.get(i).getClumpCharacteristic()));
                                        }
                                    }else{
                                        v.put(Columns.CLUMP_CHARACTERISTIC, s.get(i).getClumpCharacteristic());
                                        v.put(Columns.CLUMP_CHARACTERISTIC_SCORE, s.get(i).getClumpCharacteristicScore());
                                    }

                                    v.put(Columns.INTERNAL_SYSTOM, s.get(i).getInternalSymtom());
                                    v.put(Columns.INTERNAL_SYSTOM_SCORE, s.get(i).getInternalSymtomScore());

                                    v.put(Columns.INTERNAL_FIRMNESS, s.get(i).getInternalFirmness());
                                    v.put(Columns.INTERNAL_FIRMNESS_SCORE, s.get(i).getInternalFirmnessScore());

                                    v.put(Columns.STALK_SIZE_1, s.get(i).getStalkSize1());
                                    v.put(Columns.STALK_SIZE_2, s.get(i).getStalkSize2());
                                    v.put(Columns.STALK_SIZE_3, s.get(i).getStalkSize3());
                                    v.put(Columns.STALK_SIZE_4, s.get(i).getStalkSize4());
                                    v.put(Columns.STALK_SIZE_5, s.get(i).getStalkSize5());

                                    v.put(Columns.STALK_SIZE_AVERAGE, s.get(i).getStalkSizeAverage());
                                    v.put(Columns.STALK_SIZE_AVERAGE_SCORE, s.get(i).getStalkSizeAverageScore());

                                    v.put(Columns.INTERNODE_AMOUNT, s.get(i).getInternodeAmount());
                                    v.put(Columns.INTERNODE_AMOUNT_SCORE, s.get(i).getInternodeAmountScore());

                                    v.put(Columns.INTERNODE_LENGTH1, s.get(i).getInternodeLength1());
                                    v.put(Columns.INTERNODE_LENGTH2, s.get(i).getInternodeLength2());
                                    v.put(Columns.INTERNODE_LENGTH3, s.get(i).getInternodeLength3());
                                    v.put(Columns.INTERNODE_LENGTH4, s.get(i).getInternodeLength4());
                                    v.put(Columns.INTERNODE_LENGTH5, s.get(i).getInternodeLength5());
                                    v.put(Columns.INTERNODE_LENGTH_AVERAGE, s.get(i).getInternodeLengthAverage());
                                    v.put(Columns.INTERNODE_LENGTH_AVERAGE_SCORE, s.get(i).getInternodeLengthAverageScore());

                                    v.put(Columns.BRIX, s.get(i).getBrix());
                                    v.put(Columns.BRIX_SCORE, s.get(i).getBrixScore());

                                    v.put(Columns.FLOWERING, s.get(i).getFlowering());
                                    v.put(Columns.FLOWERING_SCORE, s.get(i).getFloweringScore());

                                    v.put(Columns.HEIGHT, s.get(i).getHeight());
                                    v.put(Columns.HEIGHT_SCORE, s.get(i).getHeightScore());

                                    v.put(Columns.STUFF, s.get(i).getStuff());
                                    v.put(Columns.STUFF_SCORE, s.get(i).getStuffScore());

                                    v.put(Columns.WHITE_FLY, s.get(i).getWhiteFly());
                                    v.put(Columns.WHITE_FLY_SCORE, s.get(i).getWhiteFlyScore());

                                    v.put(Columns.BORER, s.get(i).getBorer());
                                    v.put(Columns.BORER_SCORE, s.get(i).getBorerScore());

                                    v.put(Columns.APHID, s.get(i).getAphid());
                                    v.put(Columns.APHID_SCORE, s.get(i).getAphidScore());

                                    v.put(Columns.ICERYA_MEAL_BUG, s.get(i).getIceryaMealbug());
                                    v.put(Columns.ICERYA_MEAL_BUG_SCORE, s.get(i).getIceryaMealbugScore());

                                    v.put(Columns.SCALE, s.get(i).getScale());
                                    v.put(Columns.SCALE_SCORE, s.get(i).getScaleScore());

                                    v.put(Columns.YELLOW_SPOT, s.get(i).getYellowSpot());
                                    v.put(Columns.YELLOW_SPOT_SCORE, s.get(i).getYellowSpotScore());

                                    v.put(Columns.POKKAH_BOENG, s.get(i).getPokkahBoeng());
                                    v.put(Columns.POKKAH_BOENG_SCORE, s.get(i).getPokkahBoengScore());

                                    v.put(Columns.BROWN_SPOT, s.get(i).getBrownSpot());
                                    v.put(Columns.BROWN_SPOT_SCORE, s.get(i).getBrownSpotScore());

                                    v.put(Columns.RING_SPOT, s.get(i).getRingSpot());
                                    v.put(Columns.RING_SPOT_SCORE, s.get(i).getRingSpotScore());

                                    v.put(Columns.RUST, s.get(i).getRust());
                                    v.put(Columns.RUST_SCORE, s.get(i).getRustScore());

                                    v.put(Columns.DOWNY_MILDEW, s.get(i).getDownyMildew());
                                    v.put(Columns.DOWNY_MILDEW_SCORE, s.get(i).getDownyMildewScore());

                                    v.put(Columns.OTHER_DISEASE, s.get(i).getOtherDisease());
                                    v.put(Columns.OTHER_DISEASE_NAME, s.get(i).getOtherDiseaseName());
                                    v.put(Columns.OTHER_DISEASE_SCORE, s.get(i).getOtherDiseaseScore());

                                    v.put(Columns.OVERALL, s.get(i).getOverAll());
                                    v.put(Columns.OVERALL_SCORE, s.get(i).getOverAllScore());

                                    v.put(Columns.LEAF_SHEATH, s.get(i).getLeafSheath());
                                    v.put(Columns.LEAF_SHEATH_SCORE, s.get(i).getLeafSheathScore());



                                    c.insert(Uri.parse(uri_mystandardclone), v);

                                    v.clear();

                                }
                                OnLoadUserCloneData(ud.getUserID(), ud.getSector());
                            }

                        }
                    }).start();

                }else{
                    switch (status.getCode()){
                        case AjaxStatus.NETWORK_ERROR:

                            break;
                        case AjaxStatus.NETWORK:
                            break;
                        case AjaxStatus.TRANSFORM_ERROR:
                            OnLoadUserCloneData(ud.getUserID(), ud.getSector());
                            break;
                        case AjaxStatus.FILE:
                            break;
                        case AjaxStatus.AUTH_ERROR:
                            break;
                        case AjaxStatus.DATASTORE:
                            break;
                    }
                }
            }
        });
    }

}

