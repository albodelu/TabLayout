package ac.srikar.tablayout;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A Child Fragment class that shows Fabulous deals in the Local Fragment class.
 */
public class LocalFragment extends Fragment {

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = LocalFragment.class.getSimpleName();

    // UI elements
    private Button selectLocationButton;
    private CoordinatorLayout contentLocalFragment;
    private CoordinatorLayout coordinatorLayout;

    // Utility class
    private LocationDialog locationDialog;
    private ChangePRCVisibility prcVisibility;

    /**
     * Variable has reference to the root view
     */
    private View rootView;

    private BroadcastReceiver locationSelectedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Location is changed using the location dialog
            Log.i(LOG_TAG, "Location Selected in the Location Dialog");
            Log.i(LOG_TAG, "Received Intent: " + intent);
            // Show progress bar and remove content
            prcVisibility.retry();
            // Set up sliders in the local page
            localPagerSlider();
            // Show sliding tabs
            useSlidingTabViewPager();
        }
    };

    public LocalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_local, container, false);
        // Initialize UI elements
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        contentLocalFragment = (CoordinatorLayout) rootView.findViewById(R.id.fragment_local);
        // Initialize Utility class
        prcVisibility = new ChangePRCVisibility(rootView, contentLocalFragment);

        // Initialize Local Page
        initializeLocalPage();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Register to receive message about whether or not update the current fragment
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationSelectedBroadcastReceiver,
                new IntentFilter(Constants.UPDATE_LOCAL_FRAGMENT_LOCATION_CHANGED));
    }

    @Override
    public void onStop() {
        super.onStop();
        // Un register the receiver in the onStop to avoid the memory leaks that arise if you fail
        // to un register the registered receiver or when you try to un register in onDestroy and
        // onDestroy is failed to execute.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(locationSelectedBroadcastReceiver);
    }

    /**
     * Method initializes the view groups in the local fragment
     */
    private void initializeLocalPage() {
        // Get the all the locations
        retreiveLocalLocations();
        // Set up sliders in the local page
        localPagerSlider();
        // Show sliding tabs
        useSlidingTabViewPager();
    }

    /**
     * Method creates an Async Task to retreive locations that will be shown in the alert dialog
     */
    private void retreiveLocalLocations() {
        new PrimaryAsyncTask(getContext(), coordinatorLayout, rootView, contentLocalFragment,
                new PrimaryAsyncTask.AsyncResponseForFragment() {
                    @Override
                    public void primaryAsyncSuccess(String stringResponse) throws JSONException {
                        if (getActivity() != null && isAdded()) {
                            parseLocationsData(stringResponse);
                        }
                    }

                    @Override
                    public void primaryAsyncFailed() {
                        initializeLocalPage();
                    }
                }).executeFragment("deals.php?deal_city");
    }

    /**
     * Method parses locations JSON data
     *
     * @param stringResponse JSON data as String
     */
    private void parseLocationsData(String stringResponse) throws JSONException {
        JSONArray locationsJSONArray = new JSONArray(stringResponse);
        // String array that stores the all location variables
        String[] locationChoicesArray = new String[locationsJSONArray.length()];
        for (int i = 0; i < locationsJSONArray.length(); i++) {
            // Temporary variables
            String localCityName = null;
            JSONObject locationJSONObject = locationsJSONArray.getJSONObject(i);
            if (locationJSONObject.has(JSONKeys.KEY_LOCAL_CITY_NAME)) {
                localCityName = locationJSONObject.getString(JSONKeys.KEY_LOCAL_CITY_NAME);
            }
            if (localCityName != null) {
                if (!localCityName.isEmpty()) {
                    locationChoicesArray[i] = localCityName;
                }
            }
        }
        showLocalDialog(locationChoicesArray);
    }

    private void showLocalDialog(final String[] locationChoicesArray) {
        // Restore user selected Location button text
        locationDialog = new LocationDialog(getContext());
        selectLocationButton = (Button) rootView.findViewById(R.id.select_location_button);
        selectLocationButton.setText(locationDialog.restoreUserLocation());
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDialog.show(selectLocationButton, locationChoicesArray);
            }
        });
    }

    /**
     * Implement the tab layout and view pager
     */
    private void useSlidingTabViewPager() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        BottomSectionsPagerAdapter mBottomSectionsPagerAdapter = new BottomSectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mBottomViewPager = (ViewPager) rootView.findViewById(R.id.local_bottom_pager);
        mBottomViewPager.setOffscreenPageLimit(mBottomSectionsPagerAdapter.getCount());
        mBottomViewPager.setAdapter(mBottomSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mBottomViewPager);
    }

    /**
     * Method retrieves the image links ato show in the sliders
     */
    private void localPagerSlider() {
        new PrimaryAsyncTask(getContext(), coordinatorLayout, rootView, contentLocalFragment,
                new PrimaryAsyncTask.AsyncResponseForFragment() {
                    @Override
                    public void primaryAsyncSuccess(String stringResponse) throws JSONException {
                        if (getActivity() != null && isAdded()) {
                            parseImagesUrl(stringResponse);
                        }
                    }

                    @Override
                    public void primaryAsyncFailed() {
                        initializeLocalPage();
                    }
                }).executeFragment("slider.php?imageslider=Local Deals");
    }

    /**
     * Method parses JSON data for images url
     *
     * @param stringResponse JSON data as String
     */
    private void parseImagesUrl(String stringResponse) throws JSONException {
        JSONArray imagesUrlJSONArray = new JSONArray(stringResponse);
        if (imagesUrlJSONArray.length() == 0) {
            // Remove the image sliders section since there aren't any images to show
            rootView.findViewById(R.id.view_pager_inside_container).setVisibility(View.GONE);
        } else {
            // Initialise the View pager inside container class
            ViewPagerInsideContainer viewPagerInsideContainer = new ViewPagerInsideContainer(
                    getContext(), rootView);
            // parse json data
            viewPagerInsideContainer.parseJSON(imagesUrlJSONArray);
            // Show the sliders
            viewPagerInsideContainer.show();
        }
        // Remove the progress bar and show the content
        prcVisibility.success();
    }

}
