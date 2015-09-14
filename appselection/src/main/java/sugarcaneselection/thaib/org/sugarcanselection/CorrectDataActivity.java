package sugarcaneselection.thaib.org.sugarcanselection;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Vector;

import sugarcaneselection.thaib.org.sugarcanselection.freetest.TestFragment;


public class CorrectDataActivity extends ActionBarActivity {

    ViewPager mViewPager;
    PagerTabStrip mPagerTabStrip;
    MyViewPagerAdapter mAdapter;
    BaseApplication b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = (BaseApplication) getApplication();

        setContentView(R.layout.activity_correct_data);
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);


        Vector<Fragment> vector = new Vector<>();
        vector.add(TestFragment.newInstance("1", null));
        vector.add(TestFragment.newInstance("2", null));
        vector.add(TestFragment.newInstance("3", null));
        vector.add(TestFragment.newInstance("4", null));
        vector.add(TestFragment.newInstance("5", null));
        vector.add(TestFragment.newInstance("6", null));

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), vector);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        //Don't use super.onBackPress to protect finish
        //TODO Create confirm exit dialog before exit. And Clear TempCloneData in BaseApplication
        //
        Log.d("Tag", "OnBackPress");
    }

    private void getCloneListData(String FamilyName) {
        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = null;
        String selection = "familycode = ";
        String[] selectionArgs = {FamilyName};
        String sortOrder = null;
        Cursor c = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        Vector<Fragment> vector;

        MyViewPagerAdapter(FragmentManager fm, Vector<Fragment> vector) {
            super(fm);
            this.vector = vector;
        }

        @Override
        public Fragment getItem(int position) {
            return vector.get(position);
        }

        @Override
        public int getCount() {
            return vector.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Fragement : " + (position + 1);
        }
    }
}
