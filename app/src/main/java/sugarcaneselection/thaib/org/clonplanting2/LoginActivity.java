package sugarcaneselection.thaib.org.clonplanting2;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


import java.util.HashMap;
import java.util.List;

import sugarcaneselection.thaib.org.clonplanting2.databases.Columns;
import sugarcaneselection.thaib.org.clonplanting2.databases.SessionManager;
import sugarcaneselection.thaib.org.clonplanting2.item.LoginCallbackJson;
import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;
import sugarcaneselection.thaib.org.clonplanting2.item.PersonalDataCallbackJson;
import sugarcaneselection.thaib.org.clonplanting2.item.Upload_Status;
import sugarcaneselection.thaib.org.clonplanting2.item.UserDataItem;
import sugarcaneselection.thaib.org.clonplanting2.util.GsonTransformer;


public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btLogin;
    private AQuery aq;
    private GsonTransformer transformer;
    private SessionManager sessionManager;
    private UserDataItem ud;
    private HashMap<String, Object> object = new HashMap<>();
    private LinearLayout container;


    private ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);

        if (getSharedPreferences(SessionManager.PREF_NAME, 0) != null) {
            sessionManager = new SessionManager(getApplication(), LoginActivity.this);
            sessionManager.checkLogin();


        } else {
            sessionManager = new SessionManager(getApplication(), LoginActivity.this);


        }


        setContentView(R.layout.activity_login);
        btLogin = (Button) findViewById(R.id.btLogin);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                //TODO Drop database if exits
                onClickLogin();
                    
