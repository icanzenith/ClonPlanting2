package sugarcaneselection.thaib.org.sugarcanselection;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import sugarcaneselection.thaib.org.sugarcanselection.Dialog.UploadCheckSpecie;
import sugarcaneselection.thaib.org.sugarcanselection.Dialog.UploadingDialog;
import sugarcaneselection.thaib.org.sugarcanselection.Dialog.UploadingPicture;
import sugarcaneselection.thaib.org.sugarcanselection.Social.NetworkActivity;
import sugarcaneselection.thaib.org.sugarcanselection.Social.WorkPlaceActivity;

import static sugarcaneselection.thaib.org.sugarcanselection.R.id.bt_checkupload;


public class MenuActivity extends ActionBarActivity {

    TextView tvWorkPlace;

    Button bt_corrected;
    Button bt_upload;
    Button bt_takepicture;
    Button bt_correctData;
    Button bt_uploadPicture;
    Button btNetwork;
    Button btCheckUpload;
    BaseApplication baseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        int radius = 30;
        int stroke = 5;
        int margin = 5;
        ImageView imageView = (ImageView) findViewById(R.id.profile_image);
        Picasso.with(getApplicationContext()).load(R.drawable.ic_profile_sample)
                .transform(new RoundedRectTransformation(radius, stroke, margin))
                .into(imageView);

        baseApplication = (BaseApplication) getApplication();
        getSupportActionBar().setTitle("กรุณาเลือกเมนูที่ต้องการ");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));


        tvWorkPlace = (TextView) findViewById(R.id.tvWorkplace);
        tvWorkPlace.setText(baseApplication.getUserdata().getWorkPlace());

        bt_takepicture = (Button) findViewById(R.id.bt_takepic);
        bt_corrected = (Button) findViewById(R.id.bt_review);
        bt_upload = (Button) findViewById(R.id.bt_uploadtoserver);
        bt_correctData = (Button) findViewById(R.id.bt_correctdata);
        bt_uploadPicture = (Button) findViewById(R.id.bt_uploadpicturetoserver);
        btCheckUpload = (Button) findViewById(bt_checkupload);
        btNetwork = (Button) findViewById(R.id.bt_network);


        bt_correctData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bt_corrected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ActivityListCorrected.class);
                startActivity(intent);
            }
        });
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadingDialog dialog = new UploadingDialog().newInstance(getApplicationContext());
                dialog.show(getFragmentManager(), "dialog");
            }
        });

        bt_takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TakePictureActivity.class);
                startActivity(intent);
            }
        });
        bt_uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadingPicture dialog = new UploadingPicture().newInstance(getApplicationContext());
                dialog.show(getFragmentManager(), "PicDialog");
            }
        });
//        bt_uploadPicture.setVisibility(View.INVISIBLE);
//        bt_upload.setVisibility(View.INVISIBLE);

        btNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });

        btCheckUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadCheckSpecie d = new UploadCheckSpecie().newInstance(getApplication());
                d.show(getSupportFragmentManager(),"Species Dialog");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            BaseApplication baseApplication = (BaseApplication) getApplication();
            baseApplication.getSessionManager().Logout(this);
        }
        return true;
    }
}
