package sugarcaneselection.thaib.org.sugarcanselection;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentCloneSelect;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentDetailTable;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentFamilySelect;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentMainCorrectData2;


public class MainActivity extends ActionBarActivity implements

        FragmentFamilySelect.OnFragmentInteractionListener
//        , Fragment_CorrectStandardData.OnFragmentInteractionListener
        , FragmentPictureManager.OnFragmentInteractionListener
        , FragmentScoreDetail.OnFragmentInteractionListener
        , FragmentCloneSelect.OnFragmentInteractionListener, FragmentCorrectStandardData.OnFragmentInteractionListener {

    private BaseApplication baseApplication;
    private int fragmentEntry = 0;
    private static final String SELECT_FROM_CORRECT ="correct";



    private CloneDataItem cloneDataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));

        baseApplication = (BaseApplication) getApplication();
        if (baseApplication.getCloneDataItem() == null) {
            cloneDataItem = new CloneDataItem();
            baseApplication.setCloneDataItem(cloneDataItem);
            Log.d("Check ScoreData", "Get Score == null");

        } else {
            cloneDataItem = baseApplication.getCloneDataItem();
            Log.d("Check ScoreData", "Get Score != null");
            Log.d("Check ScoreData", baseApplication.getCloneDataItem().toString());
        }


        Fragment fragment = FragmentFamilySelect.newInstance(null, null);
        this.setActionBarTitle("เลือกเบอร์คู่ผสม");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment,"SelectFamily") //TODO Dont for get to re - Check Tag Here
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }


    @Override
    public void onChangeFragmentMenu() {
        switch (fragmentEntry) {
            case 1001:

                break;

        }
    }

    public void setFragmentEntry(int fragmentEntry) {
        this.fragmentEntry = fragmentEntry;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onFinishDiseaseCheck() {
//        Fragment fragment = new Fragment_CorrectData();
//
//        getSupportFragmentManager().beginTransaction()
//                .addToBackStack(null)
//                .replace(R.id.container, fragment)
//                .commit();

    }

    public void onFinishCorrectData() {
        Fragment fragment = FragmentDetailTable.newInstance();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment,"Conclusion")
                .commit();

    }

    public void FinishSelectFamily(String FamilyCode) {


//        Fragment fragment = StandardSpeciesManagerFragment.newInstance(familycode);
        FragmentCloneSelect fragment = FragmentCloneSelect.newInstance(SELECT_FROM_CORRECT, FamilyCode);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("familyselect")
                .replace(R.id.container, fragment,"CloneSelectFragment")
                .commit();
    }

    public void onFinishTakepicture() {

        Fragment fragment = new FragmentScoreDetail();

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment)
                .commit();
    }

    public void FinishedCorrectStandardData() {
        getSupportFragmentManager().popBackStack("StandardSpecieManager", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void FinishedStandardDataManager(String FamilyCode) {
        FragmentCloneSelect cloneSelect = FragmentCloneSelect.newInstance(null, FamilyCode);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("StandardSpecieManager")
                .replace(R.id.container, cloneSelect, "CloneSelect")
                .commit();
    }


    public void onFinishCloneSelect(int CloneType) {

        Fragment fragment = FragmentMainCorrectData2.newInstance(CloneType);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("rep")
                .replace(R.id.container, fragment,"Correct2")
                .commit();
    }

    public void onFinishDetail() {
        getSupportFragmentManager().popBackStack("rep", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void onChangeFamily() {
        Log.d("Debug","OnChange Family");
        baseApplication.setCloneDataItem(new CloneDataItem());
        baseApplication.setStandardSpecieData(new CloneDataItem());
        getSupportFragmentManager().popBackStack("familyselect", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    boolean confirmstate = false;

    @Override
    public void onBackPressed() {
        if (confirmstate){
            super.onBackPressed();
            confirmstate =false;
            return;
        }else{
        }
        if (getSupportFragmentManager().findFragmentByTag("Correct2")!=null) {
            if (getSupportFragmentManager().findFragmentByTag("Correct2").isVisible()) {
                BackDialogFragment dialogFragment = new BackDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "isFinishDialog");
/**
 *
 * Double Back button to Back
 *
 * */
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
//            }
//
//            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce=false;
//                }
//            }, 2000);
            } else {
                super.onBackPressed();
                return;
            }
        }else{
            super.onBackPressed();
            return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseApplication.setCloneDataItem(null);
        baseApplication.setStandardSpecieData(null);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void OpenStandardSpecieForm(String FamilyCode, int PlantOrder, boolean isNewStandardClone) {

//        Fragment fragment = Fragment_CorrectStandardData.newInstance(FamilyCode, PlantOrder, isNewStandardClone);
//        getSupportFragmentManager().beginTransaction()
//                .addToBackStack("StandardSpecieManager")
//                .replace(R.id.container, fragment)
//                .commit();

    }

    public static class BackDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("ยกเลิกการบันทึก").setMessage(R.string.isfinishcorrectdata)
                    .setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity s = (MainActivity) getActivity();
                            BaseApplication b = (BaseApplication) s.getApplication();
                            s.confirmstate = true ;
                            s.onBackPressed();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            MainActivity s = (MainActivity) getActivity();
                            s.confirmstate = false ;
                            dismiss();
                        }
                    });


            return builder.create();
        }
    }
}
