package sugarcaneselection.thaib.org.cloneplanting2.main;

import android.app.Application;

import item.UserDataItem;

/**
 * Created by Jitpakorn on 4/22/15 AD.
 */
public class BaseApplication extends Application {
    private UserDataItem UserDataitem;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public UserDataItem getUserDataitem() {
        return UserDataitem;
    }

    public void setUserDataitem(UserDataItem userDataitem) {
        UserDataitem = userDataitem;
    }
}
