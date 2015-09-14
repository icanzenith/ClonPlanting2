package sugarcaneselection.thaib.org.clonplanting2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static sugarcaneselection.thaib.org.clonplanting2.R.id.btOpenScaner;

/**
 * Created by Jitpakorn on 4/23/15 AD.
 */

public class TestActivity extends AppCompatActivity {


    public static final int TAG_REQUEST_QRSCANER = 1002;
    private String QrcodeResult;

    private Button btOpen;
    private TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btOpen = (Button) findViewById(btOpenScaner);
        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,String.class);
                startActivityForResult(intent, TAG_REQUEST_QRSCANER);
            }
        });

        tvResult = (TextView) findViewById(R.id.tvResult);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAG_REQUEST_QRSCANER){
            if (resultCode == RESULT_OK){
                tvResult.setText(data.getStringExtra("Result"));
            }
        }

        if (data!=null){

        }
    }
}
