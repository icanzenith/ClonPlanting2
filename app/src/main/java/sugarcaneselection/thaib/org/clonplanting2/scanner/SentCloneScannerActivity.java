package sugarcaneselection.thaib.org.clonplanting2.scanner;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.core.BarcodeScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import sugarcaneselection.thaib.org.clonplanting2.R;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;

//TODO Check ซ้ำให้ใช้ Query
public class SentCloneScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    private TextView tvCloneCode;
    private EditText edtSentAmount;
    private Button btOK;

    private String CloneCode;
    private String SentTo;
    private Integer SentAmount = 0;
    private ImageButton camSwitch;
    private int currentCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_clone_scanner);



        scannerView = (ZXingScannerView) findViewById(R.id.camera_preview);
        tvCloneCode = (TextView) findViewById(R.id.tvCloneCode);
        edtSentAmount = (EditText) findViewById(R.id.edtSentAmount);
        btOK = (Button) findViewById(R.id.btOK);

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra(AppTag.CLONECODE, CloneCode);
                intent.putExtra(AppTag.SENTAMOUNT, SentAmount);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        btOK.setEnabled(false);

        edtSentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtSentAmount.getText().toString().equals("")) {
                    SentAmount = Integer.valueOf(edtSentAmount.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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
                if (currentCamera == 0) {
                    scannerView.startCamera(1);
                    currentCamera = 1;
                } else {
                    scannerView.startCamera(0);
                    currentCamera = 0;
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setAutoFocus(false);
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
        tvCloneCode.setText(CloneCode);
        btOK.setEnabled(true);
    }
}
