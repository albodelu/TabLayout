package ac.srikar.tablayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * A Child Fragment class that shows Fabulous deals in the Local Fragment class.
 */
public class FabDealsFragment extends Fragment {

    /**
     * log cat tag
     */
    private static final String LOG_TAG = FabDealsFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "fab-deals-section-number";

    /**
     * Variable has reference to the root view
     */
    private View rootView;

    // UI elements
    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView contentFabDeals;

    // Utility class
    private ChangePRCVisibility prcVisibility;

    // UI elements
    private LinearLayout localNoResults;

    /**
     * Variable is used to load new data into the recycler view
     */
    private int fabDealsCount = 0;

    /**
     * Fabulous Local Deals Recycler View Parameters
     */
    private RecyclerView fabDealsRecyclerView;
    private RecyclerView.Adapter fabDealsRVAdapter;

    /**
     * Variable that contains Local Deals Data Array
     */
    private ArrayList<LocalDealsDataFields> fabDealsDataArray;

    /**
     * Name of the city, the user has selected
     */
    private String cityName;

    public FabDealsFragment() {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FabDealsFragment newInstance(int sectionNumber) {
        FabDealsFragment fragment = new FabDealsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fab_deals, container, false);
        // Initialize UI elements
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        contentFabDeals = (NestedScrollView) rootView.findViewById(R.id.content_fab_deals);

        // Initialize the no results layout
        localNoResults = (LinearLayout) rootView.findViewById(R.id.local_no_results);
        // Set visibility to gone
        localNoResults.setVisibility(View.GONE);

        // Initialize Utility class
        prcVisibility = new ChangePRCVisibility(rootView, contentFabDeals);

        cityName = new LocationPrefManager(getContext()).getLocationString();
        Log.i(LOG_TAG, "City Name " + ": " + cityName);
        if (cityName.equals(getString(R.string.select_location_local))) {
            // City is the default name and so far the user hasn't selected any location
            // Set the city name to empty
            cityName = "";
        }

        initializeFabDealsRV();
        return rootView;
    }

    /**
     * Method initializes fab deals fragment
     */
    private void initializeFabDealsRV() {
        InitializeLocalFragment initializeFabDeals = new InitializeLocalFragment(getContext(), rootView,
                coordinatorLayout, contentFabDeals, getString(R.string.fab_deals_category), prcVisibility);
        // Initialize Trending deals
        initializeFabDeals.initializeTrending(getString(R.string.fab_deals_merchant_name));
        // Initialize Recycler view and populates data into it
        initializeFabDeals.initializeRV();
    }
}
