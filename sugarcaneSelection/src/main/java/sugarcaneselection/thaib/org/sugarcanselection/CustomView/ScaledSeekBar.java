package sugarcaneselection.thaib.org.sugarcanselection.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;

/**
 * Created by Jitpakorn on 1/27/15 AD.
 */
public class ScaledSeekBar extends AbsSeekBar {

    private Drawable mRuler;

    public ScaledSeekBar(Context context) {
        super(context);
    }

    public ScaledSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScaledSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawScaledTrack(canvas);
    }

    private void onDrawScaledTrack(Canvas canvas) {

        mRuler.draw(canvas);
        canvas.restore();

    }

}
