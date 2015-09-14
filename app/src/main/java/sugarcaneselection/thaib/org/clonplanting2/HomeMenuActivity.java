package sugarcaneselection.thaib.org.clonplanting2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import sugarcaneselection.thaib.org.clonplanting2.item.AppTag;
import sugarcaneselection.thaib.org.clonplanting2.util.SampleData;


public class HomeMenuActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private Button btLandManagement;
    private Button btSentClone;
    private Button btReceiveClone;
    private Button btPlantClone;
    private Button btCountClonePlanted;
    private Button btCountCloneSurvive;
    private Button btConclusionSent;
    private Button btConclusionReveived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        btLandManagement = (Button) findViewById(R.id.btLandManagement);
        btLandManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, LandListActivity.class);
                startActivity(intent);
            }
        });

        btSentClone = (Button) findViewById(R.id.btSentClone);
        btSentClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this,SelectPlaceListActivity.class);
                startActivity(intent);
            }
        });

        btReceiveClone = (Button) findViewById(R.id.btReceiveClone);
        btReceiveClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this,ReceiveCloneListActivity.class);
                startActivity(intent);
            }
        });

        btPlantClone = (Button) findViewById(R.id.btPlantClone);
        btPlantClone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeMenuActivity.this,LandListActivity.class);
                intent.putExtra(AppTag.NEXT_ACTIVITY,AppTag.PlantingClone);
                startActivity(intent);
            }
        });



        btCountClonePlanted = (Button) findViewById(R.id.btCountPlanted);
        btCountClonePlanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this,LandListActivity.class);
                intent.putExtra(AppTag.NEXT_ACTIVITY,AppTag.CountClonePlanted);
                startActivity(intent);

            }
        });
        btCountCloneSurvive = (Button) findViewById(R.id.btCountSurvive);
        btCountCloneSurvive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this,LandListActivity.class);
                intent.putExtra(AppTag.NEXT_ACTIVITY,AppTag.CountCloneSurvive);
                startActivity(intent);
            }
        });

        btConclusionSent = (Button) findViewById(R.id.btConclusionSent);
        btConclusionSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, SelectPlaceToConclusionActivity.class);
                intent.putExtra("WhereToGo",SelectPlaceToConclusionActivity.SentCloneConclusion);
                startActivity(intent);
            }
        });

        btConclusionReveived = (Button) findViewById(R.id.btConclusionReceived);
        btConclusionReveived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuActivity.this, SelectPlaceToConclusionActivity.class);
                intent.putExtra("WhereToGo",SelectPlaceToConclusionActivity.ReceivedCloneConclusion);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SampleData s= new SampleData(HomeMenuActivity.this);
            s.CreateSampleData();
            return true;
        }
        if (id==R.id.action_logout){
            BaseApplication baseApplication = (BaseApplication) getApplication();
            baseApplication.getSessionManager().Logout(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void handleResult(Result result) {

    }
}
