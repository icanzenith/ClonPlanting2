package sugarcaneselection.thaib.org.clonplanting2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import sugarcaneselection.thaib.org.clonplanting2.item.ParseClass;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah_screen);
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
                if (e == null) {
                    if (list.size() != 0) {
                        int lastVersion = list.get(0).getInt("version");
                        if (lastVersion == finalVersion) {
                            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            AlertDialog d = new AlertDialog.Builder(SplashScreenActivity.this,0).setMessage(list.get(0).getString("Message")).create();
                            d.show();
                        }
                    } else {
                        Toast.makeText(SplashScreenActivity.this, "มีปัญหาในการเชื่อมต่อ", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
