package sugarcaneselection.thaib.org.cloneplanting2.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import eu.livotov.zxscan.ScannerFragment;
import eu.livotov.zxscan.ScannerView;
import sugarcaneselection.thaib.org.cloneplanting2.R;

/**
 * Created by Jitpakorn on 4/22/15 AD.
 */
public class QRScanerActivity extends ActionBarActivity implements ScannerView.ScannerViewEventListener{

    public final static String RESULT_EXTRA_STR = "data";

    private ScannerFragment scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscaner);

        if (savedInstanceState == null)
        {
            if (scanner == null)
            {
                scanner = new ScannerFragment();
                scanner.setScannerViewEventListener(this);
            }

            getSupportFragmentManager().beginTransaction().add(R.id.container, scanner).commit();
        }
    }

    @Override
    public void onScannerReady() {

    }

    @Override
    public void onScannerFailure(int i) {

    }

    @Override
    public boolean onCodeScanned(String data) {
        Intent res = new Intent();
        res.putExtra(RESULT_EXTRA_STR, data);
        setResult(RESULT_OK, res);
        finish();
        return true;
    }
}