//                Intent intent = new Intent(LoginActivity.this,HomeMenuActivity.class);
//                startActivity(intent);
            }
        });

        transformer = new GsonTransformer();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        container = (LinearLayout) findViewById(R.id.container);
        url = getResources().getString(R.string.url_login);
    }

    public void onClickLogin() {



        String username;
        String password;
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();


        ParseObject parseObject = new ParseObject("UserLogin");
        parseObject.put("username",username);
        parseObject.put("password",password);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                } else {


                }
            }
        });

        if (!username.equals("") && !password.equals("")) {
            Login(username, password);
            container.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }


    }

    public void Login(String username, String password) {
        ud = new UserDataItem();
        object.put("Username", username.trim());
        object.put("Password", password.trim());
//        Log.d("Check Param User Password",object.toString());
        aq.transformer(transformer)
                .ajax(url,
                        object,
                        LoginCallbackJson.class,
                        new AjaxCallback<LoginCallbackJson>() {
                            @Override
                            public void callback(String url, LoginCallbackJson object, AjaxStatus status) {
                                super.callback(url, object, status);
                                Log.d("Network Status", status.getMessage());
                                Log.d("Network Status", status.getCode() + "");

                                if (object != null) {
                                    if (object.getTempva().get(0).getUserID() != 0) {
                                        ud.setUserID(object.getTempva().get(0).getUserID());
                                        ud.setUsername(object.getTempva().get(0).getUsername());
                                        ud.setAuthority(object.getTempva().get(0).getAuthority());
                                        ud.setSector(object.getTempva().get(0).getSector());

                                        //TODO Subscript Parse Channel
                                        ParsePush.subscribeInBackground("ALL");
                                        ParsePush.subscribeInBackground(ud.getSector());

                                        LoadPersonalData(object.getTempva().get(0).getUserID());
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Username หรือ Password ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                                        container.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }

                                }
                            }
                        });
    }

    public void LoadPersonalData(final int userID) {
        String url_userdata = getResources().getString(R.string.url_personaldata);
        HashMap<String, Integer> object = new HashMap<String, Integer>();

        object.put("UserID", userID);
        aq.transformer(transformer)
                .ajax(url_userdata,
                        object,
                        PersonalDataCallbackJson.class,
                        new AjaxCallback<PersonalDataCallbackJson>() {
                            @Override
                            public void callback(String url, PersonalDataCallbackJson object, AjaxStatus status) {
                                super.callback(url, object, status);
                                Log.d("Net Work Status", status.getMessage());
                                if (object != null) {
                                    Log.d("Debug ", "Object != null");
                                    ud.setPicUrl(object.getUserdata().get(0).getPicURL());
                                    ud.setUUID(object.getUserdata().get(0).getUUID());
                                    ud.setName(object.getUserdata().get(0).getName());
                                    ud.setWorkPlace(object.getUserdata().get(0).getWorkPlace());

                                    ud.setTel(object.getUserdata().get(0).getTel());
//                                    Toast.makeText(getApplicationContext(),object.getUserdata().get(0).getTel(),Toast.LENGTH_LONG).show();
                                    ud.setEmail(object.getUserdata().get(0).getEmail());
                                    ud.setLineID(object.getUserdata().get(0).getLineID());
                                    ud.setFacebookID(object.getUserdata().get(0).getFacebookID());

                                    getCloneToSentData();

                                } else {
                                    Log.d("Debug ", "Object != null");
                                }
                            }
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private int countPage =0;

    private void getCloneToSentData(){
        int skip = countPage *100;
        final int limit = 100;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
        query.whereEqualTo(Columns.PlaceTest,ud.getSector());
        query.whereExists(Columns.SentAmount);
        query.setLimit(limit);
        query.setSkip(skip);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < parseObjects.size(); i++) {
                        Uri uri = Uri.parse(getString(R.string.uri_clonetosent));
                        ContentValues v = new ContentValues();
                        v.put(Columns.CloneCode, parseObjects.get(i).getString(Columns.CloneCode));
                        v.put(Columns.ObjectID, parseObjects.get(i).getObjectId());
                        v.put(Columns.SentAmount, parseObjects.get(i).getInt(Columns.SentAmount));
                        v.put(Columns.FromPlace, parseObjects.get(i).getString(Columns.PlaceTest));
                        v.put(Columns.SentTo, parseObjects.get(i).getString(Columns.SentTo));
                        v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                        getContentResolver().insert(uri, v);
                        Log.d("Debug ", "SentTo" +parseObjects.get(i).getString(Columns.SentTo)+" "+i);
                    }
                    if (limit>parseObjects.size()){
                        Log.d("Debug","ParseObject LastOb"+parseObjects.size());
                        countPage =0;
                        getReceiveCloneData();

                    }else{
                        Log.d("Debug","ParseObject"+parseObjects.size());
                        countPage++;
                        getCloneToSentData();
                    }
                } else {

                }

            }
        });


    }
    private void getPlantedCloneData(){
        int skip = countPage *100;
        final int limit = 100;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Planting);
        query.whereEqualTo(Columns.SentTo, ud.getSector());
        query.setLimit(limit);
        query.setSkip(skip);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject o = list.get(i);
                        Uri uri = Uri.parse(getString(R.string.uri_planting_clone));
                        ContentValues v = new ContentValues();
                        v.put(Columns.ObjectID,o.getObjectId());
                        v.put(Columns.RowNumber,o.getInt(Columns.RowNumber));
                        v.put(Columns.PlantedAmount,o.getInt(Columns.PlantedAmount));
                        v.put(Columns.ReceiveCloneID,o.getString(Columns.ReceiveCloneID));
                        v.put(Columns.LandID,o.getString(Columns.LandID));
                        v.put(Columns.CloneCode,o.getString(Columns.CloneCode));
                        v.put(Columns.UploadStatus,Upload_Status.UPLOADED);
                        v.put(Columns.Replication,o.getString(Columns.Replication));
                        getContentResolver().insert(uri, v);
                        Log.d("DATA DEBUG", v.toString());
                    }

                    if (limit>list.size()){
                        countPage++;
                        countPage=0;
                        sessionManager.createLoginSession(ud);
                        sessionManager.checkLogin();
                    }else{
                        countPage++;
                        getPlantedCloneData();
                    }
