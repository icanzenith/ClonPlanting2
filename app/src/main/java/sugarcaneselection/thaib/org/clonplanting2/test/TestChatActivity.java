package sugarcaneselection.thaib.org.clonplanting2.test;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import sugarcaneselection.thaib.org.clonplanting2.BaseApplication;
import sugarcaneselection.thaib.org.clonplanting2.R;

public class TestChatActivity extends ActionBarActivity {

    EditText edtMessage;
    Button btSent;
    BaseApplication b;

    StringBuilder receiveMessage = new StringBuilder();


    testBoardcastReciever br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chat);
        b = (BaseApplication) getApplication();

        final TextView tvReceiveMessage;



        final String sector = b.getUserDataitem().getSector();

        edtMessage = (EditText) findViewById(R.id.message);
        btSent = (Button) findViewById(R.id.btSent);
        tvReceiveMessage = (TextView) findViewById(R.id.receiveMessage);
        btSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();
                ParseQuery pushQuery = ParseInstallation.getQuery();
                pushQuery.whereEqualTo("channels", sector);
                pushQuery.whereNotEqualTo("installationId", ParseInstallation.getCurrentInstallation().getInstallationId());
                ParsePush p = new ParsePush();
                p.setQuery(pushQuery);
                p.setMessage(message);

                JSONObject object = new JSONObject();
                try {
                    object.put("sentFrom",sector+ParseInstallation.getCurrentInstallation().getInstallationId());
                    object.put("alert",message);
                    object.put("message",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                p.setData(object);
                p.sendInBackground(new SendCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("Debug Push", "Message Already Sent");
                        } else {
                            Log.d("Debug Push", "sector : " + sector + "  " + e.getMessage());


                        }
                    }
                });
            }
        });
        br = new testBoardcastReciever(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);

                JSONObject json = null;
                try {
                    json = new JSONObject(intent.getExtras().get("com.parse.Data").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String sentFrom = null;
                String Message = null;
                try {
                    sentFrom = json.getString("sentFrom");
                    Message = json.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                receiveMessage.append(sentFrom+'\n');
                receiveMessage.append(Message+'\n');
                tvReceiveMessage.setText(receiveMessage);
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(br,new IntentFilter("com.parse.push.intent.RECEIVE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
