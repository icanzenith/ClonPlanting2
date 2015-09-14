package icanstudio.com.newsfeed;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Toast;


public class NewsFeedActivity extends ActionBarActivity {

    //    @CoordinatorLayout.DefaultBehavior(FloatingActionButton.Behavior.class)
    FloatingActionButton f;

    //    @CoordinatorLayout.DefaultBehavior(CoordinatorLayout.Behavior.class)
    CoordinatorLayout c;

    RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        rotateAnimation = new RotateAnimation(0f, 45f);

        c = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        f = (FloatingActionButton) findViewById(R.id.fabmain);
        final View shape = findViewById(R.id.testReveal);

        f.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                shape.setVisibility(View.VISIBLE);
                int[] location = new int[2];
                f.getLocationOnScreen(location);
                f.setAnimation(rotateAnimation);
                f.startAnimation(rotateAnimation);
                Animator animator = ViewAnimationUtils.createCircularReveal(shape, location[0], location[1], 4f, (float) Math.hypot(shape.getWidth(), shape.getHeight()));
                animator.start();
//                ShowSnackBar();
                AddNewPost();
            }
        });


    }

    private void AddNewPost() {

    }

    private void ShowSnackBar() {
        Snackbar
                .make(c, "Hello", Snackbar.LENGTH_LONG)
                .setAction("Attack", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NewsFeedActivity.this, "Snack Attack", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
