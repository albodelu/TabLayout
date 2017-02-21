package ac.srikar.tablayout;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A common view pager slider that is inside a container which is commonly used in the
 * Fragments shown in the Main Activity.
 * <p/>
 * This viewpager is indie a pager container and doesn't have dots (unlike the viewpager's
 * shown in the navigation menu activities)
 */
public class ViewPagerInsideContainer {

    /**
     * log cat tag
     */
    private static final String LOG_TAG = ViewPagerInsideContainer.class.getSimpleName();

    /**
     * variable holds a reference to the resources
     */
    private Resources res;

    /**
     * variable holds a reference to the context
     */
    private final Context context;

    /**
     * Variable has reference to the url of the web page
     */
    private final ArrayList<String> clickUrls;

    /**
     * Variable has reference to the image urls array list
     */
    private final ArrayList<String> stringUrl;

    /**
     * Reference to the viewpager variable
     */
    private ViewPager viewPager;

    /**
     * Inflate image slider (View Pager)
     */
    private int[] couponsPagerLayouts;

    /**
     * Default Constructor
     *
     * @param rootView has reference to the rootView of the activity or fragment that has called this class.
     * @param context  has reference to the context of the activity or fragment that called this class.
     */
    public ViewPagerInsideContainer(Context context, View rootView) {
        this.context = context;
        if (this.context != null) {
            res = context.getResources();
        } else {
            Log.i(LOG_TAG, "Context is null");
        }
        // Initialize the array list
        stringUrl = new ArrayList<>();
        clickUrls = new ArrayList<>();
        // Initialize View pager
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
    }

    /**
     * Method creates and shows this viewpager
     */
    public void show() {
        // Initialize the In app browser
        if (context != null) {
            // Initialize In App Browser Class
        }

        // layouts of all sliders
        Log.i(LOG_TAG, "stringUrl Size: " + stringUrl.size());
        couponsPagerLayouts = new int[stringUrl.size()];
        for (int i = 0; i < couponsPagerLayouts.length; i++) {
            couponsPagerLayouts[i] = R.layout.view_pager_inside_container_slider;
        }

        // Set the view page adapter
        CouponsPagerAdapter couponsPagerAdapter = new CouponsPagerAdapter();
        viewPager.setAdapter(couponsPagerAdapter);
        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        viewPager.setOffscreenPageLimit(couponsPagerAdapter.getCount());
        if (res != null) {
            // A little space between pages
            viewPager.setPageMargin(res.getDimensionPixelSize(R.dimen.view_pager_margin));
        } else {
            Log.i(LOG_TAG, "Resource is null");
        }

        //If hardware acceleration is enabled, you should also remove
        // clipping on the rechargeViewPager for its children.
        viewPager.setClipChildren(false);

    }

    /**
     * Method parses JSON data for image url and the web page to show when the image is clicked
     *
     * @param imagesUrlJSONArray JSON Array or sliders
     */
    public void parseJSON(JSONArray imagesUrlJSONArray) throws JSONException {
        for (int i = 0; i < imagesUrlJSONArray.length(); i++) {
            // Initialize Temporary variables
            String imagesUrl = null;
            String clicksUrl = null;
            JSONObject imagesUrlJSONObject = imagesUrlJSONArray.getJSONObject(i);
            if (imagesUrlJSONObject.has(JSONKeys.KEY_SLIDER_IMAGE)) {
                imagesUrl = imagesUrlJSONObject.getString(JSONKeys.KEY_SLIDER_IMAGE);
            }
            if (imagesUrlJSONObject.has(JSONKeys.KEY_SLIDER_LINK)) {
                clicksUrl = imagesUrlJSONObject.getString(JSONKeys.KEY_SLIDER_LINK);
            }
            if (imagesUrl != null) {
                if (!imagesUrl.isEmpty()) {
                    // Remove the dots at the start of the Product Image String
                    while (imagesUrl.charAt(0) == '.') {
                        imagesUrl = imagesUrl.replaceFirst(".", "");
                    }
                    // Replace the spaces in the url with %20 (useful if there is any)
                    imagesUrl = imagesUrl.replaceAll(" ", "%20");
                    Log.i(LOG_TAG, "Images Url: " + imagesUrl);
                    // Add the image url and click url to the array
                    stringUrl.add(imagesUrl);
                    clickUrls.add(clicksUrl);
                }
            }
        }
    }

    /**
     * View pager adapter
     */
    private class CouponsPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(couponsPagerLayouts[position], container, false);
            container.addView(view);

            String sUrl = stringUrl.get(position);
            Log.i(LOG_TAG, "sUrl: " + sUrl);
            // Get the Image Uri
            if (sUrl != null) {
                if (!sUrl.isEmpty()) {
                    SimpleDraweeView miniViewPagerImage = (SimpleDraweeView) view.findViewById(R.id.view_pager_inside_container_image);
                    Uri imagesUri = Uri.parse(context.getString(R.string.server_image_assets) + sUrl);
                    Log.i(LOG_TAG, "View pager image container: " + imagesUri);
                    // Load the Image
                    miniViewPagerImage.setImageURI(imagesUri);
                }
            }
            view.setOnClickListener(this);
            return view;
        }

        @Override
        public int getCount() {
            return couponsPagerLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the current item
            int sliderPosition = viewPager.getCurrentItem();
            // Launch the browser
        }
    }
}
