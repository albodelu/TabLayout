package ac.srikar.tablayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class retrieves data array for the child fragments in the local fragment
 */
public class InitializeLocalFragment implements View.OnClickListener {

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = InitializeLocalFragment.class.getSimpleName();

    private final Context context;
    private final View rootView;
    private final ChangePRCVisibility prcVisibility;
    private final CoordinatorLayout coordinatorLayout;
    private final View contentMain;
    private final String categoryName;
    private String cityName;

    /**
     * Variable is used to load new data into the recycler view
     */
    private int localDealsCount = 0;

    /**
     * Other Local Deals Recycler View Parameters
     */
    private RecyclerView localDealsRecyclerView;
    private RecyclerView.Adapter localDealsRVAdapter;

    /**
     * Variable that contains Other Local Deals Data Array
     */
    private ArrayList<LocalDealsDataFields> localDealsDataArray;

    // UI elements
    private LinearLayout localNoResults;

    /**
     * Variable has reference to the local offers id
     */
    private int localOfferId;

    public InitializeLocalFragment(Context context, View rootView,
                                   CoordinatorLayout coordinatorLayout, View contentMain,
                                   String categoryName, ChangePRCVisibility prcVisibility) {
        this.context = context;
        this.rootView = rootView;
        this.coordinatorLayout = coordinatorLayout;
        this.contentMain = contentMain;
        this.categoryName = categoryName;
        this.prcVisibility = prcVisibility;

        // Get city name from the preferences
        if (this.context != null) {
            cityName = new LocationPrefManager(this.context).getLocationString();
            Log.i(LOG_TAG, "City Name " + categoryName + ": " + cityName);
            if (cityName.equals(context.getString(R.string.select_location_local))) {
                // City is the default name and so far the user hasn't selected any location
                // Set the city name to empty
                cityName = "";
            }
        }
        // Initialize the no results layout
        localNoResults = (LinearLayout) rootView.findViewById(R.id.local_no_results);
        // Set visibility to gone
        localNoResults.setVisibility(View.GONE);
    }

    /**
     * Method initializes trending deal by creating an Async task an parsing the data received
     * from the server.
     */
    public void initializeTrending(final String merchantName) {
        // Set listener to the trending layout
        RelativeLayout localTrendingLayout = (RelativeLayout) rootView.findViewById(R.id.local_trending);
        localTrendingLayout.setOnClickListener(this);
        new PrimaryAsyncTask(context, coordinatorLayout, rootView, contentMain,
                new PrimaryAsyncTask.AsyncResponseForFragment() {
                    @Override
                    public void primaryAsyncSuccess(String stringResponse) throws JSONException {
                        parseLocalTrending(stringResponse);
                    }

                    @Override
                    public void primaryAsyncFailed() {
                        initializeTrending(merchantName);
                    }
                }).executeFragment("deals.php?deal_list_city=" + cityName +
                "&deal_list_category=" + categoryName + "&deal_list_merchant=" + merchantName);
    }

