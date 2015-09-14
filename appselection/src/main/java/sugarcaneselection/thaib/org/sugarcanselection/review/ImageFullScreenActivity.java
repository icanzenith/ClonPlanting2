package sugarcaneselection.thaib.org.sugarcanselection.review;

import sugarcaneselection.thaib.org.sugarcanselection.Item.ImageFullScreenViewItem;
import sugarcaneselection.thaib.org.sugarcanselection.database.Columns;
import sugarcaneselection.thaib.org.sugarcanselection.review.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import sugarcaneselection.thaib.org.sugarcanselection.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ImageFullScreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_FULLSCREEN;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    private ArrayList<ImageFullScreenViewItem> imageItem;

    private TextView imagePart;
    private String CloneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_full_screen);
        getActionBar().hide();

        CloneCode = getIntent().getStringExtra("CloneCode");

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
//        final View contentView = findViewById(R.id.fullscreen_content);
        ViewPager viewPager = (ViewPager) findViewById(R.id.fullscreen_ViewPager);
        imageItem = getImageFullScreenItem();
        FullImagePagerAdapter adapter = new FullImagePagerAdapter(this, R.id.tvWhichpart,imageItem);

        viewPager.setAdapter(adapter);
        imagePart = (TextView) findViewById(R.id.tvWhichpart);
        imagePart.setText(imageItem.get(0).getFlag());
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, viewPager, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                imagePart.setText(imageItem.get(position).getFlag());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

    }

    private ArrayList<ImageFullScreenViewItem> getImageFullScreenItem() {

        ArrayList<ImageFullScreenViewItem> data = new ArrayList<>();


        Uri uri = Uri.parse(getResources().getString(R.string.URI_MYCLONE));
        String[] projection = {};
        String sortOrder = null;
        String selection = Columns.CLONECODE + " = ?";
        String[] selectionArgs = {CloneCode};
        ContentResolver r = getContentResolver();
        Cursor c = r.query(uri, projection, selection, selectionArgs, sortOrder);
        while (c != null && c.moveToNext()) {

            String full = c.getString(c.getColumnIndex(Columns.FULLPICURL));
            String lower = c.getString(c.getColumnIndex(Columns.LOWERPICURL));
            String upper = c.getString(c.getColumnIndex(Columns.UPPERPICURL));

            if (full != null) {
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(full);
                Item.setFlag("ภาพเต็ม");
                data.add(Item);
            }else{
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(null);
                Item.setFlag("ภาพเต็ม");
                data.add(Item);
            }
            if (lower!=null) {
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(lower);
                Item.setFlag("ภาพส่วนล่าง");
                data.add(Item);
            }else{
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(null);
                Item.setFlag("ภาพส่วนล่าง");
                data.add(Item);
            }
            if (upper !=null ){
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(upper);
                Item.setFlag("ภาพส่วนบน");
                data.add(Item);
            }else{
                ImageFullScreenViewItem Item = new ImageFullScreenViewItem();
                Item.setPath(null);
                Item.setFlag("ภาพส่วนบน");
                data.add(Item);
            }

        }

        c.close();

        return data;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);

            }

            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


}