//                    Log.d("Count Loop", countPage +"");
//                    Log.d("Count object size",parseObjects.size()+"");
                } else {

                }

            }

        });
    }
    private void getReceiveCloneData(){
        int skip = countPage *100;
        final int limit = 100;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Clone);
        query.whereEqualTo(Columns.SentTo, ud.getSector());
        query.whereExists(Columns.ReceiveAmount);
        query.whereNotEqualTo(Columns.ReceiveAmount, 0);
        query.setLimit(limit);
        query.setSkip(skip);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < parseObjects.size(); i++) {
                        Uri uri = Uri.parse(getString(R.string.uri_clone));
                        ContentValues v = new ContentValues();
                        v.put(Columns.CloneCode, parseObjects.get(i).getString(Columns.CloneCode));
                        v.put(Columns.ObjectID, parseObjects.get(i).getObjectId());
                        v.put(Columns.FromPlace, parseObjects.get(i).getString(Columns.PlaceTest));
                        v.put(Columns.SentTo,parseObjects.get(i).getString(Columns.SentTo));
                        v.put(Columns.SentAmount, parseObjects.get(i).getInt(Columns.SentAmount));
                        v.put(Columns.ReceiveAmount, parseObjects.get(i).getInt(Columns.ReceiveAmount));
                        v.put(Columns.PlantedAmount,parseObjects.get(i).getInt(Columns.PlantedAmount));
                        v.put(Columns.SurviveAmount,parseObjects.get(i).getInt(Columns.SurviveAmount));
                        v.put(Columns.PlantedAmount,parseObjects.get(i).getInt(Columns.PlantedAmount));
                        v.put(Columns.RowNumber, parseObjects.get(i).getInt(Columns.RowNumber));
                        v.put(Columns.PlanterID,parseObjects.get(i).getInt(Columns.PlanterID));
                        v.put(Columns.UploadStatus, Upload_Status.UPLOADED);
                        v.put(Columns.CloneType, parseObjects.get(i).getInt(Columns.CloneType));

                        getContentResolver().insert(uri, v);
                    }

                    if (limit>parseObjects.size()){
                        countPage++;
                        countPage=0;
                        getLandData();
                    }else{
                        countPage++;
                        getReceiveCloneData();
                    }
//                    Log.d("Count Loop", countPage +"");
//                    Log.d("Count object size",parseObjects.size()+"");
                } else {

                }

            }
        });
    }

    private void check_version(){
        container.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        int version = 1;
        try {
            version = getApplication().getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ParseQuery<ParseObject> p = new ParseQuery<ParseObject>(ParseClass.APP_Version);
        final int finalVersion = version;
        p.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e==null) {
                    if (list.size() != 0) {
                        int lastVersion = list.get(0).getInt("version");
                        if (lastVersion == finalVersion) {


//

                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, list.get(0).getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"มีปัญหาในการเชื่อมต่อ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLandData(){
        int skip = countPage *100;
        final int limit = 100;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseClass.Land);
        query.whereEqualTo(Columns.Sector,ud.getSector());
        query.whereEqualTo("UserCreate", ud.getUserID());
        query.setLimit(limit);
        query.setSkip(skip);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < parseObjects.size(); i++) {
                        ParseObject o = parseObjects.get(i);
                        Uri uri = Uri.parse(getString(R.string.uri_land));
                        ContentValues v = new ContentValues();
                        v.put(Columns.LandName, o.getString(Columns.LandName));
                        v.put(Columns.ObjectID,o.getObjectId());
                        v.put(Columns.LandID,o.getInt(Columns.LandID));
                        v.put(Columns.LandName,o.getString(Columns.LandName));
                        v.put(Columns.LandNumber,o.getInt(Columns.LandNumber));
                        v.put(Columns.RowAmount,o.getInt(Columns.RowAmount));
                        v.put(Columns.Latitude,o.getDouble(Columns.Latitude));
                        v.put(Columns.Longitude,o.getDouble(Columns.Longitude));
                        v.put(Columns.LandSize,o.getDouble(Columns.LandSize));
                        v.put(Columns.LandWidth,o.getDouble(Columns.LandWidth));
                        v.put(Columns.LandLength,o.getDouble(Columns.LandLength));
                        v.put(Columns.LandAddress,o.getString(Columns.LandAddress));
                        v.put(Columns.Sector,o.getString(Columns.Sector));
                        v.put(Columns.UserCreate,o.getInt(Columns.UserCreate));
                        v.put(Columns.UploadStatus,Upload_Status.UPLOADED);
                        getContentResolver().insert(uri, v);
                    }

                    if (limit>parseObjects.size()){
                        countPage++;
                        countPage = 0;
                        getPlantedCloneData();

                    }else{
                        countPage++;
                        getLandData();
                    }
//                    Log.d("Count Loop", countPage +"");
//                    Log.d("Count object size",parseObjects.size()+"");
                } else {

                }

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
