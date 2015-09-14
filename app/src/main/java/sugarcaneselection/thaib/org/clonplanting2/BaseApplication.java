package sugarcaneselection.thaib.org.clonplanting2;

import android.app.Application;


import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;

import java.util.HashMap;

import sugarcaneselection.thaib.org.clonplanting2.databases.SessionManager;
import sugarcaneselection.thaib.org.clonplanting2.item.UserDataItem;

/**
 * Created by Jitpakorn on 4/22/15 AD.
 */
public class BaseApplication extends Application {
    private UserDataItem UserDataitem;
    private SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(this, null);

//        Parse.initialize(this, "cQXfnLiS5rx70Is1NWfI3hUfFpU6C2ah2wr1vVSh", "aqISWXps5u01tdakGbfIy77i24LSUy4UpgbcLZV4");


//        For Test
        Parse.initialize(this, "HyAuXlAhwJdgEyYuoLYZpYtBEQtjEUR56sAYmJhv", "PSKRXwYlAlp0nAOTNlNTfSJVOSsssxGk80x0ZYPN");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, HomeMenu2Activity.class);

    }


    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public UserDataItem getUserDataitem() {
        return UserDataitem;
    }

    public void setUserDataitem(UserDataItem userDataitem) {
        UserDataitem = userDataitem;
    }

    private HashMap<String,Object> TempChartMap;

    public HashMap<String, Object> getTempChartMap() {
        return TempChartMap;
    }

    public void setTempChartMap(HashMap<String, Object> tempChartMap) {
        TempChartMap = tempChartMap;
    }
}
