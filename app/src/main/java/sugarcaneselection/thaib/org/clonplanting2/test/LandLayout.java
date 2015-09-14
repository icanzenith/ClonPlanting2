package sugarcaneselection.thaib.org.clonplanting2.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jitpakorn on 5/27/15 AD.
 */
public class LandLayout extends ViewGroup {


    public LandLayout(Context context) {
        super(context);
    }

    public LandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LandLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
