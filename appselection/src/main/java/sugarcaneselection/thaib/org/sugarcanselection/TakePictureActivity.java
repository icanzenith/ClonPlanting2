package sugarcaneselection.thaib.org.sugarcanselection;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import sugarcaneselection.thaib.org.sugarcanselection.Item.CloneDataItem;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentCloneSelect;
import sugarcaneselection.thaib.org.sugarcanselection.correctdata.FragmentFamilySelect;


public class TakePictureActivity extends ActionBarActivity implements
        FragmentFamilySelect.OnFragmentInteractionListener
        , FragmentCloneSelect.OnFragmentInteractionListener,
        FragmentPictureManager.OnFragmentInteractionListener {
    private static final String SELECT_FROM_TAKEPICMENU = "takpic";
    BaseApplication baseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("บันทึกรูปโคลนที่คัดเลือก");
        setContentView(R.layout.activity_take_picture);
        baseApplication = (BaseApplication) getApplication();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF228b22));
        Fragment fragment = FragmentFamilySelect.newInstance(SELECT_FROM_TAKEPICMENU, null);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    public void onChangeFamily() {
        baseApplication.setCloneDataItem(new CloneDataItem());
        getSupportFragmentManager().popBackStack("familyselect", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public void onFinishSelectFamily(String familycode) {
        FragmentCloneSelect cloneSelect = FragmentCloneSelect.newInstance(SELECT_FROM_TAKEPICMENU, familycode);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("familyselect")
                .replace(R.id.container, cloneSelect)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onChangeFragmentMenu() {

    }

    @Override
    public void setFragmentEntry(int fragmentEntry) {

    }
    private static final String WhichClone = "WhichClone";
    private static final String RowNumber = "RowNumber";
    public void onFinishCorrectData(int whicpic,int rownumber) {
        Fragment fragment = new FragmentPictureManager();
//        Fragment fragment = FragmentPictureManager.newInstance(whicpic,rownumber);
        Bundle bundle = new Bundle();
        bundle.putInt(WhichClone,whicpic);
        bundle.putInt(RowNumber,rownumber);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().addToBackStack("selectclone").replace(R.id.container, fragment).commit();
    }

    public void onFinishTakingPicture() {
        // Clear ร
        baseApplication.getCloneDataItem().setPicfull(null);
        baseApplication.getCloneDataItem().setPicupper(null);
        baseApplication.getCloneDataItem().setPiclower(null);
        getSupportFragmentManager().popBackStack("selectclone", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
