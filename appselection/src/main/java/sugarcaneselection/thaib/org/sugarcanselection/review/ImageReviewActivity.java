package sugarcaneselection.thaib.org.sugarcanselection.review;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import sugarcaneselection.thaib.org.sugarcanselection.R;

public class ImageReviewActivity extends ActionBarActivity {

    private Button btChange;
    private Button btOK;

    private static final int willChange = 3303;

    private String imagePath;
    private ImageView imageView;
    private int ResultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE|Window.FEATURE_ACTION_BAR);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_review);
        btChange = (Button) findViewById(R.id.btChange);
        btOK = (Button) findViewById(R.id.btOK);


        if (getIntent()!=null){
            imagePath = getIntent().getStringExtra("Path");
            ResultCode = getIntent().getIntExtra("ResultCode",0);
            imageView = (ImageView) findViewById(R.id.imageReview);
            Picasso.with(this).load(new File(imagePath)).into(imageView);
        }

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ResultCode);
                finish();
            }
        });

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }

}
