package sugarcaneselection.thaib.org.clonplanting2.scanner;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;

public class ReceiveCloneScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private TextView tvClonceCode;
    private EditText edtReceiveAmount;
    private Button btOK;

    private String CloneCode;
    private Integer ReceiveAmount = 0;
    private String SentFrom;

    private ImageButton camSwitch;
    private int currentCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_clone_scanner);

        scannerView = (ZXingScannerView) findViewById(R.id.camera_preview);
        tvClonceCode = (TextView) findViewById(R.id.tvCloneCode);
        edtReceiveAmount = (EditText) findViewById(R.id.edtReceiveAmount);
        btOK = (Button) findViewById(R.id.btOK);

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ตรวจสอบว่าเป็นพันธุ์เช็คหรือไม่

                SentFrom = CloneCode.split("")[2];
                Intent intent = getIntent();
                intent.putExtra(AppTag.CLONECODE, CloneCode);
                intent.putExtra(AppTag.RECEIVE_AMOUNT, ReceiveAmount);
                intent.putExtra(AppTag.SENT_FROM,SentFrom);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

       btOK.setEnabled(false);

        edtReceiveAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtReceiveAmount.getText().toString().equals("")) {
                    ReceiveAmount = Integer.valueOf(edtReceiveAmount.getText().toString());
                    Log.d("Received Amount", "" + ReceiveAmount);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        camSwitch = (ImageButton) findViewById(R.id.btSwitch);
        camSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannerView.stopCamera();
                if(currentCamera == 0){
                    scannerView.startCamera(1);
                    currentCamera = 1;
                }else{
                    scannerView.startCamera(0);
                    currentCamera = 0;
                }



            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        CloneCode = result.toString();
        tvClonceCode.setText(CloneCode);
        btOK.setEnabled(true);
        SentFrom = CloneCode.split("")[2];


    }
}
