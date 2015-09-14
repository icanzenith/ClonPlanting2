package sugarcaneselection.thaib.org.clonplanting2.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseBroadcastReceiver;

/**
 * Created by Jitpakorn on 5/26/15 AD.
 */
public class testBoardcastReciever extends ParseBroadcastReceiver {



    public testBoardcastReciever(Context context) {
        super();
    }

    public testBoardcastReciever() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.d("getNotification",intent.getExtras().get("com.parse.Data").toString());
    }
}
