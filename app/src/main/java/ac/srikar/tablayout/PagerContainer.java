package ac.srikar.tablayout;


import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Pager Container that has an Image Slider (View Pager) which is shown at the top
 * in the Coupons Fragment.
 */
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    /**
     * Boolean tells whether the viewpager needs to be re drawn
     */
    boolean mPagerNeedsRedraw = false;

    /**
     * Variable has reference to the view pager
     */
    private ViewPager mViewPager;

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //Disable clipping of children so non-selected pages are visible
        setClipChildren(false);

        //Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        //You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try {
            mViewPager = (ViewPager) getChildAt(0);
            mViewPager.addOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int) ev.getX();
                mInitialTouch.y = (int) ev.getY();
            default:
                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }

        return mViewPager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mPagerNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        LayoutParams viewPagerLayoutParams = (LayoutParams) mViewPager.getLayoutParams();
        if (position == 0) {
            // Set the gravity to start.
            // If set to center horizontal, so much space will be seen on the left side.
            viewPagerLayoutParams.gravity = Gravity.START;
            // Set the margin so that the view pager doesn't stick to the left screen.
            viewPagerLayoutParams.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.recharge_view_pager_margin),
                    0, 0, 0);
        } else if (position == mViewPager.getChildCount()) {
            // Set the gravity to end.
            // If set to center horizontal, so much space will be seen on the right side.
            viewPagerLayoutParams.gravity = Gravity.END;
            // Set the margin so that the view pager doesn't stick to the right screen.
            viewPagerLayoutParams.setMargins(0, 0,
                    getResources().getDimensionPixelSize(R.dimen.recharge_view_pager_margin), 0);
        } else {
            // Set the Gravity to center horizontal so that the view pager is in the middle.
            viewPagerLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            // Remove the excess margin so that the previous view pager
            viewPagerLayoutParams.setMargins(0, 0, 0, 0);
        }
        mViewPager.requestLayout();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mPagerNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
