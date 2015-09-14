package sugarcaneselection.thaib.org.sugarcanselection;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Jitpakorn on 1/21/15 AD.
 */
public class TestMyViewPager extends ViewPager {

    public TestMyViewPager(Context context) {
        super(context);
    }

    public TestMyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() >= 2) {
            return super.onTouchEvent(ev);
        } else return false;

    }


}