    /**
     * Method parses Trending deal data received from the server
     */
    private void parseLocalTrending(String stringResponse) throws JSONException {
        // Initialise Trending UI elements
        TextView trendingDealCategoryTextView = (TextView) rootView.findViewById(R.id.trending_deal_category_name);
        TextView trendingDealMerchantNameTextView = (TextView) rootView.findViewById(R.id.trending_deal_merchant_name);
        TextView trendingDealTitleTextView = (TextView) rootView.findViewById(R.id.trending_deal_title);
        TextView trendingDealExpiryDate = (TextView) rootView.findViewById(R.id.trending_deal_expiry_date);
        // Initialize JSON array
        JSONArray localTrendingJSONArray = new JSONArray(stringResponse);
        // Show the first deal with merchant name
        // No need to calculate the array length (@code localTrendingJSONArray.length())
        int localTrendingArrayLength = localTrendingJSONArray.length() < 1 ? localTrendingJSONArray.length() : 1;
        if (localTrendingArrayLength == 0) {
            rootView.findViewById(R.id.local_trending).setVisibility(View.GONE);
            return;
        }
        // Temporary variables
        String trendingTitle = null;
        String trendingCategory = null;
        String trendingMerchantName = null;
        String trendingExpiryDate = null;
        localOfferId = 0;
        // Only show first deal you get from the server
        // Rest are unnecessary
        JSONObject trendingJSONObject = localTrendingJSONArray.getJSONObject(0);
        if (trendingJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_ID)) {
            localOfferId = trendingJSONObject.getInt(JSONKeys.KEY_LOCAL_DEAL_ID);
        }
        if (trendingJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_TITLE)) {
            trendingTitle = trendingJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_TITLE);
        }
        if (trendingJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_CATEGORY)) {
            trendingCategory = trendingJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_CATEGORY);
        }
        if (trendingJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_MERCHANT_NAME)) {
            trendingMerchantName = trendingJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_MERCHANT_NAME);
        }
        if (trendingJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_EXPIRY_DATE)) {
            trendingExpiryDate = trendingJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_EXPIRY_DATE);
        }
        if (trendingCategory != null) {
            trendingDealCategoryTextView.setText(trendingCategory);
        }
        if (trendingMerchantName != null) {
            trendingDealMerchantNameTextView.setText(trendingMerchantName);
        }
        if (trendingTitle != null) {
            trendingDealTitleTextView.setText(trendingTitle);
        }
        if (trendingExpiryDate != null) {
            if (!trendingExpiryDate.isEmpty()) {
                trendingExpiryDate = new FormatDateCurrency().formatDateForUser(trendingExpiryDate);
                trendingDealExpiryDate.setText(context.getString(R.string.expiry_date_second_title, trendingExpiryDate));
            }
        }
    }

    /**
     * Initialize the Local Deals recycler view by using a vertical
     * linear layout manager and setting an adapter
     */
    public void initializeRV() {
        Log.i(LOG_TAG, "Initialize RV " + categoryName);
        localDealsRecyclerView = (RecyclerView) rootView.findViewById(R.id.othersRecyclerView);
        localDealsRecyclerView.setHasFixedSize(true);
        localDealsRecyclerView.setNestedScrollingEnabled(false);
        // Use a linear layout manager
        LinearLayoutManager localDealsRVLayoutManager = new LinearLayoutManager(context);
        localDealsRecyclerView.setLayoutManager(localDealsRVLayoutManager);
        // Initialize Array List
        localDealsDataArray = new ArrayList<>();
        // Endless recycler view
        /*new RVEndlessScrolling(localDealsRecyclerView, localDealsRVLayoutManager,
                new RVEndlessScrolling.RecyclerViewUpdateInterface() {
                    @Override
                    public void requestToUpdateJSON() {
                        Log.i(LOG_TAG, "Requesting later next elements for " + categoryName);
                        retrieveOthersDataArray();
                    }
                });*/
        // Retrieve the data array for the fragment
        Log.i(LOG_TAG, "Requesting elements for first time for " + categoryName);
        retrieveOthersDataArray();
    }

    /**
     * Method creates an Async task to get the Others data array
     */
    private void retrieveOthersDataArray() {
        Log.i(LOG_TAG, "Retrieve Data Array " + categoryName);
        new PrimaryAsyncTask(context, coordinatorLayout, rootView, contentMain,
                new PrimaryAsyncTask.AsyncResponseForFragment() {
                    @Override
                    public void primaryAsyncSuccess(String stringResponse) throws JSONException {
                        parseLocalDeals(stringResponse);
                    }

                    @Override
                    public void primaryAsyncFailed() {
                        retrieveOthersDataArray();
                    }
                }).executeFragment("deals.php?deal_list_city_mobile=" + cityName +
                "&deal_list_category=" + categoryName + "&limit_result=20&after=" + localDealsCount);
    }

    /**
     * Method parses Others Fragment data
     *
     * @param stringResponse JSON response as string
     */
    private void parseLocalDeals(String stringResponse) throws JSONException {
        Log.i(LOG_TAG, "Parsing Local " + categoryName);
        JSONArray localJSONArray = new JSONArray(stringResponse);
        // If the array length is less than 10 then display to the end of the JSON data or else
        // display 10 items.
        int localArrayLength = localJSONArray.length() <= 20 ? localJSONArray.length() : 20;
        Log.i(LOG_TAG, "Local Array length " + categoryName + ": " + localArrayLength);
        for (int i = 0; i < localArrayLength; i++) {
            Log.i(LOG_TAG, "Local Deals for " + categoryName + "-  Value of i: " + i);
            // Initialize Temporary variables
            int localProductId = 0;
            String localSecondTitle = null;
            String localImageUrlString = null;
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_ID)) {
                localProductId = localJSONObject.getInt(JSONKeys.KEY_LOCAL_DEAL_ID);
            }
            Log.i(LOG_TAG, "Local Deal Product Id: " + localProductId);
            if (localJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_CATEGORY)) {
                localSecondTitle = localJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_CATEGORY);
            }
            Log.i(LOG_TAG, "Local Deal Second Title: " + localSecondTitle);
            if (localJSONObject.has(JSONKeys.KEY_LOCAL_DEAL_IMAGE)) {
                localImageUrlString = localJSONObject.getString(JSONKeys.KEY_LOCAL_DEAL_IMAGE);
            }
            Log.i(LOG_TAG, "Local Deal Image: " + localImageUrlString);

            if (localImageUrlString != null) {
                if (!localImageUrlString.isEmpty()) {
                    // Remove the dots at the start of the Product Image String
                    while (localImageUrlString.charAt(0) == '.') {
                        localImageUrlString = localImageUrlString.replaceFirst(".", "");
                    }
                    // Replace the spaces in the url with %20 (useful if there is any)
                    localImageUrlString = localImageUrlString.replaceAll(" ", "%20");
                }
            }
            Log.i(LOG_TAG, "Local Deal Image: " + localImageUrlString);

            LocalDealsDataFields localDealsData = new LocalDealsDataFields();
            localDealsData.setLocalDealId(localProductId);
            localDealsData.setLocalDealSecondTitle(localSecondTitle);
            localDealsData.setLocalDealImage(localImageUrlString);

            localDealsDataArray.add(localDealsData);
        }

        for (int j = 0; j < localDealsDataArray.size(); j++) {
            Log.i(LOG_TAG, "parseLocalDeals, Fab Deal Title: " +
                    localDealsDataArray.get(j).getLocalDealId());
            Log.i(LOG_TAG, "parseLocalDeals, Fab Deal Second Title: " +
                    localDealsDataArray.get(j).getLocalDealSecondTitle());
            Log.i(LOG_TAG, "parseLocalDeals, Fab Deal Image Link: " +
                    localDealsDataArray.get(j).getLocalDealImage());
        }

        // Initialize the Local Deals List only once and notify the adapter that data set has changed
        // from second time. If you initializeRV the localDealsRVAdapter at an early instance and only
        // use the notifyDataSetChanged method here then the adapter doesn't update the data. This is
        // because the adapter won't update items if the number of previously populated items is zero.
        Log.i(LOG_TAG, "Local Deals Count of " + categoryName +
                " Before Adding: " + localDealsCount);
        if (localDealsCount == 0) {
            if (localArrayLength != 0) {
                // Populate the Local Deals list
                // Specify an adapter
                localDealsRVAdapter = new OthersAdapter(context, localDealsDataArray, categoryName);
                localDealsRecyclerView.setAdapter(localDealsRVAdapter);
            } else {
                // localArrayLength is 0; which means there are no rv elements to show.
                // So, remove the layout
                contentMain.setVisibility(View.GONE);
                // Show no results layout
                showNoResultsIfNoData(localArrayLength);
            }
        } else {
            // Notify the adapter that data set has changed
            localDealsRVAdapter.notifyDataSetChanged();
        }
        // Increase the count since parsing the first set of results are returned
        localDealsCount = localDealsCount + 20;
        // Remove the progress bar and show the content
        prcVisibility.success();
    }

    /**
     * Method shows no results layout if the total number of local deals is zero
     *
     * @param localArrayLength Length of the local deals array
     */
    private void showNoResultsIfNoData(int localArrayLength) {
        Log.i(LOG_TAG, "Local Array Length " + categoryName + ": " + localArrayLength);
        if (localArrayLength == 0) {
            localNoResults.setVisibility(View.VISIBLE);
        } else {
            localNoResults.setVisibility(View.GONE);
        }
    }

    /**
     * Method listens to click events of trending layout
     */
    @Override
    public void onClick(View view) {
        // Open Local Deals
    }
}
